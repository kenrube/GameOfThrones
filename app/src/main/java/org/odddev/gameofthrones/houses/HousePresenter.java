package org.odddev.gameofthrones.houses;

import org.odddev.gameofthrones.core.di.Injector;
import org.odddev.gameofthrones.core.layers.presenter.Presenter;
import org.odddev.gameofthrones.splash.data.CharacterRow;

import java.util.List;

import javax.inject.Inject;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * @author kenrube
 * @date 16.10.16
 */

public class HousePresenter extends Presenter<IHouseView> {

    private Subscription mDataLoadingSubscription;

    @Inject
    CompositeSubscription mCompositeSubscription;

    @Inject
    IHouseProvider mProvider;

    HousePresenter() {
        Injector.getAppComponent().inject(this);
    }

    void loadData(int houseId) {
        mDataLoadingSubscription = mProvider
                .loadCharacters(houseId)
                .subscribe(
                        this::showCharacters,
                        throwable -> {
                            Timber.e(throwable, throwable.getLocalizedMessage());
                            showError(throwable.getLocalizedMessage());
                        },
                        () -> mCompositeSubscription.remove(mDataLoadingSubscription)
                );
        mCompositeSubscription.add(mDataLoadingSubscription);
    }

    private void showCharacters(List<CharacterRow> characters) {
        for (IHouseView view : getViews()) {
            view.showCharacters(characters);
        }
    }

    private void showError(String error) {
        for (IHouseView view : getViews()) {
            view.showError(error);
        }
    }

    @Override
    protected void onDestroy() {
        mCompositeSubscription.unsubscribe();
        super.onDestroy();
    }
}
