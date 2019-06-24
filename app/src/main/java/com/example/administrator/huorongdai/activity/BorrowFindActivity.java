package com.example.administrator.huorongdai.activity;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.huorongdai.Path;
import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.base.BaseActivity;
import com.example.administrator.huorongdai.gsonbean.LoanApplyBean;
import com.example.administrator.huorongdai.gsonbean.ResCodeBean;
import com.example.administrator.huorongdai.view.MyCountDownTimer;
import com.example.administrator.huorongdai.xframe.adapter.XRecyclerViewAdapter;
import com.example.administrator.huorongdai.xframe.adapter.XViewHolder;
import com.example.administrator.huorongdai.xframe.utils.XEmptyUtils;
import com.example.administrator.huorongdai.xframe.utils.XKeyboardUtils;
import com.example.administrator.huorongdai.xframe.utils.XNetworkUtils;
import com.example.administrator.huorongdai.xframe.utils.XPreferencesUtils;
import com.example.administrator.huorongdai.xframe.utils.XRegexUtils;
import com.example.administrator.huorongdai.xframe.utils.log.XLog;
import com.example.administrator.huorongdai.xframe.widget.XToast;
import com.example.administrator.huorongdai.xframe.widget.loadingview.XLoadingView;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.DecimalFormat;
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

public class BorrowFindActivity extends BaseActivity {

    private EditText numberEt;//手机号码
    private EditText registerCodeEt;//验证码
    private TextView getRegCodeTv;//获取验证码
    private TextView nextBtn;//下一步

    private XLoadingView xLoadingView;
    private RecyclerView recyclerView;

    private MyCountDownTimer mTimer;
    private OkHttpClient client = null;
    private String session;

    private String custMobile;//手机号码
    private String custPwd;//密文密码
    private int pageNum=1;
    private String phoneNum;
    private String registerCode;//验证码

    private MyAdapter adapter;
    private List<LoanApplyBean.LoanApplyListBean> listBeans=new ArrayList<>();

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
                LoanApplyBean bean= (LoanApplyBean) message.obj;
                XKeyboardUtils.closeKeyboard(BorrowFindActivity.this);
                if(bean.isStatus()){
                    if(bean.getLoanApplyList().size()>0){
                        if(pageNum==1){
                            adapter.setDataLists(bean.getLoanApplyList());
                        }else {
                            adapter.addAll(bean.getLoanApplyList());
                        }
                    }

                    if("1".equals(bean.getPageNum()) && "0".equals(bean.getTotalPages()) && bean.getLoanApplyList().size()==0){
                        xLoadingView.showEmpty();
                    }
                    if(bean.getPageNum().equals(bean.getTotalPages()) || "0".equals(bean.getTotalPages())){
                        adapter.showLoadComplete();
                    }else {
                        pageNum=pageNum+1;
                    }

                }else {
                    XToast.normal(bean.getMessage());
                    xLoadingView.showError();
                }
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
        return R.layout.activity_borrow_find;
    }

    @Override
    protected void initView() {
        setTitle("借款查询");

        numberEt=findViewById(R.id.et_user_number);
        registerCodeEt=findViewById(R.id.et_register_yz);
        getRegCodeTv=findViewById(R.id.tv_get_yz);
        nextBtn=findViewById(R.id.btn_next);
        getRegCodeTv.setOnClickListener(this);
        nextBtn.setOnClickListener(this);
        xLoadingView=findViewById(R.id.xloading_view);
        recyclerView=findViewById(R.id.rl_borrow_find);
        xLoadingView.setVisibility(View.INVISIBLE);
        xLoadingView.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xLoadingView.showLoading();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(XNetworkUtils.isConnected()){
                            pageNum=1;
                            adapter.isLoadMore(true);
                            xLoadingView.showContent();
                        }else {
                            XToast.normal("网络连接失败!");
                        }
                    }
                }, 600);

            }
        });

        mTimer= new MyCountDownTimer(60000, 1000, getRegCodeTv);
        client = new OkHttpClient().newBuilder()
                .retryOnConnectionFailure(true)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .cache(new Cache(this.getCacheDir(), 10 * 1024 * 1024))
                .build();

        custMobile= (String) XPreferencesUtils.get("custMobile","");
        custPwd = (String) XPreferencesUtils.get("custPwd","");
        numberEt.setText(custMobile);

        adapter=new MyAdapter(recyclerView,listBeans);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);

        adapter.setOnLoadMoreListener(new XRecyclerViewAdapter.OnLoadMoreListener() {
            @Override
            public void onRetry() {

            }

            @Override
            public void onLoadMore() {
                getLoanApplyList(phoneNum,registerCode);
            }
        });
    }

    @Override
    protected void loadData() {

    }

    //融资申请列表查询接口
    private void getLoanApplyList(String phoneNum,String registerCode) {
        Map<String,Object > params =new HashMap<>();
        params .put("custMobile",custMobile);
        params .put("custPwd",custPwd);
        params .put("phoneNum",phoneNum);
        params .put("mobileCode",registerCode);
        params .put("pageNum",""+pageNum);
        params .put("pageSize","10");
        post(Path.get_loan_apply_list, params,1);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_get_yz://获取验证码
                phoneNum=numberEt.getText().toString().trim();
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
            case R.id.btn_next://下一步
                phoneNum=numberEt.getText().toString().trim();
                registerCode=registerCodeEt.getText().toString().trim();

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
                if(XNetworkUtils.isConnected()){
                    xLoadingView.setVisibility(View.VISIBLE);
                    pageNum=1;
                    adapter.isLoadMore(true);
                    xLoadingView.showContent();
                }else {
                    XToast.normal("网络连接失败!");
                }

                break;
        }
    }

    //获取验证码
    private void getCode(String num) {
        Map<String,Object > params =new HashMap<>();
        params.put("custMobile",num);
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
                    Message message=Message.obtain();

                    if(type==0){
                        ResCodeBean bean=gson.fromJson(result, ResCodeBean.class);
                        message.obj=bean;
                        message.what=0;
                    }else {
                        LoanApplyBean bean=gson.fromJson(result,LoanApplyBean.class);
                        message.obj=bean;
                        message.what=1;
                    }
                    handler.sendMessage(message);
                }
            }
        });
    }

    class MyAdapter extends XRecyclerViewAdapter<LoanApplyBean.LoanApplyListBean>{

        public MyAdapter(@NonNull RecyclerView mRecyclerView, List<LoanApplyBean.LoanApplyListBean> dataLists) {
            super(mRecyclerView, dataLists,R.layout.item_activity_borrow_find);
        }

        @Override
        protected void bindData(XViewHolder holder, LoanApplyBean.LoanApplyListBean data, int position) {
            TextView tvLinkMan=holder.getView(R.id.tv_link_man);//联系人
            TextView tvPhoneNum=holder.getView(R.id.tv_phone_num);//手机号码
            TextView tvLoanAmt=holder.getView(R.id.tv_loan_amt);//融资金额
            TextView tvLoanExpiry=holder.getView(R.id.tv_loan_expiry);//融资期限
            TextView tvAreaName=holder.getView(R.id.tv_area_name);//所属地区
            TextView tvApplyStatus=holder.getView(R.id.tv_apply_status);//状态
            TextView tvApplyTime=holder.getView(R.id.tv_apply_time);//申请时间

            tvLinkMan.setText(data.getLinkMan());
            tvPhoneNum.setText(data.getPhoneNum());
            tvLoanAmt.setText(new DecimalFormat("0").format(data.getLoanAmt()));
            tvLoanExpiry.setText(data.getLoanExpiry()+"个月");
            tvAreaName.setText(data.getAreaIdName());
            //融资申请状态，1-待审核，2-待发布，3-已发布，4-已否决
            int applyStatus=data.getApplyStatus();
            if(applyStatus==1){
                tvApplyStatus.setText("待审核");
            }else if(applyStatus==2){
                tvApplyStatus.setText("待发布");
            }else if(applyStatus==3){
                tvApplyStatus.setText("已发布");
            }else if(applyStatus==4){
                tvApplyStatus.setText("已否决");
            }

            String date=data.getApplyTime();
            String time=date.substring(0,4)+"-"+date.substring(4,6)+"-"+date.substring(6,8)+" "+date.substring(8,10)+":"+date.substring(10,12)+":"+date.substring(12,14);
            tvApplyTime.setText(time);
        }
    }
}
