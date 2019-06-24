package com.example.administrator.huorongdai.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.huorongdai.Path;
import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.base.BaseActivity;
import com.example.administrator.huorongdai.eventbusbean.CertificateTypeBean;
import com.example.administrator.huorongdai.gsonbean.ApplyBean;
import com.example.administrator.huorongdai.gsonbean.AreaBean;
import com.example.administrator.huorongdai.gsonbean.CustInfoBean;
import com.example.administrator.huorongdai.gsonbean.ResCodeBean;
import com.example.administrator.huorongdai.util.ButtonUtils;
import com.example.administrator.huorongdai.view.MyCountDownTimer;
import com.example.administrator.huorongdai.view.pickerview.builder.OptionsPickerBuilder;
import com.example.administrator.huorongdai.view.pickerview.listener.OnOptionsSelectListener;
import com.example.administrator.huorongdai.view.pickerview.view.OptionsPickerView;
import com.example.administrator.huorongdai.xframe.utils.XEmptyUtils;
import com.example.administrator.huorongdai.xframe.utils.XKeyboardUtils;
import com.example.administrator.huorongdai.xframe.utils.XNetworkUtils;
import com.example.administrator.huorongdai.xframe.utils.XPreferencesUtils;
import com.example.administrator.huorongdai.xframe.utils.XRegexUtils;
import com.example.administrator.huorongdai.xframe.utils.http.HttpCallBack;
import com.example.administrator.huorongdai.xframe.utils.http.XHttp;
import com.example.administrator.huorongdai.xframe.utils.log.XLog;
import com.example.administrator.huorongdai.xframe.widget.XToast;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
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

public class ApplyOfBorrowingActivity extends BaseActivity {

    private TextView etRealName;//真实姓名
    private TextView etPhoneNumber;//手机号码
    private EditText etBorrowYz;//输入验证码
    private TextView tvGetYz;//输入验证码
    private EditText etFinanceAmt;//融资金额
    private EditText etFinanceTime;//融资期限
    private RelativeLayout llArea;//所在区域
    private TextView tvArea;
    private TextView btnSure;//确定

    private OptionsPickerView cityOptions;//开户行地区选择器
    private List<CertificateTypeBean> oneLevelList=new ArrayList<>();//一级数据
    private List<List<CertificateTypeBean>> twoLevelList=new ArrayList<>();//二级数据
    private List<List<List<CertificateTypeBean>>> threeLevelList=new ArrayList<>();//三级数据

    private String custMobile;//客户手机号码
    private String custPwd;//密文密码
    private String linkMan;//联系人
    private String phoneNum;//联系人电话
    private String loanAmt;//融资金额
    private String areaId="";//行政区域最后一级ID
    private String loanExpiry;//融资期限

    private MyCountDownTimer mTimer;
    private OkHttpClient client = null;
    private String session;

    private Handler handler=new Handler(new MyCallback());

    private final class MyCallback implements Handler.Callback{

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
            }else if(message.what==1){//校验验证码
                ResCodeBean bean= (ResCodeBean) message.obj;
                if(bean.isStatus()){
                    loanApply();
                }else {//失败
                    XToast.normal(bean.getMessage());
                }
            }else if(message.what==2){
                getAreaList();
            }
            return true;
        }
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_apply_of_borrowing;
    }

    @Override
    protected void initView() {
        etRealName=findViewById(R.id.et_real_name);
        etPhoneNumber=findViewById(R.id.et_phone_number);
        etBorrowYz=findViewById(R.id.et_borrow_yz);
        tvGetYz=findViewById(R.id.tv_get_yz);
        tvGetYz.setOnClickListener(this);
        etFinanceAmt=findViewById(R.id.et_finance_amt);
        etFinanceTime=findViewById(R.id.et_finance_time);
        llArea=findViewById(R.id.ll_area);
        llArea.setOnClickListener(this);
        tvArea=findViewById(R.id.tv_area);
        btnSure=findViewById(R.id.btn_sure);
        btnSure.setOnClickListener(this);

        setTitle("申请借款");
        setRightBtnVisible(true);
        setRtTitle("借款查询");

        custMobile= (String) XPreferencesUtils.get("custMobile","");
        custPwd = (String) XPreferencesUtils.get("custPwd","");

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
        if(XNetworkUtils.isConnected()){
            requestID();
        }else {
            XToast.normal("网络连接失败!");
        }
    }

    @Override
    protected void onClickRight() {
        Intent intent=new Intent(this,BorrowFindActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_get_yz://获取验证码
                phoneNum=etPhoneNumber.getText().toString().trim();
                if(XEmptyUtils.isEmpty(phoneNum)){
                    XToast.normal("手机号不能为空");
                    return;
                }
                if(!XRegexUtils.checkMobile(phoneNum)){
                    XToast.normal("请输入正确的手机号码");
                    return;
                }
                if(XNetworkUtils.isConnected()){
                    getCode(phoneNum);
                }else {
                    XToast.normal("网络连接失败!");
                }
                break;
            case R.id.ll_area://所在区域
                if(cityOptions!=null&&oneLevelList.size()>0){
                    XKeyboardUtils.closeKeyboard(this);
                    cityOptions.show();
                }
                break;
            case R.id.btn_sure://确定
                linkMan=etRealName.getText().toString().trim();
                phoneNum=etPhoneNumber.getText().toString().trim();
                loanAmt=etFinanceAmt.getText().toString().trim();
                loanExpiry=etFinanceTime.getText().toString().trim();

                String registerCode=etBorrowYz.getText().toString().trim();

                if(!XRegexUtils.checkChinese(linkMan)){
                    XToast.normal("真实姓名必须全部是中文");
                    return;
                }
                if(XEmptyUtils.isEmpty(phoneNum)){
                    XToast.normal("手机号不能为空");
                    return;
                }
                if(!XRegexUtils.checkMobile(phoneNum)){
                    XToast.normal("请输入正确的手机号码");
                    return;
                }

                if(XEmptyUtils.isEmpty(registerCode)){
                    XToast.normal("验证码不能为空");
                    return;
                }
                if(XEmptyUtils.isEmpty(loanAmt)){
                    XToast.normal("融资金额不能为空");
                    return;
                }
                if(XEmptyUtils.isEmpty(loanExpiry)){
                    XToast.normal("融资期限不能为空");
                    return;
                }
                if(XEmptyUtils.isEmpty(areaId)){
                    XToast.normal("所在区域不能为空");
                    return;
                }
                if(XNetworkUtils.isConnected()){
                    if(!ButtonUtils.isFastDoubleClick()){
                        checkCode(registerCode);
                    }
                }else {
                    XToast.normal("网络连接失败!");
                }
                break;
        }
    }

    //融资申请接口
    private void loanApply() {
        Map<String,Object > params2 =new HashMap<>();
        params2.put("custMobile",custMobile);
        params2.put("custPwd",custPwd);
        params2.put("linkMan",linkMan);//联系人
        params2.put("phoneNum",phoneNum);//联系电话
        params2.put("loanAmt",loanAmt);//融资金额
        params2.put("areaId",areaId);//行政区域最后一级ID
        params2.put("loanExpiry",loanExpiry);//融资期限
        XHttp.obtain().post(Path.loan_apply, params2, new HttpCallBack<ApplyBean>() {
            @Override
            public void onSuccess(ApplyBean bean) {
                XToast.normal(bean.getMessage());
                if(bean.isStatus()){
                    Intent intent=new Intent(ApplyOfBorrowingActivity.this,BorrowSuccessActivity.class);
                    intent.putExtra("applyNo",bean.getApplyNo());
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailed(String error) {

            }
        });
    }

    //请求身份证号码
    private void requestID(){
        Map<String,Object > params2 =new HashMap<>();
        params2.put("custMobile",custMobile);
        params2.put("custPwd",custPwd);
        XHttp.obtain().post(Path.cust_info, params2, new HttpCallBack<CustInfoBean>() {
            @Override
            public void onSuccess(CustInfoBean bean) {
                Message message=Message.obtain();
                message.what=2;
                handler.sendMessage(message);
                if(bean.isStatus()){
                    String realName=bean.getCustomer().getCustRealName();
                    etRealName.setText(realName);
                    etPhoneNumber.setText(bean.getCustomer().getCustMobile());
                }
            }

            @Override
            public void onFailed(String error) {

            }
        });
    }

    //获取行政区域
    private void getAreaList(){
        XHttp.obtain().get(Path.get_area_list, new HashMap<String, Object>(), new HttpCallBack<AreaBean>() {
            @Override
            public void onSuccess(AreaBean areaBean) {
                if(areaBean.getData().isStatus()){
                    List<AreaBean.DataBean.AreaListBean> areaOne=areaBean.getData().getAreaList();
                    for (AreaBean.DataBean.AreaListBean beanOne : areaOne) {
                        oneLevelList.add(new CertificateTypeBean(beanOne.getAreaName(),beanOne.getId()));

                        List<CertificateTypeBean> beanListTwo=new ArrayList<>();
                        List<List<CertificateTypeBean>> beanListThree=new ArrayList<>();

                        for (AreaBean.DataBean.AreaListBean.SonAreaBeanX beanTwo : beanOne.getSonArea()) {
                            beanListTwo.add(new CertificateTypeBean(beanTwo.getAreaName(),beanTwo.getId()));

                            List<CertificateTypeBean> beanList=new ArrayList<>();
                            for (AreaBean.DataBean.AreaListBean.SonAreaBeanX.SonAreaBean beanThree : beanTwo.getSonArea()) {
                                beanList.add(new CertificateTypeBean(beanThree.getAreaName(),beanThree.getId()));
                            }
                            beanListThree.add(beanList);
                        }
                        twoLevelList.add(beanListTwo);

                        threeLevelList.add(beanListThree);
                    }

                    initCityOptionPicker();
                }
            }

            @Override
            public void onFailed(String error) {

            }
        });
    }

    //开户行地区选择器初始化
    private void initCityOptionPicker() {
        cityOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                tvArea.setText(oneLevelList.get(options1).getName()+twoLevelList.get(options1).get(options2).getName()+
                        threeLevelList.get(options1).get(options2).get(options3).getName());
                areaId=threeLevelList.get(options1).get(options2).get(options3).getValue();
            }
        })
                .setTitleText("所在区域")
                .setContentTextSize(18)//设置滚轮文字大小
                .setDividerColor(Color.parseColor("#5d5d5d"))//设置分割线的颜色
                .setSelectOptions(0)//默认选中项
                .setBgColor(Color.WHITE)
                .setTitleBgColor(Color.parseColor("#bfbfbf"))
                .setTitleColor(Color.parseColor("#057dff"))
                .setCancelColor(Color.parseColor("#057dff"))
                .setSubmitColor(Color.parseColor("#057dff"))
                .setTextColorCenter(Color.parseColor("#E14D4D"))
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setBackgroundId(0x00000000) //设置外部遮罩颜色
                .build();

        cityOptions.setPicker(oneLevelList,twoLevelList,threeLevelList);
    }

    //校验短信验证码
    private void checkCode(String registerCode) {
        Map<String,Object > params =new HashMap<>();
        params.put("mobileCode",registerCode);
        post(Path.check_mobile_code, params,1);
    }

    //获取验证码
    private void getCode(String mobile) {
        Map<String,Object > params =new HashMap<>();
        params.put("custMobile",mobile);
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

}
