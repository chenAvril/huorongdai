package com.example.administrator.huorongdai.eventbusbean;

/**
 * Created by Administrator on 2018/3/23 0023.
 */

public class ForgetPswMsg {
    //密码重新设置成功后，通知找回密码页面finish
    private int flag;

    public ForgetPswMsg(int flag) {
        this.flag = flag;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
