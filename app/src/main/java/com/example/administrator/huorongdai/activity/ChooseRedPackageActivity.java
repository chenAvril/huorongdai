package com.example.administrator.huorongdai.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.huorongdai.Path;
import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.base.BaseActivity;
import com.example.administrator.huorongdai.eventbusbean.RedPackageMsg;
import com.example.administrator.huorongdai.gsonbean.ExpectEarnBean;
import com.example.administrator.huorongdai.view.CustomDialog;
import com.example.administrator.huorongdai.view.PointDividerView;
import com.example.administrator.huorongdai.xframe.adapter.XRecyclerViewAdapter;
import com.example.administrator.huorongdai.xframe.adapter.XViewHolder;
import com.example.administrator.huorongdai.xframe.utils.XEmptyUtils;
import com.example.administrator.huorongdai.xframe.utils.XNetworkUtils;
import com.example.administrator.huorongdai.xframe.utils.XPreferencesUtils;
import com.example.administrator.huorongdai.xframe.utils.http.HttpCallBack;
import com.example.administrator.huorongdai.xframe.utils.http.XHttp;
import com.example.administrator.huorongdai.xframe.widget.loadingview.XLoadingView;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChooseRedPackageActivity extends BaseActivity {

    private XLoadingView xLoadingView;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private Button btnSure;

    private CustomDialog dialog;//确定使用提示框

    private String custMobile;//手机号码
    private String custPwd;//密文密码
    private String investAmt;//投资金额
    private String loanId;

    private MyAdapter adapter;
    private List<ExpectEarnBean.RedPacketListBean> listBeans=new ArrayList<>();

    private Handler handler=new Handler(new MyCallback());
    private final class MyCallback implements Handler.Callback{

        @Override
        public boolean handleMessage(Message message) {
            if (XNetworkUtils.isConnected()){
                requestData();
                xLoadingView.showContent();
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
        return R.layout.activity_choose_red_package;
    }

    @Override
    protected void initView() {
        Intent intent=getIntent();
        if(intent!=null){
            investAmt=intent.getStringExtra("investAmt");
            loanId=intent.getStringExtra("loanId");
        }
        xLoadingView=findViewById(R.id.xloading_view);
        refreshLayout=findViewById(R.id.srl_choose_red_package);
        recyclerView=findViewById(R.id.rl_choose_red_package);
        btnSure=findViewById(R.id.btn_sure);
        btnSure.setOnClickListener(this);
        refreshLayout.setColorScheme(R.color.word_red);
        refreshLayout.setOnRefreshListener(refreshListener);
        xLoadingView.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xLoadingView.showLoading();
                handler.sendMessageDelayed(Message.obtain(),600);
            }
        });

        setTitle("请选择红包");

        dialog = new CustomDialog(this, R.style.custom_dialog2, R.layout.login_notice);

        custMobile = (String) XPreferencesUtils.get("custMobile","");
        custPwd = (String) XPreferencesUtils.get("custPwd","");

        adapter=new MyAdapter(recyclerView,listBeans);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void loadData() {
        refreshLayout.setRefreshing(true);
        refreshListener.onRefresh();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_sure:
                RedPackageMsg msg=new RedPackageMsg();
                msg.setId("");
                msg.setRedpAmt(0);
                msg.setRedpTypeName("");
                msg.setUseRedpMinAmt(0);
                EventBus.getDefault().post(msg);
                finish();
                break;
        }
    }

    //请求数据
    private void requestData(){
        //investAmt:ivstAmt,loanId:loanId,custMobile:custMobile,custPwd:custPwd}
        Map<String,Object > params =new HashMap<>();
        params .put("custMobile",custMobile);
        params .put("custPwd",custPwd);
        params .put("investAmt",investAmt);
        params .put("loanId",loanId);
        XHttp.obtain().post(Path.get_expect_earn, params, new HttpCallBack<ExpectEarnBean>() {
            @Override
            public void onSuccess(ExpectEarnBean bean) {
                if(bean.isStatus()){
                    if(!XEmptyUtils.isEmpty(bean.getRedPacketList()) && bean.getRedPacketList().size()>0){
                        if(adapter!=null){
                            adapter.clear();
                        }
                        adapter.addAll(bean.getRedPacketList());
                    }else {
                        xLoadingView.showEmpty();
                    }

                }else {
                    xLoadingView.showError();
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

    //确定使用提示框
    private void initDialog(final ExpectEarnBean.RedPacketListBean data ){
        dialog.show();
        TextView titleTv=dialog.findViewById(R.id.tv_login_notice_title);
        TextView cancelTv=dialog.findViewById(R.id.tv_login_notice_cancel);
        TextView sureTv=dialog.findViewById(R.id.tv_login_notice_sure);
        final DecimalFormat decimalFormat=new DecimalFormat("0");
        titleTv.setText("确定使用"+decimalFormat.format(data.getRedpAmt())+"元"+data.getRedpTypeName()+"？");
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
                //提示框确认
                dialog.dismiss();
                RedPackageMsg msg=new RedPackageMsg();
                msg.setId(data.getId());
                msg.setRedpAmt((int) data.getRedpAmt());
                msg.setRedpTypeName(data.getRedpTypeName());
                msg.setUseRedpMinAmt((int) data.getUseRedpMinAmt());
                EventBus.getDefault().post(msg);
                finish();
            }
        });

    }

    class MyAdapter extends XRecyclerViewAdapter<ExpectEarnBean.RedPacketListBean> {
        DecimalFormat decimalFormat=new DecimalFormat("0");
        public MyAdapter(@NonNull RecyclerView mRecyclerView, List<ExpectEarnBean.RedPacketListBean> dataLists) {
            super(mRecyclerView, dataLists,R.layout.item_rl_fragment_red_package);
        }

        @Override
        protected void bindData(XViewHolder holder, final ExpectEarnBean.RedPacketListBean data, int position) {
            LinearLayout llLeft=holder.getView(R.id.ll_left_bg);//左边背景
            ImageView ivRight=holder.getView(R.id.iv_right_bg);//右边背景
            ImageView ivStar=holder.getView(R.id.iv_star);//五角星
            TextView tvMoney=holder.getView(R.id.tv_touzi_money);//投资卷的金额
            TextView tvType=holder.getView(R.id.tv_touzi_type);//投资劵的类型
            TextView tvMinMoney=holder.getView(R.id.tv_min_money);//起头金额
            TextView tvValidTime=holder.getView(R.id.tv_valid_time);//有效时间
            TextView tvUse=holder.getView(R.id.tv_use);//立即使用
            PointDividerView pointDividerView=holder.getView(R.id.item_pdv);//虚线

            tvMoney.setText("￥"+data.getRedpAmt());
            tvType.setText(data.getRedpTypeName());
            tvMinMoney.setText(decimalFormat.format(data.getUseRedpMinAmt())+"元");

            //20170729 154945
            String startTime=data.getAddTime();
            String endTime=data.getEndTime();
            String time=startTime.substring(0,4)+"-"+startTime.substring(4,6)+"-"+startTime.substring(6,8)+"-"
                    +endTime.substring(0,4)+"-"+endTime.substring(4,6)+"-"+endTime.substring(6,8);
            tvValidTime.setText(time);

            //红包状态，1-有效，2-已使用，6-过期；
            int redpStatus=data.getRedpStatus();
            if(redpStatus==1){
                pointDividerView.setmColor(getResources().getColor(R.color.bg_yellow));
                llLeft.setBackgroundResource(R.drawable.red_package_red);
                ivRight.setVisibility(View.GONE);
                ivStar.setBackgroundResource(R.drawable.yellow_star);
            }

            tvUse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    initDialog(data);
                    RedPackageMsg msg=new RedPackageMsg();
                    msg.setId(data.getId());
                    msg.setRedpAmt(data.getRedpAmt());
                    msg.setRedpTypeName(data.getRedpTypeName());
                    msg.setUseRedpMinAmt(data.getUseRedpMinAmt());
                    EventBus.getDefault().post(msg);
                    finish();
                }
            });
        }
    }
}
