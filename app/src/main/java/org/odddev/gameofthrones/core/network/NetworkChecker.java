package org.odddev.gameofthrones.core.network;

import org.odddev.gameofthrones.core.di.Injector;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import javax.inject.Inject;

/**
 * Developer: Ivan Zolotarev
 * Date: 16.10.16
 */

public class NetworkChecker implements INetworkChecker {

    @Inject
    Context mContext;

    public NetworkChecker() {
        Injector.getAppComponent().inject(this);
    }

    @Override
    public boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();

        return ni != null && ni.isConnected();
    }
}
