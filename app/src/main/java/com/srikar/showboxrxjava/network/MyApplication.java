package com.srikar.showboxrxjava.network;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

public class MyApplication extends Application {

    private static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        mInstance = this;
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }

    public void unregisterReceiver(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        unregisterReceiver(listener);//your broadcast
    }
}