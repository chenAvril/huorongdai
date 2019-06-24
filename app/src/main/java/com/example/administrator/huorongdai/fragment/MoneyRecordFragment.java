package com.example.administrator.huorongdai.fragment;


import android.annotation.SuppressLint;
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
import com.example.administrator.huorongdai.adapter.MyGridLayoutManager;
import com.example.administrator.huorongdai.base.LazyLoadFragment;
import com.example.administrator.huorongdai.gsonbean.TradeRecordBean;
import com.example.administrator.huorongdai.xframe.adapter.XRecyclerViewAdapter;
import com.example.administrator.huorongdai.xframe.adapter.XViewHolder;
import com.example.administrator.huorongdai.xframe.adapter.decoration.DividerDecoration;
import com.example.administrator.huorongdai.xframe.utils.XNetworkUtils;
import com.example.administrator.huorongdai.xframe.utils.XPreferencesUtils;
import com.example.administrator.huorongdai.xframe.utils.http.HttpCallBack;
import com.example.administrator.huorongdai.xframe.utils.http.XHttp;
import com.example.administrator.huorongdai.xframe.widget.loadingview.XLoadingView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class MoneyRecordFragment extends LazyLoadFragment {

    private TextView tvTradeFee;//手续费
    private XLoadingView xLoadingView;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private List<TradeRecordBean.TradeListBean> listBeans=new ArrayList<>();

    private String tradeType;
    private String custMobile;//手机号码
    private String custPwd;//密文密码
    private int pageNum=1;

    private Handler handler=new Handler(new MyCallable());
    private final class MyCallable implements Handler.Callback{
        @Override
        public boolean handleMessage(Message msg) {
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

    private SwipeRefreshLayout.OnRefreshListener refreshListener=new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            adapter.isLoadMore(false);
            handler.sendMessage(Message.obtain());
        }
    };
    public MoneyRecordFragment() {

    }

    @SuppressLint("ValidFragment")
    public MoneyRecordFragment(String tradeType) {
        this.tradeType=tradeType;
    }


    @Override
    protected int setLayoutId() {
        return R.layout.fragment_money_record;
    }

    @Override
    protected void initViews() {
        xLoadingView=findViewById(R.id.xloading_view);
        refreshLayout=findViewById(R.id.srl_fragment_money_record);
        refreshLayout.setColorScheme(R.color.word_red);
        refreshLayout.setOnRefreshListener(refreshListener);
        recyclerView=findViewById(R.id.rl_fragment_money_record);
        tvTradeFee=findViewById(R.id.tv_trade_fee);

        xLoadingView.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xLoadingView.showLoading();
                handler.sendMessageDelayed(Message.obtain(),600);
            }
        });

        if("2".equals(tradeType)){
            tvTradeFee.setVisibility(View.VISIBLE);
        }

        custMobile = (String) XPreferencesUtils.get("custMobile","");
        custPwd = (String) XPreferencesUtils.get("custPwd","");

        listBeans=new ArrayList<>();
        DividerDecoration decoration=new DividerDecoration(R.color.gray_bg,1);
        decoration.setDrawLastItem(false);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setLayoutManager(new MyGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        adapter=new MyAdapter(recyclerView,listBeans);
        recyclerView.setAdapter(adapter);

        adapter.setOnLoadMoreListener(new XRecyclerViewAdapter.OnLoadMoreListener() {
            @Override
            public void onRetry() {

            }

            @Override
            public void onLoadMore() {
                requestDate();
            }
        });
        refreshLayout.setRefreshing(true);
        refreshListener.onRefresh();
    }

    private void requestDate() {
        //tradeType：交易类型，1-充值，2-提现，3-投资，4-受让，5-转让，6-放款，7-还款，8-收款，9-红包，10-积分（暂时不显示），11-转账；
        //tradeStatus：交易状态，1-成功，2-失败，3-冻结，4-解冻；
        Map<String,Object > params =new HashMap<>();
        params .put("custMobile",custMobile);
        params .put("custPwd",custPwd);
        params .put("pageNum",""+pageNum);
        params .put("pageSize","20");
        params .put("tradeType",tradeType);
        XHttp.obtain().post(Path.get_trade_record_list, params, new HttpCallBack<TradeRecordBean>() {
            @Override
            public void onSuccess(TradeRecordBean bean) {
                if(bean.isStatus()){
                    if(bean.getTradeList().size()>0){
                        if(pageNum==1){
                            adapter.setDataLists(bean.getTradeList());
                            adapter.isLoadMore(true);
                        }else {
                            adapter.addAll(bean.getTradeList());
                        }
                        xLoadingView.showContent();
                    }

                    if("1".equals(bean.getPageNum()) && "0".equals(bean.getTotalPages()) && bean.getTradeList().size()==0){
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
    public void onDestroy() {
        //如果参数为null的话，会将所有的Callbacks和Messages全部清除掉。
        handler.removeCallbacksAndMessages( null );
        super.onDestroy();
    }

    @Override
    public void lazyLoad() {

    }

    class MyAdapter extends XRecyclerViewAdapter<TradeRecordBean.TradeListBean>{

        public MyAdapter(@NonNull RecyclerView mRecyclerView, List<TradeRecordBean.TradeListBean> dataLists) {
            super(mRecyclerView, dataLists,R.layout.item_trade_record);
        }

        @Override
        protected void bindData(XViewHolder holder, TradeRecordBean.TradeListBean data, int position) {
            TextView tvDate=holder.getView(R.id.tv_date);//日期
            TextView tvSum=holder.getView(R.id.tv_sum);//金额
            TextView tvFee=holder.getView(R.id.tv_fee);//手续费
            TextView tvStatus=holder.getView(R.id.tv_status);//状态

            //20170729 154945
            String date=data.getTradeDate();
            String time=date.substring(0,4)+"-"+date.substring(4,6) +"-"+date.substring(6,8);
            tvDate.setText(time);

            BigDecimal d1 = new BigDecimal(Double.toString(data.getTradeAmt()));
            BigDecimal d2 = new BigDecimal(Integer.toString(1));
            // 四舍五入,保留0位小数
            tvSum.setText(d1.divide(d2,0,BigDecimal.ROUND_HALF_UP).toString());

            if("2".equals(tradeType)){
                tvFee.setVisibility(View.VISIBLE);
                tvFee.setText(data.getTradeFee()+"");
            }

            int status=data.getTradeStatus();
            if(status==1){
                tvStatus.setText("成功");
            }else if(status==2){
                tvStatus.setText("失败");
            }else if(status==3){
                tvStatus.setText("冻结");
            }else if(status==4){
                tvStatus.setText("解冻");
            }
        }
    }
}
