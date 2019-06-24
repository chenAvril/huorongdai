package com.example.administrator.huorongdai.eventbusbean;

/**
 * Created by Administrator on 2018/3/12 0012.
 * flag=1：刷新
 */

public class SmallMicroMsg {
    private int flag;

    public SmallMicroMsg(int flag) {
        this.flag = flag;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
