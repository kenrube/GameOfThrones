package org.odddev.gameofthrones.splash;

import org.odddev.gameofthrones.R;
import org.odddev.gameofthrones.core.di.Injector;
import org.odddev.gameofthrones.core.layers.presenter.PresenterManager;
import org.odddev.gameofthrones.core.network.INetworkChecker;
import org.odddev.gameofthrones.databinding.SplashActivityBinding;
import org.odddev.gameofthrones.houses.HousesActivity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

/**
 * @author kenrube
 * @date 16.10.16
 */

public class SplashActivity extends AppCompatActivity implements ISplashView {

    private static final int PRESENTER_ID = SplashPresenter.class.getSimpleName().hashCode();
    private static final int TIMER_DURATION = 3000;

    private SplashActivityBinding mBinding;
    private SplashPresenter mPresenter;

    private boolean dataLoaded;
    private boolean timerEnded;

    @Inject
    INetworkChecker mNetworkChecker;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.splash_activity);

        Injector.getAppComponent().inject(this);

        startTimer();

        mPresenter = PresenterManager.getPresenter(PRESENTER_ID, SplashPresenter::new);
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.attachView(this);

        if (!mNetworkChecker.isConnected()) {
            showError(getString(R.string.splash_error_connection));
            mBinding.setShowProgress(false);
        } else {
            checkDbEmpty();
        }
    }

    @Override
    public void onStop() {
        mPresenter.detachView(this);
        super.onStop();
    }

    private void startTimer() {
        new Handler().postDelayed(() -> {
            if (dataLoaded) {
                openHousesActivity();
            }
            timerEnded = true;
        }, TIMER_DURATION);
    }

    private void checkDbEmpty() {
        mPresenter.checkDbEmpty();
    }

    @Override
    public void reportDbState(boolean isEmpty) {
        if (!isEmpty) {
            dataLoaded = true;
            mBinding.setShowProgress(false);
        } else {
            mPresenter.loadData();
            mBinding.setShowProgress(true);
        }
    }

    @Override
    public void showCharacterLoaded(int charactersLoaded, int charactersCount) {
        mBinding.setCharactersCount(charactersCount);
        mBinding.setCharactersLoaded(charactersLoaded);
        if (charactersLoaded == charactersCount) {
            dataLoaded = true;
            if (timerEnded) {
                openHousesActivity();
            }
        }
    }

    private void openHousesActivity() {
        HousesActivity.start(this);
        overridePendingTransition(0, 0);
    }

    @Override
    public void showError(String error) {
        Snackbar.make(mBinding.getRoot(), error, Snackbar.LENGTH_LONG).show();
    }
}
