package com.example.administrator.huorongdai.view;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * Created by Administrator on 2018/3/22 0022.
 */

public class MyCountDownTimer extends CountDownTimer {
    private TextView mBtn;
    private int mEnable;
    private int mDisable;

    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    public MyCountDownTimer(long millisInFuture, long countDownInterval, TextView btn) {
        super(millisInFuture, countDownInterval);
        mBtn = btn;
//        mEnable = enable;
//        mDisable = disable;
    }

    //计时过程
    @Override
    public void onTick(long l) {
        //防止计时过程中重复点击
        mBtn.setClickable(false);
        mBtn.setText(l / 1000 + "s后重发");
        //设置按钮背景色
        mBtn.setTextColor(Color.parseColor("#757575"));
    }

    //计时完毕的方法
    @Override
    public void onFinish() {
        //重新给Button设置文字
        mBtn.setText("获取验证码");
        //设置可点击
        mBtn.setClickable(true);
        //设置按钮背景色
        mBtn.setTextColor(Color.parseColor("#e24d4f"));
    }
}
