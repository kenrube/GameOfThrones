package org.odddev.gameofthrones.character;

import org.odddev.gameofthrones.core.layers.view.IView;
import org.odddev.gameofthrones.splash.data.CharacterRow;

/**
 * @author kenrube
 * @date 16.10.16
 */

public interface ICharacterView extends IView {

    void showWords(String words);

    void showFather(CharacterRow father);

    void showMother(CharacterRow mother);

    void showError(String error);
}
