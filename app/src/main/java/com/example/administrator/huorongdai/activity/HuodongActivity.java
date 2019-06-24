package com.example.administrator.huorongdai.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.example.administrator.huorongdai.Path;
import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.adapter.HuodongAdapter;
import com.example.administrator.huorongdai.base.BaseActivity;
import com.example.administrator.huorongdai.gsonbean.HuodongBean;
import com.example.administrator.huorongdai.util.ButtonUtils;
import com.example.administrator.huorongdai.xframe.adapter.XRecyclerViewAdapter;
import com.example.administrator.huorongdai.xframe.utils.XEmptyUtils;
import com.example.administrator.huorongdai.xframe.utils.XNetworkUtils;
import com.example.administrator.huorongdai.xframe.utils.http.HttpCallBack;
import com.example.administrator.huorongdai.xframe.utils.http.XHttp;
import com.example.administrator.huorongdai.xframe.widget.loadingview.XLoadingView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HuodongActivity extends BaseActivity {

    private XLoadingView xLoadingView;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private HuodongAdapter adapter;
    private List<HuodongBean.ListBean> listBeans=new ArrayList<>();
    private int page=1;//当前的页码

    private Handler handler=new Handler(new MyCallback());
    private final class MyCallback implements Handler.Callback{
        @Override
        public boolean handleMessage(Message message) {
            if(XNetworkUtils.isConnected()){
                adapter.isLoadMore(false);
                page=1;
                requestInfo();
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
        return R.layout.activity_huodong;
    }

    @Override
    protected void initView() {
        setTitle("热门活动");

        xLoadingView=findViewById(R.id.xloading_view);
        refreshLayout=findViewById(R.id.huodong_srl);
        recyclerView=findViewById(R.id.huodong_recyclerView);
        refreshLayout.setColorScheme(R.color.word_red);

        //重新加载
        xLoadingView.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xLoadingView.showLoading();
                handler.sendMessageDelayed(Message.obtain(),600);
            }
        });

        //下拉刷新
        refreshLayout.setOnRefreshListener(refreshListener);
        adapter=new HuodongAdapter(recyclerView,listBeans);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        //item点击
        adapter.setOnItemClickListener(new XRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                if(!ButtonUtils.isFastDoubleClick()){
                    HuodongBean.ListBean bean= adapter.getItem(position);
                    if(bean.getActivesStatus()==1){
                        Intent intent = new Intent(HuodongActivity.this, HtmlActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("url", bean.getActivesUrlw());
                        bundle.putString("title", bean.getActivesName());
                        bundle.putString("name", "activity");
                        intent.putExtra("bundle", bundle);
                        startActivity(intent);
                    }
                }
            }
        });
        //上拉加载
        adapter.setOnLoadMoreListener(new XRecyclerViewAdapter.OnLoadMoreListener() {
            @Override
            public void onRetry() {
            }

            @Override
            public void onLoadMore() {
                requestInfo();
            }
        });

        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void loadData() {
        refreshLayout.setRefreshing(true);
        refreshListener.onRefresh();
    }

    //请求活动列表数据
    private void requestInfo(){
        Map<String,Object > params =new HashMap<>();
        params .put("showPage",""+page);
        params .put("pageSize","10");
        XHttp.obtain().post(Path.huodong_url, params, new HttpCallBack<HuodongBean>() {
            @Override
            public void onSuccess(HuodongBean huodongBean) {
                if(page==1){
                    adapter.clear();
                    adapter.isLoadMore(true);
                }
                if(!XEmptyUtils.isEmpty(huodongBean.getList()) && huodongBean.getList().size()>0){
                    for (HuodongBean.ListBean bean : huodongBean.getList()) {
                        //activesStatus[1-进行中 2-已结束 3-预启用（注：预启用不显示）]
                        if(3!=bean.getActivesStatus()){
                            listBeans.add(bean);
                        }
                    }
                    adapter.notifyDataSetChanged();
                }else {
                    if(page==1){
                        xLoadingView.showEmpty();
                    }
                }

                if(huodongBean.getPa().getPageCount()==huodongBean.getPa().getShowPage()){
                    adapter.showLoadComplete();
                }else {
                    page++;
                }
                if(refreshLayout.isRefreshing()){
                    refreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailed(String error) {
                if(refreshLayout.isRefreshing()){
                    refreshLayout.setRefreshing(false);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {

    }
}
