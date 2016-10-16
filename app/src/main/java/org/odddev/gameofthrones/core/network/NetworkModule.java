package org.odddev.gameofthrones.core.network;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Developer: Ivan Zolotarev
 * Date: 16.10.16
 */

@Module
public class NetworkModule {

    @Provides
    @Singleton
    IServerApi provideServerApi() {
        return ServerApiBuilder.createApi();
    }
}
