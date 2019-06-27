package com.example.administrator.huorongdai.fragment;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.huorongdai.Path;
import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.activity.AccountInfoActivity;
import com.example.administrator.huorongdai.activity.DealManagerActivity;
import com.example.administrator.huorongdai.activity.LoginActivity;
import com.example.administrator.huorongdai.activity.LookMoneyActivity;
import com.example.administrator.huorongdai.activity.OpenAccountActivity;
import com.example.administrator.huorongdai.activity.RechargeActivity;
import com.example.administrator.huorongdai.activity.SettingActivity;
import com.example.administrator.huorongdai.activity.TouziManagerActivity;
import com.example.administrator.huorongdai.activity.WithdrawActivity;
import com.example.administrator.huorongdai.base.LazyLoadFragment;
import com.example.administrator.huorongdai.gsonbean.AccountInfo;
import com.example.administrator.huorongdai.gsonbean.RealTimeCustBean;
import com.example.administrator.huorongdai.view.CustomDialog;
import com.example.administrator.huorongdai.view.GlideCircleTransform;
import com.example.administrator.huorongdai.view.MyScrollView;
import com.example.administrator.huorongdai.xframe.utils.XEmptyUtils;
import com.example.administrator.huorongdai.xframe.utils.XNetworkUtils;
import com.example.administrator.huorongdai.xframe.utils.XPreferencesUtils;
import com.example.administrator.huorongdai.xframe.utils.http.HttpCallBack;
import com.example.administrator.huorongdai.xframe.utils.http.XHttp;
import com.example.administrator.huorongdai.xframe.utils.imageload.XImage;
import com.example.administrator.huorongdai.xframe.utils.log.XLog;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends LazyLoadFragment implements View.OnClickListener{


    public MyFragment() {

    }

    private SwipeRefreshLayout refreshLayout;
    private MyScrollView scrollView;
    private LinearLayout llUser;//个人信息
    private ImageView ivHeader;//头像
    private TextView tvName;//账户名
    private TextView canUseMoneyTv;//可用余额
    private TextView tvPay;//充值
    private TextView tvWithdrawal;//提现
    private RelativeLayout rlAllMoney;//总资产
    private TextView tvMoney;//总资产
    private RelativeLayout rlTouziManager;//投资管理
    private RelativeLayout rlDealManager;//交易记录
    private RelativeLayout rlSetting;//设置
    private CustomDialog dialog;//用户登录提示框
    private String custMobile="";//手机号码
    private String custPwd;//密文密码
    private String custName;
    private boolean isLogin;//true已登录，false未登录
    private int openStatus;//未开户是4，开户是2
    private GlideCircleTransform circleTransform;

    private DecimalFormat decimal;
    public SwipeRefreshLayout.OnRefreshListener refreshListener=new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {//刷新
            isLogin= (boolean) XPreferencesUtils.get("isLogin",false);
            openStatus= (int) XPreferencesUtils.get("openStatus",4);
            if(XNetworkUtils.isConnected()){
                if(isLogin){
                    custMobile = (String) XPreferencesUtils.get("custMobile","");
                    custPwd = (String) XPreferencesUtils.get("custPwd","");
                    custName= (String) XPreferencesUtils.get("custName","");
                    String headIcon = (String) XPreferencesUtils.get("headIcon","");
                    tvName.setText(custMobile);
                    if(XEmptyUtils.isEmpty(headIcon)){
                        XImage.getInstance().load(ivHeader, R.drawable.header);
                    }else {
                        XImage.getInstance().load(ivHeader, headIcon,circleTransform);
                    }

                    getALlMoeny();
                    getUseMoney();
                }else {
                    XImage.getInstance().load(ivHeader, R.drawable.header);
                    tvName.setText("您好，请登录/注册");
                    canUseMoneyTv.setText("0.00");
                    tvMoney.setText("0.00");
                    //initDialog();
                }
            }else {
                tvName.setText("网络连接失败!");
                canUseMoneyTv.setText("0.00");
                tvMoney.setText("0.00");
                XImage.getInstance().load(ivHeader, R.drawable.header);
                //XToast.normal("网络连接失败!");
            }
            if(refreshLayout.isRefreshing()){
                refreshLayout.setRefreshing(false);
            }
        }
    };

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    protected void initViews() {
        refreshLayout=findViewById(R.id.srl_my);
        refreshLayout.setColorScheme(R.color.word_red);
        refreshLayout.setOnRefreshListener(refreshListener);
        scrollView=findViewById(R.id.msl_my);
        ivHeader=findViewById(R.id.iv_header);
        tvName=findViewById(R.id.tv_user_name);
        canUseMoneyTv=findViewById(R.id.tv_can_use_money);
        tvPay=findViewById(R.id.tv_pay);
        tvMoney=findViewById(R.id.tv_all_money);
        tvWithdrawal=findViewById(R.id.tv_withdrawal);
        rlTouziManager=findViewById(R.id.rl_touzi_manager);
        rlDealManager=findViewById(R.id.rl_deal_manager);
        rlSetting=findViewById(R.id.rl_setting);
        llUser=findViewById(R.id.ll_user);
        rlAllMoney=findViewById(R.id.rl_all_money);
        llUser.setOnClickListener(this);
        tvPay.setOnClickListener(this);
        tvWithdrawal.setOnClickListener(this);
        rlTouziManager.setOnClickListener(this);
        rlDealManager.setOnClickListener(this);
        rlAllMoney.setOnClickListener(this);
        rlSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent setIntent=new Intent(getActivity(), SettingActivity.class);
                startActivity(setIntent);
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
        circleTransform=new GlideCircleTransform(getActivity());
        decimal=new DecimalFormat("0.00");
        dialog = new CustomDialog(getActivity(), R.style.custom_dialog2, R.layout.login_notice);

        refreshLayout.setRefreshing(true);
    }

    @Override
    public void onResume() {
        super.onResume();

        refreshListener.onRefresh();
    }

    @Override
    public void lazyLoad() {

    }

    //登录提示框
    private void initDialog(){
        dialog.show();
        TextView titleTv=dialog.findViewById(R.id.tv_login_notice_title);
        TextView cancelTv=dialog.findViewById(R.id.tv_login_notice_cancel);
        TextView sureTv=dialog.findViewById(R.id.tv_login_notice_sure);
        titleTv.setText("未登录，是否前往登录？");
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
                //提示框确认去登录
                dialog.dismiss();
                Intent intent=new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onClick(View view) {
        isLogin= (boolean) XPreferencesUtils.get("isLogin",false);
        openStatus= (int) XPreferencesUtils.get("openStatus",4);
        if(isLogin){
            switch (view.getId()){
                case R.id.ll_user://个人信息
                    Intent accInfoIntent=new Intent(getActivity(), AccountInfoActivity.class);
                    startActivity(accInfoIntent);
                    break;
                case R.id.tv_pay://充值
                    if(openStatus==4){
                        Intent intent=new Intent(getActivity(),OpenAccountActivity.class);
                        startActivity(intent);
                    }else if(openStatus==2){
                        Intent intent=new Intent(getActivity(), RechargeActivity.class);
                        startActivity(intent);
                    }
                    break;
                case R.id.tv_withdrawal://提现
                    if(openStatus==4){
                        Intent intent=new Intent(getActivity(),OpenAccountActivity.class);
                        startActivity(intent);
                    }else if(openStatus==2){
                        Intent intent=new Intent(getActivity(), WithdrawActivity.class);
                        startActivity(intent);
                    }
                    break;
                case R.id.rl_all_money://总资产
                    Intent lookMoneyIntent=new Intent(getActivity(), LookMoneyActivity.class);
                    startActivity(lookMoneyIntent);
                    break;
                case R.id.rl_touzi_manager://投资管理
                    Intent touziManagerntent=new Intent(getActivity(), TouziManagerActivity.class);
                    startActivity(touziManagerntent);
                    break;
                case R.id.rl_deal_manager://交易记录
                    Intent dealManagerIntent=new Intent(getActivity(), DealManagerActivity.class);
                    startActivity(dealManagerIntent);
                    break;
            }
        }else {
            initDialog();
        }
    }

    //获取可用余额
    private void getUseMoney(){
        Map<String,Object > params =new HashMap<>();
        params .put("custMobile",custMobile);
        params .put("custName",custName);
        params .put("custPwd",custPwd);
        XHttp.obtain().post(Path.get_geal_time_cust, params, new HttpCallBack<RealTimeCustBean>() {
            @Override
            public void onSuccess(RealTimeCustBean bean) {
                if(bean.isStatus()){
                    canUseMoneyTv.setText(""+bean.getAvailAmt());
                }
            }

            @Override
            public void onFailed(String error) {

            }
        });
    }

    //计算总资产
    private void getALlMoeny(){
        Map<String,Object > params =new HashMap<>();
        params .put("custMobile",custMobile);
        params .put("custPwd",custPwd);
        XHttp.obtain().post(Path.account_info, params, new HttpCallBack<AccountInfo>() {
            @Override
            public void onSuccess(AccountInfo bean) {
                if(bean.isStatus()){
                    double availAmt=0.00;
                    if(!XEmptyUtils.isEmpty(bean.getAvailAmt())){
                        availAmt=Double.valueOf(bean.getAvailAmt());
                    }
                    double frzAmt=0.00;
                    if(!XEmptyUtils.isEmpty(bean.getFrzAmt())){
                        frzAmt=Double.valueOf(bean.getFrzAmt());
                    }
                    double totalAsset=availAmt+frzAmt+bean.getUnEarnCaptial()+bean.getUnEarnIint();
                    tvMoney.setText(decimal.format(totalAsset));
                }
            }

            @Override
            public void onFailed(String error) {

            }
        });
    }
}
