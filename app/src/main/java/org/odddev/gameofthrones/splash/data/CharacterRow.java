package org.odddev.gameofthrones.splash.data;

import android.os.Parcelable;

import org.odddev.gameofthrones.core.db.StringListConverter;

import java.io.Serializable;
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
public interface CharacterRow extends Parcelable, Persistable {

    @Key
    int getId();

    String getName();

    String getBorn();

    String getDied();

    @Convert(StringListConverter.class)
    List<String> getTitles();

    @Convert(StringListConverter.class)
    List<String> getAliases();

    int getFatherId();

    int getMotherId();

    @Convert(StringListConverter.class)
    List<String> getTvSeries();
}
