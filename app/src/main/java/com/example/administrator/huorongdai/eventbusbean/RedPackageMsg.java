package com.example.administrator.huorongdai.eventbusbean;

/**
 * Created by Administrator on 2018/4/11 0011.
 */

public class RedPackageMsg {
    private String id;
    private double redpAmt;//金额
    private String redpTypeName;//类型
    private int useRedpMinAmt;//起投金额

    public RedPackageMsg() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getRedpAmt() {
        return redpAmt;
    }

    public void setRedpAmt(double redpAmt) {
        this.redpAmt = redpAmt;
    }

    public String getRedpTypeName() {
        return redpTypeName;
    }

    public void setRedpTypeName(String redpTypeName) {
        this.redpTypeName = redpTypeName;
    }

    public int getUseRedpMinAmt() {
        return useRedpMinAmt;
    }

    public void setUseRedpMinAmt(int useRedpMinAmt) {
        this.useRedpMinAmt = useRedpMinAmt;
    }

    @Override
    public String toString() {
        return "RedPackageMsg{" +
                "id='" + id + '\'' +
                ", redpAmt=" + redpAmt +
                ", redpTypeName='" + redpTypeName + '\'' +
                ", useRedpMinAmt=" + useRedpMinAmt +
                '}';
    }
}
