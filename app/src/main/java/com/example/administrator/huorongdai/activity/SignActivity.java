package com.example.administrator.huorongdai.activity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.huorongdai.Path;
import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.base.BaseActivity;
import com.example.administrator.huorongdai.gsonbean.CheckSignInBean;
import com.example.administrator.huorongdai.gsonbean.GetSignBean;
import com.example.administrator.huorongdai.gsonbean.ResCodeBean;
import com.example.administrator.huorongdai.view.SignCalendar;
import com.example.administrator.huorongdai.xframe.utils.XPreferencesUtils;
import com.example.administrator.huorongdai.xframe.utils.http.HttpCallBack;
import com.example.administrator.huorongdai.xframe.utils.http.XHttp;
import com.example.administrator.huorongdai.xframe.utils.log.XLog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignActivity extends BaseActivity {

    private TextView tvSignInfo;
    private SignCalendar signView;//日期控件
    private LinearLayout llSign;//签到

    private String custMobile;//手机号码
    private String custPwd;//密文密码

    @Override
    protected int getContentView() {
        return R.layout.activity_sign;
    }

    @Override
    protected void initView() {
        //备注：查询签到接口有问题
        setTitle("签到");
        tvSignInfo=findViewById(R.id.tv_sign_info);
        signView=findViewById(R.id.signview);
        llSign=findViewById(R.id.ll_sign);
        llSign.setOnClickListener(this);

        custMobile = (String) XPreferencesUtils.get("custMobile","");
        custPwd = (String) XPreferencesUtils.get("custPwd","");
    }

    @Override
    protected void loadData() {
        getSign();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_sign://签到
                String info=tvSignInfo.getText().toString().trim();
                if(!info.startsWith("已签到")){
                    signIn();
                }
                break;

        }
    }

    //查询是否签到
    private void checkSignIn() {
        Map<String,Object > params =new HashMap<>();
        params .put("custMobile", custMobile);
        params .put("custPwd", custPwd);
        XHttp.obtain().post(Path.check_signIn, params, new HttpCallBack<CheckSignInBean>() {
            @Override
            public void onSuccess(CheckSignInBean bean) {
                if(bean.isStatus()){//true已经签到过了
                    //tvSignDay.setText(""+signDay);
                    tvSignInfo.setText("已签到");
                    llSign.setBackgroundResource(R.drawable.sgin_gray_selector);
                }else {
                    tvSignInfo.setText("签到领积分");
                    llSign.setBackgroundResource(R.drawable.sgin_red_selector);
                }
            }

            @Override
            public void onFailed(String error) {

            }
        });
    }

    //签到
    private void signIn() {
        Map<String,Object > params =new HashMap<>();
        params .put("custMobile",custMobile);
        params .put("custPwd", custPwd);
        XLog.map(params);
        XHttp.obtain().post(Path.sign_in, params, new HttpCallBack<ResCodeBean>() {
            @Override
            public void onSuccess(ResCodeBean bean) {
                if(bean.isStatus()){
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
                    String  date = formatter.format(curDate);
                    if(signView!=null){
                        signView.addMark(date,0);
                    }
                    tvSignInfo.setText("已签到"+(signDay+1)+"天");
                    llSign.setBackgroundResource(R.drawable.sgin_gray_selector);
                }
            }

            @Override
            public void onFailed(String error) {

            }
        });
    }

    private List<String> listDate;//签到日期数据
    private int signDay=0;//当月签到天数
    //查询签到信息接口
    public void getSign() {
        listDate = new ArrayList<>();
        //listDate.add("2018-03-01");
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.getTime();
        final String yearAndMonth=dateFormater.format(cal.getTime());
        Map<String,Object > params =new HashMap<>();
        params .put("custMobile",custMobile);
        params .put("custPwd", custPwd);
        XHttp.obtain().post(Path.get_sign, params, new HttpCallBack<GetSignBean>() {
            @Override
            public void onSuccess(GetSignBean bean) {
                if(bean.isStatus()){
                    List<String> list=bean.getListDate();
                    if(list.size()>0){
                        signDay=list.size();
                        for (String s : list) {
                            if(s.length()==1){
                                s="0"+s;
                            }
                            s=yearAndMonth+"-"+s;
                            listDate.add(s);
                        }
                    }
                    signView.addMarks(listDate, 0);
                    //checkSignIn();

                    if(list.size()>0){
                        String listLast=list.get(list.size()-1);
                        String dateNow=bean.getDateNow();

                        if(listLast.length()==1){
                            listLast="0"+listLast;
                        }
                        int length=dateNow.length();
                        dateNow=dateNow.substring(length-2,length);
                        if(listLast.equals(dateNow)){
                            tvSignInfo.setText("已签到"+signDay+"天");
                            llSign.setBackgroundResource(R.drawable.sgin_gray_selector);
                        }else {
                            tvSignInfo.setText("签到领积分");
                            llSign.setBackgroundResource(R.drawable.sgin_red_selector);
                        }
                    }else {
                        tvSignInfo.setText("签到领积分");
                        llSign.setBackgroundResource(R.drawable.sgin_red_selector);
                    }
                }
            }

            @Override
            public void onFailed(String error) {

            }
        });
    }
}
