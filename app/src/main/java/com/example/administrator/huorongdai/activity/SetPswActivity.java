package com.example.administrator.huorongdai.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrator.huorongdai.Path;
import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.base.BaseActivity;
import com.example.administrator.huorongdai.eventbusbean.ForgetPswMsg;
import com.example.administrator.huorongdai.gsonbean.ResCodeBean;
import com.example.administrator.huorongdai.xframe.utils.XEmptyUtils;
import com.example.administrator.huorongdai.xframe.utils.XNetworkUtils;
import com.example.administrator.huorongdai.xframe.utils.http.HttpCallBack;
import com.example.administrator.huorongdai.xframe.utils.http.XHttp;
import com.example.administrator.huorongdai.xframe.widget.XToast;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

public class SetPswActivity extends BaseActivity {

    private EditText userPswEt;//密码
    private EditText userPsSureEt;//确认密码
    private Button sureBtn;//确认
    private String custMobile;//手机号码

    @Override
    protected int getContentView() {
        return R.layout.activity_set_psw;
    }

    @Override
    protected void initView() {
        setTitle("设置密码");
        custMobile=getIntent().getStringExtra("custMobile");

        userPswEt=findViewById(R.id.et_user_number);
        userPsSureEt=findViewById(R.id.et_user_number_sure);
        sureBtn=findViewById(R.id.btn_sure);

        sureBtn.setOnClickListener(this);


    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_sure:
                String psw=userPswEt.getText().toString().trim();//新登录密码
                String pswSure=userPsSureEt.getText().toString().trim();//确认登录密码

                if(XEmptyUtils.isEmpty(psw)){
                    XToast.normal("新登录密码不能为空");
                    return;
                }

                if(psw.length()<6){
                    XToast.normal("新登录密码长度必选大于或者等于6位");
                    return;
                }
                if(isNumeric(psw)){
                    XToast.normal("新登录密码不能是纯数字");
                    return;
                }

                if(XEmptyUtils.isEmpty(pswSure)){
                    XToast.normal("再次确认登录密码不能为空");
                    return;
                }

                if(psw.equals(pswSure)){
                    if(XNetworkUtils.isConnected()){
                        setPsw(custMobile,psw);
                    }else {
                        XToast.normal("网络连接失败!");
                    }
                }else {
                    XToast.normal("两次密码输入不一致");
                }

                break;
        }
    }

    //进行注册
    private void setPsw(String userNumber, String password) {
        Map<String,Object > params =new HashMap<>();
        params.put("newCustPwd",password);//明文
        params.put("custMobile",userNumber);
        XHttp.obtain().post(Path.find_account_psw, params, new HttpCallBack<ResCodeBean>() {
            @Override
            public void onSuccess(ResCodeBean bean) {
                if(bean.isStatus()){
                    EventBus.getDefault().post(new ForgetPswMsg(1));
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
