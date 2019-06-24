package com.example.administrator.huorongdai.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.administrator.huorongdai.activity.ReturnDetailActivity;
import com.example.administrator.huorongdai.adapter.MyGridLayoutManager;
import com.example.administrator.huorongdai.base.LazyLoadFragment;
import com.example.administrator.huorongdai.gsonbean.EarnListBean;
import com.example.administrator.huorongdai.gsonbean.SelfInvestBean9;
import com.example.administrator.huorongdai.xframe.adapter.XRecyclerViewAdapter;
import com.example.administrator.huorongdai.xframe.adapter.XViewHolder;
import com.example.administrator.huorongdai.xframe.adapter.decoration.DividerDecoration;
import com.example.administrator.huorongdai.xframe.utils.XEmptyUtils;
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
public class BidRecord9Fragment extends LazyLoadFragment {
    private TextView tvTitle3;
    private String custMobile;
    private String custPwd;//密文密码
    //不传默认查询9，2-投标中的投资，3-失败的投资，5-满标待审的投资，9-收款中的投资，10-已结清的投资
    private String investStatus;

    private XLoadingView xLoadingView;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private MyAdapter9 adapter9;
    private List<SelfInvestBean9.SelfInvestListBean> listBeans9;
    private int pageNum=1;
    private Handler handler=new Handler(new MyCallback());
    private final class MyCallback implements Handler.Callback{

        @Override
        public boolean handleMessage(Message message) {
            if(XNetworkUtils.isConnected()){
                pageNum=1;
                requestDate9();
            }else {
                xLoadingView.showNoNetwork();
            }
            return true;
        }
    }

    private SwipeRefreshLayout.OnRefreshListener refreshListener=new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            adapter9.isLoadMore(false);
            handler.sendMessage(Message.obtain());

        }
    };

    public BidRecord9Fragment() {
        // Required empty public constructor
    }
    @SuppressLint("ValidFragment")
    public BidRecord9Fragment(String investStatus) {
        this.investStatus=investStatus;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_bid_record9;
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
        if("10".equals(investStatus)){
            tvTitle3.setText("全部收益");
        }

        DividerDecoration decoration=new DividerDecoration(R.color.gray_bg,1);
        decoration.setDrawLastItem(false);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setLayoutManager(new MyGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        listBeans9=new ArrayList<>();
        adapter9=new MyAdapter9(recyclerView,listBeans9);
        recyclerView.setAdapter(adapter9);

        adapter9.setOnItemClickListener(new XRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                SelfInvestBean9.SelfInvestListBean bean=adapter9.getItem(position);
                if(XEmptyUtils.isEmpty(bean.getEarnList())){
                    return;
                }
                ArrayList<EarnListBean> earnBeans=new ArrayList<>();
                for(SelfInvestBean9.SelfInvestListBean.EarnListBean earnList:bean.getEarnList()){
                    EarnListBean earn=new EarnListBean();
                    earn.setCustId(earnList.getCustId());
                    earn.setEarnCapital(earnList.getEarnCapital());
                    earn.setEarnIint(earnList.getEarnIint());
                    earn.setEarnIssue(earnList.getEarnIssue());
                    earn.setEarnStatus(earnList.getEarnStatus());
                    earn.setId(earnList.getId());
                    earn.setIvstId(earnList.getIvstId());
                    earn.setLateFine(earnList.getLateFine());
                    earn.setLateIint(earnList.getLateIint());
                    earn.setLoanId(earnList.getLoanId());
                    earn.setOrderNum(earnList.getOrderNum());
                    earn.setPlatfFee(earnList.getPlatfFee());
                    earn.setRealEanDate(earnList.getRealEanDate());
                    earn.setShdEarnDate(earnList.getShdEarnDate());
                    earnBeans.add(earn);
                }
                Intent intentDetail=new Intent(getActivity(), ReturnDetailActivity.class);
                Bundle bundle =new Bundle();
                bundle.putString("investStatus",investStatus);
                bundle.putString("loanTitle",bean.getLoanTitle());
                bundle.putString("loanArp",bean.getLoanArp()+"");//年化利率
                bundle.putString("ivstAmt",bean.getIvstAmt()+"");//投资金额
                bundle.putString("repayTypeName",bean.getRepayTypeName());//还款方式
                bundle.putParcelableArrayList("earnList",earnBeans);
                intentDetail.putExtra("bundle",bundle);
                startActivity(intentDetail);
            }
        });
        adapter9.setOnLoadMoreListener(new XRecyclerViewAdapter.OnLoadMoreListener() {
            @Override
            public void onRetry() {

            }

            @Override
            public void onLoadMore() {
                requestDate9();
            }
        });

        refreshLayout.setRefreshing(true);
        refreshListener.onRefresh();
    }

    @Override
    public void lazyLoad() {

    }

    private void requestDate9(){
        Map<String,Object > params =new HashMap<>();
        params .put("custMobile",custMobile);
        params .put("custPwd",custPwd);
        params .put("investStatus",investStatus);
        params .put("pageNum",""+pageNum);
        params .put("pageSize","15");
        XHttp.obtain().post(Path.get_self_invest_list, params, new HttpCallBack<SelfInvestBean9>() {
            @Override
            public void onSuccess(SelfInvestBean9 bean) {
                if(bean.isStatus()){
                    if(bean.getSelfInvestList().size()>0){
                        if(pageNum==1){
                            adapter9.setDataLists(bean.getSelfInvestList());
                            adapter9.isLoadMore(true);
                        }else {
                            adapter9.addAll(bean.getSelfInvestList());
                        }
                        xLoadingView.showContent();
                    }

                    if("1".equals(bean.getPageNum()) && "0".equals(bean.getTotalPages()) && bean.getSelfInvestList().size()==0){
                        xLoadingView.showEmpty();
                    }
                    if(bean.getPageNum().equals(bean.getTotalPages()) || "0".equals(bean.getTotalPages())){
                        adapter9.showLoadComplete();
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

    class MyAdapter9 extends XRecyclerViewAdapter<SelfInvestBean9.SelfInvestListBean>{
        private DecimalFormat decimal=new DecimalFormat("0.00");
        public MyAdapter9(@NonNull RecyclerView mRecyclerView, List<SelfInvestBean9.SelfInvestListBean> dataLists) {
            super(mRecyclerView, dataLists,R.layout.item_fragment_bid_record);
        }

        @Override
        protected void bindData(XViewHolder holder, SelfInvestBean9.SelfInvestListBean data, int position) {
            TextView tvName=holder.getView(R.id.tv_project_name);//项目
            TextView tvRate=holder.getView(R.id.tv_rate);//利率
            TextView tvMoney=holder.getView(R.id.tv_money);//待收本金
            TextView tvTime=holder.getView(R.id.tv_time);//时间

            tvName.setText(data.getLoanTitle());
            tvRate.setText(data.getLoanArp()+"%");
            double m=0.00;
            if(!XEmptyUtils.isEmpty(data.getEarnList())){
                if("9".equals(investStatus)){
                    for (SelfInvestBean9.SelfInvestListBean.EarnListBean earnListBean : data.getEarnList()) {//earnStatus状态 1未还2已还
                        if(earnListBean.getEarnStatus()==1){
                            m=m+earnListBean.getEarnCapital()+earnListBean.getEarnIint()+earnListBean.getLateIint();
                        }
                    }
                }else {
                    for (SelfInvestBean9.SelfInvestListBean.EarnListBean earnListBean : data.getEarnList()) {//earnStatus状态 1未还2已还
                        m=m+earnListBean.getEarnCapital()+earnListBean.getEarnIint()+earnListBean.getLateIint();
                    }
                }
            }

            tvMoney.setText("¥"+decimal.format(m));
            String timeStr=data.getIvstTime();
            String date=timeStr.substring(0,4)+"-"+timeStr.substring(4,6)+"-"+timeStr.substring(6,8);
            tvTime.setText(date);

        }
    }

}
