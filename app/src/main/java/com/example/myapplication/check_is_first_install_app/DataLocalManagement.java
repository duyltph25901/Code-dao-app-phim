package com.example.myapplication.check_is_first_install_app;

import android.content.Context;

public class DataLocalManagement {
    private static final String PREF_FIRST_INSTALL = "PREF_FIRST_INSTALL";
    private static DataLocalManagement instance;
    private MySharedPreferences mySharedPreferences;

    public static void Init(Context context) {
        instance = new DataLocalManagement();
        instance.mySharedPreferences = new MySharedPreferences(context);
    }

    public static DataLocalManagement getInstance() {
        if (instance == null) {
            instance = new DataLocalManagement();
        }

        return instance;
    }

    public static void SetFirstInstall(boolean isFirstInstall) {
        DataLocalManagement.getInstance().mySharedPreferences.PutBooleanValue(PREF_FIRST_INSTALL, isFirstInstall);
    }

    public static boolean GetFirstInstall() {
        return DataLocalManagement.getInstance().mySharedPreferences.GetBooleanValue(PREF_FIRST_INSTALL);
    }
}
