package com.example.myapplication.check_is_first_install_app;

import android.app.Application;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DataLocalManagement.Init(getApplicationContext());
    }
}
