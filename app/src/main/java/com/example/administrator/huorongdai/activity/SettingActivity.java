package com.example.administrator.huorongdai.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.text.Html;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.administrator.huorongdai.Path;
import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.base.BaseActivity;
import com.example.administrator.huorongdai.eventbusbean.Msg;
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

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.WeakReference;
import java.util.HashMap;

public class SettingActivity extends BaseActivity {

    private RelativeLayout updateRl;//版本更新
    private RelativeLayout clearRl;//清除缓存
    private RelativeLayout rlExit;//退出
    private TextView tvUpdate2;
    private TextView tvSize;//缓存大小
    private int clearType;

    private CustomDialog dialogUpdate;//更新提示框
    private CustomDialog dialogDownloading;//下载框
    private CustomDialog dialogExit;//用户退出登录提示框

    private MyThread myThread;
    private String size;
    private Handler handler=new Handler(new MyCallback());
    private final class MyCallback implements Handler.Callback{

        @Override
        public boolean handleMessage(Message message) {
            tvSize.setText("(大小："+size+")");
            return true;
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {
        setTitle("设置");
        tvUpdate2=findViewById(R.id.tv_update2);
        tvSize=findViewById(R.id.tv_cache_size);
        updateRl=findViewById(R.id.rl_version_update);
        updateRl.setOnClickListener(this);
        clearRl=findViewById(R.id.rl_clear_cache);
        rlExit=findViewById(R.id.rl_exit);
        clearRl.setOnClickListener(this);
        rlExit.setOnClickListener(this);

        dialogUpdate = new CustomDialog(this, R.style.custom_dialog2, R.layout.update_notice);
        dialogDownloading = new CustomDialog(this, R.style.custom_dialog2, R.layout.dialog_downloading);
        dialogExit = new CustomDialog(this, R.style.custom_dialog2, R.layout.login_notice);

        tvUpdate2.setText("V  "+ XAppUtils.getVersionName(this));
        clearType=1;
        myThread=new MyThread(this);
        myThread.start();
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_version_update://版本更新
                check();
                break;
            case R.id.rl_clear_cache://清除缓存
                try {
                    clearType=2;
                    //myThread=new MyThread(this);
                    myThread.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.rl_exit://退出
                boolean isLogin= (boolean) XPreferencesUtils.get("isLogin",false);
                if(isLogin){
                    initDialog();
                }
                break;
        }
    }

    //退出登录提示框
    private void initDialog(){
        dialogExit.show();
        TextView titleTv=dialogExit.findViewById(R.id.tv_login_notice_title);
        TextView cancelTv=dialogExit.findViewById(R.id.tv_login_notice_cancel);
        TextView sureTv=dialogExit.findViewById(R.id.tv_login_notice_sure);
        titleTv.setText("是否要退出当前账户？");
        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //提示框取消
                dialogExit.dismiss();
            }
        });
        sureTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                XPreferencesUtils.put("isLogin",false);
                XPreferencesUtils.put("custMobile","");
                XPreferencesUtils.put("custName","");
                XPreferencesUtils.put("headIcon", "");
                XPreferencesUtils.put("custPwd","");//密文密码
                XPreferencesUtils.put("custId","");//用户ID
                //未开户是4，开户是2
                XPreferencesUtils.put("openStatus",4);
                //1-未认证 2-认证
                XPreferencesUtils.put("emailAuth",1);
                XPreferencesUtils.put("custEmail","");//电子邮箱
                XPreferencesUtils.put("login_cookies","");
                dialogExit.dismiss();
                EventBus.getDefault().post(new Msg("4"));//退出后跳到homeFragment
                finish();
            }
        });

    }

    //适配Android8.0未知来源应用安装权限方案
    private void check() {
        boolean haveInstallPermission;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //先获取是否有安装未知来源应用的权限
            haveInstallPermission = getPackageManager().canRequestPackageInstalls();
            if (!haveInstallPermission) {//没有权限
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("安装应用需要打开未知来源权限，请去设置中开启权限");
                builder.setPositiveButton("开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            startInstallPermissionSettingActivity();
                        }
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create().show();
                return;
            }
        }
        //有权限，开始安装应用程序
        checkVersion();
    }

    //版本更新
    private void checkVersion() {
        XPermission.requestPermissions(this, 101, new String[]{Manifest.permission
                .WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, new XPermission.OnPermissionListener() {
            //权限申请成功时调用
            @Override
            public void onPermissionGranted() {
                XHttp.obtain().get(Path.check_android_ver, new HashMap<String, Object>(), new HttpCallBack<CheckVersionBean>() {
                    @Override
                    public void onSuccess(CheckVersionBean bean) {
                        if(bean.getData().isStatus()){
                            if(bean.getData().getVerCode()>XAppUtils.getVersionCode(SettingActivity.this)){
                                normalUpdate(SettingActivity.this,bean.getData().getVerName(),bean.getData().getAppFile(),bean.getData().getUpdateReason());
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
                XPermission.showTipsDialog(SettingActivity.this);
            }
        });
    }

    /**
     * 正常更新
     */
    private void normalUpdate(final Context context, final String appName, final String downUrl,  String updateinfo) {

        dialogUpdate.show();
        TextView titleTv=dialogUpdate.findViewById(R.id.tv_update_title);
        TextView infoTv=dialogUpdate.findViewById(R.id.tv_update_info);
        TextView cancelTv=dialogUpdate.findViewById(R.id.tv_update_cancel);
        TextView sureTv=dialogUpdate.findViewById(R.id.tv_update_sure);
        titleTv.setText(appName+"更新");
        infoTv.setText(Html.fromHtml(updateinfo));
        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //提示框取消
                dialogUpdate.dismiss();
            }
        });
        sureTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogUpdate.dismiss();
                downloading(context,appName,downUrl);
            }
        });
    }

    //下载中
    private void downloading(final Context context, final String appName, final String downUrl){
        dialogDownloading.show();
        final SeekBar seekBar=dialogDownloading.findViewById(R.id.download_bar);
        TextView tvCancel=dialogDownloading.findViewById(R.id.tv_cancel);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InstallUtils.cancleDownload();
            }
        });
        InstallUtils.with(context)
                .setApkUrl(downUrl)
                .setApkName(appName)
                .setCallBack(new InstallUtils.DownloadCallBack() {
                    @Override
                    public void onStart() {//下载开始
                        dialogDownloading.setCancelable(false);
                    }

                    @Override
                    public void onComplete(String path) {//下载完成
                        dialogDownloading.dismiss();
                        InstallUtils.installAPK(context, path, new InstallUtils.InstallCallBack() {//安装APK
                            @Override
                            public void onSuccess() {
                            }

                            @Override
                            public void onFail(Exception e) {
                            }
                        });
                    }

                    @Override
                    public void onLoading(long total, long current) {//下载中
                        seekBar.setMax((int) total);
                        seekBar.setProgress((int) current);
                    }

                    @Override
                    public void onFail(Exception e) {//下载失败

                    }

                    @Override
                    public void cancle() {//下载取消
                        dialogDownloading.dismiss();
                    }
                }).startDownload();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startInstallPermissionSettingActivity() {
        Uri packageURI = Uri.parse("package:" + getPackageName());
        //注意这个是8.0新API
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
        startActivityForResult(intent, 120);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10086 &&  resultCode == RESULT_OK) {
            checkVersion();
        }
    }

    private void dosomething() {
        try {
            if(clearType==1){
                size= DataCleanManager.getTotalCacheSize(getApplication());
            }else if(clearType==2){
                DataCleanManager.clearAllCache(SettingActivity.this);
                size=DataCleanManager.getTotalCacheSize(getApplication());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        handler.sendMessage(Message.obtain());
    }
    private static class MyThread extends Thread {
        WeakReference<SettingActivity> mThreadActivityRef;

        public MyThread(SettingActivity activity) {
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
