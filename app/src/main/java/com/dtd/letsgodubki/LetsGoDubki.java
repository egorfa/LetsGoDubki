package com.dtd.letsgodubki;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseCrashReporting;

/**
 * Created by Egor on 09.03.2015.
 */
public class LetsGoDubki extends Application {

    public static String sDefSystemLanguage;
    public static Integer days;

    @Override
    public void onCreate() {
        ParseCrashReporting.enable(this);
        Parse.initialize(this, "KtubqRHEfFmfSf24Cr5xkLykMbwID0pa9Ew83RdI", "OwphBEzCe7CyRVfg8PYSyhjQXWMppVhM8ZNHO1yf");


        super.onCreate();
    }

}
