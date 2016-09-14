package com.tj.uimading.app;

import android.app.Application;
import android.os.Handler;

import org.xutils.x;

/**
 * Created by Administrator on 2016/9/1.
 */
public class MyXutilsApp extends Application {

    public static Handler handler;
    private static MyXutilsApp myApp;

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        handler = new Handler();
    }

    public static MyXutilsApp getMyApp() {
        return myApp;
    }
}
