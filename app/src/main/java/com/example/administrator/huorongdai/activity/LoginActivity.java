package com.example.administrator.huorongdai.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.huorongdai.MainActivity;
import com.example.administrator.huorongdai.Path;
import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.base.BaseActivity;
import com.example.administrator.huorongdai.eventbusbean.Msg;
import com.example.administrator.huorongdai.gsonbean.UserBean;
import com.example.administrator.huorongdai.xframe.utils.XEmptyUtils;
import com.example.administrator.huorongdai.xframe.utils.XEncryptUtils;
import com.example.administrator.huorongdai.xframe.utils.XPreferencesUtils;
import com.example.administrator.huorongdai.xframe.utils.XRegexUtils;
import com.example.administrator.huorongdai.xframe.utils.http.HttpCallBack;
import com.example.administrator.huorongdai.xframe.utils.http.XHttp;
import com.example.administrator.huorongdai.xframe.utils.statusbar.XStatusBar;
import com.example.administrator.huorongdai.xframe.widget.XToast;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends BaseActivity {
    private ImageView ivBack;//页面返回
    private EditText etUserNumber;//登录账号
    private EditText etUserPsw;//登录密码
    private Button btnLogin;//登录按钮
    private TextView tvRegister;//进行注册
    private TextView tvForgetPsw;//忘记密码

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void setStatusBar() {
        XStatusBar.setColor(this, getResources().getColor(R.color.gray_bg), 30);
    }

    @Override
    protected void initView() {
        getTitleBar().setVisibility(View.GONE);
        ivBack=findViewById(R.id.iv_back);
        etUserNumber=findViewById(R.id.et_user_number);
        etUserPsw=findViewById(R.id.et_user_psw);
        btnLogin=findViewById(R.id.btn_login);
        tvRegister=findViewById(R.id.tv_register);
        tvForgetPsw=findViewById(R.id.tv_forget_psw);
        ivBack.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
        tvForgetPsw.setOnClickListener(this);

        etUserNumber.setText("13515661980");
        etUserPsw.setText("fang123456");
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back://页面返回
                finish();
                break;
            case R.id.btn_login://登录按钮
                String userNumber=etUserNumber.getText().toString().trim();
                String psw=etUserPsw.getText().toString().trim();
                if(!XRegexUtils.checkMobile(userNumber)){
                    XToast.normal("请输入正确的手机号");
                    return;
                }
                if(XEmptyUtils.isEmpty(psw)){
                    XToast.normal("请输入密码");
                    return;
                }
                requestLogin(userNumber,psw);
                break;
            case R.id.tv_register://进行注册
                startActivity(new Intent(this,RegisterActivity.class));
                break;
            case R.id.tv_forget_psw://忘记密码
                startActivity(new Intent(this,ForgetPswActivity.class));
                break;
        }
    }

    private void requestLogin(String userNumber, String psw) {
        Map<String,Object > params =new HashMap<>();
        params .put("custMobile",userNumber);
//        params .put("custPwd", XEncryptUtils.MD5(psw));
        params .put("custPwd", psw);
        params .put("optSource","4");
        XHttp.obtain().post(Path.login_url, params, new HttpCallBack<UserBean>() {
            @Override
            public void onSuccess(UserBean bean) {
                if(bean.isStatus()){
                    XPreferencesUtils.put("isLogin",true);
                    XPreferencesUtils.put("custMobile",bean.getCustomer().getCustMobile());//手机号码
                    XPreferencesUtils.put("custName",bean.getCustomer().getCustName());//登陆账号
                    if(!XEmptyUtils.isEmpty(bean.getCustomer().getHeadIcon())){
                        XPreferencesUtils.put("headIcon",Path.BASE_ImgURL+bean.getCustomer().getHeadIcon());
                    }
                    XPreferencesUtils.put("custPwd",bean.getCustomer().getCustPwd());//密文密码
                    XPreferencesUtils.put("custId",bean.getCustomer().getId());//用户ID
                    //未开户是4，开户是2
                    XPreferencesUtils.put("openStatus",bean.getCustomer().getOpenStatus());
                    //1-未认证 2-认证
                    XPreferencesUtils.put("emailAuth",bean.getCustomer().getEmailAuth());
                    XPreferencesUtils.put("custEmail",bean.getCustomer().getCustEmail());//电子邮箱

                    XToast.normal("登录成功");
                    EventBus.getDefault().post(new Msg("4"));
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
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

    @Override
    public void onBackPressed() {
        finish();
        //super.onBackPressed();
    }
}
