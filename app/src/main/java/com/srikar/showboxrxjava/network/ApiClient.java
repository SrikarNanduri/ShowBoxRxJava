package com.srikar.showboxrxjava.network;

import android.util.Log;

import com.srikar.showboxrxjava.config.ConfigURL;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {

    private static Retrofit retrofit = null;
    public static Retrofit getClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(10, TimeUnit.SECONDS)
                  .readTimeout(20, TimeUnit.SECONDS)
                  .addInterceptor(logging);

        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(ConfigURL.BaseURL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
            Log.v("BaseURL", ConfigURL.BaseURL );
        }
        return retrofit;
    }
}
