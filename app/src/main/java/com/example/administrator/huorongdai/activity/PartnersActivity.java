package com.example.administrator.huorongdai.activity;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.huorongdai.Path;
import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.base.BaseActivity;
import com.example.administrator.huorongdai.gsonbean.LinkListBean;
import com.example.administrator.huorongdai.gsonbean.MemberListBean;
import com.example.administrator.huorongdai.xframe.adapter.XRecyclerViewAdapter;
import com.example.administrator.huorongdai.xframe.adapter.XViewHolder;
import com.example.administrator.huorongdai.xframe.adapter.decoration.DividerDecoration;
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

public class PartnersActivity extends BaseActivity {

    private XLoadingView xLoadingView;
    private RecyclerView mRecyclerView;
    private MyAdapter adapter;
    private List<LinkListBean.ListBean> listBeans;
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
        return R.layout.activity_partners;
    }

    @Override
    protected void initView() {
        setTitle("合作伙伴");
        mRecyclerView = findViewById(R.id.team_manager_recycler_view);
        xLoadingView=findViewById(R.id.xloading_view);
        xLoadingView.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xLoadingView.showLoading();
                handler.sendMessageDelayed(Message.obtain(),600);
            }
        });

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
        XHttp.obtain().post(Path.link_list, params, new HttpCallBack<LinkListBean>() {
            @Override
            public void onSuccess(LinkListBean bean) {
                if(!XEmptyUtils.isEmpty(bean) ){
                    if(XEmptyUtils.isEmpty(bean.getList())){
                        xLoadingView.showError();
                        return;
                    }
                    if(bean.getList().size()>0){
                        if(pageNum==1){
                            adapter.setDataLists(bean.getList());
                            adapter.isLoadMore(true);
                        }else {
                            adapter.addAll(bean.getList());
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

    class MyAdapter extends XRecyclerViewAdapter<LinkListBean.ListBean> {

        public MyAdapter(@NonNull RecyclerView mRecyclerView, List<LinkListBean.ListBean> dataLists) {
            super(mRecyclerView, dataLists,R.layout.item_activity_partners);
        }

        @Override
        protected void bindData(XViewHolder holder, final LinkListBean.ListBean data, final int position) {
            ImageView ivIcon=holder.getView(R.id.iv_icon);
            TextView tvTitle=holder.getView(R.id.tv_title);
            TextView tvInfo=holder.getView(R.id.tv_info);

            String imageUrl=Path.BASE_ImgURL+data.getCoopSiteLogo();
            XImage.getInstance().load(ivIcon,imageUrl,R.mipmap.icon404);

            tvTitle.setText(data.getCoopSiteName());
            tvInfo.setText(data.getCoopDescr());
        }
    }
}
