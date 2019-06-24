package com.example.administrator.huorongdai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.huorongdai.Path;
import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.base.BaseActivity;
import com.example.administrator.huorongdai.gsonbean.ResCodeBean;
import com.example.administrator.huorongdai.view.MyCountDownTimer;
import com.example.administrator.huorongdai.xframe.utils.XEmptyUtils;
import com.example.administrator.huorongdai.xframe.utils.XEncryptUtils;
import com.example.administrator.huorongdai.xframe.utils.XNetworkUtils;
import com.example.administrator.huorongdai.xframe.utils.XRegexUtils;
import com.example.administrator.huorongdai.xframe.utils.log.XLog;
import com.example.administrator.huorongdai.xframe.widget.XToast;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RegisterActivity extends BaseActivity {
    private EditText userNumberEt;//账号
    private EditText registerYzEt;//验证码
    private EditText etUserPsw;//登录密码
    private EditText recommendPersonEt;//邀请码
    private TextView getYzCoed;//获取验证码
    private Button btnRegister;//注册按钮

    private TextView tvServiceContract;//网站服务协议
    private MyCountDownTimer  mTimer;
    private String userNumber;
    private String registerYz;
    private String password;
    private String recommendPerson;

    private OkHttpClient client = null;
    private String session;

    private Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            ResCodeBean bean= (ResCodeBean) message.obj;

            if(message.what==0){//获取验证码
                if(bean.isStatus()){
                    mTimer.start();
                    XToast.normal("已发送,请注意查收");
                }else {//失败
                    XToast.normal(bean.getMessage());
                    //获取验证码失败，显示重新获取验证码
                    if (mTimer != null) {
                        mTimer.cancel();
                        mTimer.onFinish();
                    }
                }
            }else if(message.what==1){//校验验证码
                if(bean.isStatus()){
                    XToast.normal(bean.getMessage());
                    finish();
                }else {//失败
                    XToast.normal(bean.getMessage());
                }
            }

            return true;
        }
    });

    @Override
    protected int getContentView() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        setTitle("注册");
        userNumberEt=findViewById(R.id.et_user_number);
        recommendPersonEt=findViewById(R.id.et_recommend_person);
        registerYzEt=findViewById(R.id.et_register_yz);
        etUserPsw=findViewById(R.id.et_user_psw);
        getYzCoed=findViewById(R.id.tv_get_yz);
        tvServiceContract=findViewById(R.id.tv_service_contract);
        btnRegister=findViewById(R.id.btn_register);

        getYzCoed.setOnClickListener(this);
        tvServiceContract.setOnClickListener(this);
        btnRegister.setOnClickListener(this);

        mTimer= new MyCountDownTimer(60000, 1000, getYzCoed);
        client = new OkHttpClient().newBuilder()
                .retryOnConnectionFailure(true)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .cache(new Cache(this.getCacheDir(), 10 * 1024 * 1024))
                .build();
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_register:
                userNumber=userNumberEt.getText().toString().trim();
                registerYz=registerYzEt.getText().toString().trim();//验证码
                password=etUserPsw.getText().toString().trim();
                recommendPerson=recommendPersonEt.getText().toString().trim();

                if(XEmptyUtils.isEmpty(userNumber)){
                    XToast.normal("手机号不能为空");
                    return;
                }
                if(!XRegexUtils.checkMobile(userNumber)){
                    XToast.normal("请输入正确的手机号码");
                    return;
                }
                if(XEmptyUtils.isEmpty(registerYz)){
                    XToast.normal("验证码不能为空");
                    return;
                }
                if(XEmptyUtils.isEmpty(password)){
                    XToast.normal("密码不能为空");
                    return;
                }
                if(password.length()<6){
                    XToast.normal("密码长度必选大于或者等于6位");
                    return;
                }
                if(isNumeric(password)){
                    XToast.normal("密码不能是纯数字");
                    return;
                }
                if(XNetworkUtils.isConnected()){
                    registerAcc(userNumber,password,registerYz,recommendPerson);
                }else {
                    XToast.normal("网络连接失败!");
                }

                break;
            case R.id.tv_get_yz:
                userNumber=userNumberEt.getText().toString().trim();
                if(XEmptyUtils.isEmpty(userNumber)){
                    XToast.normal("手机号不能为空");
                    return;
                }
                if(!XRegexUtils.checkMobile(userNumber)){
                    XToast.normal("请输入正确的手机号码");
                    return;
                }
                if(XNetworkUtils.isConnected()){
                    getCode();
                }else {
                    XToast.normal("网络连接失败!");
                }

                break;
            case R.id.tv_service_contract://网站服务协议
                Intent intent = new Intent(this, HtmlActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("url", Path.web_service_contract_url);
                bundle.putString("title", "货融贷网站服务协议");
                intent.putExtra("bundle", bundle);
                startActivity(intent);
                break;
        }
    }

    //进行注册
    private void registerAcc(String userNumber, String password, String registerYz, String recommendPerson) {
        Map<String,Object > params =new HashMap<>();
        params.put("custMobile",userNumber);
        params.put("custPwd", XEncryptUtils.MD5(password));
        params.put("optSource","4");
        params.put("mobileCode",registerYz);
        params.put("introInviteCode",recommendPerson);
        post(Path.register_account, params,1);
    }

    //获取验证码
    private void getCode() {
        Map<String,Object > params =new HashMap<>();
        params.put("custMobile",userNumber);
        post(Path.get_mobile_code, params,0);
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

    //type=0获取Set-Cookie，type=1请求加入cookie
    public void post(String url, Map<String, Object> params, final int type) {
        FormBody formBody = null;
        if (null != params&&!params.isEmpty()) {
            FormBody.Builder formBodyBuilder = new FormBody.Builder();
            Set<String> keySet = params.keySet();
            for(String key:keySet) {
                String value = (String) params.get(key);
                formBodyBuilder.add(key,value);
            }
            formBody = formBodyBuilder.build();
        }

        Request request = null;
        if(type==0){
            request = new Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();
        }else if(type==1){//有cookie
            if(XEmptyUtils.isEmpty(session)){
                return;
            }
            request = new Request.Builder()
                    .addHeader("cookie",session)
                    .url(url)
                    .post(formBody)
                    .build();
        }

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                if(type==0){
                    //获取session的操作，session放在cookie头，且取出后含有“；”，取出后为下面的 s （也就是jsesseionid）
                    Headers headers = response.headers();
                    List<String> cookies = headers.values("Set-Cookie");
                    if(cookies.size()>0){
                        String  s = cookies.get(0);
                        session = s.substring(0, s.indexOf(";"));
                    }
                }

                if (response.isSuccessful()) {
                    String result = response.body().string();
                    XLog.e(result);
                    Gson gson=new Gson();
                    ResCodeBean bean=gson.fromJson(result, ResCodeBean.class);

                    Message message=Message.obtain();
                    message.obj=bean;
                    if(type==0){
                        message.what=0;
                    }else {
                        message.what=1;
                    }
                    handler.sendMessage(message);
                }
            }
        });
    }
}
