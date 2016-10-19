package org.odddev.gameofthrones.core.di;

import org.odddev.gameofthrones.BuildConfig;
import org.odddev.gameofthrones.core.network.INetworkChecker;
import org.odddev.gameofthrones.core.network.NetworkChecker;
import org.odddev.gameofthrones.core.rx.ISchedulersResolver;
import org.odddev.gameofthrones.core.rx.SchedulersResolver;
import org.odddev.gameofthrones.splash.data.Models;

import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.requery.Persistable;
import io.requery.android.sqlite.DatabaseSource;
import io.requery.rx.RxSupport;
import io.requery.rx.SingleEntityStore;
import io.requery.sql.Configuration;
import io.requery.sql.EntityDataStore;
import io.requery.sql.TableCreationMode;

/**
 * Developer: Ivan Zolotarev
 * Date: 16.10.16
 */

@Module
public class AppModule {

    private final Context mContext;
    private SingleEntityStore<Persistable> dataStore;

    public AppModule(Context context) {
        mContext = context;
    }

    @Singleton
    @Provides
    @NonNull
    Context getContext() {
        return mContext;
    }

    @Singleton
    @Provides
    ISchedulersResolver provideSchedulersResolver() {
        return new SchedulersResolver();
    }

    @Singleton
    @Provides
    INetworkChecker provideNetworkChecker() {
        return new NetworkChecker();
    }

    @Singleton
    @Provides
    SingleEntityStore<Persistable> provideRequery() {
        if (dataStore == null) {
            DatabaseSource source = new DatabaseSource(mContext, Models.DEFAULT, 1);
            Configuration configuration = source.getConfiguration();
            dataStore = RxSupport.toReactiveStore(new EntityDataStore<Persistable>(configuration));
        }
        return dataStore;
    }
}
