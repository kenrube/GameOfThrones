package org.odddev.gameofthrones.houses;

import android.content.Context;

import org.odddev.gameofthrones.core.di.Injector;
import org.odddev.gameofthrones.core.rx.ISchedulersResolver;
import org.odddev.gameofthrones.core.storage.StorageManager;
import org.odddev.gameofthrones.splash.data.CharacterRow;
import org.odddev.gameofthrones.splash.data.HouseRow;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * @author kenrube
 * @date 16.10.16
 */

public class HouseProvider implements IHouseProvider {

    @Inject
    Context mContext;

    @Inject
    ISchedulersResolver mSchedulersResolver;

    @Inject
    StorageManager mStorageManager;

    public HouseProvider() {
        Injector.getAppComponent().inject(this);
    }

    @Override
    public Observable<List<CharacterRow>> loadCharacters(int houseId) {
        HouseRow house = mStorageManager.loadHouse(houseId);
        List<CharacterRow> characters = new ArrayList<>();
        for (int i = 0; i < house.charactersList.size(); i++) {
            CharacterRow character = mStorageManager.loadCharacter(house.charactersList.get(i));
            characters.add(character);
        }
        return Observable.just(characters);
    }
}
