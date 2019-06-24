package com.example.administrator.huorongdai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.huorongdai.Path;
import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.base.BaseActivity;
import com.example.administrator.huorongdai.gsonbean.NewsBean;
import com.example.administrator.huorongdai.xframe.adapter.XRecyclerViewAdapter;
import com.example.administrator.huorongdai.xframe.adapter.XViewHolder;
import com.example.administrator.huorongdai.xframe.utils.XEmptyUtils;
import com.example.administrator.huorongdai.xframe.utils.XNetworkUtils;
import com.example.administrator.huorongdai.xframe.utils.http.HttpCallBack;
import com.example.administrator.huorongdai.xframe.utils.http.XHttp;
import com.example.administrator.huorongdai.xframe.utils.imageload.XImage;
import com.example.administrator.huorongdai.xframe.widget.loadingview.XLoadingView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewsListActivity extends BaseActivity {

    private XLoadingView xLoadingView;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private List<NewsBean.NewsListBean> listBeans;

    private Intent intent;
    private String title;
    private int pageNum=1;
    private String newsType="";

    private Handler handler=new Handler(new MyCallable());
    private final class MyCallable implements Handler.Callback{

        @Override
        public boolean handleMessage(Message msg) {
            if(XNetworkUtils.isConnected()){
                if(!XEmptyUtils.isEmpty(newsType)){
                    requestData();
                }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //如果参数为null的话，会将所有的Callbacks和Messages全部清除掉。
        handler.removeCallbacksAndMessages( null );
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_news_list;
    }

    @Override
    protected void initView() {
        xLoadingView=findViewById(R.id.xloading_view);
        refreshLayout=findViewById(R.id.srl_news_list);
        recyclerView=findViewById(R.id.rl_news_list);
        refreshLayout.setColorScheme(R.color.word_red);
        refreshLayout.setOnRefreshListener(refreshListener);

        xLoadingView.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xLoadingView.showLoading();
                handler.sendMessageDelayed(Message.obtain(),600);
            }
        });

        intent=getIntent();
        if(intent!=null){
            title=intent.getStringExtra("title");
            newsType=intent.getStringExtra("newsType");
            setTitle(title);
        }

        listBeans=new ArrayList<>();
        //recyclerView.addItemDecoration(new DividerDecoration(R.color.light_gray,1));
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        adapter=new MyAdapter(recyclerView,listBeans);
        recyclerView.setAdapter(adapter);

        //加载更多
        adapter.setOnLoadMoreListener(new XRecyclerViewAdapter.OnLoadMoreListener() {
            @Override
            public void onRetry() {

            }

            @Override
            public void onLoadMore() {
                if(!XEmptyUtils.isEmpty(newsType)){
                    requestData();
                }
            }
        });

        //item点击事件
        adapter.setOnItemClickListener(new XRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent=new Intent(NewsListActivity.this,ReadNewsActivity.class);
                intent.putExtra("title",title);
                Bundle bundle= new Bundle();
                bundle.putParcelable("news",adapter.getItem(position));
                intent.putExtra("data",bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void loadData() {
        refreshLayout.setRefreshing(true);
        refreshListener.onRefresh();
    }

    private SwipeRefreshLayout.OnRefreshListener refreshListener=new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            adapter.isLoadMore(false);
            handler.sendMessage(Message.obtain());
        }
    };

    private void requestData(){
        Map<String,Object > params =new HashMap<>();
        params.put("newsType",newsType);
        params.put("pageNum",""+pageNum);
        params.put("pageSize","10");
        XHttp.obtain().post(Path.get_news_list, params, new HttpCallBack<NewsBean>() {
            @Override
            public void onSuccess(NewsBean bean) {
                if(bean.isStatus()){
                    if(bean.getNewsList().size()>0){
                        if(pageNum==1){
                            adapter.setDataLists(bean.getNewsList());
                            adapter.isLoadMore(true);
                        }else {
                            adapter.addAll(bean.getNewsList());
                        }
                    }

                    if("1".equals(bean.getPageNum()) && "0".equals(bean.getTotalPages()) && bean.getNewsList().size()==0){
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

    @Override
    public void onClick(View view) {

    }

    class MyAdapter extends XRecyclerViewAdapter<NewsBean.NewsListBean> {

        public MyAdapter(@NonNull RecyclerView mRecyclerView, List<NewsBean.NewsListBean> dataLists) {
            super(mRecyclerView, dataLists,R.layout.item_rl_news_list);
        }

        @Override
        protected void bindData(XViewHolder holder, NewsBean.NewsListBean data, int position) {
            ImageView ivIcon=holder.getView(R.id.iv_icon);
            TextView tvTitle=holder.getView(R.id.tv_title);
            TextView tvTime=holder.getView(R.id.tv_time);

            String imageUrl=Path.BASE_ImgURL+data.getNewsIcon();
            XImage.getInstance().load(ivIcon,imageUrl,R.mipmap.icon404);

            tvTitle.setText(data.getNewsTitle());

            String date=data.getAddTime();
            String time=date.substring(0,4)+"-"+date.substring(4,6)+"-"+date.substring(6,8)+" "+date.substring(8,10)+":"+date.substring(10,12)+":"+date.substring(12,14);
            tvTime.setText(time);

        }
    }
}
