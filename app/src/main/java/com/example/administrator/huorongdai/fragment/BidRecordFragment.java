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
import android.widget.TextView;

import com.example.administrator.huorongdai.Path;
import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.activity.BidDetailActivity;
import com.example.administrator.huorongdai.adapter.MyGridLayoutManager;
import com.example.administrator.huorongdai.base.LazyLoadFragment;
import com.example.administrator.huorongdai.gsonbean.SelfInvestBean;
import com.example.administrator.huorongdai.xframe.adapter.XRecyclerViewAdapter;
import com.example.administrator.huorongdai.xframe.adapter.XViewHolder;
import com.example.administrator.huorongdai.xframe.adapter.decoration.DividerDecoration;
import com.example.administrator.huorongdai.xframe.utils.XNetworkUtils;
import com.example.administrator.huorongdai.xframe.utils.XPreferencesUtils;
import com.example.administrator.huorongdai.xframe.utils.http.HttpCallBack;
import com.example.administrator.huorongdai.xframe.utils.http.XHttp;
import com.example.administrator.huorongdai.xframe.widget.loadingview.XLoadingView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class BidRecordFragment extends LazyLoadFragment {
    private TextView tvTitle3;
    private String custMobile;
    private String custPwd;//密文密码
    //不传默认查询9，2-投标中的投资，3-失败的投资，5-满标待审的投资，9-收款中的投资，10-已结清的投资
    private String investStatus;

    private XLoadingView xLoadingView;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private List<SelfInvestBean.SelfInvestListBean> listBeans;
    private int pageNum=1;
    private Handler handler=new Handler(new MyCallback());
    private final class MyCallback implements Handler.Callback{

        @Override
        public boolean handleMessage(Message message) {
            if(XNetworkUtils.isConnected()){
                pageNum=1;
                requestDate();
            }else {
                xLoadingView.showNoNetwork();
            }
            return true;
        }
    }

    private SwipeRefreshLayout.OnRefreshListener refreshListener=new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            adapter.isLoadMore(false);
            handler.sendMessage(Message.obtain());
        }
    };

    public BidRecordFragment() {

    }

    @SuppressLint("ValidFragment")
    public BidRecordFragment(String investStatus) {
        this.investStatus=investStatus;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_bid_record;
    }

    @Override
    protected void initViews() {
        tvTitle3=findViewById(R.id.tv_title3);
        xLoadingView=findViewById(R.id.xloading_view);
        refreshLayout=findViewById(R.id.srl_fragment_bid_record);
        refreshLayout.setColorScheme(R.color.word_red);
        refreshLayout.setOnRefreshListener(refreshListener);
        recyclerView=findViewById(R.id.rl_fragment_bid_record);
        xLoadingView.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xLoadingView.showLoading();
                handler.sendMessageDelayed(Message.obtain(),600);
            }
        });

        custMobile = (String) XPreferencesUtils.get("custMobile","");
        custPwd = (String) XPreferencesUtils.get("custPwd","");

        DividerDecoration decoration=new DividerDecoration(R.color.gray_bg,1);
        decoration.setDrawLastItem(false);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setLayoutManager(new MyGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        listBeans=new ArrayList<>();
        adapter=new MyAdapter(recyclerView,listBeans);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new XRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intentDetail=new Intent(getActivity(), BidDetailActivity.class);
                intentDetail.putExtra("loanID",adapter.getItem(position).getLoanId());
                intentDetail.putExtra("loanTitle",adapter.getItem(position).getLoanTitle());
                if("2".equals(investStatus)){//投资中
                    intentDetail.putExtra("loanStatus","7");
                }else if("5".equals(investStatus)){//投资完成
                    intentDetail.putExtra("loanStatus","8");
                }

                startActivity(intentDetail);
            }
        });
        adapter.setOnLoadMoreListener(new XRecyclerViewAdapter.OnLoadMoreListener() {
            @Override
            public void onRetry() {

            }

            @Override
            public void onLoadMore() {
                requestDate();
            }
        });


        refreshLayout.setRefreshing(true);
        refreshListener.onRefresh();
    }

    @Override
    public void lazyLoad() {

    }

    private void requestDate(){
        Map<String,Object > params =new HashMap<>();
        params .put("custMobile",custMobile);
        params .put("custPwd",custPwd);
        params .put("investStatus",investStatus);
        params .put("pageNum",""+pageNum);
        params .put("pageSize","20");
        XHttp.obtain().post(Path.get_self_invest_list, params, new HttpCallBack<SelfInvestBean>() {
            @Override
            public void onSuccess(SelfInvestBean bean) {
                if(bean.isStatus()){
                    if(bean.getSelfInvestList().size()>0){
                        if(pageNum==1){
                            adapter.setDataLists(bean.getSelfInvestList());
                            adapter.isLoadMore(true);
                        }else {
                            adapter.addAll(bean.getSelfInvestList());
                        }
                        xLoadingView.showContent();
                    }

                    if("1".equals(bean.getPageNum()) && "0".equals(bean.getTotalPages()) && bean.getSelfInvestList().size()==0){
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

    class MyAdapter extends XRecyclerViewAdapter<SelfInvestBean.SelfInvestListBean>{

        public MyAdapter(@NonNull RecyclerView mRecyclerView, List<SelfInvestBean.SelfInvestListBean> dataLists) {
            super(mRecyclerView, dataLists,R.layout.item_fragment_bid_record);
        }

        @Override
        protected void bindData(XViewHolder holder, SelfInvestBean.SelfInvestListBean data, int position) {
            TextView tvName=holder.getView(R.id.tv_project_name);//项目
            TextView tvRate=holder.getView(R.id.tv_rate);//利率
            TextView tvMoney=holder.getView(R.id.tv_money);//待收本金
            TextView tvTime=holder.getView(R.id.tv_time);//时间

            tvName.setText(data.getLoanTitle());
            tvRate.setText(data.getLoanArp()+"%");
            tvMoney.setText("¥"+new DecimalFormat("0.00").format(data.getAllinterest()+data.getIvstAmt()));
            String timeStr=data.getIvstTime();
            String date=timeStr.substring(0,4)+"-"+timeStr.substring(4,6)+"-"+timeStr.substring(6,8);
            tvTime.setText(date);

        }
    }
}
