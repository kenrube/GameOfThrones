package org.odddev.gameofthrones.splash.data;

import android.support.annotation.DrawableRes;

import org.odddev.gameofthrones.R;

/**
 * @author kenrube
 * @date 18.10.16
 */

public class CharacterImages {

    @DrawableRes
    public static int getIcon(@HouseItem int id) {
        switch (id) {
            default:
            case HouseItem.STARK: {
                return R.drawable.stark_icon;
            }
            case HouseItem.LANNISTER: {
                return R.drawable.lannister_icon;
            }
            case HouseItem.TARGARYEN: {
                return R.drawable.targaryen_icon;
            }
        }
    }

    @DrawableRes
    public static int getArms(@HouseItem int id) {
        switch (id) {
            default:
            case HouseItem.STARK: {
                return R.drawable.stark;
            }
            case HouseItem.LANNISTER: {
                return R.drawable.lannister;
            }
            case HouseItem.TARGARYEN: {
                return R.drawable.targaryen;
            }
        }
    }
}
