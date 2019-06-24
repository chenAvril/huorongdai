package com.example.administrator.huorongdai.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.huorongdai.Path;
import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.base.BaseActivity;
import com.example.administrator.huorongdai.gsonbean.ReturnPlanBean;
import com.example.administrator.huorongdai.xframe.utils.XEmptyUtils;
import com.example.administrator.huorongdai.xframe.utils.XPreferencesUtils;
import com.example.administrator.huorongdai.xframe.utils.http.HttpCallBack;
import com.example.administrator.huorongdai.xframe.utils.http.XHttp;
import com.example.administrator.huorongdai.xframe.utils.log.XLog;

import java.util.HashMap;
import java.util.Map;

public class ReturnPlanActivity extends BaseActivity {

    private SwipeRefreshLayout refreshLayout;
    private TextView tvAmt;//投资本金
    private TextView tvAmt2;//应收本金
    private TextView tvInterest;//应收利息
    private TextView tvLateAmt;//应收利息
    private TextView tvSource;//投标方式
    private TextView tvSatus;//状态
    private TextView tvEarnIssue;//期数
    private TextView tvShdEarnDate;//应收时间
    private TextView tvRealEanDate;//实收时间
    private TextView tvNextDate;//下次还款
    private LinearLayout llRealTime;

    private String custMobile;
    private String custPwd;//密文密码

    private String earnId;
    private String earnStatus;//回款状态===>不传查全部，1-待收，2-已收

    private SwipeRefreshLayout.OnRefreshListener refreshListener=new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            requestDate();
        }
    };

    @Override
    protected int getContentView() {
        return R.layout.activity_return_plan;
    }

    @Override
    protected void initView() {
        refreshLayout=findViewById(R.id.srl_return_plan);
        refreshLayout.setColorScheme(R.color.word_red);
        refreshLayout.setOnRefreshListener(refreshListener);
        tvAmt=findViewById(R.id.tv_amt);
        tvAmt2=findViewById(R.id.tv_amt2);
        tvInterest=findViewById(R.id.tv_interest);
        tvLateAmt=findViewById(R.id.tv_late_amt);
        tvSource=findViewById(R.id.tv_source);
        tvSatus=findViewById(R.id.tv_earn_status);
        tvEarnIssue=findViewById(R.id.tv_earnIssue);
        tvShdEarnDate=findViewById(R.id.tv_shdEarnDate);
        tvRealEanDate=findViewById(R.id.tv_realEanDate);
        tvNextDate=findViewById(R.id.tv_next_date);
        llRealTime=findViewById(R.id.ll_real_time);

        earnId=getIntent().getStringExtra("earnId");
        earnStatus=getIntent().getStringExtra("earnStatus");
        custMobile = (String) XPreferencesUtils.get("custMobile","");
        custPwd = (String) XPreferencesUtils.get("custPwd","");
    }

    @Override
    protected void loadData() {
        refreshLayout.setRefreshing(true);
        refreshListener.onRefresh();
    }

    private void requestDate(){
        Map<String,Object > params =new HashMap<>();
        params .put("custMobile",custMobile);
        params .put("custPwd",custPwd);
        params .put("earnId",earnId);
        XLog.map(params);
        XHttp.obtain().post(Path.get_returned_money_detail, params, new HttpCallBack<ReturnPlanBean>() {
            @Override
            public void onSuccess(ReturnPlanBean bean) {
                if(bean.isStatus()){
                    setTitle(bean.getReturnedMoneyDetail().getLoan().getLoanTitle());

                    int ivstAmt=bean.getReturnedMoneyDetail().getInvest().getIvstAmt();//投资本金
                    tvAmt.setText(ivstAmt+"元");
                    int earnCapital=bean.getReturnedMoneyDetail().getEarnCapital();//应收本金
                    tvAmt2.setText(earnCapital+"元");

                    double earnIint=bean.getReturnedMoneyDetail().getEarnIint();//应收利息
                    tvInterest.setText(earnIint+"元");
                    double lateIint=bean.getReturnedMoneyDetail().getLateIint();//逾期罚息
                    tvLateAmt.setText(lateIint+"元");

                    //ivstSource投标方式   1-pc 2-wap 3-微信 4-安卓 5-iOS
                    int ivstSource=bean.getReturnedMoneyDetail().getInvest().getIvstSource();
                    if(ivstSource==1){
                        tvSource.setText("电脑pc投资");
                    }else if(ivstSource==2){
                        tvSource.setText("wap投资");
                    }else if(ivstSource==3){
                        tvSource.setText("微信投资");
                    }else if(ivstSource==4){
                        tvSource.setText("安卓投资");
                    }else if(ivstSource==5){
                        tvSource.setText("iOS投资");
                    }

                    int status=bean.getReturnedMoneyDetail().getEarnStatus();//1待收款  2已收款     逾期待收款
                    if(status==1){
                        tvSatus.setText("待收款");
                    }else if(status==2){
                        tvSatus.setText("已收款");
                    }else {
                        tvSatus.setText("逾期待收款");
                    }

                    int earnIssue=bean.getReturnedMoneyDetail().getEarnIssue();
                    tvEarnIssue.setText("第"+earnIssue+"期");

                    String date=bean.getReturnedMoneyDetail().getShdEarnDate();
                    date=date.substring(0,4)+"-"+date.substring(4,6)+"-"+date.substring(6,8);
                    tvShdEarnDate.setText(date);

                    if("2".equals(earnStatus)){
                        llRealTime.setVisibility(View.VISIBLE);
                        String lateTime=bean.getReturnedMoneyDetail().getRealEanDate();
                        lateTime=lateTime.substring(0,4)+"-"+lateTime.substring(4,6)+"-"+lateTime.substring(6,8)+" "+lateTime.substring(8,10)+":"+lateTime.substring(10,12)+":"+lateTime.substring(12,14);
                        tvRealEanDate.setText(lateTime);
                    }

                    //2018-01-11 11::11:11 第5/10期
                    int nextNum;
                    if(bean.getReturnedMoneyDetail().getLoan().getExpiryUnit()==1){
                        nextNum=1;
                    }else {
                        nextNum=bean.getReturnedMoneyDetail().getLoan().getLoanExpiry();
                    }

                    String nextTime=bean.getReturnedMoneyDetail().getNetShdEarnDate();
                    if(!XEmptyUtils.isEmpty(nextTime)){
                        nextTime=nextTime.substring(0,4)+"-"+nextTime.substring(4,6)+"-"+nextTime.substring(6,8)+" "+nextTime.substring(8,10)+":"+nextTime.substring(10,12)+":"+nextTime.substring(12,14)+"    ";
                    }else {
                        nextTime="";
                    }

                    tvNextDate.setText(nextTime+"第"+earnIssue+"/"+nextNum+"期");

                    if(refreshLayout.isRefreshing()){
                        refreshLayout.setRefreshing(false);
                    }
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
