package org.odddev.gameofthrones.core.rx;

import java.util.concurrent.Executors;

import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Developer: Ivan Zolotarev
 * Date: 16.10.16
 */

public class SchedulersResolver implements ISchedulersResolver {

    private Scheduler newThreadScheduler() {
        return Schedulers.from(Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors()));
    }

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
                .subscribeOn(newThreadScheduler())
                .observeOn(mainThreadScheduler());
    }
}
