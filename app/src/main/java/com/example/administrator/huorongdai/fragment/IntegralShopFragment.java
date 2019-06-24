package com.example.administrator.huorongdai.fragment;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.huorongdai.Path;
import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.adapter.MyGridLayoutManager;
import com.example.administrator.huorongdai.base.LazyLoadFragment;
import com.example.administrator.huorongdai.gsonbean.ExchangeBean;
import com.example.administrator.huorongdai.gsonbean.GoodListBean;
import com.example.administrator.huorongdai.view.CustomDialog;
import com.example.administrator.huorongdai.xframe.adapter.XRecyclerViewAdapter;
import com.example.administrator.huorongdai.xframe.adapter.XViewHolder;
import com.example.administrator.huorongdai.xframe.utils.XNetworkUtils;
import com.example.administrator.huorongdai.xframe.utils.XPreferencesUtils;
import com.example.administrator.huorongdai.xframe.utils.XRegexUtils;
import com.example.administrator.huorongdai.xframe.utils.http.HttpCallBack;
import com.example.administrator.huorongdai.xframe.utils.http.XHttp;
import com.example.administrator.huorongdai.xframe.utils.imageload.XImage;
import com.example.administrator.huorongdai.xframe.widget.XLoadingDialog;
import com.example.administrator.huorongdai.xframe.widget.XToast;
import com.example.administrator.huorongdai.xframe.widget.loadingview.XLoadingView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class IntegralShopFragment extends LazyLoadFragment {

    private XLoadingView xLoadingView;
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private List<GoodListBean.ListBean> dataLists;
    private int page=1;
    private CustomDialog dialogNormal;//普通兑换弹框
    private CustomDialog dialogRed;//红包兑换弹框
    private String categoryId;//商品类型id （商品类型接口返回参 shanglist → id）
    private String custId;//用户id
    private String goodsType;//商品类型

    private Handler handler=new Handler(new MyCallable());
    private final class MyCallable implements Handler.Callback{
        @Override
        public boolean handleMessage(Message msg) {
            if(XNetworkUtils.isConnected()){
                page=1;
                requestInfo();
                adapter.isLoadMore(false);
                xLoadingView.showContent();
            }else {
                xLoadingView.showNoNetwork();
            }
            return true;
        }
    }

    @SuppressLint("ValidFragment")
    public IntegralShopFragment(String categoryId,String goodsType) {
        this.categoryId=categoryId;
        this.goodsType=goodsType;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_integral_shop;
    }

    @Override
    protected void initViews() {
        xLoadingView=findViewById(R.id.xloading_view);
        recyclerView=findViewById(R.id.rl_integral_shop);
        xLoadingView.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xLoadingView.showLoading();
                handler.sendMessageDelayed(Message.obtain(),600);
            }
        });

        dataLists=new ArrayList<>();
        adapter=new MyAdapter(recyclerView,dataLists);
        recyclerView.setLayoutManager(new MyGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);

        adapter.setOnLoadMoreListener(new XRecyclerViewAdapter.OnLoadMoreListener() {
            @Override
            public void onRetry() {

            }

            @Override
            public void onLoadMore() {
                requestInfo();
            }
        });

        custId= (String) XPreferencesUtils.get("custId","");//用户ID
        dialogNormal = new CustomDialog(getActivity(), R.style.custom_dialog2, R.layout.dialog_fragment_integral_shop);
        dialogRed = new CustomDialog(getActivity(), R.style.custom_dialog2, R.layout.login_notice);

        handler.sendMessage(Message.obtain());
    }

    @Override
    public void lazyLoad() {

    }

    public void refresh(){
        handler.sendMessage(Message.obtain());
    }

    private void requestInfo(){
        Map<String,Object > params =new HashMap<>();
        params .put("categoryId",categoryId);
        params .put("pageNum",""+page);
        params .put("pageSize","10");
        XHttp.obtain().post(Path.goods_list, params, new HttpCallBack<GoodListBean>() {
            @Override
            public void onSuccess(GoodListBean loanBean) {
                if("true".equals(loanBean.getStatus())){
                    if(loanBean.getList().size()>0){
                        if(page==1){
                            adapter.setDataLists(loanBean.getList());
                            adapter.isLoadMore(true);
                        }else {
                            adapter.addAll(loanBean.getList());
                        }
                    }else {
                        xLoadingView.showEmpty();
                    }

                    if(page==loanBean.getTotalPages() || 0 == loanBean.getTotalCount()){
                        adapter.showLoadComplete();
                    }else {
                        page=page+1;
                    }
                }else {
                    xLoadingView.showError();
                }

                if(onRefresh!=null){
                    onRefresh.setOnRefresh();
                }
            }

            @Override
            public void onFailed(String error) {

            }
        });
    }

    //普通兑换提示框
    private void initDialogNormal(final int position){
        dialogNormal.show();
        ImageView ivDelete=dialogNormal.findViewById(R.id.iv_delete);
        final EditText etName=dialogNormal.findViewById(R.id.et_person_name);//名字
        final EditText etAddress=dialogNormal.findViewById(R.id.et_address);//收货地址或充值号
        TextView tvSure=dialogNormal.findViewById(R.id.tv_sure);
        etName.setText("");
        etAddress.setText("");
        final GoodListBean.ListBean bean=adapter.getItem(position);
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=etName.getText().toString().trim();
                String address=etAddress.getText().toString().trim();
                if(bean.getGoodsName().contains("话费充值")){
                    if(!XRegexUtils.checkMobile(address)){
                        XToast.normal("请输入正确的手机号码");
                        return;
                    }
                    exchange(2,bean.getId(),"",name,address);
                }else {
                    exchange(1,bean.getId(),address,name,"");
                }
            }
        });
        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogNormal.dismiss();
            }
        });
    }

    //红包兑换弹框
    private void initDialogRed(final int position){
        dialogRed.show();
        TextView titleTv=dialogRed.findViewById(R.id.tv_login_notice_title);
        TextView cancelTv=dialogRed.findViewById(R.id.tv_login_notice_cancel);
        TextView sureTv=dialogRed.findViewById(R.id.tv_login_notice_sure);
        final GoodListBean.ListBean bean=adapter.getItem(position);
        titleTv.setText("是否兑换"+bean.getGoodsName()+"?");
        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogRed.dismiss();
            }
        });
        sureTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exchange(3,bean.getId(),"","","");
            }
        });

    }

    //兑换商品     type：1=》普通类型兑换；2=》手机充值兑换；3=》投资红包兑换
    private void exchange(final int type,String goodsId,String address,String name,String mobile){
        XLoadingDialog.with(getActivity())
                .setCanceled(false) //设置手动不可取消
                .setOrientation(XLoadingDialog.HORIZONTAL) //设置显示方式（水平或者垂直）
                .setBackgroundColor(Color.parseColor("#aa000000"))//设置对话框背景
                .setMessageColor(Color.WHITE)//设置消息字体颜色
                .setMessage("兑换中...")//设置消息文本
                .show();//显示对话框

        Map<String,Object > params =new HashMap<>();
        params .put("custId",custId);
        params .put("goodsId",goodsId);
        if(type==1){
            params .put("address",address);
            params .put("name",name);
        }else if(type==2){
            params .put("name",name);
            params .put("mobile",mobile);
        }
        XHttp.obtain().post(Path.point_to_redp, params, new HttpCallBack<ExchangeBean>() {
            @Override
            public void onSuccess(ExchangeBean bean) {
                if(type==3){
                    if(dialogRed.isShowing()){
                        dialogRed.dismiss();
                    }
                }else {
                    if(dialogNormal.isShowing()){
                        dialogNormal.dismiss();
                    }
                }
                XLoadingDialog.with(getActivity()).dismiss();
                XToast.normal(bean.getOptResultMsg());
            }

            @Override
            public void onFailed(String error) {

            }
        });
    }

    class MyAdapter extends XRecyclerViewAdapter<GoodListBean.ListBean> {

        public MyAdapter(@NonNull RecyclerView mRecyclerView, List<GoodListBean.ListBean> dataLists) {
            super(mRecyclerView, dataLists,R.layout.item_fragment_integral_shop);
        }

        @Override
        protected void bindData(XViewHolder holder, GoodListBean.ListBean data, final int position) {
            ImageView ivGood=holder.getView(R.id.iv_good_img);//商品图片
            TextView tvName=holder.getView(R.id.tv_good_name);//商品名称
            TextView tvIntegral=holder.getView(R.id.tv_integral);//商品兑换积分
            TextView tvExchange=holder.getView(R.id.tv_exchange);//立即兑换

            XImage.getInstance().load(ivGood, Path.BASE_ImgURL+data.getImageUrl());
            tvName.setText(data.getGoodsName());
            tvIntegral.setText(data.getNowPoint()+"积分");
            tvExchange.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if("投资红包".equals(goodsType)){
                        initDialogRed(position);
                    }else {
                        initDialogNormal(position);
                    }
                }
            });
        }
    }

    public void setOnRefresh(OnRefresh onRefresh) {
        this.onRefresh = onRefresh;
    }

    public OnRefresh onRefresh;
    public interface OnRefresh{
        void setOnRefresh();
    }

}
