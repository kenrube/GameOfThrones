package org.odddev.gameofthrones.splash.data;

import android.databinding.Bindable;
import android.databinding.Observable;
import android.os.Parcelable;

import io.requery.CascadeAction;
import io.requery.Entity;
import io.requery.Key;
import io.requery.OneToMany;
import io.requery.Persistable;

/**
 * @author kenrube
 * @date 13.10.16
 */

@Entity
public interface HouseRow extends Observable, Parcelable, Persistable {

    @Key
    int getId();

    @Bindable
    String getWords();

    @OneToMany(mappedBy = "allegiance", cascade = {CascadeAction.DELETE, CascadeAction.SAVE})
    List<CharacterRow> getCharactersList();
}
