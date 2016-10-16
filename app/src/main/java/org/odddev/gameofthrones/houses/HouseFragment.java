package org.odddev.gameofthrones.houses;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.odddev.gameofthrones.character.CharacterActivity;
import org.odddev.gameofthrones.core.layers.presenter.PresenterManager;
import org.odddev.gameofthrones.databinding.HouseFragmentBinding;
import org.odddev.gameofthrones.splash.data.CharacterRow;
import org.odddev.gameofthrones.splash.data.HouseItem;

import java.util.List;

/**
 * @author kenrube
 * @date 16.10.16
 */

@SuppressLint("ValidFragment")
public class HouseFragment extends Fragment implements IHouseView,
        CharactersAdapter.ItemClickListener {

    private static final int PRESENTER_ID = HousePresenter.class.getSimpleName().hashCode();
    private static final String HOUSE_ID = "HOUSE_ID";

    private HouseFragmentBinding mBinding;
    private HousePresenter mPresenter;

    private LinearLayoutManager mLinearLayoutManager;
    private CharactersAdapter mAdapter;

    private List<CharacterRow> mCharacters;

    private int mHouseId;

    public HouseFragment() {
    }

    public HouseFragment(int houseId) {
        Bundle args = new Bundle();
        args.putInt(HOUSE_ID, houseId);
        setArguments(args);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mHouseId = getArguments().getInt(HOUSE_ID);

        mPresenter = PresenterManager.getPresenter(PRESENTER_ID + mHouseId, HousePresenter::new);

        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = HouseFragmentBinding.inflate(inflater, container, false);

        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mBinding.recycler.setLayoutManager(mLinearLayoutManager);
        mAdapter = new CharactersAdapter(this, mHouseId);
        mBinding.recycler.setAdapter(mAdapter);

        return mBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();

        mPresenter.attachView(this);
        if (mCharacters != null) {
            showCharacters(mCharacters);
        } else {
            mPresenter.loadData(mHouseId);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.detachView(this);
    }

    @Override
    public void showCharacters(List<CharacterRow> characters) {
        mCharacters = characters;
        mAdapter.setItems(characters);
    }

    @Override
    public void chooseCharacter(CharacterRow character, @HouseItem int houseId) {
        CharacterActivity.start(getContext(), character, houseId);
    }

    @Override
    public void showError(String error) {
        Snackbar.make(mBinding.getRoot(), error, Snackbar.LENGTH_LONG).show();
    }
}
