package com.example.administrator.huorongdai.activity;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.huorongdai.Path;
import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.base.BaseActivity;
import com.example.administrator.huorongdai.gsonbean.ResCodeBean;
import com.example.administrator.huorongdai.gsonbean.UserBean;
import com.example.administrator.huorongdai.view.MyCountDownTimer;
import com.example.administrator.huorongdai.xframe.utils.XEmptyUtils;
import com.example.administrator.huorongdai.xframe.utils.XNetworkUtils;
import com.example.administrator.huorongdai.xframe.utils.XPreferencesUtils;
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

public class BindingEmailActivity extends BaseActivity {

    private EditText etEmailAddress;//邮箱地址
    private EditText etEmailYz;//验证码
    private TextView tvGetYz;//获取验证码
    private TextView btnSure;//确定

    private int emailAuth;//1-未认证 2-认证

    private MyCountDownTimer mTimer;
    private OkHttpClient client = null;
    private String session;

    private String custMobile;//手机号码
    private String custPwd;//密文密码

    private Handler handler=new Handler(new MyCallable());
    private final class MyCallable implements Handler.Callback{

        @Override
        public boolean handleMessage(Message message) {
            if(message.what==0){//获取验证码
                ResCodeBean bean= (ResCodeBean) message.obj;
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
            }else if(message.what==1){
                UserBean bean= (UserBean) message.obj;
                XToast.normal(bean.getMessage());
                if(bean.isStatus()){
                    //1-未认证 2-认证
                    XPreferencesUtils.put("emailAuth",bean.getCustomer().getEmailAuth());
                    XPreferencesUtils.put("custEmail",bean.getCustomer().getCustEmail());
                    finish();
                }
            }
            return true;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //如果参数为null的话，会将所有的Callbacks和Messages全部清除掉。
        handler.removeCallbacksAndMessages( null );
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_binding_email;
    }

    @Override
    protected void initView() {
        etEmailAddress=findViewById(R.id.et_email_address);
        etEmailYz=findViewById(R.id.et_email_yz);
        tvGetYz=findViewById(R.id.tv_get_yz);
        btnSure=findViewById(R.id.btn_sure);
        tvGetYz.setOnClickListener(this);
        btnSure.setOnClickListener(this);

        custMobile = (String) XPreferencesUtils.get("custMobile","");
        custPwd = (String) XPreferencesUtils.get("custPwd","");
        emailAuth= (int) XPreferencesUtils.get("emailAuth",1);
        if(emailAuth==2){
            setTitle("修改邮箱");
            String custEmail= (String) XPreferencesUtils.get("custEmail","");
            etEmailAddress.setText(custEmail);
        }else {
            setTitle("绑定邮箱");
        }

        mTimer= new MyCountDownTimer(60000, 1000, tvGetYz);
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
        String emailAddress=etEmailAddress.getText().toString().trim();
        switch (view.getId()){
            case R.id.tv_get_yz://获取验证码
                if(XEmptyUtils.isEmpty(emailAddress)){
                    XToast.normal("邮箱地址不能为空");
                    return;
                }
                if(!XRegexUtils.checkEmail(emailAddress)){
                    XToast.normal("请输入正确的邮箱地址");
                    return;
                }
                if(XNetworkUtils.isConnected()){
                    getCode(emailAddress);
                }else {
                    XToast.normal("网络连接失败!");
                }
                break;
            case R.id.btn_sure://确定
                String yzCode=etEmailYz.getText().toString().trim();
                if(XEmptyUtils.isEmpty(emailAddress)){
                    XToast.normal("邮箱地址不能为空");
                    return;
                }
                if(!XRegexUtils.checkEmail(emailAddress)){
                    XToast.normal("请输入正确的邮箱地址");
                    return;
                }

                if(XEmptyUtils.isEmpty(yzCode)){
                    XToast.normal("邮箱验证码不能为空");
                    return;
                }
                if(XNetworkUtils.isConnected()){
                    emailAuth(emailAddress,yzCode);
                }else {
                    XToast.normal("网络连接失败!");
                }
                break;
        }
    }

    //验证邮箱
    private void emailAuth(String emailAddress, String emailCode) {
        Map<String,Object > params =new HashMap<>();
        params .put("custMobile",custMobile);
        params.put("custPwd",custPwd);
        params.put("custEmail",emailAddress);
        params .put("emailCode",emailCode);
        XLog.map(params);
        post(Path.email_auth, params,1);
    }

    //获取验证码
    private void getCode(String emailAddress) {
        Map<String,Object > params =new HashMap<>();
        params.put("custMobile",custMobile);
        params.put("custPwd",custPwd);
        params.put("custEmail",emailAddress);
        post(Path.send_active_email, params,0);
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
                    Message message=Message.obtain();

                    if(type==0){
                        ResCodeBean bean=gson.fromJson(result, ResCodeBean.class);
                        message.obj=bean;
                        message.what=0;
                    }else {
                        UserBean bean=gson.fromJson(result, UserBean.class);
                        message.obj=bean;
                        message.what=1;
                    }
                    handler.sendMessage(message);
                }
            }
        });
    }

}
