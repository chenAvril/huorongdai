package com.example.administrator.huorongdai.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.example.administrator.huorongdai.activity.BidDetailActivity;
import com.example.administrator.huorongdai.activity.BorrowedDetailActivity;
import com.example.administrator.huorongdai.adapter.MyGridLayoutManager;
import com.example.administrator.huorongdai.base.LazyLoadFragment;
import com.example.administrator.huorongdai.gsonbean.SelfLoanBean;
import com.example.administrator.huorongdai.xframe.adapter.XRecyclerViewAdapter;
import com.example.administrator.huorongdai.xframe.adapter.XViewHolder;
import com.example.administrator.huorongdai.xframe.adapter.decoration.DividerDecoration;
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
@SuppressLint("ValidFragment")
public class BorrowingRecordFragment extends LazyLoadFragment {

    private XLoadingView xLoadingView;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;

    private MyAdapter adapter;
    private List<SelfLoanBean.SelfLoanListBean> listBeans=new ArrayList<>();

    private String custMobile;//手机号码
    private String custPwd;//密文密码
    private int pageNum=1;
    private String loanStatusScope;//项目状态，不传为不限，7-招标中、8-已满标，12-还款中，13-已还清

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

    public BorrowingRecordFragment() {
    }

    @SuppressLint("ValidFragment")
    public BorrowingRecordFragment(String loanStatusScope) {
        this.loanStatusScope=loanStatusScope;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_borrowing_record;
    }

    @Override
    protected void initViews() {
        xLoadingView=findViewById(R.id.xloading_view);
        refreshLayout=findViewById(R.id.srl_fragment_borrowing_record);
        refreshLayout.setColorScheme(R.color.word_red);
        refreshLayout.setOnRefreshListener(refreshListener);
        recyclerView=findViewById(R.id.rl_fragment_borrowing_record);

        custMobile = (String) XPreferencesUtils.get("custMobile","");
        custPwd = (String) XPreferencesUtils.get("custPwd","");

        xLoadingView.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xLoadingView.showLoading();
                handler.sendMessageDelayed(Message.obtain(),600);
            }
        });

        listBeans=new ArrayList<>();
        DividerDecoration decoration=new DividerDecoration(R.color.light_gray,1);
        decoration.setDrawLastItem(false);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setLayoutManager(new MyGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        adapter=new MyAdapter(recyclerView,listBeans);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new XRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                if(!"13".equals(loanStatusScope)){
                    Intent intent;
                    if("7".equals(loanStatusScope)){
                        intent=new Intent(getActivity(), BidDetailActivity.class);//融资中
                        intent.putExtra("loanStatus","7");
                    }else {
                        intent=new Intent(getActivity(),BorrowedDetailActivity.class);//还款中
                        intent.putExtra("loanStatus","12");
                    }
                    intent.putExtra("loanID",adapter.getItem(position).getId());
                    intent.putExtra("loanTitle",adapter.getItem(position).getLoanTitle());
                    startActivity(intent);
                }
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
        loadData();
    }

    public void loadData(){
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

    private void requestDate() {
        //tradeType：交易类型，1-充值，2-提现，3-投资，4-受让，5-转让，6-放款，7-还款，8-收款，9-红包，10-积分（暂时不显示），11-转账；
        //tradeStatus：交易状态，1-成功，2-失败，3-冻结，4-解冻；
        Map<String,Object > params =new HashMap<>();
        params .put("custMobile",custMobile);
        params .put("custPwd",custPwd);
        params .put("pageNum",""+pageNum);
        params .put("pageSize","20");
        params .put("loanStatusScope",loanStatusScope);
        XHttp.obtain().post(Path.self_loan_list, params, new HttpCallBack<SelfLoanBean>() {
            @Override
            public void onSuccess(SelfLoanBean bean) {
                if(bean.isStatus()){
                    if(bean.getSelfLoanList().size()>0){
                        if(pageNum==1){
                            adapter.setDataLists(bean.getSelfLoanList());
                            adapter.isLoadMore(true);
                        }else {
                            adapter.addAll(bean.getSelfLoanList());
                        }
                        xLoadingView.showContent();
                    }

                    if("1".equals(bean.getPageNum()) && "0".equals(bean.getTotalPages()) && bean.getSelfLoanList().size()==0){
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
    public void lazyLoad() {

    }

    class MyAdapter extends XRecyclerViewAdapter<SelfLoanBean.SelfLoanListBean> {

        public MyAdapter(@NonNull RecyclerView mRecyclerView, List<SelfLoanBean.SelfLoanListBean> dataLists) {
            super(mRecyclerView, dataLists,R.layout.item_fragment_borrowing_record);
        }

        @Override
        protected void bindData(XViewHolder holder, SelfLoanBean.SelfLoanListBean data, int position) {
            TextView tvName=holder.getView(R.id.tv_project_name);//名称
            TextView tvAmt=holder.getView(R.id.tv_borrow_amt);//借款金额
            TextView tvTime=holder.getView(R.id.tv_flag_time);//立标时间

            tvName.setText(data.getLoanTitle());
            tvAmt.setText("¥"+new DecimalFormat("0").format(data.getLoanAmt()));
            String date=data.getReleaseTime();
            //String time=date.substring(0,4)+"-"+date.substring(4,6)+"-"+date.substring(6,8)+" "+date.substring(8,10)+":"+date.substring(10,12)+":"+date.substring(12,14);
            String time=date.substring(0,4)+"-"+date.substring(4,6)+"-"+date.substring(6,8);
            tvTime.setText(time);
//
//            BigDecimal d1 = new BigDecimal(Double.toString(data.getTradeAmt()));
//            BigDecimal d2 = new BigDecimal(Integer.toString(1));
//            // 四舍五入,保留0位小数
//            tvSum.setText(d1.divide(d2,0,BigDecimal.ROUND_HALF_UP).toString());

        }
    }

}
