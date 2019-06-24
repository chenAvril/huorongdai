package com.example.administrator.huorongdai.gsonbean;

/**
 * Created by Administrator on 2018/4/14 0014.
 */

public class PointsTotalBean {

    /**
     * avalPoint : 614755
     * message : 用户不合法
     * status : true
     * totalPoint : 2303997
     * usedPoint : 1689242
     */

    private int avalPoint;
    private String message;
    private boolean status;
    private int totalPoint;
    private int usedPoint;

    public int getAvalPoint() {
        return avalPoint;
    }

    public void setAvalPoint(int avalPoint) {
        this.avalPoint = avalPoint;
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

    public int getTotalPoint() {
        return totalPoint;
    }

    public void setTotalPoint(int totalPoint) {
        this.totalPoint = totalPoint;
    }

    public int getUsedPoint() {
        return usedPoint;
    }

    public void setUsedPoint(int usedPoint) {
        this.usedPoint = usedPoint;
    }
}
