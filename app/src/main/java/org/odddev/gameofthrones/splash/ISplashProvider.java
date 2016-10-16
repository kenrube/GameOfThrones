package org.odddev.gameofthrones.splash;

import org.odddev.gameofthrones.splash.data.CharacterRow;

import rx.Observable;

/**
 * @author kenrube
 * @date 16.10.16
 */

public interface ISplashProvider {

    Observable<Integer> getCharactersCount();

    Observable<CharacterRow> loadCharacters();
}
