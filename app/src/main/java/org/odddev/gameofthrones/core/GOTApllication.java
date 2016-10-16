package org.odddev.gameofthrones.core;

import com.facebook.stetho.Stetho;

import org.odddev.gameofthrones.core.di.AppModule;
import org.odddev.gameofthrones.core.di.DaggerAppComponent;
import org.odddev.gameofthrones.core.di.Injector;

import android.app.Application;

import timber.log.Timber;

/**
 * Developer: Ivan Zolotarev
 * Date: 16.10.16
 */

public class GOTApllication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initTimber();
        initStetho();
        initDagger();
    }

    private void initTimber() {
        Timber.plant(new Timber.DebugTree());
    }

    private void initStetho() {
        Stetho.InitializerBuilder builder = Stetho.newInitializerBuilder(this);
        builder.enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this));
        builder.enableDumpapp(Stetho.defaultDumperPluginsProvider(this));
        Stetho.Initializer initializer = builder.build();
        Stetho.initialize(initializer);
    }

    private void initDagger() {
        Injector.init(DaggerAppComponent.builder().appModule(new AppModule(this)).build());
    }
}
