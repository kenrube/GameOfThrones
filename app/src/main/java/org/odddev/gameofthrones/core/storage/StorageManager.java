package org.odddev.gameofthrones.core.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import org.odddev.gameofthrones.splash.data.CharacterRow;
import org.odddev.gameofthrones.splash.data.HouseItem;
import org.odddev.gameofthrones.splash.data.HouseRow;

/**
 * @author kenrube
 * @date 16.10.16
 */

public class StorageManager {

    private static final String HOUSE_PREFIX = "house_";
    private static final String CHARACTER_PREFIX = "character_";

    private SharedPreferences mSharedPreferences;

    public StorageManager(Context context) {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public boolean isDbEmpty() {
        return loadHouse(HouseItem.STARK) == null;
    }

    public void saveHouse(HouseRow house) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(HOUSE_PREFIX + String.valueOf(house.id), new Gson().toJson(house));
        editor.apply();
    }

    public HouseRow loadHouse(Integer id) {
        return new Gson().fromJson(mSharedPreferences
                .getString(HOUSE_PREFIX + id.toString(), null), HouseRow.class);
    }

    public void saveCharacter(CharacterRow character) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(CHARACTER_PREFIX + String.valueOf(character.id), new Gson().toJson(character));
        editor.apply();
    }

    public CharacterRow loadCharacter(Integer id) {
        return new Gson().fromJson(mSharedPreferences
                .getString(CHARACTER_PREFIX + id.toString(), null), CharacterRow.class);
    }
}
