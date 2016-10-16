package org.odddev.gameofthrones.character;

import android.text.TextUtils;

import org.odddev.gameofthrones.BuildConfig;
import org.odddev.gameofthrones.core.di.Injector;
import org.odddev.gameofthrones.core.network.IServerApi;
import org.odddev.gameofthrones.core.rx.ISchedulersResolver;
import org.odddev.gameofthrones.core.storage.StorageManager;
import org.odddev.gameofthrones.splash.data.Character;
import org.odddev.gameofthrones.splash.data.CharacterRow;

import javax.inject.Inject;

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
    StorageManager mStorageManager;

    public CharacterProvider() {
        Injector.getAppComponent().inject(this);
    }

    @Override
    public Observable<String> loadWords(int houseId) {
        if (mStorageManager.loadHouse(houseId) != null) {
            return Observable.just(mStorageManager.loadHouse(houseId).words);
        } else {
            return Observable.just(null);
        }
    }

    @Override
    public Observable<CharacterRow> loadCharacter(int characterId) {
        if (mStorageManager.loadCharacter(characterId) != null) {
            return Observable.just(mStorageManager.loadCharacter(characterId));
        } else {
            return mServerApi
                    .getCharacter(characterId)
                    .map(this::saveCharacterToDb)
                    .compose(mSchedulersResolver.applyDefaultSchedulers());
        }
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

    private int characterIdFromUrl(String url) {
        if (!TextUtils.isEmpty(url)) {
            return Integer.parseInt(
                    url.substring(BuildConfig.API_URL.length() + CHARACTER_PATH.length()));
        } else {
            return -1;
        }
    }
}
