package com.kenchow.demo.demostockapplication;

import android.app.Application;

import com.kenchow.demo.demostockapplication.api.RetrofitHelper;

/**
 * Created by mac on 20/1/2018.
 */

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RetrofitHelper.getInstance().build();
    }


}
