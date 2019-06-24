package com.example.administrator.huorongdai.activity;

import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.base.BaseActivity;

public class MyMessageActivity extends BaseActivity implements View.OnTouchListener {
    private RelativeLayout webSiteMsgRl;//站内信
    private TextView webSiteMsgTv;
    private RelativeLayout mediaReportRl;//媒体报道
    private TextView mediaReportTv;
    private RelativeLayout huorongStateRl;//货融动态
    private TextView huorongStateTv;
    private RelativeLayout huorongColumnRl;//货融专栏
    private TextView huorongColumnTv;

    @Override
    protected int getContentView() {
        return R.layout.activity_my_message;
    }

    @Override
    protected void initView() {
        setTitle("货融资讯");
        webSiteMsgRl=findViewById(R.id.rl_web_site_msg);
        webSiteMsgTv=findViewById(R.id.tv_web_site_msg);
        mediaReportRl=findViewById(R.id.rl_media_report);
        mediaReportTv=findViewById(R.id.tv_media_report);
        huorongStateRl=findViewById(R.id.rl_huorong_state);
        huorongStateTv=findViewById(R.id.tv_huorong_state);
        huorongColumnRl=findViewById(R.id.rl_huorong_column);
        huorongColumnTv=findViewById(R.id.tv_huorong_column);

        webSiteMsgRl.setOnClickListener(this);
        webSiteMsgRl.setOnTouchListener(this);
        mediaReportRl.setOnClickListener(this);
        mediaReportRl.setOnTouchListener(this);
        huorongStateRl.setOnClickListener(this);
        huorongStateRl.setOnTouchListener(this);
        huorongColumnRl.setOnClickListener(this);
        huorongColumnRl.setOnTouchListener(this);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_web_site_msg://站内信
                startActivity(new Intent(this,WebSiteMsgActivity.class));
                break;
            case R.id.rl_media_report://媒体报道
                Intent intent1=new Intent(this,NewsListActivity.class);
                intent1.putExtra("title","媒体报道");
                intent1.putExtra("newsType","meitibaodao");
                startActivity(intent1);
                break;
            case R.id.rl_huorong_state://货融专栏
                Intent intent2=new Intent(this,NewsListActivity.class);
                intent2.putExtra("title","货融动态");
                intent2.putExtra("newsType","huijinzhuanlan");
                startActivity(intent2);
                break;
            case R.id.rl_huorong_column://货融动态
                Intent intent3=new Intent(this,NewsListActivity.class);
                intent3.putExtra("title","货融专栏");
                intent3.putExtra("newsType","huijindongtai");
                startActivity(intent3);
                break;
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (view.getId()){
            case R.id.rl_web_site_msg://站内信
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        webSiteMsgTv.setTextColor(getResources().getColor(R.color.word_red));
                        break;
                    case MotionEvent.ACTION_UP:
                        webSiteMsgTv.setTextColor(getResources().getColor(R.color.word_dark));
                        break;
                }
                break;
            case R.id.rl_media_report://媒体报道
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        mediaReportTv.setTextColor(getResources().getColor(R.color.word_red));
                        break;
                    case MotionEvent.ACTION_UP:
                        mediaReportTv.setTextColor(getResources().getColor(R.color.word_dark));
                        break;
                }
                break;
            case R.id.rl_huorong_state://货融动态
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        huorongStateTv.setTextColor(getResources().getColor(R.color.word_red));
                        break;
                    case MotionEvent.ACTION_UP:
                        huorongStateTv.setTextColor(getResources().getColor(R.color.word_dark));
                        break;
                }
                break;
            case R.id.rl_huorong_column://货融专栏
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        huorongColumnTv.setTextColor(getResources().getColor(R.color.word_red));
                        break;
                    case MotionEvent.ACTION_UP:
                        huorongColumnTv.setTextColor(getResources().getColor(R.color.word_dark));
                        break;
                }
                break;
        }
        return false;
    }
}
