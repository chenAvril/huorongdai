package com.example.administrator.huorongdai.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.huorongdai.Path;
import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.base.BaseActivity;
import com.example.administrator.huorongdai.eventbusbean.ReadLetterMsg;
import com.example.administrator.huorongdai.gsonbean.ResCodeBean;
import com.example.administrator.huorongdai.xframe.utils.XNetworkUtils;
import com.example.administrator.huorongdai.xframe.utils.XPreferencesUtils;
import com.example.administrator.huorongdai.xframe.utils.http.HttpCallBack;
import com.example.administrator.huorongdai.xframe.utils.http.XHttp;
import com.example.administrator.huorongdai.xframe.widget.XToast;
import com.example.administrator.huorongdai.xframe.widget.loadingview.XLoadingView;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

public class ReadLetterActivity extends BaseActivity {

    private TextView tvTitle,tvTime,tvCont;
    private XLoadingView xLoadingView;
    private Intent intent;

    private  String custMobile;//客户手机号码
    private String custPwd;//密文密码
    private String letterId;//站内信编号
    private int position;

    private Handler handler=new Handler(new MyCallable());
    private final class MyCallable implements Handler.Callback{
        @Override
        public boolean handleMessage(Message msg) {
            if(XNetworkUtils.isConnected()){
                xLoadingView.showContent();
                requestData();
            }else {
                XToast.normal("网络连接失败!");
                xLoadingView.showNoNetwork();
            }
            return true;
        }
    }

    @Override
    protected void onDestroy() {
        //如果参数为null的话，会将所有的Callbacks和Messages全部清除掉。
        handler.removeCallbacksAndMessages( null );
        super.onDestroy();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_read_letter;
    }

    @Override
    protected void initView() {
        setTitle("站内信");
        tvTitle=findViewById(R.id.tv_read_letter_title);
        tvTime=findViewById(R.id.tv_read_letter_time);
        tvCont=findViewById(R.id.tv_read_letter_content);
        xLoadingView=findViewById(R.id.xloading_view);

        custMobile= (String) XPreferencesUtils.get("custMobile","");
        custPwd = (String) XPreferencesUtils.get("custPwd","");

        intent=getIntent();
        if(intent!=null){
            letterId=intent.getStringExtra("letterId");
            position=intent.getIntExtra("position",0);
        }

        xLoadingView.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xLoadingView.showLoading();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        handler.sendMessage(Message.obtain());
                    }
                },600);
            }
        });
    }

    @Override
    protected void loadData() {
        if(XNetworkUtils.isConnected()){
            requestData();
        }else {
            XToast.normal("网络连接失败!");
            xLoadingView.showNoNetwork();
        }
    }

    private void requestData() {
        Map<String,Object > params =new HashMap<>();
        params.put("custMobile",custMobile);
        params.put("custPwd",custPwd);
        params.put("letterId",letterId);
        XHttp.obtain().post(Path.read_letter, params, new HttpCallBack<ResCodeBean>() {
            @Override
            public void onSuccess(ResCodeBean bean) {
                if(bean.isStatus()){
                    xLoadingView.showContent();
                    if(intent!=null){
                        EventBus.getDefault().post(new ReadLetterMsg(1,position));
                        String title=intent.getStringExtra("title");
                        String time=intent.getStringExtra("time");
                        String cont=intent.getStringExtra("cont");

                        tvTitle.setText(title);
                        tvTime.setText(time);
                        tvCont.setText(cont);
                    }
                }else {
                    xLoadingView.showError();
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
