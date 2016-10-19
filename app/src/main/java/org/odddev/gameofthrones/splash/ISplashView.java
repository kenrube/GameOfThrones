package org.odddev.gameofthrones.splash;

import org.odddev.gameofthrones.core.layers.view.IView;

/**
 * @author kenrube
 * @date 16.10.16
 */

public interface ISplashView extends IView {

    void reportDbState(boolean isEmpty);

    void showCharacterLoaded(int charactersLoaded, int charactersCount);

    void showError(String error);
}
