package com.example.administrator.huorongdai.gsonbean;

/**
 * Created by Administrator on 2018/3/29 0029.
 */

public class AccountInfo {


    /**
     * unEarnCaptial : 0.0
     * frzAmt : 0.00
     * unEarnIint : 0.0
     * availAmt : 0.00
     * status : true
     */

    private double unEarnCaptial;
    private String frzAmt;
    private double unEarnIint;
    private String availAmt;
    private boolean status;

    public double getUnEarnCaptial() {
        return unEarnCaptial;
    }

    public void setUnEarnCaptial(double unEarnCaptial) {
        this.unEarnCaptial = unEarnCaptial;
    }

    public String getFrzAmt() {
        return frzAmt;
    }

    public void setFrzAmt(String frzAmt) {
        this.frzAmt = frzAmt;
    }

    public double getUnEarnIint() {
        return unEarnIint;
    }

    public void setUnEarnIint(double unEarnIint) {
        this.unEarnIint = unEarnIint;
    }

    public String getAvailAmt() {
        return availAmt;
    }

    public void setAvailAmt(String availAmt) {
        this.availAmt = availAmt;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
