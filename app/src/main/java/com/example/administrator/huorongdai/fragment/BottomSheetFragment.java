package com.example.administrator.huorongdai.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.huorongdai.Path;
import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.activity.ChooseRedPackageActivity;
import com.example.administrator.huorongdai.eventbusbean.RedPackageMsg;
import com.example.administrator.huorongdai.gsonbean.ExpectEarnBean;
import com.example.administrator.huorongdai.gsonbean.InvestBean;
import com.example.administrator.huorongdai.gsonbean.RealTimeCustBean;
import com.example.administrator.huorongdai.util.ButtonUtils;
import com.example.administrator.huorongdai.view.AmountView;
import com.example.administrator.huorongdai.xframe.utils.XEmptyUtils;
import com.example.administrator.huorongdai.xframe.utils.XPreferencesUtils;
import com.example.administrator.huorongdai.xframe.utils.http.HttpCallBack;
import com.example.administrator.huorongdai.xframe.utils.http.XHttp;
import com.example.administrator.huorongdai.xframe.widget.XLoadingDialog;
import com.example.administrator.huorongdai.xframe.widget.XToast;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by caik on 2016/9/28.
 */

public class BottomSheetFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    private TextView tvBalance;//账户余额
    private TextView tvCast;//可投金额
    private AmountView amountView;//投资金额
    private TextView tvEarning;//预计收益
    private TextView tvLine;
    private RelativeLayout llPacket;
    private LinearLayout llChoose;//选择红包
    private EditText etTradePsw;//交易密码
    private TextView btnSure;//确认投资

    private int useRedpMinAmt=0;//选择红包的起投金额
    private String redPackageId="";//红包的id
    private TextView tvRedPackage;//红包
    private String balance ;//当前账户剩余金额
    private int amountNum=0;

    private String custMobile;
    private String custName;
    private String custPwd;//密文密码

    private String loanID="";//标的id
    private String canBeCast="";//可用金额

    public void setDatas(String loanID , String canBeCast) {
        this.loanID = loanID;
        this.canBeCast = canBeCast;
    }

    public BottomSheetFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_bottom_sheet, container, false);
        //拿到布局填充器返回的view后
        ScreenAdapterTools.getInstance().loadView(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        tvBalance=view.findViewById(R.id.tv_account_balance);
        tvCast=view.findViewById(R.id.tv_can_be_cast);
        tvEarning=view.findViewById(R.id.tv_earning);
        etTradePsw=view.findViewById(R.id.et_trade_psw);
        btnSure=view.findViewById(R.id.btn_investment_sure);
        llChoose=view.findViewById(R.id.ll_choose_red_package);
        tvRedPackage=view.findViewById(R.id.tv_red_package);
        tvLine=view.findViewById(R.id.tv_line);
        llPacket=view.findViewById(R.id.ll_red_packet);
        amountView=view.findViewById(R.id.amount_view);
        btnSure.setOnClickListener(this);
        llChoose.setOnClickListener(this);

        custMobile = (String) XPreferencesUtils.get("custMobile","");
        custPwd = (String) XPreferencesUtils.get("custPwd","");
        custName= (String) XPreferencesUtils.get("custName","");

        int goods=0;
        if(!XEmptyUtils.isEmpty(canBeCast)){
            goods=Integer.parseInt(canBeCast)/100;

            String cast=canBeCast;
            if(cast.endsWith("000")){
                cast=cast.substring(0,cast.length()-3);
                //tvCast.setText(cast+",000");
                tvCast.setText(cast+"000");
            }else {
                tvCast.setText(cast);
            }
        }

        amountView.setGoods_storage(goods);
        amountView.etAmount.setText(0+"");
        amountView.setOnAmountChangeListener(new AmountView.OnAmountChangeListener() {
            @Override
            public void onAmountChange(View view, int amount) {
                amountNum=amount;
                requestExpectEarn(amount+"",loanID);
            }
        });
        getRealCust();
    }

    @Override
    public void onClick(View view) {
        int touziMoney=amountNum;
        switch (view.getId()){
            case R.id.ll_choose_red_package://选择红包
                Intent intent=new Intent(getActivity(),ChooseRedPackageActivity.class);
                intent.putExtra("loanId",loanID);
                intent.putExtra("investAmt",touziMoney+"");
                startActivity(intent);
                break;
            case R.id.btn_investment_sure://确认投资
                if(XEmptyUtils.isEmpty(balance)){
                    return;
                }
                if(balance.contains(".")){
                    balance=balance.substring(0,balance.indexOf("."));
                }
                if(Integer.valueOf(balance)>touziMoney || Integer.valueOf(balance)==touziMoney){
                    String tradePwd=etTradePsw.getText().toString().trim();
                    if(XEmptyUtils.isEmpty(tradePwd)){
                        XToast.normal("投资密码不能为空");
                        return;
                    }
                    if(touziMoney==0){
                        XToast.normal("投资金额必须大于0");
                        return;
                    }
                    if(touziMoney%100!=0){
                        XToast.normal("投资金额必须是100的整数倍");
                        return;
                    }
                    if(useRedpMinAmt!=0){
                        if(touziMoney<useRedpMinAmt){
                            XToast.normal("当前红包最小起投金额是"+useRedpMinAmt+"元");
                            return;
                        }
                    }
                    if(!ButtonUtils.isFastDoubleClick()){
                        XLoadingDialog.with(getActivity())
                                .setCanceled(false) //设置手动不可取消
                                .setOrientation(XLoadingDialog.HORIZONTAL) //设置显示方式（水平或者垂直）
                                .setBackgroundColor(Color.parseColor("#aa000000"))//设置对话框背景
                                .setMessageColor(Color.WHITE)//设置消息字体颜色
                                .setMessage("投资中...")//设置消息文本
                                .show();//显示对话框
                        requestInvest(tradePwd,touziMoney);
                    }
                }else {
                    XToast.normal("您的账户余额不足,请进行充值!");
                }
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMsg(RedPackageMsg message){
        useRedpMinAmt=message.getUseRedpMinAmt();
        redPackageId=message.getId();
        if(tvRedPackage!=null){
            if(!XEmptyUtils.isEmpty(redPackageId)){
                tvRedPackage.setTextColor(getResources().getColor(R.color.word_red));
                tvRedPackage.setText(message.getRedpAmt()+"元"+message.getRedpTypeName());
            }else {
                tvRedPackage.setTextColor(getResources().getColor(R.color.word_gray));
                tvRedPackage.setText("请选择红包");
            }
        }
    }

    //获取可用余额
    private void getRealCust(){
        Map<String,Object > params =new HashMap<>();
        params .put("custMobile",custMobile);
        params .put("custName",custName);
        params .put("custPwd",custPwd);
        XHttp.obtain().post(Path.get_geal_time_cust, params, new HttpCallBack<RealTimeCustBean>() {
            @Override
            public void onSuccess(RealTimeCustBean bean) {
                if(bean.isStatus()){
                    balance=bean.getAvailAmt();
                    tvBalance.setText(balance);
                }
            }

            @Override
            public void onFailed(String error) {

            }
        });
    }

    //投资
    private void requestInvest(String tradePwd,int investAmt){
        Map<String,Object > params =new HashMap<>();
        params .put("custMobile",custMobile);
        params .put("custPwd",custPwd);
        params .put("backUrl","http://www.huorongdai.com");//必给
        params .put("optSource","4");
        params .put("loanId",loanID);
        params .put("agreePwd","");
        params .put("tradePwd",tradePwd);
        params .put("investAmt",investAmt+"");
        params .put("redPacketId",redPackageId);
        XHttp.obtain().post(Path.invest, params, new HttpCallBack<InvestBean>() {
            @Override
            public void onSuccess(InvestBean bean) {
                XLoadingDialog.with(getActivity()).dismiss();
                if(bean.isStatus()){
                    onReFresh.onReFresh(bean.isStatus());
                }
                if(!XEmptyUtils.isEmpty(bean.getMessage())){
                    XToast.normal(bean.getMessage());
                }
                if(!XEmptyUtils.isEmpty(bean.getOptResultMsg())){
                    XToast.normal(bean.getOptResultMsg());
                }
            }

            @Override
            public void onFailed(String error) {
                XLoadingDialog.with(getActivity()).dismiss();
            }
        });
    }

    //请求红包和收益的数据
    private void requestExpectEarn(String investAmt,String loanId){
        Map<String,Object > params =new HashMap<>();
        params .put("custMobile",custMobile);
        params .put("custPwd",custPwd);
        params .put("investAmt",investAmt);
        params .put("loanId",loanId);
        XHttp.obtain().post(Path.get_expect_earn, params, new HttpCallBack<ExpectEarnBean>() {
            @Override
            public void onSuccess(ExpectEarnBean bean) {
                if(bean.isStatus()){
                    if(tvEarning!=null){
                        tvEarning.setText(bean.getExpectEarn()+"");
                    }
                    if(bean.getRedPacketList().size()>0){
                        if(tvLine!=null){
                            tvLine.setVisibility(View.VISIBLE);
                        }
                        if(llPacket!=null){
                            llPacket.setVisibility(View.VISIBLE);
                        }
                    }else {
                        if(tvLine!=null){
                            tvLine.setVisibility(View.GONE);
                        }
                        if(llPacket!=null){
                            llPacket.setVisibility(View.GONE);
                        }
                    }
                }else {
                    if(tvLine!=null){
                        tvLine.setVisibility(View.GONE);
                    }
                    if(llPacket!=null){
                        llPacket.setVisibility(View.GONE);
                    }
                    if(tvEarning!=null){
                        tvEarning.setText("0.0");
                    }
                }
            }

            @Override
            public void onFailed(String error) {

            }
        });
    }

    public void setOnReFresh(OnReFresh onReFresh) {
        this.onReFresh = onReFresh;
    }

    private OnReFresh onReFresh;
    public interface OnReFresh{
        void onReFresh(boolean isReFresh);
    }

}
