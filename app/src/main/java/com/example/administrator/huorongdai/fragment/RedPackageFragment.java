package com.example.administrator.huorongdai.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.huorongdai.MainActivity;
import com.example.administrator.huorongdai.Path;
import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.adapter.MyGridLayoutManager;
import com.example.administrator.huorongdai.base.LazyLoadFragment;
import com.example.administrator.huorongdai.eventbusbean.Msg;
import com.example.administrator.huorongdai.gsonbean.GetRedPacketBean;
import com.example.administrator.huorongdai.view.PointDividerView;
import com.example.administrator.huorongdai.xframe.adapter.XRecyclerViewAdapter;
import com.example.administrator.huorongdai.xframe.adapter.XViewHolder;
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

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class RedPackageFragment extends LazyLoadFragment {

    private XLoadingView xLoadingView;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;

    private String redpStatus;
    private String custMobile;//手机号码
    private String custPwd;//密文密码
    private int pageNum=1;

    private MyAdapter adapter;
    private List<GetRedPacketBean.RedPacketListBean> listBeans=new ArrayList<>();

    private Handler handler=new Handler(new MyCallback());
    private final class MyCallback implements Handler.Callback{

        @Override
        public boolean handleMessage(Message message) {
            if(message.what==1){
                if(XNetworkUtils.isConnected()){
                    pageNum=1;
                    requestData();
                    xLoadingView.showContent();
                }else {
                    if(refreshLayout.isRefreshing()){
                        refreshLayout.setRefreshing(false);
                    }
                    xLoadingView.showNoNetwork();
                }
            }else if(message.what==2){
                //getActivity().finish();
                EventBus.getDefault().post(new Msg("5"));
            }
            return true;
        }
    }

    public RedPackageFragment() {
    }

    @SuppressLint("ValidFragment")
    public RedPackageFragment(String redpStatus) {
        this.redpStatus=redpStatus;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_red_package;
    }

    @Override
    protected void initViews() {
        xLoadingView=findViewById(R.id.xloading_view);
        refreshLayout=findViewById(R.id.srl_fragment_red_package);
        refreshLayout.setColorScheme(R.color.word_red);
        refreshLayout.setOnRefreshListener(refreshListener);
        recyclerView=findViewById(R.id.rl_fragment_red_package);

        xLoadingView.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xLoadingView.showLoading();
                Message message=Message.obtain();
                message.what=1;
                handler.sendMessageDelayed(message,600);
            }
        });

        custMobile = (String) XPreferencesUtils.get("custMobile","");
        custPwd = (String) XPreferencesUtils.get("custPwd","");

        adapter=new MyAdapter(recyclerView,listBeans);

        recyclerView.setLayoutManager(new MyGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);

        adapter.setOnLoadMoreListener(new XRecyclerViewAdapter.OnLoadMoreListener() {
            @Override
            public void onRetry() {

            }

            @Override
            public void onLoadMore() {
                requestData();
            }
        });

        refreshLayout.setRefreshing(true);
        refreshListener.onRefresh();
    }

    @Override
    public void lazyLoad() {

    }

    private SwipeRefreshLayout.OnRefreshListener refreshListener=new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            adapter.isLoadMore(false);
            Message message=Message.obtain();
            message.what=1;
            handler.sendMessage(message);
        }
    };

    //请求数据
    private void requestData(){
        //红包状态，1-有效，2-已使用，3-划转中，4-还款成功，5-还款失败，6-过期；
        Map<String,Object > params =new HashMap<>();
        params .put("custMobile",custMobile);
        params .put("custPwd",custPwd);
        params .put("pageNum",""+pageNum);
        params .put("pageSize","10");
        params .put("redpStatus",redpStatus);
        XHttp.obtain().post(Path.get_red_packet, params, new HttpCallBack<GetRedPacketBean>() {
            @Override
            public void onSuccess(GetRedPacketBean bean) {
                if(bean.isStatus()){
                    if(bean.getRedPacketList().size()>0){
                        if(pageNum==1){
                            adapter.setDataLists(bean.getRedPacketList());
                            adapter.isLoadMore(true);
                        }else {
                            adapter.addAll(bean.getRedPacketList());
                        }
                    }

                    if("1".equals(bean.getPageNum()) && "0".equals(bean.getTotalPages()) && bean.getRedPacketList().size()==0){
                        xLoadingView.showEmpty();
                    }
                    if(bean.getPageNum().equals(bean.getTotalPages()) || "0".equals(bean.getTotalPages())){
                        adapter.showLoadComplete();
                    }else {
                        pageNum=pageNum+1;
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

    class MyAdapter extends XRecyclerViewAdapter<GetRedPacketBean.RedPacketListBean>{
        DecimalFormat decimalFormat=new DecimalFormat("0");
        public MyAdapter(@NonNull RecyclerView mRecyclerView, List<GetRedPacketBean.RedPacketListBean> dataLists) {
            super(mRecyclerView, dataLists,R.layout.item_rl_fragment_red_package);
        }

        @Override
        protected void bindData(XViewHolder holder, GetRedPacketBean.RedPacketListBean data, int position) {
            LinearLayout llLeft=holder.getView(R.id.ll_left_bg);//左边背景
            ImageView ivRight=holder.getView(R.id.iv_right_bg);//右边背景
            ImageView ivStar=holder.getView(R.id.iv_star);//五角星
            TextView tvMoney=holder.getView(R.id.tv_touzi_money);//投资卷的金额
            TextView tvType=holder.getView(R.id.tv_touzi_type);//投资劵的类型
            TextView tvQitou=holder.getView(R.id.tv_qitou);//起头金额
            TextView tvMinMoney=holder.getView(R.id.tv_min_money);//起头金额
            TextView tvTimeTitle=holder.getView(R.id.tv_time_title);//有效时间
            TextView tvValidTime=holder.getView(R.id.tv_valid_time);//有效时间
            TextView tvUse=holder.getView(R.id.tv_use);//立即使用
            PointDividerView pointDividerView=holder.getView(R.id.item_pdv);//虚线


            tvMoney.setText("¥"+data.getRedpAmt());
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
            }else if(redpStatus==2){
                pointDividerView.setmColor(getResources().getColor(R.color.bg_yellow));
                llLeft.setBackgroundResource(R.drawable.red_package_red);
                tvUse.setVisibility(View.GONE);
                ivStar.setBackgroundResource(R.drawable.yellow_star);
                ivRight.setImageResource(R.drawable.ysy);
            }else {
                pointDividerView.setmColor(getResources().getColor(R.color.word_gray));
                tvMoney.setTextColor(getResources().getColor(R.color.word_gray));
                tvType.setTextColor(getResources().getColor(R.color.word_gray));
                tvMinMoney.setTextColor(getResources().getColor(R.color.word_gray));
                tvQitou.setTextColor(getResources().getColor(R.color.word_gray));
                llLeft.setBackgroundResource(R.drawable.red_package_grad);
                tvUse.setVisibility(View.GONE);
                ivStar.setBackgroundResource(R.drawable.grad_star);
                tvValidTime.setTextColor(getResources().getColor(R.color.word_gray));
                tvTimeTitle.setTextColor(getResources().getColor(R.color.word_gray));
                ivRight.setImageResource(R.drawable.ygq);
            }

            tvUse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    Message message=Message.obtain();
                    message.what=2;
                    handler.sendMessageDelayed(message,100);
                }
            });
        }
    }
}
