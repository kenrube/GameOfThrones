package org.odddev.gameofthrones.splash.data;

import android.support.annotation.IntDef;

/**
 * @author kenrube
 * @date 13.10.16
 */

@IntDef({
        HouseItem.LANNISTER,
        HouseItem.STARK,
        HouseItem.TARGARYEN})
@interface HouseItem {

    int LANNISTER = 229;
    int STARK = 362;
    int TARGARYEN = 378;
}
