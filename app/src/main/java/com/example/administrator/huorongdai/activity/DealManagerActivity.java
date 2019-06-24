package com.example.administrator.huorongdai.activity;

import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.base.BaseActivity;

public class DealManagerActivity extends BaseActivity implements View.OnTouchListener {

    private RelativeLayout moneyRecordRl;//充值提现
    private TextView tvMoney;
    private RelativeLayout dealRecordRl;//交易记录
    private TextView tvDeal;

    @Override
    protected int getContentView() {
        return R.layout.activity_deal_manager;
    }

    @Override
    protected void initView() {
        setTitle("交易管理");
        moneyRecordRl=findViewById(R.id.rl_deal_money_packet);
        tvMoney=findViewById(R.id.tv_deal_money_packet);
        dealRecordRl=findViewById(R.id.rl_deal_record);
        tvDeal=findViewById(R.id.tv_deal_record);

        moneyRecordRl.setOnClickListener(this);
        moneyRecordRl.setOnTouchListener(this);
        dealRecordRl.setOnClickListener(this);
        dealRecordRl.setOnTouchListener(this);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_deal_money_packet://充值提现
                Intent intent1=new Intent(this,MoneyRecordActivity.class);
                startActivity(intent1);
                break;
            case R.id.rl_deal_record://交易记录
                Intent intent2=new Intent(this,DealRecordActivity.class);
                startActivity(intent2);
                break;
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (view.getId()){
            case R.id.rl_deal_money_packet://充值提现
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        tvMoney.setTextColor(getResources().getColor(R.color.word_red));
                        break;
                    case MotionEvent.ACTION_UP:
                        tvMoney.setTextColor(getResources().getColor(R.color.word_dark));
                        break;
                }
                break;
            case R.id.rl_deal_record://交易记录
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        tvDeal.setTextColor(getResources().getColor(R.color.word_red));
                        break;
                    case MotionEvent.ACTION_UP:
                        tvDeal.setTextColor(getResources().getColor(R.color.word_dark));
                        break;
                }
                break;
        }
        return false;
    }
}
