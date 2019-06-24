package com.example.administrator.huorongdai.gsonbean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2018/5/20 0020.
 */

public class EarnListBean implements Parcelable {
    private String custId;
    private double earnCapital;//本金
    private double earnIint;//利息
    private int earnIssue;//期数
    private int earnStatus;//状态 1未还2已还
    private String id;
    private String ivstId;
    private double lateFine;
    private double lateIint;
    private String loanId;
    private String orderNum;
    private int platfFee;
    private String realEanDate;
    private String shdEarnDate;


    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public double getEarnCapital() {
        return earnCapital;
    }

    public void setEarnCapital(double earnCapital) {
        this.earnCapital = earnCapital;
    }

    public double getEarnIint() {
        return earnIint;
    }

    public void setEarnIint(double earnIint) {
        this.earnIint = earnIint;
    }

    public int getEarnIssue() {
        return earnIssue;
    }

    public void setEarnIssue(int earnIssue) {
        this.earnIssue = earnIssue;
    }

    public int getEarnStatus() {
        return earnStatus;
    }

    public void setEarnStatus(int earnStatus) {
        this.earnStatus = earnStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIvstId() {
        return ivstId;
    }

    public void setIvstId(String ivstId) {
        this.ivstId = ivstId;
    }

    public double getLateFine() {
        return lateFine;
    }

    public void setLateFine(double lateFine) {
        this.lateFine = lateFine;
    }

    public double getLateIint() {
        return lateIint;
    }

    public void setLateIint(double lateIint) {
        this.lateIint = lateIint;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public int getPlatfFee() {
        return platfFee;
    }

    public void setPlatfFee(int platfFee) {
        this.platfFee = platfFee;
    }

    public String getRealEanDate() {
        return realEanDate;
    }

    public void setRealEanDate(String realEanDate) {
        this.realEanDate = realEanDate;
    }

    public String getShdEarnDate() {
        return shdEarnDate;
    }

    public void setShdEarnDate(String shdEarnDate) {
        this.shdEarnDate = shdEarnDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.custId);
        dest.writeDouble(this.earnCapital);
        dest.writeDouble(this.earnIint);
        dest.writeInt(this.earnIssue);
        dest.writeInt(this.earnStatus);
        dest.writeString(this.id);
        dest.writeString(this.ivstId);
        dest.writeDouble(this.lateFine);
        dest.writeDouble(this.lateIint);
        dest.writeString(this.loanId);
        dest.writeString(this.orderNum);
        dest.writeInt(this.platfFee);
        dest.writeString(this.realEanDate);
        dest.writeString(this.shdEarnDate);
    }

    public EarnListBean() {
    }

    protected EarnListBean(Parcel in) {
        this.custId = in.readString();
        this.earnCapital = in.readDouble();
        this.earnIint = in.readDouble();
        this.earnIssue = in.readInt();
        this.earnStatus = in.readInt();
        this.id = in.readString();
        this.ivstId = in.readString();
        this.lateFine = in.readDouble();
        this.lateIint = in.readDouble();
        this.loanId = in.readString();
        this.orderNum = in.readString();
        this.platfFee = in.readInt();
        this.realEanDate = in.readString();
        this.shdEarnDate = in.readString();
    }

    public static final Parcelable.Creator<EarnListBean> CREATOR = new Parcelable.Creator<EarnListBean>() {
        @Override
        public EarnListBean createFromParcel(Parcel source) {
            return new EarnListBean(source);
        }

        @Override
        public EarnListBean[] newArray(int size) {
            return new EarnListBean[size];
        }
    };


    @Override
    public String toString() {
        return "EarnListBean{" +
                "custId='" + custId + '\'' +
                ", earnCapital=" + earnCapital +
                ", earnIint=" + earnIint +
                ", earnIssue=" + earnIssue +
                ", earnStatus=" + earnStatus +
                ", id='" + id + '\'' +
                ", ivstId='" + ivstId + '\'' +
                ", lateFine=" + lateFine +
                ", lateIint=" + lateIint +
                ", loanId='" + loanId + '\'' +
                ", orderNum='" + orderNum + '\'' +
                ", platfFee=" + platfFee +
                ", realEanDate='" + realEanDate + '\'' +
                ", shdEarnDate='" + shdEarnDate + '\'' +
                '}';
    }
}
