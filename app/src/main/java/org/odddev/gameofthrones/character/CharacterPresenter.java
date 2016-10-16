package org.odddev.gameofthrones.character;

import org.odddev.gameofthrones.core.di.Injector;
import org.odddev.gameofthrones.core.layers.presenter.Presenter;
import org.odddev.gameofthrones.splash.data.CharacterRow;

import javax.inject.Inject;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * @author kenrube
 * @date 16.10.16
 */

public class CharacterPresenter extends Presenter<ICharacterView> {

    private Subscription mWordsLoadingSubscription;
    private Subscription mFatherLoadingSubscription;
    private Subscription mMotherLoadingSubscription;

    @Inject
    CompositeSubscription mCompositeSubscription;

    @Inject
    ICharacterProvider mProvider;

    CharacterPresenter() {
        Injector.getAppComponent().inject(this);
    }

    void loadWords(int houseId) {
        mWordsLoadingSubscription = mProvider
                .loadWords(houseId)
                .subscribe(
                        this::showWords,
                        throwable -> {
                            Timber.e(throwable, throwable.getLocalizedMessage());
                            showError(throwable.getLocalizedMessage());
                        },
                        () -> mCompositeSubscription.remove(mWordsLoadingSubscription)
                );
        mCompositeSubscription.add(mWordsLoadingSubscription);
    }

    void loadFather(int fatherId) {
        mFatherLoadingSubscription = mProvider
                .loadCharacter(fatherId)
                .subscribe(
                        this::showFather,
                        throwable -> {
                            Timber.e(throwable, throwable.getLocalizedMessage());
                            showError(throwable.getLocalizedMessage());
                        },
                        () -> mCompositeSubscription.remove(mFatherLoadingSubscription)
                );
        mCompositeSubscription.add(mFatherLoadingSubscription);
    }

    void loadMother(int motherId) {
        mMotherLoadingSubscription = mProvider
                .loadCharacter(motherId)
                .subscribe(
                        this::showMother,
                        throwable -> {
                            Timber.e(throwable, throwable.getLocalizedMessage());
                            showError(throwable.getLocalizedMessage());
                        },
                        () -> mCompositeSubscription.remove(mMotherLoadingSubscription)
                );
        mCompositeSubscription.add(mMotherLoadingSubscription);
    }

    private void showWords(String words) {
        for (ICharacterView view : getViews()) {
            view.showWords(words);
        }
    }

    private void showFather(CharacterRow father) {
        for (ICharacterView view : getViews()) {
            view.showFather(father);
        }
    }

    private void showMother(CharacterRow mother) {
        for (ICharacterView view : getViews()) {
            view.showMother(mother);
        }
    }

    private void showError(String error) {
        for (ICharacterView view : getViews()) {
            view.showError(error);
        }
    }

    @Override
    protected void onDestroy() {
        mCompositeSubscription.unsubscribe();
        super.onDestroy();
    }
}
