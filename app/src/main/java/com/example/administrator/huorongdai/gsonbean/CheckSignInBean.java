package com.example.administrator.huorongdai.gsonbean;

/**
 * Created by Administrator on 2018/3/29 0029.
 */

public class CheckSignInBean {

    /**
     * weekDay : 周四
     * message : 未签到
     * status : false
     */

    private String weekDay;
    private String message;
    private boolean status;

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
