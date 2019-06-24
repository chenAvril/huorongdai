package com.example.administrator.huorongdai.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.gsonbean.LoanAllBean;
import com.example.administrator.huorongdai.view.circlepercentview.CirclePercentBar;
import com.example.administrator.huorongdai.xframe.adapter.XRecyclerViewAdapter;
import com.example.administrator.huorongdai.xframe.adapter.XViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/5/8 0008.
 */

public class SmallMicroAdaper extends XRecyclerViewAdapter<LoanAllBean.LoanListBean> {
    private DecelerateInterpolator decelerateInterpolator=new DecelerateInterpolator();

    public SmallMicroAdaper(@NonNull RecyclerView mRecyclerView, List<LoanAllBean.LoanListBean> dataLists) {
        super(mRecyclerView, dataLists, R.layout.item_small_micro);
    }

    @Override
    public XViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    protected void bindData(XViewHolder holder, LoanAllBean.LoanListBean data, int position) {
        TextView tvProName= holder.getView(R.id.tv_pro_name);
        LinearLayout llCanBe=holder.getView(R.id.ll_can_be);
        holder.setText(R.id.tv_loan_title,data.getLoanTitle());
        holder.setText(R.id.tv_loan_arp,data.getLoanArp()+"");
        holder.setText(R.id.tv_loan_expiry,data.getLoanExpiry()+"");
        holder.setText(R.id.tv_expiry_unitStr,data.getExpiryUnitStr());
        tvProName.setText(data.getProduName());

        CirclePercentBar barRed=holder.getView(R.id.circle_bar_red);
        CirclePercentBar barGray=holder.getView(R.id.circle_bar_gray);

        long loadAmt=data.getLoanAmt();
        long totalInvestMoney =data.getTotalInvestMoney();
        String money=String.valueOf(loadAmt-totalInvestMoney);
//        if(money.length()>3){
//            StringBuffer stringBuffer=new StringBuffer(money);
//            stringBuffer.insert(money.length()-3,",");
//            money=stringBuffer.toString();
//        }
        holder.setText(R.id.tv_can_be_cast,money);//可投金额
        float num=((float)totalInvestMoney/loadAmt)*100;
        //7-招标中 8-待满标 9-待划转  10-划款中11-划款失败 12-待还款 13-已还清 14-已流标 15-已过期
        if(7==data.getLoanStatus()||8==data.getLoanStatus()){
            barRed.setVisibility(View.VISIBLE);
            barGray.setVisibility(View.GONE);
            barRed.setPercentData(num,decelerateInterpolator);
            if(7==data.getLoanStatus()){
                llCanBe.setVisibility(View.VISIBLE);
                barRed.setText("立即投资");
            }else if(8==data.getLoanStatus()){
                barRed.setText("已满标");
                llCanBe.setVisibility(View.GONE);
            }
        }else {
            llCanBe.setVisibility(View.GONE);
            barRed.setVisibility(View.GONE);
            barGray.setVisibility(View.VISIBLE);
            barGray.setPercentData(num,decelerateInterpolator);
            //9-待划转  10-划款中11-划款失败 12-待还款 13-已还清 14-已流标 15-已过期
            if(9==data.getLoanStatus()){
                barGray.setText("待划转");
            }else if(10==data.getLoanStatus()){
                barGray.setText("还款中");
            }else if(11==data.getLoanStatus()){
                barGray.setText("划款失败");
            }else if(12==data.getLoanStatus()){
                barGray.setText("还款中");
            }else if(13==data.getLoanStatus()){
                barGray.setText("已还清");
            }else if(14==data.getLoanStatus()){
                barGray.setText("已流标");
            }else if(15==data.getLoanStatus()){
                barGray.setText("已过期");
            }
        }
    }
}

