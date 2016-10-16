package org.odddev.gameofthrones.core.rx;

import rx.Observable;
import rx.Scheduler;

/**
 * Developer: Ivan Zolotarev
 * Date: 16.10.16
 */

public interface ISchedulersResolver {

    Scheduler ioScheduler();

    Scheduler mainThreadScheduler();

    <T> Observable.Transformer<T, T> applyDefaultSchedulers();
}
