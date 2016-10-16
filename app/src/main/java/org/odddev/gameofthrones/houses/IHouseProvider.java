package org.odddev.gameofthrones.houses;


import org.odddev.gameofthrones.splash.data.CharacterRow;

import java.util.List;

import rx.Observable;

/**
 * @author kenrube
 * @date 16.10.16
 */

public interface IHouseProvider {

    Observable<List<CharacterRow>> loadCharacters(int houseId);
}
