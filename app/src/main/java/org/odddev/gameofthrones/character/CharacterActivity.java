package org.odddev.gameofthrones.character;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;

import org.odddev.gameofthrones.R;
import org.odddev.gameofthrones.core.di.Injector;
import org.odddev.gameofthrones.core.layers.presenter.PresenterManager;
import org.odddev.gameofthrones.databinding.CharacterActivityBinding;
import org.odddev.gameofthrones.splash.data.CharacterRow;
import org.odddev.gameofthrones.splash.data.HouseItem;

import java.util.List;

/**
 * @author kenrube
 * @date 16.10.16
 */

public class CharacterActivity extends AppCompatActivity implements ICharacterView {

    private static final int PRESENTER_ID = CharacterPresenter.class.getSimpleName().hashCode();
    private static final String CHARACTER_ID = "CHARACTER_ID";
    private static final String HOUSE_ID = "HOUSE_ID";
    private static final String SEASON_PREFIX = "Season ";

    private CharacterActivityBinding mBinding;
    private CharacterPresenter mPresenter;

    private CharacterRow mCharacter;
    private int mHouseId;

    private CharacterRow mFather;
    private CharacterRow mMother;

    public static void start(Context context, CharacterRow character, @HouseItem int houseId) {
        start(context, Intent.FLAG_ACTIVITY_NEW_TASK, character, houseId);
    }

    public static void start(Context context, int flags, CharacterRow character,
                             @HouseItem int houseId) {
        Intent intent = new Intent(context, CharacterActivity.class);
        intent.setFlags(intent.getFlags() | flags);
        intent.putExtra(CHARACTER_ID, character);
        intent.putExtra(HOUSE_ID, houseId);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Injector.getAppComponent().inject(this);

        mBinding = DataBindingUtil.setContentView(this, R.layout.character_activity);
        mPresenter = PresenterManager.getPresenter(PRESENTER_ID, CharacterPresenter::new);

        mCharacter = getIntent().getParcelableExtra(CHARACTER_ID);
        mHouseId = getIntent().getIntExtra(HOUSE_ID, HouseItem.STARK);

        mBinding.setActionsHandler(this);
        mBinding.setHouseId(mHouseId);
        mBinding.setCharacter(mCharacter);

        initToolbar();

        showMessageIfCharacterDead();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.attachView(this);

        mPresenter.loadWords(mHouseId);
        if (mCharacter.getFatherId() != -1) {
            mPresenter.loadFather(mCharacter.getFatherId());
        }
        if (mCharacter.getMotherId() != -1) {
            mPresenter.loadMother(mCharacter.getMotherId());
        }
    }

    @Override
    protected void onStop() {
        mPresenter.detachView(this);
        super.onStop();
    }

    private void initToolbar() {
        setSupportActionBar(mBinding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void showMessageIfCharacterDead() {
        List<String> tvSeries = mCharacter.getTvSeries();
        if (!TextUtils.isEmpty(mCharacter.getDied())
                && tvSeries.size() != 0
                && !TextUtils.isEmpty(tvSeries.get(tvSeries.size() - 1))) {
            String season = tvSeries.get(tvSeries.size() - 1).substring(SEASON_PREFIX.length());
            Snackbar.make(mBinding.getRoot(), getString(R.string.character_message_died, season),
                    Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                onBackPressed();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showWords(String words) {
        mBinding.setWords(words);
    }

    @Override
    public void showFather(CharacterRow father) {
        mFather = father;
        mBinding.setFather(father.getName());
    }

    @Override
    public void showMother(CharacterRow mother) {
        mMother = mother;
        mBinding.setMother(mother.getName());
        //mBinding.notifyPropertyChanged(BR.mother);
    }

    @Override
    public void showError(String error) {
        Snackbar.make(mBinding.getRoot(), error, Snackbar.LENGTH_LONG).show();
    }

    public void openFather() {
        finish();
        start(this, mFather, mHouseId);
    }

    public void openMother() {
        finish();
        start(this, mMother, mHouseId);
    }
}
