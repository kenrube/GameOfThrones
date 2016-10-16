package org.odddev.gameofthrones.core.di;

import org.odddev.gameofthrones.core.network.INetworkChecker;
import org.odddev.gameofthrones.core.network.NetworkChecker;
import org.odddev.gameofthrones.core.rx.ISchedulersResolver;
import org.odddev.gameofthrones.core.rx.SchedulersResolver;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Developer: Ivan Zolotarev
 * Date: 16.10.16
 */

@Module
public class AppModule {

    private final Context mContext;

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
}
