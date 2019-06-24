package com.example.administrator.huorongdai.activity;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.huorongdai.Path;
import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.base.BaseActivity;
import com.example.administrator.huorongdai.gsonbean.MemberListBean;
import com.example.administrator.huorongdai.gsonbean.QuestionBean;
import com.example.administrator.huorongdai.xframe.adapter.XRecyclerViewAdapter;
import com.example.administrator.huorongdai.xframe.adapter.XViewHolder;
import com.example.administrator.huorongdai.xframe.adapter.decoration.DividerDecoration;
import com.example.administrator.huorongdai.xframe.utils.XEmptyUtils;
import com.example.administrator.huorongdai.xframe.utils.XNetworkUtils;
import com.example.administrator.huorongdai.xframe.utils.http.HttpCallBack;
import com.example.administrator.huorongdai.xframe.utils.http.XHttp;
import com.example.administrator.huorongdai.xframe.widget.loadingview.XLoadingView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeamManagerActivity extends BaseActivity {

    private XLoadingView xLoadingView;
    private RecyclerView mRecyclerView;
    private MyAdapter adapter;
    private List<Map<String,String>> listBeans;
    private int pageNum=1;
    private Handler handler=new Handler(new MyCallback());
    private final class MyCallback implements Handler.Callback{

        @Override
        public boolean handleMessage(Message message) {
            if (XNetworkUtils.isConnected()){
                pageNum=1;
                requestData();
            }else {
                xLoadingView.showNoNetwork();
            }
            return true;
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_team_manager;
    }

    @Override
    protected void initView() {
        setTitle("团队高管");
        mRecyclerView = findViewById(R.id.team_manager_recycler_view);
        xLoadingView=findViewById(R.id.xloading_view);
        xLoadingView.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xLoadingView.showLoading();
                handler.sendMessageDelayed(Message.obtain(),600);
            }
        });

        DividerDecoration decoration=new DividerDecoration(R.color.gray_bg,1);
        decoration.setDrawLastItem(false);
        mRecyclerView.addItemDecoration(decoration);

        listBeans=new ArrayList<>();
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        adapter=new MyAdapter(mRecyclerView,listBeans);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnLoadMoreListener(new XRecyclerViewAdapter.OnLoadMoreListener() {
            @Override
            public void onRetry() {

            }

            @Override
            public void onLoadMore() {
                requestData();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //如果参数为null的话，会将所有的Callbacks和Messages全部清除掉。
        handler.removeCallbacksAndMessages( null );
    }

    @Override
    protected void loadData() {
        adapter.isLoadMore(false);
        handler.sendMessage(Message.obtain());
    }

    @Override
    public void onClick(View view) {

    }

    private void requestData() {
        Map<String,Object > params =new HashMap<>();
        params.put("totalpages","10");
        params.put("pageNum",""+pageNum);
        XHttp.obtain().post(Path.member_list, params, new HttpCallBack<MemberListBean>() {
            @Override
            public void onSuccess(MemberListBean bean) {
                if(!XEmptyUtils.isEmpty(bean) ){
                    if(XEmptyUtils.isEmpty(bean.getList())){
                        xLoadingView.showError();
                        return;
                    }
                    if(bean.getList().size()>0){
                        List<Map<String,String>> list=new ArrayList<>();
                        for (MemberListBean.ListBean listBean : bean.getList()) {
                            Map<String,String> map=new HashMap<>();
                            map.put("title",listBean.getMemberName()+" ("+listBean.getMemberPost()+")");
                            map.put("info",listBean.getMemberIntro());
                            map.put("status","0");
                            list.add(map);
                        }

                        if(pageNum==1){
                            adapter.setDataLists(list);
                            adapter.isLoadMore(true);
                        }else {
                            adapter.addAll(list);
                        }
                        xLoadingView.showContent();
                    }

                    if("1".equals(bean.getPageNum()) && "0".equals(bean.getTotalPages()) && bean.getList().size()==0){
                        xLoadingView.showEmpty();
                    }
                    if(bean.getTotalPages().equals(bean.getPageNum()+"") || "0".equals(bean.getTotalPages())){
                        adapter.showLoadComplete();
                    }else {
                        pageNum=pageNum+1;
                    }
                }else {
                    xLoadingView.showError();
                }
            }

            @Override
            public void onFailed(String error) {

            }
        });
    }

    class MyAdapter extends XRecyclerViewAdapter<Map<String,String>> {

        public MyAdapter(@NonNull RecyclerView mRecyclerView, List<Map<String,String>> dataLists) {
            super(mRecyclerView, dataLists,R.layout.item_activity_team_manager);
        }

        @Override
        protected void bindData(XViewHolder holder, final Map<String,String> data, final int position) {
            final RelativeLayout rlTitle=holder.getView(R.id.rl_help_title);
            TextView tvTitle=holder.getView(R.id.tv_help_title);
            final LinearLayout llInfo=holder.getView(R.id.ll_info);
            final ImageView ivGray=holder.getView(R.id.iv_gray);
            TextView tvInfo=holder.getView(R.id.tv_help_info);

            tvTitle.setText(data.get("title"));
            //tvInfo.setText(data.get("info"));
            tvInfo.setText(data.get("info"));
            if("0".equals(data.get("status"))){//不显示
                ivGray.setBackgroundResource(R.drawable.right_gray);

                if(position==0 ){
                    rlTitle.setBackgroundResource(R.drawable.bg_online_customer_service_white);
                }else if(position==listBeans.size()-1) {
                    rlTitle.setBackgroundResource(R.drawable.bg_online_customer_service_white);
                }else{
                    rlTitle.setBackgroundColor(getResources().getColor(R.color.white));
                }
                llInfo.setVisibility(View.GONE);
                ivGray.setBackgroundResource(R.drawable.right_gray);
            }else {
                ivGray.setBackgroundResource(R.drawable.top_gray);

                if(position==0){
                    rlTitle.setBackgroundResource(R.drawable.bg_gray_top);
                }else {
                    rlTitle.setBackgroundColor(getResources().getColor(R.color.bg_help_gray));
                }
                llInfo.setVisibility(View.VISIBLE);
                ivGray.setBackgroundResource(R.drawable.top_gray);
            }
            rlTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if("0".equals(data.get("status"))){//不显示
                        if(position==0){
                            rlTitle.setBackgroundResource(R.drawable.bg_gray_top);
                        }else {
                            rlTitle.setBackgroundColor(getResources().getColor(R.color.bg_help_gray));
                        }

                        data.put("status","1");
                        llInfo.setVisibility(View.VISIBLE);
                        ivGray.setBackgroundResource(R.drawable.top_gray);
                    }else {
                        if(position==0 ){
                            rlTitle.setBackgroundResource(R.drawable.bg_online_customer_service_white);
                        }else if(position==listBeans.size()-1) {
                            rlTitle.setBackgroundResource(R.drawable.bg_online_customer_service_white);
                        }else{
                            rlTitle.setBackgroundColor(getResources().getColor(R.color.white));
                        }

                        data.put("status","0");
                        llInfo.setVisibility(View.GONE);
                        ivGray.setBackgroundResource(R.drawable.right_gray);
                    }

                }
            });
        }
    }
}
