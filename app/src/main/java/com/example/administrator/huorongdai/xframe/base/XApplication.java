package com.example.administrator.huorongdai.xframe.base;

import android.app.Application;

import com.example.administrator.huorongdai.xframe.XFrame;


public class XApplication extends Application {
    private static XApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        XFrame.init(this);
    }


    public static XApplication getInstance() {
        return instance;
    }


}
