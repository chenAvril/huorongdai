package com.example.administrator.huorongdai.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.example.administrator.huorongdai.Path;
import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.base.BaseActivity;
import com.example.administrator.huorongdai.gsonbean.NoticeDetailBean;
import com.example.administrator.huorongdai.view.MyScrollView;
import com.example.administrator.huorongdai.xframe.utils.XEmptyUtils;
import com.example.administrator.huorongdai.xframe.utils.XNetworkUtils;
import com.example.administrator.huorongdai.xframe.utils.http.HttpCallBack;
import com.example.administrator.huorongdai.xframe.utils.http.XHttp;
import com.example.administrator.huorongdai.xframe.widget.loadingview.XLoadingView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class NoticeDetailActivity extends BaseActivity {

    private TextView tvNoticeTitle;//通知标题
    private TextView tvPublishTime;//通知发布时间
    private TextView tvCont;//通知内容
    private String notice_id;
    private XLoadingView xLoadingView;
    private MyScrollView scrollView;
    private SwipeRefreshLayout refreshLayout;

    private Handler handler=new Handler(new MyCallback());
    private final class MyCallback implements Handler.Callback{

        @Override
        public boolean handleMessage(Message message) {
            if (XNetworkUtils.isConnected()){
                requsetNotice();
            }else {
                if(refreshLayout.isRefreshing()){
                    refreshLayout.setRefreshing(false);
                }
                xLoadingView.showNoNetwork();
            }
            return true;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //如果参数为null的话，会将所有的Callbacks和Messages全部清除掉。
        handler.removeCallbacksAndMessages( null );
    }

    private SwipeRefreshLayout.OnRefreshListener refreshListener=new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            handler.sendMessage(Message.obtain());
        }
    };

    @Override
    protected int getContentView() {
        return R.layout.activity_notice_detail;
    }

    @Override
    protected void initView() {
        setTitle("公告详情");
        Intent intent=getIntent();
        if(intent!=null) {
            notice_id = intent.getStringExtra("notice_id");
        }

        xLoadingView=findViewById(R.id.xloading_view);
        scrollView=findViewById(R.id.sl_notice_detail);
        refreshLayout=findViewById(R.id.srl_notice_detail);
        refreshLayout.setColorScheme(R.color.word_red);
        refreshLayout.setOnRefreshListener(refreshListener);
        tvNoticeTitle=findViewById(R.id.tv_notice_title);
        tvPublishTime=findViewById(R.id.tv_publish_time);
        tvCont=findViewById(R.id.tv_cont);
        xLoadingView.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xLoadingView.showLoading();
                handler.sendMessageDelayed(Message.obtain(),600);
            }
        });

        //解决swiperefreshlayout与scrollview的冲突问题
        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if (refreshLayout != null) {
                    refreshLayout.setEnabled(scrollView.getScrollY() == 0);
                }
            }
        });
    }

    @Override
    protected void loadData() {
        refreshLayout.setRefreshing(true);
        refreshListener.onRefresh();
    }

    private void requsetNotice(){
        if(!XEmptyUtils.isEmpty(notice_id)){
            Map<String,Object > params =new HashMap<>();
            params .put("id",notice_id);
            XHttp.obtain().post(Path.notice_detail, params, new HttpCallBack<NoticeDetailBean>() {
                @Override
                public void onSuccess(NoticeDetailBean detailBean) {
                    if(detailBean.isStatus()){
                        String noticeTitle=detailBean.getNotice().getNoticeTitle();
                        String publishTime=detailBean.getNotice().getAddTime();
                        String cont=detailBean.getNotice().getNoticeCont();

                        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddhhmmss");
                        Date date = null;
                        try {
                            date = sdf1.parse(publishTime);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                        publishTime= sdf2.format(date);
                        tvNoticeTitle.setText(noticeTitle);
                        tvPublishTime.setText(publishTime);
                        tvCont.setText(Html.fromHtml(cont));

                        xLoadingView.showContent();
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
    }

    @Override
    public void onClick(View view) {

    }
}
