package com.example.administrator.huorongdai.activity;

import android.content.Intent;
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
import com.example.administrator.huorongdai.adapter.MyGridLayoutManager;
import com.example.administrator.huorongdai.base.BaseActivity;
import com.example.administrator.huorongdai.gsonbean.LoanInvestBean;
import com.example.administrator.huorongdai.xframe.adapter.XRecyclerViewAdapter;
import com.example.administrator.huorongdai.xframe.adapter.XViewHolder;
import com.example.administrator.huorongdai.xframe.adapter.decoration.DividerDecoration;
import com.example.administrator.huorongdai.xframe.utils.XNetworkUtils;
import com.example.administrator.huorongdai.xframe.utils.XPreferencesUtils;
import com.example.administrator.huorongdai.xframe.utils.http.HttpCallBack;
import com.example.administrator.huorongdai.xframe.utils.http.XHttp;
import com.example.administrator.huorongdai.xframe.widget.loadingview.XLoadingView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//投资列表
public class LoanInvestListActivity extends BaseActivity {

    private XLoadingView xLoadingView;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private List<LoanInvestBean.LoanInvestListBean> beanList=new ArrayList<>();

    private String custMobile;
    private String custPwd;//密文密码
    private String loanID="";
    private int pageNum=1;

    private Handler handler=new Handler(new MyCallback());
    private final class MyCallback implements Handler.Callback{

        @Override
        public boolean handleMessage(Message message) {
            if (XNetworkUtils.isConnected()){
                pageNum=1;
                load();
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
        return R.layout.activity_loan_invest_list;
    }

    @Override
    protected void initView() {
        Intent intent=getIntent();
        if(intent!=null){
            loanID=intent.getStringExtra("loanID");
        }

        setTitle("投资列表");
        xLoadingView=findViewById(R.id.xloading_view);
        refreshLayout=findViewById(R.id.srl_activity_loan_invest_list);
        refreshLayout.setColorScheme(R.color.word_red);
        refreshLayout.setOnRefreshListener(refreshListener);
        recyclerView=findViewById(R.id.rl_loan_invest_list);

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
        adapter=new MyAdapter(recyclerView,beanList);
        recyclerView.setAdapter(adapter);
        adapter.setOnLoadMoreListener(new XRecyclerViewAdapter.OnLoadMoreListener() {
            @Override
            public void onRetry() {

            }

            @Override
            public void onLoadMore() {
                load();
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

    private void load(){
        Map<String,Object > params =new HashMap<>();
        params.put("custMobile",custMobile);
        params.put("custPwd",custPwd);
        params.put("loanId",loanID);
        params.put("pageNum",""+pageNum);
        params.put("pageSize","20");
        XHttp.obtain().post(Path.get_loanInvest_list, params, new HttpCallBack<LoanInvestBean>() {
            @Override
            public void onSuccess(LoanInvestBean bean) {
                if(bean.isStatus()){
                    if(bean.getLoanInvestList().size()>0){
                        if(pageNum==1){
                            adapter.setDataLists(bean.getLoanInvestList());
                            adapter.isLoadMore(true);
                        }else {
                            adapter.addAll(bean.getLoanInvestList());
                        }
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

    class MyAdapter extends XRecyclerViewAdapter<LoanInvestBean.LoanInvestListBean> {

        public MyAdapter(@NonNull RecyclerView mRecyclerView, List<LoanInvestBean.LoanInvestListBean> dataLists) {
            super(mRecyclerView, dataLists,R.layout.item_loan_invest_list);
        }

        @Override
        protected void bindData(XViewHolder holder, LoanInvestBean.LoanInvestListBean data, int position) {
            TextView tvName=holder.getView(R.id.tv_invest_name);//投资名字
            TextView tvAmt=holder.getView(R.id.tv_invest_amt);//投资金额
            ImageView ivSource=holder.getView(R.id.iv_invest_source);//操作来源
            TextView tvTime=holder.getView(R.id.tv_invest_time);//投资时间

            String name=data.getCustName();
            if(name.length()>2){
                String first=name.substring(0,1);
                String last=name.substring(name.length()-1,name.length());
                name=first+"***"+last;
                tvName.setText(name);

            }else if(name.length()==2){
                String first=name.substring(0,1);
                String last=name.substring(1,2);
                name=first+"***"+last;
                tvName.setText(name);
            }else {
                tvName.setText(name+"***");
            }

            tvAmt.setText("¥"+data.getIvstAmt());

            //投资来源   1-pc 2-wap 3-微信 4-安卓 5-iOS
            switch (data.getIvstSource()){
                case 1:
                    ivSource.setBackgroundResource(R.drawable.pc_icon);
                    break;
                case 2:
                    ivSource.setBackgroundResource(R.drawable.wap_icon);
                    break;
                case 3:
                    ivSource.setBackgroundResource(R.drawable.weixin_icon);
                    break;
                case 4:
                    ivSource.setBackgroundResource(R.drawable.android_icon);
                    break;
                case 5:
                    ivSource.setBackgroundResource(R.drawable.ios_icon);
                    break;
            }

            //2018-01-11 11:11:11        2018-04-02 14:1030
            StringBuffer time=new StringBuffer(data.getIvstTime());
            time.insert(4,"-");
            time.insert(7,"-");
            time.insert(10," ");
            time.insert(13,":");
            time.insert(16,":");
            tvTime.setText(time.toString());
        }
    }
}
