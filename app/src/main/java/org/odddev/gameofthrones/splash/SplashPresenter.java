package org.odddev.gameofthrones.splash;

import org.odddev.gameofthrones.core.di.Injector;
import org.odddev.gameofthrones.core.layers.presenter.Presenter;

import android.content.Context;

import javax.inject.Inject;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * @author kenrube
 * @date 16.10.16
 */

public class SplashPresenter extends Presenter<ISplashView> {

    private Subscription mDataLoadingSubscription;

    private int mCharactersCount;
    private int mCharactersLoaded;

    @Inject
    CompositeSubscription mCompositeSubscription;

    @Inject
    ISplashProvider mProvider;

    @Inject
    Context mContext;

    SplashPresenter() {
        Injector.getAppComponent().inject(this);
    }

    void loadData() {
        mDataLoadingSubscription = mProvider
                .getCharactersCount()
                .subscribe(
                        charactersCount -> {
                            mCharactersCount = charactersCount;
                            mProvider
                                    .loadCharacters()
                                    .subscribe(
                                            character -> showCharacterLoaded(),
                                            throwable -> {
                                                Timber.e(throwable, throwable.getLocalizedMessage());
                                                showError(throwable.getLocalizedMessage());
                                            }

                                    );
                        },
                        throwable -> {
                            Timber.e(throwable, throwable.getLocalizedMessage());
                            showError(throwable.getLocalizedMessage());
                        }
                );
        mCompositeSubscription.add(mDataLoadingSubscription);
    }

    private void showCharacterLoaded() {
        mCharactersLoaded++;
        for (ISplashView view : getViews()) {
            view.showCharacterLoaded(mCharactersLoaded, mCharactersCount);
        }
    }

    private void showError(String error) {
        for (ISplashView view : getViews()) {
            view.showError(error);
        }
    }

    @Override
    protected void onDestroy() {
        mCompositeSubscription.unsubscribe();
        super.onDestroy();
    }
}
