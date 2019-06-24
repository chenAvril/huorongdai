package com.example.administrator.huorongdai.activity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.base.BaseActivity;
import com.example.administrator.huorongdai.xframe.utils.permission.XPermission;

public class OnlineCustomerServiceActivity extends BaseActivity {

    private RelativeLayout rlKefu;//客服电话
    private RelativeLayout rlTousu;//投诉电话

    @Override
    protected int getContentView() {
        return R.layout.activity_online_customer_service;
    }

    @Override
    protected void initView() {
        setTitle("在线客服");

        rlKefu=findViewById(R.id.rl_kefu_phone);
        rlTousu=findViewById(R.id.rl_tousu_phone);
        rlKefu.setOnClickListener(this);
        rlTousu.setOnClickListener(this);

    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_kefu_phone://客服电话
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
                        XPermission.showTipsDialog(OnlineCustomerServiceActivity.this);
                    }
                });
                break;
            case R.id.rl_tousu_phone:
                XPermission.requestPermissions(this, 100, new String[]{Manifest.permission
                        .CALL_PHONE}, new XPermission.OnPermissionListener() {
                    //权限申请成功时调用
                    @Override
                    public void onPermissionGranted() {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:055169106647"));
                        startActivity(intent);
                    }
                    //权限被用户禁止时调用
                    @Override
                    public void onPermissionDenied() {
                        //给出友好提示，并且提示启动当前应用设置页面打开权限
                        XPermission.showTipsDialog(OnlineCustomerServiceActivity.this);
                    }
                });
                break;
        }
    }
}
