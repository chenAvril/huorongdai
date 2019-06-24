package com.example.administrator.huorongdai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.adapter.MyGridLayoutManager;
import com.example.administrator.huorongdai.base.BaseActivity;
import com.example.administrator.huorongdai.gsonbean.EarnListBean;
import com.example.administrator.huorongdai.xframe.adapter.XRecyclerViewAdapter;
import com.example.administrator.huorongdai.xframe.adapter.XViewHolder;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ReturnDetailActivity extends BaseActivity {

    private SwipeRefreshLayout refreshLayout;
    private TextView tvAmt;//投资金额
    private TextView tvArp;//年化利率
    private TextView tvYiAmt;//已收本金
    private TextView tvWeiAmt;//待收本金
    private TextView tvNumberType;
    private TextView tvExpect;//预计收益
    private TextView tvDone;//已收利息
    private TextView tvToBe;//待收利息
    private RecyclerView recyclerView;
    private MyAdapter adapter;

    private String investStatus;//9-收款中的投资，10-已结清的投资
    private String ivstAmt;//投资金额
    private String loanArp;//年化利率
    private String repayTypeName;//还款方式
    private String loanTitle;
    private ArrayList<EarnListBean> earnBeans;

    private SwipeRefreshLayout.OnRefreshListener refreshListener=new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            tvAmt.setText(ivstAmt+"元");
            tvArp.setText(loanArp+"%");

            if("9".equals(investStatus)){
                tvYiAmt.setText("0元");
                tvWeiAmt.setText(ivstAmt+"元");
            }else if("10".equals(investStatus)){
                tvWeiAmt.setText("0元");
                tvYiAmt.setText(ivstAmt+"元");
            }

            int doNum=0;//实际已回款数
            double expectMoney=0;//预计收益
            double doneMoney=0;//已收利息
            double toBeMoney=0;//待收利息
            for (EarnListBean bean:earnBeans){//earnStatus状态 1未还2已还
                expectMoney=expectMoney+bean.getEarnIint();
                if(bean.getEarnStatus()==1){
                    toBeMoney=toBeMoney+bean.getEarnIint();
                }else if(bean.getEarnStatus()==2){
                    doNum++;
                    doneMoney=doneMoney+bean.getEarnIint();
                }
            }
            DecimalFormat decimal=new DecimalFormat("0.00");
            tvExpect.setText(decimal.format(expectMoney)+"元");
            tvDone.setText(decimal.format(doneMoney)+"元");
            tvToBe.setText(decimal.format(toBeMoney)+"元");
            tvNumberType.setText(doNum+"/"+earnBeans.size()+"("+repayTypeName+")");

            if(refreshLayout.isRefreshing()){
                refreshLayout.setRefreshing(false);
            }
        }
    };

    @Override
    protected int getContentView() {
        return R.layout.activity_return_detail;
    }

    @Override
    protected void initView() {
        refreshLayout=findViewById(R.id.srl_return_detail);
        refreshLayout.setColorScheme(R.color.word_red);
        refreshLayout.setOnRefreshListener(refreshListener);
        tvAmt=findViewById(R.id.tv_amt);
        tvArp=findViewById(R.id.tv_arp);
        tvYiAmt=findViewById(R.id.tv_yi_amt);
        tvWeiAmt=findViewById(R.id.tv_wei_amt);
        tvNumberType=findViewById(R.id.tv_number_type);
        tvExpect=findViewById(R.id.tv_expect);
        tvDone=findViewById(R.id.tv_done);
        tvToBe=findViewById(R.id.tv_to_be);
        recyclerView=findViewById(R.id.rl_return_detail);

        Intent intent=getIntent();
        if(intent!=null){
            Bundle bundle=intent.getBundleExtra("bundle");
            investStatus=bundle.getString("investStatus");
            ivstAmt=bundle.getString("ivstAmt");
            loanArp=bundle.getString("loanArp");
            repayTypeName=bundle.getString("repayTypeName");
            loanTitle=bundle.getString("loanTitle");

            earnBeans=bundle.getParcelableArrayList("earnList");
        }

        setTitle(loanTitle);
        recyclerView.setLayoutManager(new MyGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        adapter=new MyAdapter(recyclerView,earnBeans);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void loadData() {
        refreshLayout.setRefreshing(true);
        refreshListener.onRefresh();
    }

    @Override
    public void onClick(View view) {

    }

    class MyAdapter extends XRecyclerViewAdapter<EarnListBean> {

        public MyAdapter(@NonNull RecyclerView mRecyclerView, List<EarnListBean> dataLists) {
            super(mRecyclerView, dataLists,R.layout.item_activity_return_detail);
        }

        @Override
        protected void bindData(XViewHolder holder, EarnListBean data, int position) {
            TextView tvDate=holder.getView(R.id.tv_date);//日期
            TextView tvYi=holder.getView(R.id.tv_yi);
            TextView tvCapital=holder.getView(R.id.tv_capital);//本金
            TextView tvIint=holder.getView(R.id.tv_iint);//利息

            String date=data.getShdEarnDate();
            date=date.substring(0,4)+"-"+date.substring(4,6)+"-"+date.substring(6,8);
            tvDate.setText(date);
            tvCapital.setText(data.getEarnCapital()+"");
            tvIint.setText("+"+data.getEarnIint());
            if(data.getEarnStatus()==1){
                tvYi.setVisibility(View.GONE);
            }
        }
    }
}
