package com.bung.bungapp;

import android.app.Application;
import android.content.SharedPreferences;

import com.bung.bungapp.core.ApiCaller;
import com.bung.bungapp.core.LocationClient;
import com.bung.bungapp.model.User;

/**
 * Created by rok on 2015. 5. 31..
 */
public class BaseApplication extends Application {

    public static final int REFRESH_INTERVAL = 30 * 1000;
    public static final String SHARED_PREFERENCE_KEY = "pref";
    public static final String PREFERENCE_DO_NOT_ASK_GPS_AGAIN = "do_not_ask_gps";

    public static boolean showAd = false;

    private static BaseApplication instance;
    private static User user;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        LocationClient.init(this);
        String accessToken = BaseApplication.getSharedPreferences().getString("access_token", null);
        if (accessToken != null) {
            ApiCaller.setAccessToken(accessToken);
        }
    }

    public static BaseApplication getInstance() {
        return instance;
    }

    public static SharedPreferences getSharedPreferences() {
        return instance.getSharedPreferences(SHARED_PREFERENCE_KEY, MODE_PRIVATE);
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        BaseApplication.user = user;
    }


}
