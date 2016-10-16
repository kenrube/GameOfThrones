package org.odddev.gameofthrones.core.network;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import org.odddev.gameofthrones.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

/**
 * Developer: Ivan Zolotarev
 * Date: 16.10.16
 */

public class ServerApiBuilder {

    private static final String API_URL = BuildConfig.API_URL;

    private static final String HTTP_LOG_TAG = "OkHttp";

    static IServerApi createApi() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        builder.addInterceptor(new StethoInterceptor());

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(
                message -> Timber.tag(HTTP_LOG_TAG).d(message));
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(loggingInterceptor);

        builder.followRedirects(false);

        OkHttpClient httpClient = builder.build();

        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(httpClient);

        return retrofitBuilder.build().create(IServerApi.class);
    }
}
