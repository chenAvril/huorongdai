package com.example.administrator.huorongdai.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.example.administrator.huorongdai.Path;
import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.base.BaseActivity;
import com.example.administrator.huorongdai.eventbusbean.BorrowMsg;
import com.example.administrator.huorongdai.gsonbean.RepayBean;
import com.example.administrator.huorongdai.gsonbean.RpmtViewBean;
import com.example.administrator.huorongdai.util.ButtonUtils;
import com.example.administrator.huorongdai.view.CustomDialog;
import com.example.administrator.huorongdai.view.MyScrollView;
import com.example.administrator.huorongdai.view.circlepercentview.CirclePercentBarBig;
import com.example.administrator.huorongdai.xframe.adapter.XRecyclerViewAdapter;
import com.example.administrator.huorongdai.xframe.adapter.XViewHolder;
import com.example.administrator.huorongdai.xframe.utils.XDateUtils;
import com.example.administrator.huorongdai.xframe.utils.XEmptyUtils;
import com.example.administrator.huorongdai.xframe.utils.XPreferencesUtils;
import com.example.administrator.huorongdai.xframe.utils.http.HttpCallBack;
import com.example.administrator.huorongdai.xframe.utils.http.XHttp;
import com.example.administrator.huorongdai.xframe.widget.XLoadingDialog;
import com.example.administrator.huorongdai.xframe.widget.XToast;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BorrowedDetailActivity extends BaseActivity {

    private SwipeRefreshLayout refreshLayout;
    private MyScrollView scrollView;
    private CirclePercentBarBig circleBar;//进度圆
    private TextView tvProgress;//投资进度
    private TextView tvLoanAmt;//总金额
    private TextView tvLoanExpiry;//还款期限
    private TextView tvRemainingAmount;//剩余投资金额
    private TextView tvMinIvst;//起投金额
    private TextView tvMaxIvst;//单笔最大投资金额
    private TextView tvPaymentStyle;//还款方式
    private TextView tvStartTime;//开始时间
    private TextView tvEndTime;//截止时间
    private TextView tvRepayment;//立即还款
    private RecyclerView recyclerView;
    private CustomDialog dialog;//还款提示框
    private String custMobile;
    private String custPwd;//密文密码
    private String loanID;
    private String loanTitle;
    private String loanStatus;

    private MyAdapter adapter;
    private List<RpmtViewBean.ListBean.LstRptPlanBean> planListBeans;


    private SwipeRefreshLayout.OnRefreshListener refreshListener= new SwipeRefreshLayout.OnRefreshListener(){

        @Override
        public void onRefresh() {
            requestDate();
        }
    };

    @Override
    protected int getContentView() {
        return R.layout.activity_borrowed_detail;
    }

    @Override
    protected void initView() {
        scrollView=findViewById(R.id.msl_borrowed_detail);
        refreshLayout=findViewById(R.id.srl_borrowed_detail);
        refreshLayout.setColorScheme(R.color.word_red);
        refreshLayout.setOnRefreshListener(refreshListener);
        circleBar=findViewById(R.id.circle_bar_bid);
        tvProgress=findViewById(R.id.tv_progress);
        tvLoanAmt=findViewById(R.id.tv_loan_amt);
        tvRemainingAmount=findViewById(R.id.tv_remaining_amount);
        tvLoanExpiry=findViewById(R.id.tv_loan_expiry);
        tvMinIvst=findViewById(R.id.tv_min_ivst);
        tvMaxIvst=findViewById(R.id.tv_max_ivst);
        tvPaymentStyle=findViewById(R.id.tv_payment_style);
        tvStartTime=findViewById(R.id.tv_start_time);
        tvEndTime=findViewById(R.id.tv_end_time);
        recyclerView=findViewById(R.id.srl_borrowing_detail);
        tvRepayment=findViewById(R.id.tv_repayment);
        tvRepayment.setOnClickListener(this);

        //解决swiperefreshlayout与scrollview的冲突问题
        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if (refreshLayout != null) {
                    refreshLayout.setEnabled(scrollView.getScrollY() == 0);
                }
            }
        });

        Intent intent=getIntent();
        if(intent!=null){
            loanID=intent.getStringExtra("loanID");
            loanTitle=intent.getStringExtra("loanTitle");
            //7-招标中 8-待满标 9-待划转  10-划款中11-划款失败 12-待还款 13-已还清 14-已流标 15-已过期
            loanStatus=intent.getStringExtra("loanStatus");
            setTitle(loanTitle);
        }

        custMobile = (String) XPreferencesUtils.get("custMobile","");
        custPwd = (String) XPreferencesUtils.get("custPwd","");
        dialog = new CustomDialog(this, R.style.custom_dialog2, R.layout.login_notice);

        planListBeans =new ArrayList<>();
        CustomLinearLayoutManager linearLayoutManager = new CustomLinearLayoutManager(BorrowedDetailActivity.this);
        linearLayoutManager.setScrollEnabled(false);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter=new MyAdapter(recyclerView,planListBeans);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void loadData() {
        refreshLayout.setRefreshing(true);
        refreshListener.onRefresh();
    }

    private double yearRate;//年利率
    private void requestDate() {
        Map<String,Object > params =new HashMap<>();
        params .put("custMobile",custMobile);
        params .put("custPwd",custPwd);
        params .put("loanId",loanID);
        XHttp.obtain().post(Path.rpmt_view, params, new HttpCallBack<RpmtViewBean>() {
            @Override
            public void onSuccess(RpmtViewBean bean) {
                if(bean.isStatus()){
                    RpmtViewBean.LoanBean loanBean=bean.getLoan();

                    yearRate=loanBean.getLoanArp();//预计年利率

                    float progress=((float)loanBean.getTotalInvestMoney()/loanBean.getLoanAmt())*100;
                    tvProgress.setText(new DecimalFormat("0.0").format(progress)+"%");
                    circleBar.setPercentData(progress,new DecelerateInterpolator());
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
                    if(remainingAmount.endsWith("0000")){
                        remainingAmount=remainingAmount.substring(0,remainingAmount.length()-4);
                        tvRemainingAmount.setText(remainingAmount+"万元");
                    }else {
                        tvRemainingAmount.setText(remainingAmount+"元");
                    }

                    if(loanBean.getLoanExpiry()==1){//还款期限
                        tvLoanExpiry.setText(loanBean.getLoanExpiry()+"个月");
                    }else {
                        tvLoanExpiry.setText(loanBean.getLoanExpiry()+"天");
                    }

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

                    List<RpmtViewBean.ListBean.LstRptPlanBean>  beans= bean.getList().get(0).getLstRptPlan();
                    if(!XEmptyUtils.isEmpty(beans)){
                       adapter.setDataLists(beans);
                    }

                    for(RpmtViewBean.ListBean.LstRptPlanBean rptPlanBean:planListBeans){
                        if(rptPlanBean.getRpmtStatus()==1){
                            tvRepayment.setVisibility(View.VISIBLE);
                            tvRepayment.setText("第"+rptPlanBean.getRpmtIssue()+"期立即还款");
                            break;
                        }
                    }
                }
                if(refreshLayout.isRefreshing()){
                    refreshLayout.setRefreshing(false);
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
            case R.id.tv_repayment://立即还款
                for(RpmtViewBean.ListBean.LstRptPlanBean rptPlanBean:planListBeans){
                    if(rptPlanBean.getRpmtStatus()==1){
                        initDialog(rptPlanBean.getRpmtIssue(),rptPlanBean.getId());
                        break;
                    }
                }
                break;
        }
    }

    //还款提示框
    private void initDialog(final int number, final String repayId){
        dialog.show();
        final TextView titleTv=dialog.findViewById(R.id.tv_login_notice_title);
        TextView cancelTv=dialog.findViewById(R.id.tv_login_notice_cancel);
        TextView sureTv=dialog.findViewById(R.id.tv_login_notice_sure);
        titleTv.setText("是否确认还款第"+number+"期？");
        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //提示框取消
                dialog.dismiss();
            }
        });
        sureTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!ButtonUtils.isFastDoubleClick()){
                    dialog.dismiss();
                    XLoadingDialog.with(BorrowedDetailActivity.this)
                            .setCanceled(false) //设置手动不可取消
                            .setOrientation(XLoadingDialog.HORIZONTAL) //设置显示方式（水平或者垂直）
                            .setBackgroundColor(Color.parseColor("#aa000000"))//设置对话框背景
                            .setMessageColor(Color.WHITE)//设置消息字体颜色
                            .setMessage("还款中...")//设置消息文本
                            .show();//显示对话框
                    if(number==planListBeans.size()){
                        repay(repayId,true);
                    }else {
                        repay(repayId,false);
                    }
                }
            }
        });
    }

    private void repay(final String repayId ,final boolean isFinish){
        Map<String,Object > params =new HashMap<>();
        params .put("custMobile",custMobile);
        params .put("custPwd",custPwd);
        params .put("repayId",repayId);
        params .put("optSource","4");
        params .put("backUrl","123");
        XHttp.obtain().post(Path.repay, params, new HttpCallBack<RepayBean>() {
            @Override
            public void onSuccess(RepayBean bean) {
                XLoadingDialog.with(BorrowedDetailActivity.this).dismiss();
                if(bean.isStatus()){
                    if(isFinish){
                        EventBus.getDefault().post(new BorrowMsg(true));//通知还款记录的fragment刷新
                        finish();
                    }else {
                        refreshLayout.setRefreshing(true);
                        refreshListener.onRefresh();
                    }
                }
                XToast.normal(bean.getOptResultMsg());
            }

            @Override
            public void onFailed(String error) {
                XLoadingDialog.with(BorrowedDetailActivity.this).dismiss();
            }
        });
    }

    class MyAdapter extends XRecyclerViewAdapter<RpmtViewBean.ListBean.LstRptPlanBean>{
        DecimalFormat decimal=new DecimalFormat("0.00");
        public MyAdapter(@NonNull RecyclerView mRecyclerView, List<RpmtViewBean.ListBean.LstRptPlanBean> dataLists) {
            super(mRecyclerView, dataLists,R.layout.item_activity_borrowing_detail);
        }

        @Override
        protected void bindData(XViewHolder holder, RpmtViewBean.ListBean.LstRptPlanBean data, int position) {
            TextView tvDate1=holder.getView(R.id.tv_date1);//合约还款日期
            TextView tvAmt=holder.getView(R.id.tv_amt);//应还本息
            TextView tvLateAmt=holder.getView(R.id.tv_late_amt);//应付罚息
            TextView tvDate2=holder.getView(R.id.tv_date2);//实际还款日期
            TextView tvDai=holder.getView(R.id.tv_dai);//实际还款日期

            String date1=data.getShdRpmtDate();
            date1=date1.substring(0,4)+"-"+date1.substring(4,6)+"-"+date1.substring(6,8);
            tvDate1.setText(date1);

            tvLateAmt.setText(decimal.format(data.getLateIint()));

            double amt=data.getRpmtCapital()+data.getRpmtIint();
            tvAmt.setText("￥"+decimal.format(amt));


            if(data.getRpmtStatus()==2){
                String date2=data.getRealRpmtDate();
                date2=date2.substring(0,4)+"-"+date2.substring(4,6)+"-"+date2.substring(6,8);
                tvDate2.setText(date2);
            }else {
                tvDate2.setVisibility(View.GONE);
                tvDai.setVisibility(View.VISIBLE);
            }

        }
    }

    class CustomLinearLayoutManager extends LinearLayoutManager {
        private boolean isScrollEnabled = true;

        public CustomLinearLayoutManager(Context context) {
            super(context);
        }

        public void setScrollEnabled(boolean flag) {
            this.isScrollEnabled = flag;
        }

        @Override
        public boolean canScrollVertically() {
            //Similarly you can customize "canScrollHorizontally()" for managing horizontal scroll
            return isScrollEnabled && super.canScrollVertically();
        }
    }
}
