package org.odddev.gameofthrones.core.di;

import org.odddev.gameofthrones.character.CharacterActivity;
import org.odddev.gameofthrones.character.CharacterPresenter;
import org.odddev.gameofthrones.character.CharacterProvider;
import org.odddev.gameofthrones.core.network.NetworkChecker;
import org.odddev.gameofthrones.core.network.NetworkModule;
import org.odddev.gameofthrones.houses.HousePresenter;
import org.odddev.gameofthrones.houses.HouseProvider;
import org.odddev.gameofthrones.splash.SplashActivity;
import org.odddev.gameofthrones.splash.SplashPresenter;
import org.odddev.gameofthrones.splash.SplashProvider;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Developer: Ivan Zolotarev
 * Date: 16.10.16
 */

@Singleton
@Component(modules = {
        AppModule.class,
        PresentersModule.class,
        NetworkModule.class,
        ProvidersModule.class
})
public interface AppComponent {

    void inject(NetworkChecker networkChecker);

    void inject(SplashPresenter splashPresenter);

    void inject(SplashProvider splashProvider);

    void inject(HousePresenter housePresenter);

    void inject(HouseProvider houseProvider);

    void inject(SplashActivity splashActivity);

    void inject(CharacterActivity characterActivity);

    void inject(CharacterPresenter characterPresenter);

    void inject(CharacterProvider characterProvider);
}
