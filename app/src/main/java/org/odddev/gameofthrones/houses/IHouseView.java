package org.odddev.gameofthrones.houses;

import org.odddev.gameofthrones.core.layers.view.IView;
import org.odddev.gameofthrones.splash.data.CharacterRow;

import java.util.List;

/**
 * @author kenrube
 * @date 16.10.16
 */

public interface IHouseView extends IView {

    void showCharacters(List<CharacterRow> characters);

    void showError(String error);
}
