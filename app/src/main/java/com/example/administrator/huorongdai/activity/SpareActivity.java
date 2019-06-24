package com.example.administrator.huorongdai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;

import com.example.administrator.huorongdai.MainActivity;
import com.example.administrator.huorongdai.Path;
import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.gsonbean.QueryLockBean;
import com.example.administrator.huorongdai.util.DataCleanManager;
import com.example.administrator.huorongdai.xframe.utils.XEmptyUtils;
import com.example.administrator.huorongdai.xframe.utils.XNetworkUtils;
import com.example.administrator.huorongdai.xframe.utils.XPreferencesUtils;
import com.example.administrator.huorongdai.xframe.utils.http.HttpCallBack;
import com.example.administrator.huorongdai.xframe.utils.http.XHttp;
import com.example.administrator.huorongdai.xframe.utils.statusbar.XStatusBar;
import com.example.administrator.huorongdai.xframe.widget.XToast;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class SpareActivity extends AppCompatActivity {

    RelativeLayout ivSpare;
    boolean  isLogin;
    MyThread myThread;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spare);
        //在setContentView();后面加上适配语句
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        XStatusBar.setTransparent(this);
        ivSpare=findViewById(R.id.iv_spare);
        myThread=new MyThread(this);

        //页面的跳转
        Boolean first = (Boolean) XPreferencesUtils.get("FIRST",true);
        isLogin= (boolean) XPreferencesUtils.get("isLogin",false);
        if (first) {
            //第一次
            startActivity(new Intent(SpareActivity.this, GuideActivity.class));
            finish();
        } else {
            //myThread.start();

            AlphaAnimation alphaAnimation=new AlphaAnimation(0.1f,1.0f);
            alphaAnimation.setDuration(1500);//设置动画播放时长
            ivSpare.startAnimation(alphaAnimation);
            //设置动画监听
            alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }
                //动画结束
                @Override
                public void onAnimationEnd(Animation animation) {
                    //页面的跳转
                    if(isLogin){
                        String custId= (String) XPreferencesUtils.get("custId","");//用户ID
                        if(XNetworkUtils.isConnected()){
                            queryPwd(custId);
                        }else {
                            XToast.normal("网络连接失败!");
                        }
                    }else {
                        Intent intent=new Intent(SpareActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
        }
    }

    private void queryPwd(String custId){
        Map<String , Object> map =new HashMap<>();
        map.put("custId",custId);
        XHttp.obtain().post(Path.query_hand_lock, map, new HttpCallBack<QueryLockBean>() {
            @Override
            public void onSuccess(QueryLockBean bean) {
                //imeitype手势密码类型   1 未设置 2 已设置
                if(bean.isStatus()){
                    if(!XEmptyUtils.isEmpty(bean.getImeitype())){
                        if(bean.getImeitype()==1){
                            Intent intent=new Intent(SpareActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }else if(bean.getImeitype()==2){
                            Intent intent=new Intent(SpareActivity.this, HandLockActivity.class);
                            intent.putExtra("mode", "spare");
                            startActivity(intent);
                            finish();
                        }
                    }
                }
            }

            @Override
            public void onFailed(String error) {

            }
        });
    }

    private class MyThread extends Thread {
        WeakReference<SpareActivity> mThreadActivityRef;

        public MyThread(SpareActivity activity) {
            mThreadActivityRef = new WeakReference<>(activity);
        }

        @Override
        public void run() {
            super.run();
            if (mThreadActivityRef == null)
                return;
            if (mThreadActivityRef.get() != null){
                DataCleanManager.clearAllCache(SpareActivity.this);
            }
        }
    }
}
