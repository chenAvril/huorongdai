package com.example.administrator.huorongdai.fragment;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.activity.AccountSafeActivity;
import com.example.administrator.huorongdai.activity.ApplyOfBorrowingActivity;
import com.example.administrator.huorongdai.activity.BorrowingRecordActivity;
import com.example.administrator.huorongdai.activity.CalculatorActivity;
import com.example.administrator.huorongdai.activity.HandLockActivity;
import com.example.administrator.huorongdai.activity.InformationDisclosureActivity;
import com.example.administrator.huorongdai.activity.IntegralShopActivity;
import com.example.administrator.huorongdai.activity.LatestNoticeActivity;
import com.example.administrator.huorongdai.activity.LoginActivity;
import com.example.administrator.huorongdai.activity.MyFriendActivity;
import com.example.administrator.huorongdai.activity.MyRedPacketActivity;
import com.example.administrator.huorongdai.activity.OpenAccountActivity;
import com.example.administrator.huorongdai.activity.PrizeActivity;
import com.example.administrator.huorongdai.activity.SignActivity;
import com.example.administrator.huorongdai.activity.WebSiteMsgActivity;
import com.example.administrator.huorongdai.base.LazyLoadFragment;
import com.example.administrator.huorongdai.view.CustomDialog;
import com.example.administrator.huorongdai.xframe.utils.XPreferencesUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiscoverFragment extends LazyLoadFragment implements View.OnClickListener {

    public DiscoverFragment() {

    }

    private boolean isLogin;//true已登录，false未登录
    private CustomDialog dialog;//用户登录提示框

    private LinearLayout llShop;//积分商城
    private LinearLayout llMyFriend;//我的好友
    private LinearLayout llMsg;//站内消息
    private LinearLayout llSign;//今日签到
    private LinearLayout llCalculator;//收益计算器
    private LinearLayout llRedPacket;//我的红包
    private LinearLayout llPrize;//投资奖品
    private LinearLayout llHandPsw;//手势密码
    private LinearLayout llBorrow;//我要借款
    private LinearLayout llAccounSafe;//账户安全
    private LinearLayout llBorrowRecord;//借款记录
    private LinearLayout llNotes;//信息披露

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_discover;
    }

    @Override
    protected void initViews() {
        llShop=findViewById(R.id.ll_jifen_shop);
        llMyFriend=findViewById(R.id.ll_my_friend);
        llMsg=findViewById(R.id.ll_net_msg);
        llSign=findViewById(R.id.ll_sign);
        llCalculator=findViewById(R.id.ll_calculator);
        llRedPacket=findViewById(R.id.ll_red_packet);
        llPrize=findViewById(R.id.ll_prize);
        llHandPsw=findViewById(R.id.ll_hand_psw);
        llBorrow=findViewById(R.id.ll_borrow);
        llAccounSafe=findViewById(R.id.ll_account_safe);
        llBorrowRecord=findViewById(R.id.ll_borrow_record);
        llNotes=findViewById(R.id.ll_latest_notes);
        llShop.setOnClickListener(this);
        llMyFriend.setOnClickListener(this);
        llMsg.setOnClickListener(this);
        llSign.setOnClickListener(this);
        llCalculator.setOnClickListener(this);
        llRedPacket.setOnClickListener(this);
        llPrize.setOnClickListener(this);
        llHandPsw.setOnClickListener(this);
        llBorrow.setOnClickListener(this);
        llAccounSafe.setOnClickListener(this);
        llBorrowRecord.setOnClickListener(this);
        llNotes.setOnClickListener(this);

        dialog = new CustomDialog(getActivity(), R.style.custom_dialog2, R.layout.login_notice);
    }

    @Override
    public void lazyLoad() {
    }

    @Override
    public void onClick(View view) {
        isLogin= (boolean) XPreferencesUtils.get("isLogin",false);
        if(isLogin){
            //未开户是4，开户是2
            int openStatus= (int) XPreferencesUtils.get("openStatus",4);
            switch (view.getId()){
                case R.id.ll_jifen_shop://积分商城
                    startActivity(new Intent(getActivity(),IntegralShopActivity.class));
                    break;
                case R.id.ll_my_friend://我的好友
                    startActivity(new Intent(getActivity(),MyFriendActivity.class));
                    break;
                case R.id.ll_net_msg://站内消息
                    startActivity(new Intent(getActivity(),WebSiteMsgActivity.class));
                    break;
                case R.id.ll_sign://今日签到
                    startActivity(new Intent(getActivity(),SignActivity.class));
                    break;
                case R.id.ll_calculator://收益计算器
                    startActivity(new Intent(getActivity(),CalculatorActivity.class));
                    break;
                case R.id.ll_red_packet://我的红包
                    Intent myRedPacketIntent=new Intent(getActivity(), MyRedPacketActivity.class);
                    startActivity(myRedPacketIntent);
                    break;
                case R.id.ll_prize://投资奖品
                    Intent intentHtml = new Intent(getActivity(), PrizeActivity.class);
                    startActivity(intentHtml);
                    break;
                case R.id.ll_hand_psw://手势密码
                    Intent intentLock=new Intent(getActivity(),HandLockActivity.class);
                    intentLock.putExtra("mode", "");
                    startActivity(intentLock);
                    break;
                case R.id.ll_borrow://我要借款
                    if(openStatus==4){
                        Intent intent=new Intent(getActivity(),OpenAccountActivity.class);
                        startActivity(intent);
                    }else if(openStatus==2){
                        Intent applyIntent=new Intent(getActivity(), ApplyOfBorrowingActivity.class);
                        startActivity(applyIntent);
                    }
                    break;
                case R.id.ll_account_safe://账户安全
                    Intent accSafeIntent=new Intent(getActivity(), AccountSafeActivity.class);
                    startActivity(accSafeIntent);

                    break;
                case R.id.ll_borrow_record://借款记录
                    Intent borrowingRecordIntent=new Intent(getActivity(), BorrowingRecordActivity.class);
                    startActivity(borrowingRecordIntent);
                    break;
                case R.id.ll_latest_notes://信息披露
                    Intent intentNotice=new Intent(getActivity(), InformationDisclosureActivity.class);
                    startActivity(intentNotice);
                    break;
            }
        }else {
            initDialog();
        }
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
}
