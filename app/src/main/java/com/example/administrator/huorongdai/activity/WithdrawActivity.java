package com.example.administrator.huorongdai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.huorongdai.Path;
import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.base.BaseActivity;
import com.example.administrator.huorongdai.gsonbean.RealTimeCustBean;
import com.example.administrator.huorongdai.view.MyScrollView;
import com.example.administrator.huorongdai.xframe.utils.XEmptyUtils;
import com.example.administrator.huorongdai.xframe.utils.XNetworkUtils;
import com.example.administrator.huorongdai.xframe.utils.XPreferencesUtils;
import com.example.administrator.huorongdai.xframe.utils.http.HttpCallBack;
import com.example.administrator.huorongdai.xframe.utils.http.XHttp;
import com.example.administrator.huorongdai.xframe.widget.XToast;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class WithdrawActivity extends BaseActivity {
    private MyScrollView scrollView;
    private EditText accountBalanceEt;//账户余额
    private EditText withdrawMoneyEt;//提现金额
    private EditText moneyToAccountEt;//实际到账

    private String custMobile;
    private String custPwd;//密文密码
    private String custName;

    private TextView btnWithdraw;//提现按钮
    private SwipeRefreshLayout refreshLayout;

    private Handler handler=new Handler(new MyCallback());

    private final class MyCallback implements Handler.Callback{

        @Override
        public boolean handleMessage(Message message) {
            Map<String,Object > params =new HashMap<>();
            params .put("custMobile",custMobile);
            params .put("custName",custName);
            params .put("custPwd",custPwd);
            XHttp.obtain().post(Path.get_geal_time_cust, params, new HttpCallBack<RealTimeCustBean>() {
                @Override
                public void onSuccess(RealTimeCustBean bean) {
                    if(bean.isStatus()){
                        accountBalanceEt.setText(bean.getAvailAmt());
                        withdrawMoneyEt.setText("");
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
        return R.layout.activity_withdraw;
    }

    @Override
    protected void initView() {
        setTitle("提现");

        scrollView=findViewById(R.id.msl_withdraw);
        accountBalanceEt=findViewById(R.id.et_account_balance);
        withdrawMoneyEt=findViewById(R.id.et_withdraw_money);
        moneyToAccountEt=findViewById(R.id.et_money_to_account);
        btnWithdraw=findViewById(R.id.btn_withdraw);
        refreshLayout=findViewById(R.id.srl_withdraw);
        refreshLayout.setColorScheme(R.color.word_red);
        refreshLayout.setOnRefreshListener(refreshListener);
        btnWithdraw.setOnClickListener(this);
        withdrawMoneyEt.addTextChangedListener(new MyWatcher());

        //解决swiperefreshlayout与scrollview的冲突问题
        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if (refreshLayout != null) {
                    refreshLayout.setEnabled(scrollView.getScrollY() == 0);
                }
            }
        });

        custMobile = (String) XPreferencesUtils.get("custMobile","");
        custPwd = (String) XPreferencesUtils.get("custPwd","");
        custName= (String) XPreferencesUtils.get("custName","");
    }

    SwipeRefreshLayout.OnRefreshListener refreshListener=new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            if(XNetworkUtils.isConnected()){
                handler.sendMessage(Message.obtain());
            }else {
                accountBalanceEt.setText("");
                XToast.normal("网络连接失败!");
            }

            if(refreshLayout.isRefreshing()){
                refreshLayout.setRefreshing(false);
            }

        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        refreshLayout.setRefreshing(true);
        refreshListener.onRefresh();
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_withdraw:
                if(!XNetworkUtils.isConnected()){
                    return;
                }
                String withdrawMoney=withdrawMoneyEt.getText().toString().trim();
                if(XEmptyUtils.isEmpty(withdrawMoney)){
                    return;
                }
                String balance=accountBalanceEt.getText().toString().trim();
                if(XEmptyUtils.isEmpty(balance)){
                    return;
                }
                double yve=Double.valueOf(balance);
                double tixian=Double.valueOf(withdrawMoney);
                if(yve<tixian){
                    XToast.normal("提现金额不能大于账户余额,请重新输入!");
                    withdrawMoneyEt.setText("");
                    return;
                }

                if(tixian<2||tixian==2){
                    XToast.normal("提现金额必须大于2,请重新输入!");
                    withdrawMoneyEt.setText("");
                    return;
                }

                String url= Path.withdraw+"?custMobile="+custMobile+"&custName="+custName+"&custPwd="+custPwd+"&optSource=4&withDrawAmt="+tixian+"&backUrl=http://www.huorongdai.com/";
                Intent intent=new Intent(this,HtmlActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("url",url);
                bundle.putString("title", "提现");
                intent.putExtra("bundle",bundle);
                startActivity(intent);
                break;
        }

    }

    class MyWatcher  implements TextWatcher {
        DecimalFormat decimal=new DecimalFormat("0.00");
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            //限制小数点后两位
            if (charSequence.toString().contains(".")) {
                if (charSequence.length() - 1 - charSequence.toString().indexOf(".") > 2) {
                    charSequence = charSequence.toString().subSequence(0, charSequence.toString().indexOf(".") + 3);
                    withdrawMoneyEt.setText(charSequence);
                    withdrawMoneyEt.setSelection(charSequence.length());
                }
            }
            if (charSequence.toString().trim().substring(0).equals(".")) {
                charSequence = "0" + charSequence;
                withdrawMoneyEt.setText(charSequence);
                withdrawMoneyEt.setSelection(2);
            }
            if (charSequence.toString().startsWith("0") && charSequence.toString().trim().length() > 1) {
                if (!charSequence.toString().substring(1, 2).equals(".")) {
                    withdrawMoneyEt.setText(charSequence.subSequence(0, 1));
                    withdrawMoneyEt.setSelection(1);
                    return;
                }
            }

            //计算实际到账金额
            if(!XEmptyUtils.isEmpty(charSequence.toString())){
                double moneyToAccount=Double.valueOf(charSequence.toString());
                if(moneyToAccount>2){
                    moneyToAccount=moneyToAccount-2;
                    moneyToAccountEt.setText(decimal.format(moneyToAccount));
                }else {
                    moneyToAccountEt.setText("");
                }
            }else {
                moneyToAccountEt.setText("");
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    }
}
