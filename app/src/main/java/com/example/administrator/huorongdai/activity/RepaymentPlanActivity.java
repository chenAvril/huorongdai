package com.example.administrator.huorongdai.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.adapter.MyGridLayoutManager;
import com.example.administrator.huorongdai.base.BaseActivity;
import com.example.administrator.huorongdai.eventbusbean.RepaymentPlanBean;
import com.example.administrator.huorongdai.xframe.adapter.XRecyclerViewAdapter;
import com.example.administrator.huorongdai.xframe.adapter.XViewHolder;
import com.example.administrator.huorongdai.xframe.adapter.decoration.DividerDecoration;

import java.util.ArrayList;

//还款计划
public class RepaymentPlanActivity extends BaseActivity {

    private String loanStatus;
    private ArrayList<RepaymentPlanBean> planBeans;//还款计划数据
    private RecyclerView recyclerView;
    private MyAdapter adapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_repayment_plan;
    }

    @Override
    protected void initView() {
        recyclerView=findViewById(R.id.repayment_plan_recyclerview);
        setTitle("还款计划");

        Intent intent=getIntent();
        if(intent!=null){
            //7-招标中 8-待满标 9-待划转  10-划款中11-划款失败 12-待还款 13-已还清 14-已流标 15-已过期
            loanStatus=intent.getStringExtra("loanStatus");
            planBeans=intent.getBundleExtra("plan").getParcelableArrayList("repayment_plan");
            DividerDecoration decoration=new DividerDecoration(R.color.gray_bg,1);
            decoration.setDrawLastItem(false);
            recyclerView.addItemDecoration(decoration);
            recyclerView.setLayoutManager(new MyGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
            adapter=new MyAdapter(recyclerView,planBeans);
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View view) {

    }

    class MyAdapter extends XRecyclerViewAdapter<RepaymentPlanBean> {

        public MyAdapter(@NonNull RecyclerView mRecyclerView, ArrayList<RepaymentPlanBean> dataLists) {
            super(mRecyclerView, dataLists,R.layout.item_repayment_plan_activity);
        }

        @Override
        protected void bindData(XViewHolder holder, RepaymentPlanBean data, int position) {
            TextView tvIssue=holder.getView(R.id.tv_item_rpmt_issue);//还款期数
            TextView tvCapital=holder.getView(R.id.tv_item_rpmt_capital);//本金
            TextView tvIint=holder.getView(R.id.tv_item_rpmt_iint);//应还利息
            TextView tvDate=holder.getView(R.id.tv_item_rpmt_time);//还款时间

            String capital=data.getRpmtCapital();
            if(capital.contains(".")){
                capital=capital.substring(0,capital.indexOf("."));
            }
            tvCapital.setText("¥"+capital);
            tvIssue.setText(data.getRpmtIssue());
            tvIint.setText("¥"+data.getRpmtIint());

            String date=data.getShdRpmtDateStr();
            //date=date.substring(0,date.indexOf(" "));
            tvDate.setText(date);
        }
    }
}
