package com.example.administrator.huorongdai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.huorongdai.Path;
import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.base.BaseActivity;
import com.example.administrator.huorongdai.gsonbean.AccountInfo;
import com.example.administrator.huorongdai.xframe.utils.XEmptyUtils;
import com.example.administrator.huorongdai.xframe.utils.XNetworkUtils;
import com.example.administrator.huorongdai.xframe.utils.XPreferencesUtils;
import com.example.administrator.huorongdai.xframe.utils.http.HttpCallBack;
import com.example.administrator.huorongdai.xframe.utils.http.XHttp;
import com.example.administrator.huorongdai.xframe.widget.XToast;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class LookMoneyActivity extends BaseActivity {

    private SwipeRefreshLayout refreshLayout;
    private TextView rechargeBtn;//充值
    private TextView withdrawBtn;//提现
    private String custMobile;
    private String custPwd;//密文密码
    private TextView totalAssetTv;//资产总额
    private TextView availAmtTv;//可用余额
    private TextView frzAmtTv;//冻结金额
    private TextView unEarnCaptialTv;//待收本金
    private TextView unEarnIintTv;//待收利息

    private DecimalFormat decimal;

    private Handler handler=new Handler(new MyCallback());
    private final class MyCallback implements Handler.Callback{

        @Override
        public boolean handleMessage(Message message) {
            Map<String,Object > params =new HashMap<>();
            params .put("custMobile",custMobile);
            params .put("custPwd",custPwd);
            XHttp.obtain().post(Path.account_info, params, new HttpCallBack<AccountInfo>() {
                @Override
                public void onSuccess(AccountInfo bean) {
                    if(bean.isStatus()){

                        double availAmt=0.00;
                        if(!XEmptyUtils.isEmpty(bean.getAvailAmt())){
                            availAmt=Double.valueOf(bean.getAvailAmt());
                        }
                        double frzAmt=0.00;
                        if(!XEmptyUtils.isEmpty(bean.getFrzAmt())){
                            frzAmt=Double.valueOf(bean.getFrzAmt());
                        }

                        availAmtTv.setText("¥"+decimal.format(availAmt));
                        frzAmtTv.setText("¥"+decimal.format(frzAmt));
                        unEarnCaptialTv.setText("¥"+decimal.format(bean.getUnEarnCaptial()));
                        unEarnIintTv.setText("¥"+decimal.format(bean.getUnEarnIint()));

                        Double totalAsset=availAmt+frzAmt+bean.getUnEarnCaptial()+bean.getUnEarnIint();
                        totalAssetTv.setText(decimal.format(totalAsset));
                    }
                }

                @Override
                public void onFailed(String error) {

                }
            });
            return true;
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_look_money;
    }

    @Override
    protected void initView() {
        setTitle("资金总览");
        refreshLayout=findViewById(R.id.srl_look_money);
        rechargeBtn=findViewById(R.id.btn_recharge);
        withdrawBtn=findViewById(R.id.btn_withdraw);
        availAmtTv=findViewById(R.id.tv_availAmt);
        frzAmtTv=findViewById(R.id.tv_frzAmt);
        unEarnCaptialTv=findViewById(R.id.tv_unEarnCaptial);
        unEarnIintTv=findViewById(R.id.tv_unEarnIint);
        totalAssetTv=findViewById(R.id.tv_total_asset);

        refreshLayout.setColorScheme(R.color.word_red);
        refreshLayout.setOnRefreshListener(refreshListener);
        rechargeBtn.setOnClickListener(this);
        withdrawBtn.setOnClickListener(this);

        decimal=new DecimalFormat("0.00");
        custMobile = (String) XPreferencesUtils.get("custMobile","");
        custPwd = (String) XPreferencesUtils.get("custPwd","");
    }

    SwipeRefreshLayout.OnRefreshListener refreshListener=new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {

            if(XNetworkUtils.isConnected()){
                handler.sendMessage(Message.obtain());
            }else {
                totalAssetTv.setText("");
                availAmtTv.setText("");
                frzAmtTv.setText("");
                unEarnCaptialTv.setText("");
                unEarnIintTv.setText("");
                XToast.normal("网络连接失败!");
            }

            if(refreshLayout.isRefreshing()){
                refreshLayout.setRefreshing(false);
            }
        }
    };

    @Override
    protected void loadData() {
        refreshLayout.setRefreshing(true);
        refreshListener.onRefresh();
    }

    @Override
    public void onClick(View view) {
        boolean isLogin= (boolean) XPreferencesUtils.get("isLogin",false);
        if(isLogin){
            //未开户是4，开户是2
            int openStatus= (int) XPreferencesUtils.get("openStatus",4);
            //openStatus=2;
            switch (view.getId()){
                case R.id.btn_recharge://充值
                    if(openStatus==4){
                        Intent intent=new Intent(this,OpenAccountActivity.class);
                        startActivity(intent);
                    }else if(openStatus==2){
                        Intent intent=new Intent(this, RechargeActivity.class);
                        startActivity(intent);
                    }
                    break;
                case R.id.btn_withdraw://提现
                    if(openStatus==4){
                        Intent intent=new Intent(this,OpenAccountActivity.class);
                        startActivity(intent);
                    }else if(openStatus==2){
                        Intent intent=new Intent(this, WithdrawActivity.class);
                        startActivity(intent);
                    }
                    break;
            }
        }
    }
}
