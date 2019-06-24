package com.example.administrator.huorongdai.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.huorongdai.Path;
import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.gsonbean.HuodongBean;
import com.example.administrator.huorongdai.xframe.adapter.XRecyclerViewAdapter;
import com.example.administrator.huorongdai.xframe.adapter.XViewHolder;
import com.example.administrator.huorongdai.xframe.utils.XEmptyUtils;
import com.example.administrator.huorongdai.xframe.utils.imageload.XImage;

import java.util.List;

/**
 * Created by Administrator on 2018/3/15 0015.
 */

public class HuodongAdapter extends XRecyclerViewAdapter<HuodongBean.ListBean> {
    public HuodongAdapter(@NonNull RecyclerView mRecyclerView, List<HuodongBean.ListBean> dataLists) {
        super(mRecyclerView, dataLists, R.layout.item_huodong);
    }

    @Override
    protected void bindData(XViewHolder holder, HuodongBean.ListBean data, int position) {
        LinearLayout ll=holder.getView(R.id.ll_huodong_item);
        ImageView ivDone=holder.getView(R.id.iv_huodong_item_done);
        ImageView imageView=holder.getView(R.id.iv_huodong_item);
        TextView tvName = holder.getView(R.id.tv_huodong_item_name);
        TextView tvTime = holder.getView(R.id.tv_huodong_item_time);

        XImage.getInstance().load(imageView, Path.BASE_ImgURL+data.getActivesImage(),R.mipmap.icon404);
        tvName.setText(data.getActivesName());

        //activesStatus:1-进行中 2-已结束 3-预启用
        String time="";
        if(1==data.getActivesStatus()){
            ivDone.setVisibility(View.GONE);
            ll.setVisibility(View.GONE);
            time= XEmptyUtils.isEmpty(data.getStartTime())?"":data.getStartTime();
        }else if(2==data.getActivesStatus()){
            ivDone.setVisibility(View.VISIBLE);
            ll.setVisibility(View.VISIBLE);
            time=(XEmptyUtils.isEmpty(data.getStartTime())?"":data.getStartTime())+"至"+(XEmptyUtils.isEmpty(data.getEndTime())?"":data.getEndTime());
        }

        tvTime.setText(time);
    }
}
