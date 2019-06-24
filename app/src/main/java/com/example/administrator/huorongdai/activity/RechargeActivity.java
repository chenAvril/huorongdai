package com.example.administrator.huorongdai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.huorongdai.Path;
import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.base.BaseActivity;
import com.example.administrator.huorongdai.xframe.utils.XEmptyUtils;
import com.example.administrator.huorongdai.xframe.utils.XPreferencesUtils;
import com.example.administrator.huorongdai.xframe.widget.XToast;

public class RechargeActivity extends BaseActivity {

    private EditText rechargeMoneyEt;//充值金额D
    private TextView rechargeBtn;//充值按钮

    private String custMobile;
    private String custPwd;//密文密码
    private String custName;

    @Override
    protected int getContentView() {
        return R.layout.activity_recharge;
    }

    @Override
    protected void initView() {
        setTitle("充值");

        rechargeMoneyEt=findViewById(R.id.et_recharge_money);
        rechargeBtn=findViewById(R.id.btn_recharge);
        rechargeBtn.setOnClickListener(this);

        custMobile = (String) XPreferencesUtils.get("custMobile","");
        custPwd = (String) XPreferencesUtils.get("custPwd","");
        custName= (String) XPreferencesUtils.get("custName","");
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_recharge://充值按钮
                String m=rechargeMoneyEt.getText().toString().trim();
                if(XEmptyUtils.isEmpty(m)){
                    return;
                }
                int money=Integer.parseInt(m);
                if(money<100){
                    XToast.normal("充值金额不能小于100");
                    return;
                }
                if(money%100!=0){
                    XToast.normal("充值金额必须是100元的整数倍");
                    return;
                }

                recharge(money);
                break;
        }
    }

    private void recharge(int reChargeAmt) {
//        Map<String,Object > params =new HashMap<>();
//        params .put("custMobile",custMobile);
//        params .put("custPwd",custPwd);
//        params .put("optSource","4");
//        params .put("backUrl","123");
//        params .put("reChargeAmt",reChargeAmt+"");
        String url=Path.recharge+"?custMobile="+custMobile+"&custName="+custName+"&custPwd="+custPwd+"&optSource=4&reChargeAmt="+reChargeAmt+"&backUrl=http://www.huorongdai.com/";
        Intent intent=new Intent(RechargeActivity.this,HtmlActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("url",url);
        bundle.putString("title", "充值");
        intent.putExtra("bundle",bundle);
        startActivity(intent);
    }
}
