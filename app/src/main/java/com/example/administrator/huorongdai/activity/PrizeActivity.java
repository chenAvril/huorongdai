package com.example.administrator.huorongdai.activity;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.huorongdai.Path;
import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.base.BaseActivity;
import com.example.administrator.huorongdai.gsonbean.PrizeBean;
import com.example.administrator.huorongdai.gsonbean.ResCodeBean;
import com.example.administrator.huorongdai.view.CustomDialog;
import com.example.administrator.huorongdai.xframe.adapter.XRecyclerViewAdapter;
import com.example.administrator.huorongdai.xframe.adapter.XViewHolder;
import com.example.administrator.huorongdai.xframe.adapter.decoration.DividerDecoration;
import com.example.administrator.huorongdai.xframe.utils.XEmptyUtils;
import com.example.administrator.huorongdai.xframe.utils.XNetworkUtils;
import com.example.administrator.huorongdai.xframe.utils.XPreferencesUtils;
import com.example.administrator.huorongdai.xframe.utils.http.HttpCallBack;
import com.example.administrator.huorongdai.xframe.utils.http.XHttp;
import com.example.administrator.huorongdai.xframe.widget.XToast;
import com.example.administrator.huorongdai.xframe.widget.loadingview.XLoadingView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class PrizeActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private List<PrizeBean.DiscountListBean> list;
    private CustomDialog dialogDis;//折现弹框
    private CustomDialog dialogEx;//兑换弹框
    private SwipeRefreshLayout refreshLayout;
    private XLoadingView xLoadingView;
    private int page=1;
    private String custId;//用户id

    private Handler handler=new Handler(new MyCallable());
    private final class MyCallable implements Handler.Callback{
        @Override
        public boolean handleMessage(Message msg) {
            if(XNetworkUtils.isConnected()){
                page=1;
                requestInfo();
                adapter.isLoadMore(false);
            }else {
                xLoadingView.showNoNetwork();
                if(refreshLayout.isRefreshing()){
                    refreshLayout.setRefreshing(false);
                }
            }
            return true;
        }
    }

    private SwipeRefreshLayout.OnRefreshListener refreshListener=new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            handler.sendMessage(Message.obtain());
        }
    };

    @Override
    protected int getContentView() {
        return R.layout.activity_prize;
    }

    @Override
    protected void initView() {
        setTitle("投资奖品");
        custId= (String) XPreferencesUtils.get("custId","");//用户ID
        dialogDis = new CustomDialog(this, R.style.custom_dialog2, R.layout.login_notice);
        dialogEx = new CustomDialog(this, R.style.custom_dialog2, R.layout.dialog_fragment_integral_shop);
        xLoadingView=findViewById(R.id.xloading_view);
        recyclerView=findViewById(R.id.rl_activity_prize);
        refreshLayout=findViewById(R.id.srl_activity_prize);
        refreshLayout.setColorScheme(R.color.word_red);
        refreshLayout.setOnRefreshListener(refreshListener);
        xLoadingView.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xLoadingView.showLoading();
                handler.sendMessageDelayed(Message.obtain(),600);
            }
        });
        list=new ArrayList<>();
        DividerDecoration decoration=new DividerDecoration(R.color.light_gray,1);
        decoration.setDrawLastItem(false);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        adapter=new MyAdapter(recyclerView,list);
        recyclerView.setAdapter(adapter);
//        adapter.setOnItemClickListener(new XRecyclerViewAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View v, int position) {
//                Intent intent=new Intent(PrizeActivity.this,PrizeDetailActivity.class);
//                startActivity(intent);
//            }
//        });
        adapter.setOnLoadMoreListener(new XRecyclerViewAdapter.OnLoadMoreListener() {
            @Override
            public void onRetry() {

            }

            @Override
            public void onLoadMore() {
                requestInfo();
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

    //折现提示框
    private void initDialogDis(final int position){
        dialogDis.show();
        TextView titleTv=dialogDis.findViewById(R.id.tv_login_notice_title);
        TextView cancelTv=dialogDis.findViewById(R.id.tv_login_notice_cancel);
        TextView sureTv=dialogDis.findViewById(R.id.tv_login_notice_sure);
        titleTv.setText("请选择将您的商品兑换还是折现？");
        cancelTv.setText("兑换");
        sureTv.setText("折现");

        final String discountId=adapter.getItem(position).getId();
        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//兑换
                dialogDis.dismiss();
                initDialogEx(1,discountId);
            }
        });
        sureTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//折现
                operationRequest(2,discountId,"","");
            }
        });
    }

    //普通兑换提示框
    private void initDialogEx(final int status, final String discountId){
        dialogEx.show();
        ImageView ivDelete=dialogEx.findViewById(R.id.iv_delete);
        final EditText etName=dialogEx.findViewById(R.id.et_person_name);//名字
        final EditText etAddress=dialogEx.findViewById(R.id.et_address);//收货地址或充值号
        TextView tvSure=dialogEx.findViewById(R.id.tv_sure);
        TextView tvAddress=dialogEx.findViewById(R.id.tv_address);
        etName.setText("");
        etAddress.setText("");
        tvAddress.setText("输入收货地址：");
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uname=etName.getText().toString().trim();
                String address=etAddress.getText().toString().trim();
                operationRequest(status,discountId,uname,address);
            }
        });
        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogEx.dismiss();
            }
        });
    }

    //投资商品操作请求          status ===>1  兑换  2 折现
    private void operationRequest(int status,String discountId,String uname,String address){
        LinkedHashMap<String,Object > params =new LinkedHashMap<>();
        params .put("custId",custId);
        params .put("discountId",discountId);//商品类型id

        String sign= "";
        if(status==1){//1  兑换
            params .put("uname",uname);
            params .put("address",address);
            params .put("status",""+status);
            sign=Path.getSign(Path.update_cash_discount,params);
            params .put("sign",sign);
        }else {//2  折现
            params .put("status",""+status);
            sign=Path.getSign(Path.update_cash_discount,params);
            params .put("sign",sign);
        }
        XHttp.obtain().post(Path.update_cash_discount, params, new HttpCallBack<ResCodeBean>() {
            @Override
            public void onSuccess(ResCodeBean bean) {
                XToast.normal(bean.getMessage());
                if(bean.isStatus()){
                    handler.sendMessage(Message.obtain());
                    if(dialogDis.isShowing()){
                        dialogDis.dismiss();
                    }
                    if(dialogEx.isShowing()){
                        dialogEx.dismiss();
                    }
                }
            }

            @Override
            public void onFailed(String error) {

            }
        });
    }

    //投资商品列表请求
    private void requestInfo(){
        LinkedHashMap<String,Object > params =new LinkedHashMap<>();
        params .put("custId",custId);
        params .put("pageNum",""+page);
        params .put("pageSize","15");
        String sign=Path.getSign(Path.cash_discount_list,params);
        params .put("sign",sign);
        XHttp.obtain().post(Path.cash_discount_list, params, new HttpCallBack<PrizeBean>() {
            @Override
            public void onSuccess(PrizeBean bean) {
                if(bean.isStatus()){
                    if(!XEmptyUtils.isEmpty(bean.getDiscountList())){
                        if(bean.getDiscountList().size()>0){
                            if(page==1){
                                adapter.setDataLists(bean.getDiscountList());
                                adapter.isLoadMore(true);
                            }else {
                                adapter.addAll(bean.getDiscountList());
                            }
                            xLoadingView.showContent();
                        }else {
                            xLoadingView.showEmpty();
                        }

                        if(page==bean.getTotalPages() || 0 == bean.getTotalCount()){
                            adapter.showLoadComplete();
                        }else {
                            page=page+1;
                        }
                    }else {
                        xLoadingView.showEmpty();
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


    class MyAdapter extends XRecyclerViewAdapter<PrizeBean.DiscountListBean>{
        DecimalFormat decimal=new DecimalFormat("0");

        public MyAdapter(@NonNull RecyclerView mRecyclerView, List<PrizeBean.DiscountListBean> dataLists) {
            super(mRecyclerView, dataLists,R.layout.item_activity_prize);
        }

        @Override
        protected void bindData(XViewHolder holder, PrizeBean.DiscountListBean data, final int position) {
            TextView tvName=holder.getView(R.id.tv_bid_name);//标的名称
            TextView tvAmt=holder.getView(R.id.tv_bid_amt);//投资金额
            TextView tvPrize=holder.getView(R.id.tv_prize_name);//奖品名称
            TextView tvOperation=holder.getView(R.id.tv_operation);//操作

            tvName.setText(data.getLoanTitle());
            tvAmt.setText(decimal.format(data.getIvstAmt()));
            tvPrize.setText(data.getCashdiscountName());
            tvOperation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    initDialogDis(position);
                }
            });

            //status  1 已兑换  2 已折现  为空则可以进行兑换或折现操作
            if(XEmptyUtils.isEmpty(data.getStatus())){
                tvOperation.setBackgroundResource(R.drawable.btn_red_gray_selector);
                tvOperation.setTextColor(getResources().getColor(R.color.white));
                tvOperation.setText("兑换/折现");
            }else {
                int status=data.getStatus();
                if(status==1){
                    tvOperation.setOnClickListener(null);
                    tvOperation.setBackgroundResource(R.drawable.small_gray_selector);
                    tvOperation.setTextColor(getResources().getColor(R.color.word_gray));
                    tvOperation.setText("已兑换");
                }else if(status==2){
                    tvOperation.setOnClickListener(null);
                    tvOperation.setBackgroundResource(R.drawable.small_gray_selector);
                    tvOperation.setTextColor(getResources().getColor(R.color.word_gray));
                    tvOperation.setText("已折现");
                }
            }
        }
    }

}
