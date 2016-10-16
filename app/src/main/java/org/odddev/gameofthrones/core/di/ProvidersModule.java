package org.odddev.gameofthrones.core.di;

import org.odddev.gameofthrones.character.CharacterProvider;
import org.odddev.gameofthrones.character.ICharacterProvider;
import org.odddev.gameofthrones.houses.HouseProvider;
import org.odddev.gameofthrones.houses.IHouseProvider;
import org.odddev.gameofthrones.splash.ISplashProvider;
import org.odddev.gameofthrones.splash.SplashProvider;

import dagger.Module;
import dagger.Provides;

/**
 * @author kenrube
 * @date 16.10.16
 */

@Module
public class ProvidersModule {

    @Provides
    ISplashProvider provideSplashProvider() {
        return new SplashProvider();
    }

    @Provides
    IHouseProvider provideHouseProvider() {
        return new HouseProvider();
    }

    @Provides
    ICharacterProvider provideCharacterProvider() {
        return new CharacterProvider();
    }
}
