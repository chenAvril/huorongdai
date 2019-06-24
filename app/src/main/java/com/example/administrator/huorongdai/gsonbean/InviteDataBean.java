package com.example.administrator.huorongdai.gsonbean;

/**
 * Created by Administrator on 2018/4/10 0010.
 */

public class InviteDataBean {

    /**
     * inviteCustNum : 7
     * inviteRegistRedpAmt : 0
     * inviteIvstRedpAmt : 0
     * invitePoint : 0
     * status : true
     */

    private int inviteCustNum;
    private double inviteRegistRedpAmt;
    private double inviteIvstRedpAmt;
    private int invitePoint;
    private boolean status;

    public int getInviteCustNum() {
        return inviteCustNum;
    }

    public void setInviteCustNum(int inviteCustNum) {
        this.inviteCustNum = inviteCustNum;
    }

    public double getInviteRegistRedpAmt() {
        return inviteRegistRedpAmt;
    }

    public void setInviteRegistRedpAmt(double inviteRegistRedpAmt) {
        this.inviteRegistRedpAmt = inviteRegistRedpAmt;
    }

    public double getInviteIvstRedpAmt() {
        return inviteIvstRedpAmt;
    }

    public void setInviteIvstRedpAmt(double inviteIvstRedpAmt) {
        this.inviteIvstRedpAmt = inviteIvstRedpAmt;
    }

    public int getInvitePoint() {
        return invitePoint;
    }

    public void setInvitePoint(int invitePoint) {
        this.invitePoint = invitePoint;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
