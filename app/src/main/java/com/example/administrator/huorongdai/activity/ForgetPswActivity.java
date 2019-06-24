package com.example.administrator.huorongdai.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.huorongdai.Path;
import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.base.BaseActivity;
import com.example.administrator.huorongdai.eventbusbean.ForgetPswMsg;
import com.example.administrator.huorongdai.gsonbean.ResCodeBean;
import com.example.administrator.huorongdai.view.MyCountDownTimer;
import com.example.administrator.huorongdai.xframe.utils.XEmptyUtils;
import com.example.administrator.huorongdai.xframe.utils.XNetworkUtils;
import com.example.administrator.huorongdai.xframe.utils.XRegexUtils;
import com.example.administrator.huorongdai.xframe.utils.log.XLog;
import com.example.administrator.huorongdai.xframe.widget.XToast;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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

public class ForgetPswActivity extends BaseActivity {

    private EditText numberEt;//手机号码
    private EditText registerCodeEt;//验证码
    private TextView getRegCodeTv;//获取验证码
    private Button nextBtn;//下一步

    private MyCountDownTimer  mTimer;
    private OkHttpClient client = null;
    private String session;

    private String custMobile;//手机号码

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
                    custMobile=numberEt.getText().toString().trim();
                    Intent intent=new Intent(ForgetPswActivity.this,SetPswActivity.class);
                    intent.putExtra("custMobile",custMobile);
                    startActivity(intent);
                }else {//失败
                    XToast.normal(bean.getMessage());
                }
            }

            return true;
        }
    });

    @Override
    protected int getContentView() {
        return R.layout.activity_forget_psw;
    }

    @Override
    protected void initView() {
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }

        setTitle("找回密码");
        numberEt=findViewById(R.id.et_user_number);
        registerCodeEt=findViewById(R.id.et_register_yz);
        getRegCodeTv=findViewById(R.id.tv_get_yz);
        nextBtn=findViewById(R.id.btn_next);

        getRegCodeTv.setOnClickListener(this);
        nextBtn.setOnClickListener(this);

        mTimer= new MyCountDownTimer(60000, 1000, getRegCodeTv);
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
            case R.id.tv_get_yz://获取验证码
                custMobile=numberEt.getText().toString().trim();
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
            case R.id.btn_next://下一步
                custMobile=numberEt.getText().toString().trim();
                String registerCode=registerCodeEt.getText().toString().trim();

                if(XEmptyUtils.isEmpty(custMobile)){
                    XToast.normal("手机号不能为空");
                    return;
                }
                if(!XRegexUtils.checkMobile(custMobile)){
                    XToast.normal("请输入正确的手机号码");
                    return;
                }

                if(XEmptyUtils.isEmpty(registerCode)){
                    XToast.normal("验证码不能为空");
                    return;
                }
                if(XNetworkUtils.isConnected()){
                    checkCode(registerCode);
                }else {
                    XToast.normal("网络连接失败!");
                }

                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void isFinish(ForgetPswMsg forgetPswMsg){
        if(forgetPswMsg.getFlag()==1){
            finish();
        }
    }

    //校验短信验证码
    private void checkCode(String registerCode) {
        Map<String,Object > params =new HashMap<>();
        params.put("mobileCode",registerCode);
        post(Path.check_mobile_code, params,1);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
