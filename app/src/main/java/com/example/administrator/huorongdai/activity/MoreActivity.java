package com.example.administrator.huorongdai.activity;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.text.Html;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.huorongdai.MainActivity;
import com.example.administrator.huorongdai.Path;
import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.base.BaseActivity;
import com.example.administrator.huorongdai.gsonbean.CheckVersionBean;
import com.example.administrator.huorongdai.util.DataCleanManager;
import com.example.administrator.huorongdai.util.update.InstallUtils;
import com.example.administrator.huorongdai.view.CustomDialog;
import com.example.administrator.huorongdai.xframe.utils.XAppUtils;
import com.example.administrator.huorongdai.xframe.utils.XPreferencesUtils;
import com.example.administrator.huorongdai.xframe.utils.http.HttpCallBack;
import com.example.administrator.huorongdai.xframe.utils.http.XHttp;
import com.example.administrator.huorongdai.xframe.utils.permission.XPermission;
import com.example.administrator.huorongdai.xframe.widget.XToast;

import java.lang.ref.WeakReference;
import java.util.HashMap;

public class MoreActivity extends BaseActivity implements View.OnTouchListener {
    private RelativeLayout activityRl;//活动专区
    private TextView tvActivity;
    private RelativeLayout integralRl;//我的积分
    private TextView tvIntegral;
    private RelativeLayout noticeRl;//最新公告
    private TextView tvNotice;
    private RelativeLayout aboutUsRl;//关于我们
    private TextView tvUs;
    private RelativeLayout callingRl;//客服电话
    private TextView tvCall1;
    private TextView tvCall2;
    private RelativeLayout handPwdRl;//手势密码
    private TextView tvHandPwd;
    private RelativeLayout updateRl;//版本更新
    private TextView tvUpdate1;
    private TextView tvUpdate2;
    private RelativeLayout clearRl;//清除缓存
    private TextView tvSize;//缓存大小
    private TextView tvCache;//缓存大小

    private boolean isLogin;

    private MyThread myThread;
    private String size;
    private Handler handler=new Handler(new MyCallback());
    private final class MyCallback implements Handler.Callback{

        @Override
        public boolean handleMessage(Message message) {
            tvSize.setText(size);
            return true;
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_more;
    }

    @Override
    protected void initView() {
        setTitle("更多");

        activityRl=findViewById(R.id.rl_more_activity);
        activityRl.setOnClickListener(this);
        integralRl=findViewById(R.id.rl_my_integral);
        integralRl.setOnClickListener(this);
        noticeRl=findViewById(R.id.rl_latest_notice);
        noticeRl.setOnClickListener(this);
        aboutUsRl=findViewById(R.id.rl_about_us);
        aboutUsRl.setOnClickListener(this);
        callingRl=findViewById(R.id.rl_calling);
        callingRl.setOnClickListener(this);
        handPwdRl=findViewById(R.id.rl_hand_pwd);
        handPwdRl.setOnClickListener(this);
        updateRl=findViewById(R.id.rl_version_update);
        updateRl.setOnClickListener(this);
        clearRl=findViewById(R.id.rl_clear_cache);
        clearRl.setOnClickListener(this);
        tvSize=findViewById(R.id.tv_cache_size);
        tvActivity=findViewById(R.id.tv_activity);
        tvIntegral=findViewById(R.id.tv_integral);
        tvNotice=findViewById(R.id.tv_notice);
        tvUs=findViewById(R.id.tv_us);
        tvCall1=findViewById(R.id.tv_call1);
        tvCall2=findViewById(R.id.tv_call2);
        tvHandPwd=findViewById(R.id.tv_hand_pwd);
        tvUpdate1=findViewById(R.id.tv_update1);
        tvUpdate2=findViewById(R.id.tv_update2);
        tvCache=findViewById(R.id.tv_cache);

        activityRl.setOnTouchListener(this);
        integralRl.setOnTouchListener(this);
        noticeRl.setOnTouchListener(this);
        aboutUsRl.setOnTouchListener(this);
        callingRl.setOnTouchListener(this);
        handPwdRl.setOnTouchListener(this);
        updateRl.setOnTouchListener(this);
        clearRl.setOnTouchListener(this);

        isLogin= (boolean) XPreferencesUtils.get("isLogin",false);

        tvUpdate2.setText("当前版本:"+ XAppUtils.getVersionName(this));
        clearType=1;
        myThread=new MyThread(this);
        myThread.start();

        dialog = new CustomDialog(this, R.style.custom_dialog2, R.layout.update_notice);
        manger = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    protected void loadData() {
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_more_activity://活动专区
                Intent intentHuodong=new Intent(this, HuodongActivity.class);
                startActivity(intentHuodong);
                break;
            case R.id.rl_my_integral://我的积分
                if(isLogin){
                    Intent intent=new Intent(this, MyIntegralActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent=new Intent(this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
                break;
            case R.id.rl_latest_notice://最新公告
                Intent intentNotice=new Intent(this, LatestNoticeActivity.class);
                startActivity(intentNotice);
                break;
            case R.id.rl_about_us://关于我们
                Intent intentUs=new Intent(this, AboutUsActivity.class);
                startActivity(intentUs);
                break;
            case R.id.rl_calling://客服电话4000703188
                XPermission.requestPermissions(this, 100, new String[]{Manifest.permission
                        .CALL_PHONE}, new XPermission.OnPermissionListener() {
                    //权限申请成功时调用
                    @Override
                    public void onPermissionGranted() {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:4000703188"));
                        startActivity(intent);
                    }
                    //权限被用户禁止时调用
                    @Override
                    public void onPermissionDenied() {
                        //给出友好提示，并且提示启动当前应用设置页面打开权限
                        XPermission.showTipsDialog(MoreActivity.this);
                    }
                });
                break;
            case R.id.rl_hand_pwd://手势密码
                if(isLogin){
                    Intent intentLock=new Intent(this, LockSettingActivity.class);
                    startActivity(intentLock);
                }else {
                    Intent intent=new Intent(this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
                break;
            case R.id.rl_version_update://版本更新
                //checkVersion();
                XToast.normal("该版本已是最新版本");
                break;
            case R.id.rl_clear_cache://清除缓存
                try {
                    clearType=2;
                    myThread=new MyThread(this);
                    myThread.start();
                    XToast.normal("清除成功");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    //版本更新
    private void checkVersion() {
        XPermission.requestPermissions(this, 101, new String[]{Manifest.permission
                .WRITE_EXTERNAL_STORAGE}, new XPermission.OnPermissionListener() {
            //权限申请成功时调用
            @Override
            public void onPermissionGranted() {
                XHttp.obtain().get(Path.check_android_ver, new HashMap<String, Object>(), new HttpCallBack<CheckVersionBean>() {
                    @Override
                    public void onSuccess(CheckVersionBean bean) {
                        if(bean.getData().isStatus()){
                            if(bean.getData().getVerCode()>XAppUtils.getVersionCode(MoreActivity.this)){
                                normalUpdate(MoreActivity.this,bean.getData().getVerName(),bean.getData().getAppFile(),bean.getData().getUpdateReason());
                            }else {
                                XToast.normal("该版本已是最新版本");
                            }
                        }
                    }

                    @Override
                    public void onFailed(String error) {

                    }
                });
            }
            //权限被用户禁止时调用
            @Override
            public void onPermissionDenied() {
                //给出友好提示，并且提示启动当前应用设置页面打开权限
                XPermission.showTipsDialog(MoreActivity.this);
            }
        });
    }

    private NotificationManager manger ;
    private CustomDialog dialog;//更新提示框
    /**
     * 正常更新
     * @param context
     * @param appName
     * @param downUrl
     * @param updateinfo
     */
    private void normalUpdate(final Context context, final String appName, final String downUrl, final String updateinfo) {

        dialog.show();
        TextView titleTv=dialog.findViewById(R.id.tv_update_title);
        TextView infoTv=dialog.findViewById(R.id.tv_update_info);
        TextView cancelTv=dialog.findViewById(R.id.tv_update_cancel);
        TextView sureTv=dialog.findViewById(R.id.tv_update_sure);
        titleTv.setText(appName+"更新");
        infoTv.setText(Html.fromHtml(updateinfo));
        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //提示框取消
                dialog.dismiss();
            }
        });
        sureTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InstallUtils.with(context)
                        //必须-下载地址
                        .setApkUrl(downUrl)
                        //非必须，默认update
                        .setApkName(appName)
                        //非必须-下载保存的路径
//                        .setApkPath(Constants.APK_SAVE_PATH)
                        //非必须-下载回调
                        .setCallBack(new InstallUtils.DownloadCallBack() {
                            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
                            Notification notification=null;
                            @Override
                            public void onStart() {//下载开始

                            }

                            @Override
                            public void onComplete(String path) {//下载完成
                                manger.cancel(2);
                                //8.0碰到了安装解析失败问题请添加下面判断
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    //先获取是否有安装未知来源应用的权限
                                    boolean haveInstallPermission = getPackageManager().canRequestPackageInstalls();
                                    if (!haveInstallPermission) {
                                        //跳转设置开启允许安装
                                        Uri packageURI = Uri.parse("package:"+context.getPackageName());
                                        Intent intent =new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES,packageURI);
                                        startActivityForResult(intent,1000);
                                        return;
                                    }
                                }
                                InstallUtils.installAPK(context, path, new InstallUtils.InstallCallBack() {//安装APK
                                    @Override
                                    public void onSuccess() {
                                        XToast.normal("正在安装程序");
                                        Toast.makeText(context, "正在安装程序", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onFail(Exception e) {
                                        XToast.normal("安装失败:"+e.toString());
                                    }
                                });
                            }

                            @Override
                            public void onLoading(long total, long current) {//下载中
                                // 禁止用户点击删除按钮删除
                                builder.setAutoCancel(false);
                                //禁止滑动删除
                                builder.setOngoing(true);
                                builder.setSmallIcon(R.mipmap.ic_launcher);
                                builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher));
                                builder.setDefaults(NotificationCompat.DEFAULT_ALL);
                                builder.setAutoCancel(true); //禁止用户点击删除按钮删除
                                builder.setContentTitle(appName+"下载");
                                PendingIntent pIntent = PendingIntent.getActivity(context,1,new Intent(),0);
                                builder.setContentIntent(pIntent);
                                //这句是重点
                                builder.setFullScreenIntent(pIntent,true);
                                //取消右上角的时间显示
                                builder.setShowWhen(false);
                                double result = current * 1.0 / total;
                                int progress= (int) (result*100);
                                builder.setContentTitle("下载中..."+progress+"%");
                                builder.setProgress(100,progress,false);

                                notification =builder.build();
                                manger.notify(2,notification);
                            }

                            @Override
                            public void onFail(Exception e) {//下载中

                            }

                            @Override
                            public void cancle() {//下载取消

                            }
                        })
                        //开始下载
                        .startDownload();
            }
        });
    }

    @Override
    protected void onClickLeft() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (view.getId()){
            case R.id.rl_more_activity://活动专区
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        tvActivity.setTextColor(getResources().getColor(R.color.word_red));
                        break;
                    case MotionEvent.ACTION_UP:
                        tvActivity.setTextColor(getResources().getColor(R.color.word_gray));
                        break;
                }
                break;
            case R.id.rl_my_integral://我的积分
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        tvIntegral.setTextColor(getResources().getColor(R.color.word_red));
                        break;
                    case MotionEvent.ACTION_UP:
                        tvIntegral.setTextColor(getResources().getColor(R.color.word_gray));
                        break;
                }
                break;
            case R.id.rl_latest_notice://最新公告
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        tvNotice.setTextColor(getResources().getColor(R.color.word_red));
                        break;
                    case MotionEvent.ACTION_UP:
                        tvNotice.setTextColor(getResources().getColor(R.color.word_gray));
                        break;
                }
                break;
            case R.id.rl_about_us://关于我们
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        tvUs.setTextColor(getResources().getColor(R.color.word_red));
                        break;
                    case MotionEvent.ACTION_UP:
                        tvUs.setTextColor(getResources().getColor(R.color.word_gray));
                        break;
                }
                break;
            case R.id.rl_calling://客服电话4000703188
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        tvCall1.setTextColor(getResources().getColor(R.color.word_red));
                        tvCall2.setTextColor(getResources().getColor(R.color.word_red));
                        break;
                    case MotionEvent.ACTION_UP:
                        tvCall1.setTextColor(getResources().getColor(R.color.word_gray));
                        tvCall2.setTextColor(getResources().getColor(R.color.word_gray));
                        break;
                }
                break;
            case R.id.rl_hand_pwd://手势密码
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        tvHandPwd.setTextColor(getResources().getColor(R.color.word_red));
                        break;
                    case MotionEvent.ACTION_UP:
                        tvHandPwd.setTextColor(getResources().getColor(R.color.word_gray));
                        break;
                }
                break;
            case R.id.rl_version_update://版本更新
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        tvUpdate1.setTextColor(getResources().getColor(R.color.word_red));
                        tvUpdate2.setTextColor(getResources().getColor(R.color.word_red));
                        break;
                    case MotionEvent.ACTION_UP:
                        tvUpdate1.setTextColor(getResources().getColor(R.color.word_gray));
                        tvUpdate2.setTextColor(getResources().getColor(R.color.word_gray));
                        break;
                }
                break;
            case R.id.rl_clear_cache://清除缓存
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        tvSize.setTextColor(getResources().getColor(R.color.word_red));
                        tvCache.setTextColor(getResources().getColor(R.color.word_red));
                        break;
                    case MotionEvent.ACTION_UP:
                        tvSize.setTextColor(getResources().getColor(R.color.word_gray));
                        tvCache.setTextColor(getResources().getColor(R.color.word_gray));
                        break;
                }
                break;
        }
        return false;
    }

    private int clearType;
    private void dosomething() {
        try {
            if(clearType==1){
                size=DataCleanManager.getTotalCacheSize(getApplication());
            }else if(clearType==2){
                DataCleanManager.clearAllCache(MoreActivity.this);
                size=DataCleanManager.getTotalCacheSize(getApplication());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        handler.sendMessage(Message.obtain());
    }
    private static class MyThread extends Thread {
        WeakReference<MoreActivity> mThreadActivityRef;

        public MyThread(MoreActivity activity) {
            mThreadActivityRef = new WeakReference<>(activity);
        }

        @Override
        public void run() {
            super.run();
            if (mThreadActivityRef == null)
                return;
            if (mThreadActivityRef.get() != null){
                mThreadActivityRef.get().dosomething();
            }

        }
    }
}
