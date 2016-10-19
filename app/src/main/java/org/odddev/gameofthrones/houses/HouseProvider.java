package org.odddev.gameofthrones.houses;

import android.content.Context;

import org.odddev.gameofthrones.core.di.Injector;
import org.odddev.gameofthrones.core.rx.ISchedulersResolver;
import org.odddev.gameofthrones.splash.data.CharacterRow;
import org.odddev.gameofthrones.splash.data.CharacterRowEntity;
import org.odddev.gameofthrones.splash.data.HouseRow;
import org.odddev.gameofthrones.splash.data.HouseRowEntity;

import java.util.List;

import javax.inject.Inject;

import io.requery.Persistable;
import io.requery.rx.SingleEntityStore;
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
    SingleEntityStore<Persistable> mRequery;

    public HouseProvider() {
        Injector.getAppComponent().inject(this);
    }

    @Override
    public Observable<List<CharacterRow>> loadCharacters(int houseId) {
        return mRequery
                .select(HouseRow.class)
                .where(HouseRowEntity.ID.equal(houseId))
                .get()
                .toObservable()
                .map(HouseRow::getCharactersList)
                .flatMap(Observable::from)
                .flatMap(characterId -> mRequery
                        .select(CharacterRow.class)
                        .where(CharacterRowEntity.ID.equal(characterId))
                        .get()
                        .toObservable())
                .toList()
                .compose(mSchedulersResolver.applyDefaultSchedulers());
    }
}
