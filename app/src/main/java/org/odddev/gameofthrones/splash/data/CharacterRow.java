package org.odddev.gameofthrones.splash.data;

import android.support.annotation.DrawableRes;

import org.odddev.gameofthrones.R;

import java.io.Serializable;
import java.util.List;

/**
 * @author kenrube
 * @date 16.10.16
 */

public class CharacterRow implements Serializable {

    public int id;

    public String name;

    public String born;

    public String died;

    public List<String> titles;

    public List<String> aliases;

    public int fatherId;

    public int motherId;

    public List<String> tvSeries;

    @DrawableRes
    public int getIcon(@HouseItem int id) {
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
    public int getArms(@HouseItem int id) {
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
