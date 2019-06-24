package com.example.administrator.huorongdai.fragment;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.example.administrator.huorongdai.Path;
import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.activity.LoginActivity;
import com.example.administrator.huorongdai.activity.ProductDetailActivity;
import com.example.administrator.huorongdai.adapter.AllFragmentAdapter;
import com.example.administrator.huorongdai.adapter.MyGridLayoutManager;
import com.example.administrator.huorongdai.base.LazyLoadFragment;
import com.example.administrator.huorongdai.gsonbean.LoanAllBean;
import com.example.administrator.huorongdai.util.ButtonUtils;
import com.example.administrator.huorongdai.xframe.adapter.XRecyclerViewAdapter;
import com.example.administrator.huorongdai.xframe.adapter.decoration.DividerDecoration;
import com.example.administrator.huorongdai.xframe.utils.XNetworkUtils;
import com.example.administrator.huorongdai.xframe.utils.XPreferencesUtils;
import com.example.administrator.huorongdai.xframe.utils.http.HttpCallBack;
import com.example.administrator.huorongdai.xframe.utils.http.XHttp;
import com.example.administrator.huorongdai.xframe.widget.XToast;
import com.example.administrator.huorongdai.xframe.widget.loadingview.XLoadingView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/1/13.
 * 全部
 */
public class AllFragment extends LazyLoadFragment {

    private static AllFragment fragment;
    public AllFragment(){

    }
    public static AllFragment getInstance() {
        if (fragment == null) {
            synchronized (AllFragment.class) {
                if (fragment == null) {
                    fragment = new AllFragment();
                }
            }
        }
        return fragment;
    }

    private Handler handler=new Handler(new MyCallback());

    private final class MyCallback implements Handler.Callback{

        @Override
        public boolean handleMessage(Message message) {
            page=1;
            adapter.clear();
            adapter.isLoadMore(true);
            refreshLayout.setRefreshing(false);
            return true;
        }
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_all;
    }
    public XLoadingView xLoadingView;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private AllFragmentAdapter adapter;
    private List<LoanAllBean.LoanListBean> list=new ArrayList<>();
    private  int page;

    private String loanID="";
    private String loanTitle="";
    private String loanStatus="";

    @Override
    protected void initViews() {
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        xLoadingView=findViewById(R.id.xloading_view);
        refreshLayout=findViewById(R.id.srl_all_fragment);
        refreshLayout.setColorScheme(R.color.word_red);
        recyclerView=findViewById(R.id.rl_all_fragment);
        adapter=new AllFragmentAdapter(recyclerView,list);
        recyclerView.addItemDecoration(new DividerDecoration(R.color.light_gray,1));
        recyclerView.setLayoutManager(new MyGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);

        //重新加载
        xLoadingView.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xLoadingView.showLoading();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadData();
                    }
                },2000);
            }
        });

        //下拉刷新
        refreshLayout.setOnRefreshListener(refreshListener);

        //加载更多
        adapter.setOnLoadMoreListener(new XRecyclerViewAdapter.OnLoadMoreListener() {
            @Override
            public void onRetry() {

            }

            @Override
            public void onLoadMore() {
                //requestData();
            }
        });

        //item点击
        adapter.setOnItemClickListener(new XRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                boolean isLogin= (boolean) XPreferencesUtils.get("isLogin",false);
                if(isLogin){
                    if(!ButtonUtils.isFastDoubleClick()){
                        loanID=adapter.getItem(position).getId();
                        loanTitle=adapter.getItem(position).getLoanTitle();
                        loanStatus=adapter.getItem(position).getLoanStatus()+"";
                        Intent intent=new Intent(getActivity(), ProductDetailActivity.class);
                        intent.putExtra("loanID",loanID);
                        intent.putExtra("loanTitle",loanTitle);
                        intent.putExtra("loanStatus",loanStatus);
                        startActivity(intent);
                    }
                }else {//去登录
                    Intent intentLogin=new Intent(getActivity(), LoginActivity.class);
                    startActivity(intentLogin);
                }
            }
        });

        loadData();
    }

    public void loadData() {
        xLoadingView.showContent();
        refreshLayout.setRefreshing(true);
        refreshListener.onRefresh();
    }

    @Override
    public void lazyLoad() {

    }

    SwipeRefreshLayout.OnRefreshListener refreshListener=new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            if(XNetworkUtils.isConnected()){
                handler.sendMessage(Message.obtain());
            }else {
                if(refreshLayout.isRefreshing()){
                    refreshLayout.setRefreshing(false);
                }
                XToast.normal("网络连接失败!");
                xLoadingView.showNoNetwork();
            }

        }
    };

    //数据请求
    private void requestData(String date,String rate) {
        Map<String,Object > params =new HashMap<>();
        params.put("ivstCategory","5");//5是全部，小微是2，综合是3，新手是1
        params .put("loanStatusScope","");
        params .put("produId","");
        params .put("loanTypeStr","");
        params .put("loanExpiryScope","");
        params .put("loanArpScope","");
        params .put("pageNum",""+page);
        params .put("pageSize","10");
        XHttp.obtain().post(Path.project_list_url, params, new HttpCallBack<LoanAllBean>() {
            @Override
            public void onSuccess(LoanAllBean allBean) {
                if(!allBean.isStatus()){
                    xLoadingView.showError();
                }
                if(allBean.getLoanList().size()>0){
                    if(allBean.isStatus()){
                        adapter.addAll(allBean.getLoanList());
                    }
                }else {
                    if(page==1){
                        xLoadingView.showEmpty();
                    }
                }
                if(allBean.getPageNum().equals(allBean.getTotalPages())){
                    adapter.showLoadComplete();
                }else {
                    page++;
                }
            }

            @Override
            public void onFailed(String error) {

            }
        });
    }

    @Override
    protected void stopLoad() {
//        if(adapter!=null){
//            adapter.isLoadMore(false);
//            adapter.clear();
//        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
