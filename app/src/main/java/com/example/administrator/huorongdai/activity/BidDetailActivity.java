package com.example.administrator.huorongdai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.huorongdai.Path;
import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.base.BaseActivity;
import com.example.administrator.huorongdai.eventbusbean.RepaymentPlanBean;
import com.example.administrator.huorongdai.fragment.BottomSheetFragment;
import com.example.administrator.huorongdai.gsonbean.LoanViewBean;
import com.example.administrator.huorongdai.view.MyScrollView;
import com.example.administrator.huorongdai.view.circlepercentview.CirclePercentBarBig;
import com.example.administrator.huorongdai.xframe.utils.XDateUtils;
import com.example.administrator.huorongdai.xframe.utils.XEmptyUtils;
import com.example.administrator.huorongdai.xframe.utils.XNetworkUtils;
import com.example.administrator.huorongdai.xframe.utils.XPreferencesUtils;
import com.example.administrator.huorongdai.xframe.utils.http.HttpCallBack;
import com.example.administrator.huorongdai.xframe.utils.http.XHttp;
import com.example.administrator.huorongdai.xframe.widget.loadingview.XLoadingView;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BidDetailActivity extends BaseActivity {

    private RelativeLayout rlShowYou;
    private RelativeLayout rlBidDetail;
    private TextView tvProgress;//投资进度
    private TextView tvLoanAmt;//总金额
    private TextView tvRemainingAmount;//剩余投资金额
    private TextView tvLoanExpiry;//还款期限
    private TextView tvMinIvst;//起投金额
    private TextView tvMaxIvst;//单笔最大投资金额
    private TextView tvTitleRed;
    private TextView tvPaymentStyle;//还款方式
    private TextView tvStartTime;//开始时间
    private TextView tvEndTime;//截止时间
    private TextView tvLoanStatus;//标的状态
    private LinearLayout llProjectInfo;//项目简介
    private LinearLayout llBidList;//投资列表
    private LinearLayout llPlan;//还款计划
    private LinearLayout llControl;//风险控制
    private CirclePercentBarBig circleBar;//进度圆
    private ImageView ivCalculator;//计算器
    private TextView tvInvestment;//立即投资
    private LinearLayout llBottom;//底部立即投资
    private XLoadingView xLoadingView;
    private SwipeRefreshLayout refreshLayout;
    private MyScrollView scrollView;

    private String custMobile;
    private String custName;
    private String custPwd;//密文密码
    private String loanID;
    private String loanTitle;
    private String loanStatus;//除了“7”，立即投资均不能点击
    private String canBeCast;//当前标的可投金额

    private String loanDesc;//项目描述
    private String bannerPath;//证件类型
    private String danbaoInfo;//保障措施
    private String repaymentInfo;//还款来源
    private ArrayList<RepaymentPlanBean> planBeans;//还款计划数据

    private BottomSheetFragment bottomFragment;//底部弹框

    private Handler handler=new Handler(new MyCallback());
    private final class MyCallback implements Handler.Callback{

        @Override
        public boolean handleMessage(Message message) {
            if(XNetworkUtils.isConnected()){
                requestData();
            }else {
                if(refreshLayout.isRefreshing()){
                    refreshLayout.setRefreshing(false);
                }
                xLoadingView.showNoNetwork();
            }
            return true;
        }
    }

    private SwipeRefreshLayout.OnRefreshListener refreshListener=new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            handler.sendMessage(Message.obtain());
        }
    };

    @Override
    protected int getContentView() {
        return R.layout.activity_bid_detail;
    }

    @Override
    protected void initView() {
        Intent intent=getIntent();
        if(intent!=null){
            loanID=intent.getStringExtra("loanID");
            loanTitle=intent.getStringExtra("loanTitle");
            //7-招标中 8-待满标 9-待划转  10-划款中11-划款失败 12-待还款 13-已还清 14-已流标 15-已过期
            loanStatus=intent.getStringExtra("loanStatus");
            setTitle("理财项目");
        }

        rlShowYou=findViewById(R.id.rl_show_you);
        rlBidDetail=findViewById(R.id.rl_bid_detail);
        xLoadingView=findViewById(R.id.xloading_view);
        refreshLayout=findViewById(R.id.srl_bid_detail);
        refreshLayout.setColorScheme(R.color.word_red);
        refreshLayout.setOnRefreshListener(refreshListener);
        scrollView=findViewById(R.id.msl_bid_detail);
        tvProgress=findViewById(R.id.tv_progress);
        tvLoanAmt=findViewById(R.id.tv_loan_amt);
        tvTitleRed=findViewById(R.id.tv_title_red);
        tvRemainingAmount=findViewById(R.id.tv_remaining_amount);
        tvLoanExpiry=findViewById(R.id.tv_loan_expiry);
        tvMinIvst=findViewById(R.id.tv_min_ivst);
        tvMaxIvst=findViewById(R.id.tv_max_ivst);
        tvPaymentStyle=findViewById(R.id.tv_payment_style);
        tvStartTime=findViewById(R.id.tv_start_time);
        tvEndTime=findViewById(R.id.tv_end_time);
        tvLoanStatus=findViewById(R.id.tv_loan_status);
        llProjectInfo=findViewById(R.id.ll_project_info);
        llProjectInfo.setOnClickListener(this);
        llBidList=findViewById(R.id.ll_bid_list);
        llBidList.setOnClickListener(this);
        llPlan=findViewById(R.id.ll_plan);
        llPlan.setOnClickListener(this);
        llControl=findViewById(R.id.ll_control);
        llControl.setOnClickListener(this);
        llBottom=findViewById(R.id.ll_bid_bottom);
        circleBar=findViewById(R.id.circle_bar_bid);
        tvInvestment=findViewById(R.id.tv_investment);
        tvInvestment.setOnClickListener(this);
        ivCalculator=findViewById(R.id.iv_calculator);
        ivCalculator.setOnClickListener(this);
        decimal=new DecimalFormat("0.0");

        xLoadingView.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xLoadingView.showLoading();
                handler.sendMessageDelayed(Message.obtain(),600);
            }
        });
        //解决swiperefreshlayout与scrollview的冲突问题
        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if (refreshLayout != null) {
                    refreshLayout.setEnabled(scrollView.getScrollY() == 0);
                }
            }
        });

        planBeans=new ArrayList<>();
        custMobile = (String) XPreferencesUtils.get("custMobile","");
        custPwd = (String) XPreferencesUtils.get("custPwd","");
        custName= (String) XPreferencesUtils.get("custName","");

        if(!"7".equals(loanStatus)){
            llBottom.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshLayout.setRefreshing(true);
        refreshListener.onRefresh();
    }

    @Override
    protected void loadData() {

    }

    private DecimalFormat decimal;
    //请求数据
    private void requestData(){
        final Map<String,Object > params =new HashMap<>();
        params .put("custMobile",custMobile);
        params .put("custPwd",custPwd);
        params .put("loanId",loanID);
        XHttp.obtain().post(Path.loan_view, params, new HttpCallBack<LoanViewBean>() {
            @Override
            public void onSuccess(LoanViewBean bean) {
                if(bean.isStatus()){
                    if(refreshLayout.isRefreshing()){
                        refreshLayout.setRefreshing(false);
                    }
                    if(!"7".equals(loanStatus)){
                        if(!XEmptyUtils.isEmpty(bean.getShowYou())){
                            if("showYou".equals(bean.getShowYou())){
                                rlBidDetail.setVisibility(View.VISIBLE);
                                rlShowYou.setVisibility(View.GONE);
                            }else {
                                rlBidDetail.setVisibility(View.GONE);
                                rlShowYou.setVisibility(View.VISIBLE);
                            }
                        }else {
                            rlBidDetail.setVisibility(View.GONE);
                            rlShowYou.setVisibility(View.VISIBLE);
                        }
                    }

                    LoanViewBean.LoanBean loanBean=bean.getLoan();

                    double yearRate=loanBean.getLoanArp();//预计年利率

                    float progress=((float)loanBean.getTotalInvestMoney()/loanBean.getLoanAmt())*100;
                    tvProgress.setText(decimal.format(progress)+"%");
                    circleBar.setPercentData(progress,new DecelerateInterpolator());
                    if(loanBean.getTotalInvestMoney()==loanBean.getLoanAmt()){
                        llBottom.setVisibility(View.GONE);
                    }
                    circleBar.setText("预计年化率");
                    circleBar.setYearRate(yearRate+"");

                    String loanAmt=loanBean.getLoanAmt()+"";//总金额
                    if(loanAmt.endsWith("0000")){
                        loanAmt=loanAmt.substring(0,loanAmt.length()-4);
                        tvLoanAmt.setText(loanAmt+"万元");
                    }else {
                        tvLoanAmt.setText(loanAmt+"元");
                    }

                    String  remainingAmount=(loanBean.getLoanAmt()-loanBean.getTotalInvestMoney())+"";//剩余金额
                    canBeCast=remainingAmount;
                    if(remainingAmount.endsWith("0000")){
                        remainingAmount=remainingAmount.substring(0,remainingAmount.length()-4);
                        tvRemainingAmount.setText(remainingAmount+"万元");
                    }else {
                        tvRemainingAmount.setText(remainingAmount+"元");
                    }

                    //还款期限
                    String timeDate="";
                    if(loanBean.getExpiryUnit()==1){
                        timeDate="天";
                    }else if(loanBean.getExpiryUnit()==2){
                        timeDate="个月";
                    }
                    tvLoanExpiry.setText(loanBean.getLoanExpiry()+timeDate);
                    tvTitleRed.setText(loanBean.getLoanIdentityName()+"\t\t"+loanTitle);

                    tvMinIvst.setText(loanBean.getMinIvst()+"元");//起投金额
                    String maxIvst=loanBean.getMaxIvst()+"";//单笔最大投资金额
                    if(maxIvst.endsWith("0000")){
                        maxIvst=maxIvst.substring(0,maxIvst.length()-4);
                        tvMaxIvst.setText(maxIvst+"万元");
                    }else {
                        tvMaxIvst.setText(maxIvst+"元");
                    }

                    tvPaymentStyle.setText(loanBean.getRpmtTypeName());//付款方式
                    tvStartTime.setText(XDateUtils.millis2String(loanBean.getStartTimeMill()));//开始时间
                    tvEndTime.setText(XDateUtils.millis2String(loanBean.getEndTimeMill()));//截止时间

                    if("7".equals(loanStatus)){
                        tvLoanStatus.setText("未满标");
                    }else{
                        String fullTime=loanBean.getFullLoanAuditTime();
                        if(!XEmptyUtils.isEmpty(fullTime)){
                            fullTime=fullTime.substring(0,4)+"-"+fullTime.substring(4,6)+"-"+fullTime.substring(6,8)+" "+fullTime.substring(8,10)+":"+fullTime.substring(10,12)+":"+fullTime.substring(12,14);
                            tvLoanStatus.setText(fullTime);
                        }else {
                            tvLoanStatus.setText("已满标");
                        }
                    }


                    loanDesc=loanBean.getLoanDesc();//项目描述
                    loanDesc=loanDesc.replace("<p>","");
                    loanDesc=loanDesc.replace("</p>","");

                    List<LoanViewBean.RpmtPlanListBean> planListBeans= bean.getRpmtPlanList();

                    if(planBeans!=null){
                        planBeans.clear();
                    }
                    for (LoanViewBean.RpmtPlanListBean planListBean : planListBeans) {
                        RepaymentPlanBean planBean=new RepaymentPlanBean();
                        planBean.setRpmtIssue(planListBean.getRpmtIssue()+"");
                        planBean.setRpmtIint(planListBean.getRpmtIint()+"");
                        planBean.setRpmtCapital(planListBean.getRpmtCapital()+"");
                        planBean.setShdRpmtDateStr(planListBean.getShdRpmtDateStr()+"");
                        planBeans.add(planBean);
                    }

                    bannerPath=bean.getAttrDataMap().getREL_DATUM();//证件类型
                    danbaoInfo=bean.getAttrDataMap().getDANBAO_MEASURE();//保障措施
                    repaymentInfo=bean.getAttrDataMap().getRMPT_RESOURCE();//还款来源

                    if(!isDate2Bigger(XDateUtils.getCurrentDate(),XDateUtils.millis2String(loanBean.getEndTimeMill()))){
                        //tvInvestment.setText("已过期");
                        llBottom.setVisibility(View.GONE);
                    }

                    xLoadingView.showContent();
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
        switch (view.getId()){
            case R.id.ll_project_info://项目简介
                if(!XEmptyUtils.isEmpty(loanDesc)){
                    Intent intentInfo=new Intent(this,ProIntroductionActivity.class);
                    intentInfo.putExtra("loanDesc",loanDesc);
                    intentInfo.putExtra("bannerPath",bannerPath);
                    startActivity(intentInfo);
                }
                break;
            case R.id.ll_bid_list://投资列表
                if(!XEmptyUtils.isEmpty(loanID)){
                    Intent intenList=new Intent(this,LoanInvestListActivity.class);
                    intenList.putExtra("loanID",loanID);
                    startActivity(intenList);
                }
                break;
            case R.id.ll_plan://还款计划
                if(planBeans.size()>0){
                    Intent intenPlan=new Intent(this,RepaymentPlanActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putParcelableArrayList("repayment_plan",planBeans);
                    intenPlan.putExtra("loanStatus",loanStatus);
                    intenPlan.putExtra("plan",bundle);
                    startActivity(intenPlan);
                }
                break;
            case R.id.ll_control://风险控制
                Intent intentRisk=new Intent(this,RiskControlActivity.class);
                intentRisk.putExtra("danbaoInfo",danbaoInfo);
                intentRisk.putExtra("repaymentInfo",repaymentInfo);
                startActivity(intentRisk);
                break;
            case R.id.iv_calculator://计算器
                Intent intentC=new Intent(this,CalculatorActivity.class);
                startActivity(intentC);
                break;

            case R.id.tv_investment://立即投资
                //未开户是4，开户是2
                int openStatus= (int) XPreferencesUtils.get("openStatus",4);
                if(openStatus==4){
                    Intent intent=new Intent(this,OpenAccountActivity.class);
                    startActivity(intent);
                }else if(openStatus==2){
                    showBottomDialog();
                }
                break;
        }
    }

    private void showBottomDialog() {
        bottomFragment=new BottomSheetFragment();
        bottomFragment.setDatas(loanID,canBeCast);
        bottomFragment.setOnReFresh(new BottomSheetFragment.OnReFresh() {
            @Override
            public void onReFresh(boolean isReFresh) {
                if(isReFresh){
                    bottomFragment.dismiss();
                    refreshLayout.setRefreshing(true);
                    refreshListener.onRefresh();
                }
            }
        });

        bottomFragment.show(getSupportFragmentManager(),BottomSheetFragment.class.getSimpleName());
    }

    /**
     * 比较两个日期的大小，日期格式为yyyy-MM-dd
     *
     * @param str1 the first date
     * @param str2 the second date
     * @return true str1日期靠后   false  str2日期靠后
     */
    public static boolean isDate2Bigger(String str1, String str2) {
        boolean isBigger = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dt1 = null;
        Date dt2 = null;
        try {
            dt1 = sdf.parse(str1);
            dt2 = sdf.parse(str2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (dt1.getTime() > dt2.getTime()) {
            isBigger = false;
        } else if (dt1.getTime() < dt2.getTime()) {
            isBigger = true;
        }
        return isBigger;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
