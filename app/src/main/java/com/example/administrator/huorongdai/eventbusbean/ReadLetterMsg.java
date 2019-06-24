package com.example.administrator.huorongdai.eventbusbean;

/**
 * Created by Administrator on 2018/4/8 0008.
 */

public class ReadLetterMsg {
    private int status;//1阅读成功
    private int position;

    public ReadLetterMsg(int status, int position) {
        this.status = status;
        this.position = position;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
