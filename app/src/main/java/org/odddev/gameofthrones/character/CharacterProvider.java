package org.odddev.gameofthrones.character;

import android.text.TextUtils;

import org.odddev.gameofthrones.BuildConfig;
import org.odddev.gameofthrones.core.di.Injector;
import org.odddev.gameofthrones.core.network.IServerApi;
import org.odddev.gameofthrones.core.rx.ISchedulersResolver;
import org.odddev.gameofthrones.splash.data.Character;
import org.odddev.gameofthrones.splash.data.CharacterRow;
import org.odddev.gameofthrones.splash.data.CharacterRowEntity;
import org.odddev.gameofthrones.splash.data.HouseRow;
import org.odddev.gameofthrones.splash.data.HouseRowEntity;

import javax.inject.Inject;

import io.requery.Persistable;
import io.requery.rx.SingleEntityStore;
import rx.Observable;

/**
 * @author kenrube
 * @date 16.10.16
 */

public class CharacterProvider implements ICharacterProvider {

    private static final String CHARACTER_PATH = "characters/";

    @Inject
    ISchedulersResolver mSchedulersResolver;

    @Inject
    IServerApi mServerApi;

    @Inject
    SingleEntityStore<Persistable> mRequery;

    public CharacterProvider() {
        Injector.getAppComponent().inject(this);
    }

    @Override
    public Observable<String> loadWords(int houseId) {
        return mRequery
                .select(HouseRow.class)
                .where(HouseRowEntity.ID.equal(houseId))
                .get()
                .toObservable()
                .map(HouseRow::getWords)
                .compose(mSchedulersResolver.applyDefaultSchedulers());
    }

    @Override
    public Observable<CharacterRow> loadCharacter(int characterId) {
        CharacterRow character = mRequery
                .select(CharacterRow.class)
                .where(CharacterRowEntity.ID.equal(characterId))
                .get()
                .firstOrNull();
        if (character != null) {
            return Observable.just(character);
        } else {
            return mServerApi
                    .getCharacter(characterId)
                    .map(this::saveCharacterToDb)
                    .compose(mSchedulersResolver.applyDefaultSchedulers());
        }
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

    private int characterIdFromUrl(String url) {
        if (!TextUtils.isEmpty(url)) {
            return Integer.parseInt(
                    url.substring(BuildConfig.API_URL.length() + CHARACTER_PATH.length()));
        } else {
            return -1;
        }
    }
}
