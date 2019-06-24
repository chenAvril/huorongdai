package com.example.administrator.huorongdai.activity;

import android.view.View;
import android.widget.TextView;

import com.example.administrator.huorongdai.Path;
import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.base.BaseActivity;
import com.example.administrator.huorongdai.gsonbean.CustInfoBean;
import com.example.administrator.huorongdai.xframe.utils.XNetworkUtils;
import com.example.administrator.huorongdai.xframe.utils.XPreferencesUtils;
import com.example.administrator.huorongdai.xframe.utils.XRegexUtils;
import com.example.administrator.huorongdai.xframe.utils.http.HttpCallBack;
import com.example.administrator.huorongdai.xframe.utils.http.XHttp;
import com.example.administrator.huorongdai.xframe.widget.XToast;

import java.util.HashMap;
import java.util.Map;

public class IDCardActivity extends BaseActivity {

    private TextView tvName;//名字
    private TextView tvIdNumber;//身份证号码

    private  String custMobile;//客户手机号码
    private String custPwd;//密文密码

    @Override
    protected int getContentView() {
        return R.layout.activity_idcard;
    }

    @Override
    protected void initView() {
        setTitle("实名认证");

        tvName=findViewById(R.id.tv_real_name);
        tvIdNumber=findViewById(R.id.tv_id_card_number);
        custMobile= (String) XPreferencesUtils.get("custMobile","");
        custPwd = (String) XPreferencesUtils.get("custPwd","");
    }

    @Override
    protected void loadData() {
        if(XNetworkUtils.isConnected()){
            requestID();
        }else {
            XToast.normal("网络连接失败!");
        }
    }

    //请求身份证号码
    private void requestID(){
        Map<String,Object > params2 =new HashMap<>();
        params2.put("custMobile",custMobile);
        params2.put("custPwd",custPwd);
        XHttp.obtain().post(Path.cust_info, params2, new HttpCallBack<CustInfoBean>() {
            @Override
            public void onSuccess(CustInfoBean bean) {
                if(bean.isStatus()){
                    String realName=bean.getCustomer().getCustRealName();
                    if(realName.length()>1){
                        int num=realName.length()-1;
                        realName=realName.substring(0,1);
                        for (int i = 0; i < num; i++) {
                            realName=realName+"*";
                        }
                        tvName.setText(realName);
                    }else {
                        tvName.setText(realName);
                    }
                    tvIdNumber.setText(XRegexUtils.idHide(bean.getCustomer().getCardNum()));
                }
            }

            @Override
            public void onFailed(String error) {

            }
        });
    }

    @Override
    public void onClick(View view) {

    }
}
