package com.example.administrator.huorongdai.activity;

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
import com.example.administrator.huorongdai.gsonbean.PointsBean;
import com.example.administrator.huorongdai.gsonbean.PointsTotalBean;
import com.example.administrator.huorongdai.xframe.adapter.XRecyclerViewAdapter;
import com.example.administrator.huorongdai.xframe.adapter.XViewHolder;
import com.example.administrator.huorongdai.xframe.adapter.decoration.DividerDecoration;
import com.example.administrator.huorongdai.xframe.utils.XNetworkUtils;
import com.example.administrator.huorongdai.xframe.utils.XPreferencesUtils;
import com.example.administrator.huorongdai.xframe.utils.http.HttpCallBack;
import com.example.administrator.huorongdai.xframe.utils.http.XHttp;
import com.example.administrator.huorongdai.xframe.utils.log.XLog;
import com.example.administrator.huorongdai.xframe.widget.XToast;
import com.example.administrator.huorongdai.xframe.widget.loadingview.XLoadingView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyIntegralActivity extends BaseActivity {
    private SwipeRefreshLayout refreshLayout;
    private XLoadingView xLoadingView;
    private RecyclerView recyclerView;

    private TextView tvUse;//可用积分
    private TextView tvConsume;//消耗积分
    private TextView tvAll;//全部积分

    private String custMobile;//手机号码
    private String custPwd;//密文密码
    private int pageNum=1;

    private MyAdapter adapter;
    private List<PointsBean.PointsListBean> listBeans=new ArrayList<>();

    private Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if(message.what==0){
                adapter.isLoadMore(true);
                if(refreshLayout.isRefreshing()){
                    refreshLayout.setRefreshing(false);
                }
            }
            return true;
        }
    });

    private SwipeRefreshLayout.OnRefreshListener refreshListener=new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            pageNum=1;
            adapter.clear();
            getPointsTotal();
        }
    };

    @Override
    protected int getContentView() {
        return R.layout.activity_my_integral;
    }

    @Override
    protected void initView() {
        setTitle("我的积分 ");

        tvUse=findViewById(R.id.tv_can_use_integral);
        tvConsume=findViewById(R.id.tv_consume);
        tvAll=findViewById(R.id.tv_all);
        refreshLayout=findViewById(R.id.srl_activity_my_integral);
        refreshLayout.setColorScheme(R.color.word_red);
        refreshLayout.setOnRefreshListener(refreshListener);
        xLoadingView=findViewById(R.id.xloading_view);
        recyclerView=findViewById(R.id.rl_activity_my_integral);

        custMobile = (String) XPreferencesUtils.get("custMobile","");
        custPwd = (String) XPreferencesUtils.get("custPwd","");

        xLoadingView.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xLoadingView.showLoading();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(XNetworkUtils.isConnected()){
                            xLoadingView.showContent();
                            refreshLayout.setRefreshing(true);
                            refreshListener.onRefresh();
                        }else {
                            XToast.normal("网络连接失败!");
                        }
                    }
                }, 2000);
            }
        });

        adapter=new MyAdapter(recyclerView,listBeans);
        DividerDecoration decoration=new DividerDecoration(R.color.gray_bg,1);
        decoration.setDrawLastItem(false);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);

        adapter.setOnLoadMoreListener(new XRecyclerViewAdapter.OnLoadMoreListener() {
            @Override
            public void onRetry() {

            }

            @Override
            public void onLoadMore() {
                getPointsList();
            }
        });
    }

    @Override
    protected void loadData() {
        if(XNetworkUtils.isConnected()){
            xLoadingView.showContent();
            refreshLayout.setRefreshing(true);
            refreshListener.onRefresh();
        }else {
            XToast.normal("网络连接失败!");
        }
    }

    @Override
    public void onClick(View view) {

    }

    //积分总额查询
    private void getPointsTotal(){
        Map<String,Object > params =new HashMap<>();
        params .put("custMobile",custMobile);
        params .put("custPwd",custPwd);
        XHttp.obtain().post(Path.get_points_total, params, new HttpCallBack<PointsTotalBean>() {
            @Override
            public void onSuccess(PointsTotalBean bean) {
                Message message=Message.obtain();
                message.what=0;
                handler.sendMessage(message);
                if(bean.isStatus()){
                    tvUse.setText(bean.getAvalPoint()+"");
                    tvConsume.setText(bean.getUsedPoint()+"");
                    tvAll.setText(bean.getTotalPoint()+"");
                }else {
                    XToast.normal(bean.getMessage());
                }
            }

            @Override
            public void onFailed(String error) {

            }
        });
    }

    //积分总额查询
    private void getPointsList(){
        Map<String,Object > params =new HashMap<>();
        params .put("custMobile",custMobile);
        params .put("custPwd",custPwd);
        //params .put("pointType","");
        params .put("pageNum",""+pageNum);
        params .put("pageSize","30");
        XLog.map(params);
        XHttp.obtain().post(Path.get_points_list, params, new HttpCallBack<PointsBean>() {
            @Override
            public void onSuccess(PointsBean bean) {
                if(bean.isStatus()){
                    if(bean.getPointsList().size()>0){
                        adapter.addAll(bean.getPointsList());
                    }

                    if("1".equals(bean.getPageNum()) && "0".equals(bean.getTotalPages()) && bean.getPointsList().size()==0){
                        xLoadingView.showEmpty();
                    }
                    if(bean.getPageNum().equals(bean.getTotalPages()) || "0".equals(bean.getTotalPages())){
                        adapter.showLoadComplete();
                    }else {
                        pageNum=pageNum+1;
                    }

                }else {
                    XToast.normal(bean.getMessage());
                    xLoadingView.showError();
                }
            }

            @Override
            public void onFailed(String error) {

            }
        });
    }

    class MyAdapter extends XRecyclerViewAdapter<PointsBean.PointsListBean>{

        public MyAdapter(@NonNull RecyclerView mRecyclerView, List<PointsBean.PointsListBean> dataLists) {
            super(mRecyclerView, dataLists,R.layout.item_activity_my_integral);
        }

        @Override
        protected void bindData(XViewHolder holder, PointsBean.PointsListBean data, int position) {
            TextView tvType=holder.getView(R.id.tv_type);
            TextView tvPoint=holder.getView(R.id.tv_point);
            TextView tvTime=holder.getView(R.id.tv_time);

            tvType.setText(data.getPointTypeName());
            tvPoint.setText(data.getPointAmount()+"");
            String date=data.getAddTime();
            String time=date.substring(0,4)+"-"+date.substring(4,6)+"-"+date.substring(6,8);
            tvTime.setText(time);
        }
    }
}
