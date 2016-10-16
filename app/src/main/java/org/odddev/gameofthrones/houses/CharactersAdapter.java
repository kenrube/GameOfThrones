package org.odddev.gameofthrones.houses;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.odddev.gameofthrones.databinding.CharacterItemBinding;
import org.odddev.gameofthrones.splash.data.CharacterRow;
import org.odddev.gameofthrones.splash.data.HouseItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kenrube
 * @date 16.10.16
 */

public class CharactersAdapter extends RecyclerView.Adapter<CharactersAdapter.CharacterViewHolder> {

    private ItemClickListener mListener;
    private @HouseItem int mHouseId;

    private List<CharacterRow> mItems = new ArrayList<>();

    public CharactersAdapter(ItemClickListener listener, @HouseItem int houseId) {
        mListener = listener;
        mHouseId = houseId;
    }

    @Override
    public CharacterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        CharacterItemBinding binding = CharacterItemBinding.inflate(inflater, parent, false);
        return new CharacterViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(CharacterViewHolder holder, int position) {
        CharacterRow character = mItems.get(position);

        holder.mBinding.setHouseId(mHouseId);
        holder.mBinding.setCharacter(character);
        holder.mBinding.getRoot().setOnClickListener(v ->
                mListener.chooseCharacter(character, mHouseId));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void setItems(List<CharacterRow> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    class CharacterViewHolder extends RecyclerView.ViewHolder {

        CharacterItemBinding mBinding;

        CharacterViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }
    }

    interface ItemClickListener {

        void chooseCharacter(CharacterRow character, @HouseItem int houseId);
    }
}
