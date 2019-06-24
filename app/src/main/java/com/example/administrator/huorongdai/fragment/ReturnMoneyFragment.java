package com.example.administrator.huorongdai.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.huorongdai.Path;
import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.activity.ReturnPlanActivity;
import com.example.administrator.huorongdai.adapter.MyGridLayoutManager;
import com.example.administrator.huorongdai.base.LazyLoadFragment;
import com.example.administrator.huorongdai.gsonbean.ReturnMoneyBean;
import com.example.administrator.huorongdai.xframe.adapter.XRecyclerViewAdapter;
import com.example.administrator.huorongdai.xframe.adapter.XViewHolder;
import com.example.administrator.huorongdai.xframe.adapter.decoration.DividerDecoration;
import com.example.administrator.huorongdai.xframe.utils.XNetworkUtils;
import com.example.administrator.huorongdai.xframe.utils.XPreferencesUtils;
import com.example.administrator.huorongdai.xframe.utils.http.HttpCallBack;
import com.example.administrator.huorongdai.xframe.utils.http.XHttp;
import com.example.administrator.huorongdai.xframe.utils.log.XLog;
import com.example.administrator.huorongdai.xframe.widget.loadingview.XLoadingView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@SuppressLint("ValidFragment")
public class ReturnMoneyFragment extends LazyLoadFragment {

    private String custMobile;
    private String custPwd;//密文密码

    private String earnStatus;//回款状态===>不传查全部，1-待收，2-已收
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private XLoadingView xLoadingView;
    private MyAdapter adapter;
    private List<ReturnMoneyBean.ReturnedMoneyListBean> listBeans;
    private int pageNum=1;

    private Handler handler=new Handler(new MyCallback());
    private final class MyCallback implements Handler.Callback{

        @Override
        public boolean handleMessage(Message message) {
            if(XNetworkUtils.isConnected()){
                pageNum=1;
                requestDate();
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
            adapter.isLoadMore(false);
            handler.sendMessage(Message.obtain());
        }
    };

    public ReturnMoneyFragment(){

    }

    @SuppressLint("ValidFragment")
    public ReturnMoneyFragment(String earnStatus){
        this.earnStatus=earnStatus;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_return_money;
    }

    @Override
    protected void initViews() {
        xLoadingView=findViewById(R.id.xloading_view);
        refreshLayout=findViewById(R.id.srl_fragment_return_money);
        refreshLayout.setColorScheme(R.color.word_red);
        refreshLayout.setOnRefreshListener(refreshListener);
        recyclerView=findViewById(R.id.rl_fragment_return_money);
        xLoadingView.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xLoadingView.showLoading();
                handler.sendMessageDelayed(Message.obtain(),600);
            }
        });

        custMobile = (String) XPreferencesUtils.get("custMobile","");
        custPwd = (String) XPreferencesUtils.get("custPwd","");

        listBeans=new ArrayList<>();
        DividerDecoration decoration=new DividerDecoration(R.color.gray_bg,1);
        decoration.setDrawLastItem(false);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setLayoutManager(new MyGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        adapter=new MyAdapter(recyclerView,listBeans);
        recyclerView.setAdapter(adapter);

        //earnId
        adapter.setOnItemClickListener(new XRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent=new Intent(getActivity(),ReturnPlanActivity.class);
                intent.putExtra("earnId",adapter.getItem(position).getId());
                intent.putExtra("earnStatus",earnStatus);
                startActivity(intent);
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
        params .put("earnStatus",earnStatus);
        params .put("pageNum",""+pageNum);
        params .put("pageSize","20");
        XLog.map(params);
        XHttp.obtain().post(Path.get_returned_money_list, params, new HttpCallBack<ReturnMoneyBean>() {
            @Override
            public void onSuccess(ReturnMoneyBean bean) {
                if(bean.isStatus()){
                    if(bean.getReturnedMoneyList().size()>0){
                        if(pageNum==1){
                            adapter.setDataLists(bean.getReturnedMoneyList());
                            adapter.isLoadMore(true);
                        }else {
                            adapter.addAll(bean.getReturnedMoneyList());
                        }
                        xLoadingView.showContent();
                    }

                    if("1".equals(bean.getPageNum()) && "0".equals(bean.getTotalPages()) && bean.getReturnedMoneyList().size()==0){
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

    class MyAdapter extends XRecyclerViewAdapter<ReturnMoneyBean.ReturnedMoneyListBean> {

        public MyAdapter(@NonNull RecyclerView mRecyclerView, List<ReturnMoneyBean.ReturnedMoneyListBean> dataLists) {
            super(mRecyclerView, dataLists,R.layout.item_fragment_return_money);
        }

        @Override
        protected void bindData(XViewHolder holder, ReturnMoneyBean.ReturnedMoneyListBean data, int position) {
            TextView tvName=holder.getView(R.id.tv_project_name);//项目
            TextView tvRate=holder.getView(R.id.tv_rate);//应收本金
            TextView tvMoney=holder.getView(R.id.tv_money);//应收利息
            TextView tvTime=holder.getView(R.id.tv_time);//应收日期

            tvName.setText(data.getLoan().getLoanTitle());
            tvRate.setText("¥"+new DecimalFormat("0").format(data.getEarnCapital()));
            tvMoney.setText("¥"+data.getEarnIint());
            //"shdEarnDate","20180804122938"
            String shdEarnDate=data.getShdEarnDate();
            String time=shdEarnDate.substring(0,4)+"-"+shdEarnDate.substring(4,6)+"-"+shdEarnDate.substring(6,8);
            tvTime.setText(time);
        }
    }
}
