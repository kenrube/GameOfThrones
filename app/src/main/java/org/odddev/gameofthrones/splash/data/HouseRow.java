package org.odddev.gameofthrones.splash.data;

import android.os.Parcelable;

import org.odddev.gameofthrones.core.db.IntegerListConverter;

import java.util.List;

import io.requery.Convert;
import io.requery.Entity;
import io.requery.Key;
import io.requery.Persistable;

/**
 * @author kenrube
 * @date 16.10.16
 */

@Entity
public interface HouseRow extends Parcelable, Persistable {

    @Key
    int getId();

    String getWords();

    @Convert(IntegerListConverter.class)
    List<Integer> getCharactersList();
}
