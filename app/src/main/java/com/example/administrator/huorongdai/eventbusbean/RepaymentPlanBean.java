package com.example.administrator.huorongdai.eventbusbean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2018/5/10 0010.
 */

public class RepaymentPlanBean implements Parcelable {

    private String rpmtIssue;//期数
    private String rpmtIint;//应还利息
    private String rpmtCapital;//应还本金
    private String shdRpmtDateStr;//应还日期
    private String rpmtStatus;//状态  1未还2已还

    public String getRpmtIssue() {
        return rpmtIssue;
    }

    public void setRpmtIssue(String rpmtIssue) {
        this.rpmtIssue = rpmtIssue;
    }

    public String getRpmtIint() {
        return rpmtIint;
    }

    public void setRpmtIint(String rpmtIint) {
        this.rpmtIint = rpmtIint;
    }

    public String getRpmtCapital() {
        return rpmtCapital;
    }

    public void setRpmtCapital(String rpmtCapital) {
        this.rpmtCapital = rpmtCapital;
    }

    public String getShdRpmtDateStr() {
        return shdRpmtDateStr;
    }

    public void setShdRpmtDateStr(String shdRpmtDateStr) {
        this.shdRpmtDateStr = shdRpmtDateStr;
    }

    public String getRpmtStatus() {
        return rpmtStatus;
    }

    public void setRpmtStatus(String rpmtStatus) {
        this.rpmtStatus = rpmtStatus;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.rpmtIssue);
        dest.writeString(this.rpmtIint);
        dest.writeString(this.rpmtCapital);
        dest.writeString(this.shdRpmtDateStr);
        dest.writeString(this.rpmtStatus);
    }

    public RepaymentPlanBean() {
    }

    protected RepaymentPlanBean(Parcel in) {
        this.rpmtIssue = in.readString();
        this.rpmtIint = in.readString();
        this.rpmtCapital = in.readString();
        this.shdRpmtDateStr = in.readString();
        this.rpmtStatus = in.readString();
    }

    public static final Parcelable.Creator<RepaymentPlanBean> CREATOR = new Parcelable.Creator<RepaymentPlanBean>() {
        @Override
        public RepaymentPlanBean createFromParcel(Parcel source) {
            return new RepaymentPlanBean(source);
        }

        @Override
        public RepaymentPlanBean[] newArray(int size) {
            return new RepaymentPlanBean[size];
        }
    };
}
