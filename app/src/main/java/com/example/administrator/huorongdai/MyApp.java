package com.example.administrator.huorongdai;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.example.administrator.huorongdai.util.GlideImageLoader;
import com.example.administrator.huorongdai.util.OKHttpEngine;
import com.example.administrator.huorongdai.xframe.XFrame;
import com.example.administrator.huorongdai.xframe.base.XApplication;
import com.example.administrator.huorongdai.xframe.utils.XCrashHandlerUtils;
import com.example.administrator.huorongdai.xframe.utils.XPreferencesUtils;
import com.example.administrator.huorongdai.xframe.utils.log.XLog;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.yatoooon.screenadaptation.ScreenAdapterTools;


/**
 * Created by Administrator on 2018/3/8.
 */

public class MyApp extends XApplication {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        XFrame.initXHttp(new OKHttpEngine());
        XFrame.initXImageLoader(new GlideImageLoader(this));
        XFrame.initXLoadingView().setErrorViewResId(R.layout.error_view)
                .setEmptyViewResId(R.layout.empty_view)
                .setLoadingViewResId(R.layout.loading_view)
                .setNoNetworkViewResId(R.layout.no_net_work_view);
        XLog.init().setTag("chen");
        XPreferencesUtils.initPreferences(this);

        ScreenAdapterTools.init(this);
        //崩溃日志捕获
        //XCrashHandlerUtils.getInstance().init(this);


        //设置LOG开关，默认为false
        UMConfigure.setLogEnabled(true);
        //初始化组件化基础库, 统计SDK/推送SDK/分享SDK都必须调用此初始化接口
        UMConfigure.init(this, "5b1dee948f4a9d7f6f00006f", "Umeng", UMConfigure.DEVICE_TYPE_PHONE,"");
    }

    {
        PlatformConfig.setWeixin("wxa4615d91c56f861e", "6e4fa7ea37a17d76ce592843cc73274d");
    }

}
