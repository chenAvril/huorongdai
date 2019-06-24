package com.example.administrator.huorongdai.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.huorongdai.Path;
import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.base.BaseActivity;
import com.example.administrator.huorongdai.gsonbean.ChangePwdBean;
import com.example.administrator.huorongdai.xframe.utils.XEmptyUtils;
import com.example.administrator.huorongdai.xframe.utils.XNetworkUtils;
import com.example.administrator.huorongdai.xframe.utils.XPreferencesUtils;
import com.example.administrator.huorongdai.xframe.utils.http.HttpCallBack;
import com.example.administrator.huorongdai.xframe.utils.http.XHttp;
import com.example.administrator.huorongdai.xframe.widget.XToast;

import java.util.HashMap;
import java.util.Map;

public class ChangePasswordActivity extends BaseActivity {

    private TextView tvForgetPwd;//忘记密码

    private EditText etInitPwd;//原密码
    private EditText etNewPwd;//新密码
    private EditText etNewPwdSure;//确认新密码
    private TextView btnChange;//确认修改

    private String title;
    private String custMobile;//手机号码
    private String custPwd;//密文密码

    @Override
    protected int getContentView() {
        return R.layout.activity_change_password;
    }

    @Override
    protected void initView() {
        tvForgetPwd=findViewById(R.id.tv_forget_pwd);
        tvForgetPwd.setOnClickListener(this);
        etInitPwd=findViewById(R.id.et_init_pwd);
        etNewPwd=findViewById(R.id.et_new_pwd);
        etNewPwdSure=findViewById(R.id.et_new_pwd_sure);
        btnChange=findViewById(R.id.btn_change);
        btnChange.setOnClickListener(this);

        Intent intent=getIntent();
        if(intent!=null){
            title=intent.getStringExtra("title");
            if("login".equals(title)){
                tvForgetPwd.setVisibility(View.GONE);
                setTitle("修改登录密码");
            }else if("trade".equals(title)){
                setTitle("修改投资密码");

                etInitPwd.setHint("请输入原投资密码");
                etNewPwd.setHint("请输入新密码(长度8-20位)");
            }
        }

        custMobile = (String) XPreferencesUtils.get("custMobile","");
        custPwd = (String) XPreferencesUtils.get("custPwd","");
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_forget_pwd://忘记密码
                Intent intent=new Intent(this,FindTradePwdActivity.class);
                startActivityForResult(intent,1003);
                break;
            case R.id.btn_change://确认修改
                String initPwd=etInitPwd.getText().toString().trim();
                String newPwd=etNewPwd.getText().toString().trim();
                String newPwdSure=etNewPwdSure.getText().toString().trim();

                if(XEmptyUtils.isEmpty(initPwd)){
                    XToast.normal("原密码不能为空");
                    return;
                }
                if(XEmptyUtils.isEmpty(newPwd)){
                    XToast.normal("新密码不能为空");
                    return;
                }
                if(XEmptyUtils.isEmpty(newPwdSure)){
                    XToast.normal("确认密码不能为空");
                    return;
                }

                if("login".equals(title)){
                    if(newPwd.length()<6){
                        XToast.normal("新密码长度必选大于或者等于6位");
                        return;
                    }
                }else if("trade".equals(title)){
                    if(newPwd.length()<8 || newPwd.length()>20){
                        XToast.normal("投资密码长度必须是8-20位");
                        return;
                    }
                }
                if(isNumeric(newPwd)){
                    XToast.normal("新密码不能是纯数字");
                    return;
                }
                if(initPwd.equals(newPwd)){
                    XToast.normal("新密码不能与原密码相同!");
                    return;
                }
                if(newPwd.equals(newPwdSure)){
                    if(XNetworkUtils.isConnected()){
                        if("login".equals(title)){
                            changeLoginPwd(initPwd,newPwd); //修改登录密码
                        }else if("trade".equals(title)){
                            changeTradePwd(initPwd,newPwd);//修改交易密码
                        }
                    }else {
                        XToast.normal("网络连接失败!");
                    }
                }else {
                    XToast.normal("两次输入的新密码不一致!");
                }
                break;
        }
    }

    //修改登录密码
    private void changeLoginPwd(String initPwd, final String newPwd) {
        Map<String,Object > params =new HashMap<>();
        params .put("custMobile",custMobile);
        params .put("custPwd",custPwd);
        params .put("oldCustPwd",initPwd);
        params .put("newCustPwd",newPwd);
        XHttp.obtain().post(Path.alter_customer_info, params, new HttpCallBack<ChangePwdBean>() {
            @Override
            public void onSuccess(ChangePwdBean bean) {
                XToast.normal(bean.getMessage());
                if(bean.isStatus()){
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
                    setResult(1003);
                    startActivity(new Intent(ChangePasswordActivity.this,LoginActivity.class));
                    finish();
                }
            }

            @Override
            public void onFailed(String error) {

            }
        });
    }

    //修改交易密码
    private void changeTradePwd(String initPwd, String newPwd) {
        Map<String,Object > params =new HashMap<>();
        params .put("custMobile",custMobile);
        params .put("custPwd",custPwd);
        params .put("tradePwd",initPwd);
        params .put("confirmTradePwd",newPwd);
        XHttp.obtain().post(Path.update_trade_pwd, params, new HttpCallBack<ChangePwdBean>() {
            @Override
            public void onSuccess(ChangePwdBean bean) {

                if(bean.isStatus()){
                    XToast.normal("修改成功");
                    finish();
                }else {
                    XToast.normal(bean.getMessage());
                }
            }

            @Override
            public void onFailed(String error) {

            }
        });
    }

    /** * 纯数字
     * @param str
     * @return */
    public boolean isNumeric(String str){
        for (int i = str.length();--i>=0;){
            if (!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return true;
    }
}
