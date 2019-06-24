package com.example.administrator.huorongdai.activity;

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
import com.example.administrator.huorongdai.base.BaseActivity;
import com.example.administrator.huorongdai.gsonbean.NoticeBean;
import com.example.administrator.huorongdai.util.ButtonUtils;
import com.example.administrator.huorongdai.xframe.adapter.XRecyclerViewAdapter;
import com.example.administrator.huorongdai.xframe.adapter.XViewHolder;
import com.example.administrator.huorongdai.xframe.adapter.decoration.DividerDecoration;
import com.example.administrator.huorongdai.xframe.utils.XNetworkUtils;
import com.example.administrator.huorongdai.xframe.utils.http.HttpCallBack;
import com.example.administrator.huorongdai.xframe.utils.http.XHttp;
import com.example.administrator.huorongdai.xframe.widget.loadingview.XLoadingView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LatestNoticeActivity extends BaseActivity {

    private XLoadingView xLoadingView;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;

    private int pageNum=1;
    private MyAdapter adapter;
    private List<NoticeBean.NoticeListBean> listBeans=new ArrayList<>();

    private Handler handler=new Handler(new MyCallBack());
    private final class MyCallBack implements Handler.Callback{

        @Override
        public boolean handleMessage(Message message) {
            if (XNetworkUtils.isConnected()) {
                pageNum=1;
                requestNotice();
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
            adapter.isLoadMore(false);
            handler.sendMessage(Message.obtain());
        }
    };

    @Override
    protected int getContentView() {
        return R.layout.activity_latest_notice;
    }

    @Override
    protected void initView() {
        setTitle("平台公告");

        xLoadingView=findViewById(R.id.xloading_view);
        refreshLayout=findViewById(R.id.srl_latest_notice);
        refreshLayout.setColorScheme(R.color.word_red);
        refreshLayout.setOnRefreshListener(refreshListener);
        recyclerView=findViewById(R.id.rl_latest_notice);

        xLoadingView.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xLoadingView.showLoading();
                handler.sendMessageDelayed(Message.obtain(),600);
            }
        });

        DividerDecoration decoration=new DividerDecoration(R.color.gray_bg,1);
        decoration.setDrawLastItem(false);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        adapter=new MyAdapter(recyclerView,listBeans);
        recyclerView.setAdapter(adapter);

        adapter.setOnLoadMoreListener(new XRecyclerViewAdapter.OnLoadMoreListener() {
            @Override
            public void onRetry() {

            }

            @Override
            public void onLoadMore() {
                requestNotice();
            }
        });

        adapter.setOnItemClickListener(new XRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                if(!ButtonUtils.isFastDoubleClick()){
                    Intent intent=new Intent(LatestNoticeActivity.this, NoticeDetailActivity.class);
                    intent.putExtra("notice_id",adapter.getItem(position).getId());
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void loadData() {
        refreshLayout.setRefreshing(true);
        refreshListener.onRefresh();
    }

    @Override
    public void onClick(View view) {

    }

    //notice通知请求
    private void requestNotice(){
        Map<String,Object > params =new HashMap<>();
        params .put("pageNum",""+pageNum);
        params .put("pageSize","20");
        XHttp.obtain().post(Path.notice_url, params, new HttpCallBack<NoticeBean>() {
            @Override
            public void onSuccess(NoticeBean bean) {
                if(bean.isStatus()){
                    if(bean.getNoticeList().size()>0){
                        if(pageNum==1){
                            adapter.addAll(bean.getNoticeList());
                            adapter.isLoadMore(true);
                        }else {
                            adapter.addAll(bean.getNoticeList());
                        }
                    }

                    if("1".equals(bean.getPageNum()) && "0".equals(bean.getTotalPages()) && bean.getNoticeList().size()==0){
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

    class MyAdapter extends XRecyclerViewAdapter<NoticeBean.NoticeListBean> {

        public MyAdapter(@NonNull RecyclerView mRecyclerView, List<NoticeBean.NoticeListBean> dataLists) {
            super(mRecyclerView, dataLists,R.layout.item_activity_latest_notice);
        }

        @Override
        protected void bindData(XViewHolder holder, NoticeBean.NoticeListBean data, int position) {
            TextView tvTtitle=holder.getView(R.id.tv_notice_title);
            TextView tvTime=holder.getView(R.id.tv_notice_time);

            tvTtitle.setText(data.getNoticeTitle());

            String date=data.getAddTime();
            //+" "+date.substring(8,10)+":"+date.substring(10,12)+":"+date.substring(12,14)
            String time=date.substring(0,4)+"-"+date.substring(4,6)+"-"+date.substring(6,8);
            tvTime.setText(time);
        }
    }
}
