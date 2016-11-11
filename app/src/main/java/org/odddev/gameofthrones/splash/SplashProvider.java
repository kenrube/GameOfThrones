package org.odddev.gameofthrones.splash;


import android.content.Context;
import android.text.TextUtils;

import org.odddev.gameofthrones.BuildConfig;
import org.odddev.gameofthrones.core.di.Injector;
import org.odddev.gameofthrones.core.network.IServerApi;
import org.odddev.gameofthrones.core.rx.ISchedulersResolver;
import org.odddev.gameofthrones.splash.data.Character;
import org.odddev.gameofthrones.splash.data.CharacterRow;
import org.odddev.gameofthrones.splash.data.CharacterRowEntity;
import org.odddev.gameofthrones.splash.data.House;
import org.odddev.gameofthrones.splash.data.HouseItem;
import org.odddev.gameofthrones.splash.data.HouseRow;
import org.odddev.gameofthrones.splash.data.HouseRowEntity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.requery.Persistable;
import io.requery.rx.SingleEntityStore;
import rx.Observable;

/**
 * @author kenrube
 * @date 16.10.16
 */

public class SplashProvider implements ISplashProvider {

    private static final String HOUSES_PATH = "houses/";
    private static final String CHARACTER_PATH = "characters/";
    private static final int MAX_CONCURRENT = 4;

    @Inject
    ISchedulersResolver mSchedulersResolver;

    @Inject
    IServerApi mServerApi;

    @Inject
    Context mContext;

    @Inject
    SingleEntityStore<Persistable> mRequery;

    public SplashProvider() {
        Injector.getAppComponent().inject(this);
    }

    @Override
    public Observable<Boolean> isDbEmpty() {
        return Observable.just(
                mRequery
                        .select(HouseRow.class)
                        .get()
                        .firstOrNull() == null);
    }

    public Observable<Integer> getCharactersCount() {
        return Observable.combineLatest(
                mServerApi.getHouse(HouseItem.LANNISTER),
                mServerApi.getHouse(HouseItem.STARK),
                mServerApi.getHouse(HouseItem.TARGARYEN),
                (houseLannister, houseStark, houseTargaryen) ->
                        houseLannister.swornMembers.size()
                                + houseStark.swornMembers.size()
                                + houseTargaryen.swornMembers.size()
                )
                .compose(mSchedulersResolver.applyDefaultSchedulers());
    }

    @Override
    public Observable<CharacterRow> loadCharacters() {
        return Observable.combineLatest(
                mServerApi.getHouse(HouseItem.LANNISTER),
                mServerApi.getHouse(HouseItem.STARK),
                mServerApi.getHouse(HouseItem.TARGARYEN),
                (houseLannister, houseStark, houseTargaryen) -> {
                    List<House> houses = new ArrayList<>();
                    houses.add(houseLannister);
                    houses.add(houseStark);
                    houses.add(houseTargaryen);
                    return houses;
                })
                .flatMap(Observable::from)
                .map(house -> saveHouseToDb(house).getCharactersList())
                .flatMap(Observable::from)
                .flatMap(characterId -> mServerApi.getCharacter(characterId), MAX_CONCURRENT)
                .map(this::saveCharacterToDb)
                .compose(mSchedulersResolver.applyDefaultSchedulers());
    }

    private HouseRowEntity saveHouseToDb(House house) {
        HouseRowEntity houseRow = new HouseRowEntity();
        houseRow.setId(houseIdFromUrl(house.url));
        houseRow.setWords(house.words);
        List<Integer> charactersList = new ArrayList<>();
        for (int i = 0; i < house.swornMembers.size(); i++) {
            charactersList.add(characterIdFromUrl(house.swornMembers.get(i)));
        }
        houseRow.setCharactersList(charactersList);
        mRequery
                .upsert(houseRow)
                .subscribe();
        return houseRow;
    }

    private CharacterRowEntity saveCharacterToDb(Character character) {
        CharacterRowEntity characterRow = new CharacterRowEntity();
        characterRow.setId(characterIdFromUrl(character.url));
        characterRow.setName(character.name);
        characterRow.setBorn(character.born);
        characterRow.setDied(character.died);
        characterRow.setTitles(character.titles);
        characterRow.setAliases(character.aliases);
        characterRow.setFatherId(characterIdFromUrl(character.father));
        characterRow.setMotherId(characterIdFromUrl(character.mother));
        characterRow.setTvSeries(character.tvSeries);
        mRequery
                .upsert(characterRow)
                .subscribe();
        return characterRow;
    }

    private int houseIdFromUrl(String url) {
        return Integer.parseInt(
                url.substring(BuildConfig.API_URL.length() + HOUSES_PATH.length()));
    }

    private int characterIdFromUrl(String url) {
        if (!TextUtils.isEmpty(url)) {
            return Integer.parseInt(
                    url.substring(BuildConfig.API_URL.length() + CHARACTER_PATH.length()));
        } else {
            return -1;
        }
    }
}
