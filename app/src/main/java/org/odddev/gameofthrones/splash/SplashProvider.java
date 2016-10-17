package org.odddev.gameofthrones.splash;


import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;

import org.odddev.gameofthrones.BuildConfig;
import org.odddev.gameofthrones.core.di.Injector;
import org.odddev.gameofthrones.core.network.IServerApi;
import org.odddev.gameofthrones.core.rx.ISchedulersResolver;
import org.odddev.gameofthrones.core.storage.StorageManager;
import org.odddev.gameofthrones.splash.data.Character;
import org.odddev.gameofthrones.splash.data.CharacterRow;
import org.odddev.gameofthrones.splash.data.House;
import org.odddev.gameofthrones.splash.data.HouseItem;
import org.odddev.gameofthrones.splash.data.HouseRow;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import timber.log.Timber;

/**
 * @author kenrube
 * @date 16.10.16
 */

public class SplashProvider implements ISplashProvider {

    private static final String HOUSES_PATH = "houses/";
    private static final String CHARACTER_PATH = "characters/";

    @Inject
    ISchedulersResolver mSchedulersResolver;

    @Inject
    IServerApi mServerApi;

    @Inject
    Context mContext;

    @Inject
    StorageManager mStorageManager;

    public SplashProvider() {
        Injector.getAppComponent().inject(this);
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
                .map(house -> saveHouseToDb(house).charactersList)
                .flatMap(Observable::from)
                .flatMap(characterId -> mServerApi.getCharacter(characterId), 4)
                .map(this::saveCharacterToDb)
                .compose(mSchedulersResolver.applyDefaultSchedulers());
    }

    private HouseRow saveHouseToDb(House house) {
        HouseRow houseRow = new HouseRow();
        houseRow.id = houseIdFromUrl(house.url);
        houseRow.words = house.words;
        List<Integer> charactersList = new ArrayList<>();
        for (int i = 0; i < house.swornMembers.size(); i++) {
            charactersList.add(characterIdFromUrl(house.swornMembers.get(i)));
        }
        houseRow.charactersList = charactersList;
        mStorageManager.saveHouse(houseRow);
        return houseRow;
    }

    private CharacterRow saveCharacterToDb(Character character) {
        CharacterRow characterRow = new CharacterRow();
        characterRow.id = characterIdFromUrl(character.url);
        characterRow.name = character.name;
        characterRow.born = character.born;
        characterRow.died = character.died;
        characterRow.titles = character.titles;
        characterRow.aliases = character.aliases;
        characterRow.fatherId = characterIdFromUrl(character.father);
        characterRow.motherId = characterIdFromUrl(character.mother);
        characterRow.tvSeries = character.tvSeries;
        mStorageManager.saveCharacter(characterRow);
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
