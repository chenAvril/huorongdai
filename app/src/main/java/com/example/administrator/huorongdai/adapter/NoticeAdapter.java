package com.example.administrator.huorongdai.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.eventbusbean.NoticeModel;
import com.example.administrator.huorongdai.view.noticeview.BaseBannerAdapter;
import com.example.administrator.huorongdai.view.noticeview.VerticalBannerView;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

import java.util.List;

/**
 * Created by Administrator on 2018/3/14 0014.
 */

public class NoticeAdapter extends BaseBannerAdapter<NoticeModel> {

    private List<NoticeModel> mDatas;

    public NoticeAdapter(List<NoticeModel> datas) {
        super(datas);
    }

    @Override
    public View getView(VerticalBannerView parent) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notice,null);
        ScreenAdapterTools.getInstance().loadView(view);
        return view;
    }

    @Override
    public void setItem(final View view, final NoticeModel data) {
        TextView tv = view.findViewById(R.id.tv_notice_title);
        tv.setText(data.title);
        //你可以增加点击事件
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //比如打开url
                onItemClickListener.onItemClick(v,data);
            }
        });
    }

    //自定义 回调    点击item
    public interface  OnItemClickListener{
        //参数1   点击了那个item  参数2 当前的item的位置
        void onItemClick(View view,NoticeModel  data);
    }

    private OnItemClickListener onItemClickListener;
    //在Activity中调用此方法
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}
