package org.odddev.gameofthrones.core.rx;

import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Developer: Ivan Zolotarev
 * Date: 16.10.16
 */

public class SchedulersResolver implements ISchedulersResolver {

    @Override
    public Scheduler ioScheduler() {
        return Schedulers.io();
    }

    @Override
    public Scheduler mainThreadScheduler() {
        return AndroidSchedulers.mainThread();
    }

    public <T> Observable.Transformer<T, T> applyDefaultSchedulers() {
        return observable -> observable
                .subscribeOn(ioScheduler())
                .observeOn(mainThreadScheduler());
    }
}
