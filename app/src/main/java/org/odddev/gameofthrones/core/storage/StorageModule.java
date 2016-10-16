package org.odddev.gameofthrones.core.storage;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author kenrube
 * @date 16.10.16
 */

@Module
public class StorageModule {

    @Provides
    @Singleton
    StorageManager getStorageManager(Context context) {
        return new StorageManager(context);
    }
}
