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

public class FindTradePwdActivity extends BaseActivity {

    private TextView etPhoneNumber;//手机号码
    private EditText etYzCode;//验证码
    private TextView tvGetYz;//获取验证码
    private EditText etTradePsw;//投资密码
    private EditText etTradePswSure;//确认投资密码
    private TextView btnSure;//确定

    private MyCountDownTimer mTimer;
    private OkHttpClient client = null;
    private String session;

    private String custMobile;//手机号码

    private Handler handler=new Handler(new MyCallable());
    private final class MyCallable implements Handler.Callback{

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
            }else if(message.what==1){
                if(!XEmptyUtils.isEmpty(bean.getMessage())){
                    XToast.normal(bean.getMessage());
                }

                if(bean.isStatus()){
                    setResult(1003);
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
        return R.layout.activity_find_trade_pwd;
    }

    @Override
    protected void initView() {
        etPhoneNumber=findViewById(R.id.et_phone_number);
        etYzCode=findViewById(R.id.et_find_trade_yz);
        tvGetYz=findViewById(R.id.tv_get_yz);
        etTradePsw=findViewById(R.id.et_trade_psw);
        etTradePswSure=findViewById(R.id.et_trade_psw_sure);
        btnSure=findViewById(R.id.btn_sure);
        tvGetYz.setOnClickListener(this);
        btnSure.setOnClickListener(this);

        setTitle("找回投资密码");
        custMobile = (String) XPreferencesUtils.get("custMobile","");
        etPhoneNumber.setText(custMobile);

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
        //retrieve();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_get_yz://获取验证码
                custMobile=etPhoneNumber.getText().toString().trim();
                if(XEmptyUtils.isEmpty(custMobile)){
                    XToast.normal("手机号不能为空");
                    return;
                }
                if(!XRegexUtils.checkMobile(custMobile)){
                    XToast.normal("请输入正确的手机号码");
                    return;
                }
                if(XNetworkUtils.isConnected()){
                    getCode();
                }else {
                    XToast.normal("网络连接失败!");
                }
                break;
            case R.id.btn_sure://确定
                custMobile=etPhoneNumber.getText().toString().trim();
                String yzCode=etYzCode.getText().toString().trim();
                String newPwd=etTradePsw.getText().toString().trim();
                String newPwdSure=etTradePswSure.getText().toString().trim();

                if(XEmptyUtils.isEmpty(custMobile)){
                    XToast.normal("手机号不能为空");
                    return;
                }
                if(!XRegexUtils.checkMobile(custMobile)){
                    XToast.normal("请输入正确的手机号码");
                    return;
                }

                if(XEmptyUtils.isEmpty(yzCode)){
                    XToast.normal("验证码不能为空");
                    return;
                }
                if(XEmptyUtils.isEmpty(newPwd)){
                    XToast.normal("投资密码不能为空");
                    return;
                }
                if(XEmptyUtils.isEmpty(newPwdSure)){
                    XToast.normal("确认投资密码不能为空");
                    return;
                }
                if(newPwd.length()<8 || newPwd.length()>20){
                    XToast.normal("投资密码长度必须是8-20位");
                    return;
                }
                if(isNumeric(newPwd)){
                    XToast.normal("投资密码不能是纯数字");
                    return;
                }
                if(newPwd.equals(newPwdSure)){
                    if(XNetworkUtils.isConnected()){
                        retrieveTradePwd(newPwd,yzCode);
                    }else {
                        XToast.normal("网络连接失败!");
                    }
                }else {
                    XToast.normal("两次输入的新密码不一致!");
                }

                break;
        }
    }

    //找回投资密码
    private void retrieveTradePwd(String newTradePwd, String mobileCode) {
        Map<String,Object > params =new HashMap<>();
        params .put("custMobile",custMobile);
        params .put("newTradePwd",newTradePwd);
        params .put("mobileCode",mobileCode);
        post(Path.retrieve_trade_pwd, params,1);
    }

    //获取验证码
    private void getCode() {
        Map<String,Object > params =new HashMap<>();
        params.put("custMobile",custMobile);
        post(Path.get_mobile_code, params,0);
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
