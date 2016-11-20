package com.stackdroid.api;

import com.github.simonpercic.oklog.OkLogInterceptor;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import timber.log.Timber;

/**
 * Created by aditlal on 06/04/16.
 */
public class ApiGenerator {

    public static String BASE_URL = "https://api.stackexchange.com/2.2/";

    // No need to instantiate this class.
    public ApiGenerator() {
    }

    public static <S> S createService(Class<S> serviceClass) {
        OkLogInterceptor okLogInterceptor = OkLogInterceptor.builder().build();
        OkHttpClient okHttpClient = new OkHttpClient();
        List<Interceptor> clientInterceptors = okHttpClient.interceptors();
        Collections.addAll(clientInterceptors, okLogInterceptor);
        okHttpClient.setReadTimeout(60, TimeUnit.SECONDS);
        okHttpClient.setConnectTimeout(60, TimeUnit.SECONDS);

        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .setLog(message -> {
                    Timber.tag("RetroFit");
                    Timber.d(message);
                })
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setClient(new OkClient(okHttpClient));

        RestAdapter adapter = builder.build();

        return adapter.create(serviceClass);
    }

}
