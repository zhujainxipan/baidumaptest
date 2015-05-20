package com.ht.baidumapdemo;

import android.app.Application;
import android.content.Context;
import com.baidu.mapapi.SDKInitializer;

/**
 * Created by annuo on 2015/5/18.
 */
public class MyAppliacation extends Application {
    @Override
    public void onCreate() {
        SDKInitializer.initialize(getApplicationContext());
        super.onCreate();
    }
}
