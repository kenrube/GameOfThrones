package org.odddev.gameofthrones.core.di;

import dagger.Module;
import dagger.Provides;
import rx.subscriptions.CompositeSubscription;

/**
 * Developer: Ivan Zolotarev
 * Date: 16.10.16
 */

@Module
public class PresentersModule {

    @Provides
    CompositeSubscription provideCompositeSubscription() {
        return new CompositeSubscription();
    }
}
