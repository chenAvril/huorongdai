package com.example.administrator.huorongdai.activity;

import android.content.Intent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.administrator.huorongdai.Path;
import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.base.BaseActivity;
import com.example.administrator.huorongdai.gsonbean.QueryLockBean;
import com.example.administrator.huorongdai.view.lockview.LockMode;
import com.example.administrator.huorongdai.xframe.utils.XEmptyUtils;
import com.example.administrator.huorongdai.xframe.utils.XPreferencesUtils;
import com.example.administrator.huorongdai.xframe.utils.http.HttpCallBack;
import com.example.administrator.huorongdai.xframe.utils.http.XHttp;

import java.util.HashMap;
import java.util.Map;


public class LockSettingActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {

    private TextView tvStatus;//手势开关状态
    private Switch switchStatus;//手势开关状态
    private RelativeLayout rlChangeLock;//修改手势密码
    private TextView tvLine;
    private String custId;//用户id

    private String handLock;//手势密码

    @Override
    protected int getContentView() {
        return R.layout.activity_lock_setting;
    }

    @Override
    protected void initView() {
        setTitle("手势密码设置");

        tvStatus=findViewById(R.id.tv_lock_status);
        rlChangeLock=findViewById(R.id.rl_change_lock);
        rlChangeLock.setOnClickListener(this);
        switchStatus=findViewById(R.id.switch_lock_status);
        tvLine=findViewById(R.id.tv_line);
        custId= (String) XPreferencesUtils.get("custId","");//用户ID
        switchStatus.setOnCheckedChangeListener(this);//注册
    }

    @Override
    protected void onResume() {
        super.onResume();
        queryPwd();
    }

    @Override
    protected void loadData() {

    }

    private void queryPwd(){
        Map<String , Object> map =new HashMap<>();
        map.put("custId",custId);
        XHttp.obtain().post(Path.query_hand_lock, map, new HttpCallBack<QueryLockBean>() {
            @Override
            public void onSuccess(QueryLockBean bean) {
                //imeitype手势密码类型   1 未设置 2 已设置
                if(bean.isStatus()){
                    if(!XEmptyUtils.isEmpty(bean.getImeitype())){
                        if(bean.getImeitype()==1){
                            tvStatus.setText("关闭");
                            switchStatus.setChecked(false);

                        }else if(bean.getImeitype()==2){
                            tvStatus.setText("开启");
                            switchStatus.setChecked(true);
                        }
                    }
                    if(!XEmptyUtils.isEmpty(bean.getImei())){
                        handLock=bean.getImei();
                        rlChangeLock.setVisibility(View.VISIBLE);
                        tvLine.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailed(String error) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_change_lock://修改手势密码
                Intent intent=new Intent(this, HandLockActivity.class);
                intent.putExtra("lock_mode", LockMode.EDIT_PASSWORD);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if(b){
            tvStatus.setText("开启");
            if(XEmptyUtils.isEmpty(handLock)){
                Intent intent=new Intent(LockSettingActivity.this, HandLockActivity.class);
                intent.putExtra("lock_mode", LockMode.SETTING_PASSWORD);
                startActivity(intent);
            }else {
                changePwd(handLock,2);
            }
        }else {
            tvStatus.setText("关闭");
            changePwd(handLock,1);
        }
    }


    private void changePwd(String imei, final int type){
        Map<String , Object> map =new HashMap<>();
        map.put("custId",custId);
        map.put("imei",imei);//密码
        map.put("imeitype",type+"");//手势密码类型   1 未设置 2 已设置
        XHttp.obtain().post(Path.change_hand_lock, map, new HttpCallBack<QueryLockBean>() {
            @Override
            public void onSuccess(QueryLockBean bean) {
                if(!bean.isStatus()){
                    if(type==1){
                        switchStatus.setChecked(true);
                    }else if(type==2){
                        switchStatus.setChecked(false);
                    }
                }
            }

            @Override
            public void onFailed(String error) {

            }
        });
    }
}
