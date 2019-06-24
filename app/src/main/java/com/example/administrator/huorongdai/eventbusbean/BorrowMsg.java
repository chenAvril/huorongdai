package com.example.administrator.huorongdai.eventbusbean;

/**
 * Created by Administrator on 2018/5/22 0022.
 */

public class BorrowMsg {
    public BorrowMsg(boolean isRefresh) {
        this.isRefresh = isRefresh;
    }

    public boolean isRefresh() {
        return isRefresh;
    }

    public void setRefresh(boolean refresh) {
        isRefresh = refresh;
    }

    private boolean isRefresh;
}
