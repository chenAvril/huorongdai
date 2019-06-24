package com.example.administrator.huorongdai.gsonbean;

/**
 * Created by Administrator on 2018/6/7 0007.
 */

public class QueryLockBean {

    /**
     * imeitype : 2
     * imei : 090807060504030201
     * message : 查询成功
     * status : true
     */

    private int imeitype;//手势密码类型   1 未设置 2 已设置
    private String imei;
    private String message;
    private boolean status;

    public int getImeitype() {
        return imeitype;
    }

    public void setImeitype(int imeitype) {
        this.imeitype = imeitype;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
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
