package com.example.administrator.huorongdai.fragment;


import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.TextView;

import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.adapter.MyGridLayoutManager;
import com.example.administrator.huorongdai.base.LazyLoadFragment;
import com.example.administrator.huorongdai.xframe.adapter.XRecyclerViewAdapter;
import com.example.administrator.huorongdai.xframe.adapter.XViewHolder;
import com.example.administrator.huorongdai.xframe.adapter.decoration.DividerDecoration;
import com.example.administrator.huorongdai.xframe.utils.XEmptyUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class RepaymentPlanFragment extends LazyLoadFragment {

    public RepaymentPlanFragment(){

    }

    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private List<Map<String, String>> arrayList=new ArrayList<>();
    private String amt;//总金额=本金

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_repayment_plan;
    }

    @Override
    protected void initViews() {
        recyclerView=findViewById(R.id.repayment_plan_recyclerview);
        recyclerView.addItemDecoration(new DividerDecoration(R.color.light_gray,1));
        recyclerView.setLayoutManager(new MyGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        adapter=new MyAdapter(recyclerView,arrayList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void lazyLoad() {

    }

    public void setData(List<Map<String, String>> arrayList,String amt){
        if(XEmptyUtils.isEmpty(adapter)){
           return;
        }
        if(adapter.getItemCount()>0){
            adapter.clear();
        }
        adapter.addAll(arrayList);
        if(!XEmptyUtils.isEmpty(amt)){
            amt=amt.substring(0,amt.length()-3);
            //this.amt="¥"+amt+",000元";
            this.amt="¥"+amt+"000元";
        }
    }

    class MyAdapter extends XRecyclerViewAdapter<Map<String,String>> {


        public MyAdapter(@NonNull RecyclerView mRecyclerView, List<Map<String, String>> dataLists) {
            super(mRecyclerView, dataLists,R.layout.item_repayment_plan_rl);
        }

        @Override
        protected void bindData(XViewHolder holder, Map<String, String> data, int position) {
            TextView tvIssue=holder.getView(R.id.tv_item_rpmt_issue);//还款期数
            TextView tvIint=holder.getView(R.id.tv_item_rpmt_iint);//应还利息
            TextView tvCapital=holder.getView(R.id.tv_item_rpmt_capital);//应还本金
            TextView tvRemainingMoney=holder.getView(R.id.tv_remaining_money);//剩余本经

            tvIssue.setText(data.get("rpmtIssue"));
            tvIint.setText("¥"+data.get("rpmtIint"));

            String rpmtCapital=data.get("rpmtCapital");//应还本金
            if(rpmtCapital.endsWith("000")){
                rpmtCapital=rpmtCapital.substring(0,rpmtCapital.length()-3);
                //tvCapital.setText("¥"+rpmtCapital+",000元");
                tvCapital.setText("¥"+rpmtCapital+"000元");
            }else if("0.0".equals(rpmtCapital)){
                tvCapital.setText("¥"+rpmtCapital+"0");
            }else {
                tvCapital.setText("¥"+rpmtCapital);
            }

            if(position==adapter.getItemCount()-1){
                tvRemainingMoney.setText("¥0.00");
            }else {
                tvRemainingMoney.setText(amt);
            }
        }
    }
}
