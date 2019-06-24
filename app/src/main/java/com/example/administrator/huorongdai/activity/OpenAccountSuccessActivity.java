package com.example.administrator.huorongdai.activity;

import android.view.View;
import android.widget.TextView;

import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.base.BaseActivity;
import com.example.administrator.huorongdai.eventbusbean.Msg;

import org.greenrobot.eventbus.EventBus;

public class OpenAccountSuccessActivity extends BaseActivity {

    private TextView backHomeBtn;//回到首页
    private TextView gotoAccountBtn;//前往账户

    @Override
    protected int getContentView() {
        return R.layout.activity_open_account_sucess;
    }

    @Override
    protected void initView() {
        setTitle("结果");
        backHomeBtn=findViewById(R.id.btn_back_home);
        gotoAccountBtn=findViewById(R.id.btn_goto_account);
        gotoAccountBtn.setOnClickListener(this);
        backHomeBtn.setOnClickListener(this);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_back_home://回到首页
                EventBus.getDefault().post(new Msg("4"));
                finish();
                break;
            case R.id.btn_goto_account://前往账户
                EventBus.getDefault().post(new Msg("3"));
                finish();
                break;
        }

    }
}
