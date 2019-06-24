package com.example.administrator.huorongdai.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.base.BaseActivity;

public class BorrowSuccessActivity extends BaseActivity {

    private TextView tvApplyNo;

    @Override
    protected int getContentView() {
        return R.layout.activity_borrow_success;
    }

    @Override
    protected void initView() {
        setTitle("申请借款成功");
        tvApplyNo=findViewById(R.id.tv_borrow_apply_no);
        Intent intent=getIntent();
        if(intent!=null){
            tvApplyNo.setText(intent.getStringExtra("applyNo"));
        }
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View view) {

    }
}
