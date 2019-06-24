package com.example.administrator.huorongdai.activity;

import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class TouziManagerActivity extends BaseActivity implements View.OnTouchListener {

    private RelativeLayout bidRecordRl;//投标记录
    private TextView tvRecord;
    private RelativeLayout returnMoneyRl;//回款计划
    private TextView tvMoney;

    @Override
    protected int getContentView() {
        return R.layout.activity_tuozi_manager;
    }

    @Override
    protected void initView() {
        setTitle("投资管理");
        bidRecordRl=findViewById(R.id.rl_touzi_bid_record);
        tvRecord=findViewById(R.id.tv_touzi_bid_record);
        returnMoneyRl=findViewById(R.id.rl_touzi_return_money);
        tvMoney=findViewById(R.id.tv_touzi_return_money);

        bidRecordRl.setOnTouchListener(this);
        bidRecordRl.setOnClickListener(this);
        returnMoneyRl.setOnClickListener(this);
        returnMoneyRl.setOnTouchListener(this);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_touzi_bid_record://投标记录
                Intent intent1=new Intent(this,BidRecordActivity.class);
                startActivity(intent1);
                break;
            case R.id.rl_touzi_return_money://回款计划
                Intent intent2=new Intent(this,ReturnMoneyActivity.class);
                startActivity(intent2);
                break;
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (view.getId()){
            case R.id.rl_touzi_bid_record://投标记录
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        tvRecord.setTextColor(getResources().getColor(R.color.word_red));
                        break;
                    case MotionEvent.ACTION_UP:
                        tvRecord.setTextColor(getResources().getColor(R.color.word_dark));
                        break;
                }
                break;
            case R.id.rl_touzi_return_money://回款计划
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        tvMoney.setTextColor(getResources().getColor(R.color.word_red));
                        break;
                    case MotionEvent.ACTION_UP:
                        tvMoney.setTextColor(getResources().getColor(R.color.word_dark));
                        break;
                }
                break;
        }
        return false;
    }
}
