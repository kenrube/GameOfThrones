package org.odddev.gameofthrones.character;

import org.odddev.gameofthrones.splash.data.CharacterRow;

import rx.Observable;

/**
 * @author kenrube
 * @date 16.10.16
 */

public interface ICharacterProvider {

    Observable<String> loadWords(int houseId);

    Observable<CharacterRow> loadCharacter(int characterId);
}
