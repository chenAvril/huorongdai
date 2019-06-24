package com.example.administrator.huorongdai.gsonbean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2018/3/16 0016.
 */

public class UserBean {

    /**
     * customer : {"accountType":1,"activateMember":1,"activitySource":"baidu","androidLoginStatus":2,"custEmail":"","custLevel":1,"custMobile":"18110962902","custName":"hrd_18110962902","custOrigin":3,"custPwd":"917be34183d6425242a450399d0bc5df","custStatus":2,"custType":1,"emailAuth":1,"errorCount":0,"errorTime":"20180316115817","headIcon":"install/images/image/20180306/9e5fb69bf7574af496dabfaa40502451.jpg","id":"3a3b9ec3d48e421f80d98b64092d9ab8","introInviteCode":"","iosLoginStatus":1,"nickname":"hrd_18110962902","openStatus":4,"payPasswordStatus":1,"pcLoginStatus":1,"registIp":"36.60.228.38","registTime":"20180302101950","selfInviteCode":"qLnFZG1Z","wapLoginStatus":2,"webchatLoginStatus":1}
     * message : 请求参数非法
     * status : true
     */

    private CustomerBean customer;
    private String message;
    private boolean status;

    public CustomerBean getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerBean customer) {
        this.customer = customer;
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

    public static class CustomerBean implements Parcelable {

        @Override
        public String toString() {
            return "CustomerBean{" +
                    "accountType=" + accountType +
                    ", activateMember=" + activateMember +
                    ", activitySource='" + activitySource + '\'' +
                    ", androidLoginStatus=" + androidLoginStatus +
                    ", custEmail='" + custEmail + '\'' +
                    ", custLevel=" + custLevel +
                    ", custMobile='" + custMobile + '\'' +
                    ", custName='" + custName + '\'' +
                    ", custOrigin=" + custOrigin +
                    ", custPwd='" + custPwd + '\'' +
                    ", custStatus=" + custStatus +
                    ", custType=" + custType +
                    ", emailAuth=" + emailAuth +
                    ", errorCount=" + errorCount +
                    ", errorTime='" + errorTime + '\'' +
                    ", headIcon='" + headIcon + '\'' +
                    ", id='" + id + '\'' +
                    ", introInviteCode='" + introInviteCode + '\'' +
                    ", iosLoginStatus=" + iosLoginStatus +
                    ", nickname='" + nickname + '\'' +
                    ", openStatus=" + openStatus +
                    ", payPasswordStatus=" + payPasswordStatus +
                    ", pcLoginStatus=" + pcLoginStatus +
                    ", registIp='" + registIp + '\'' +
                    ", registTime='" + registTime + '\'' +
                    ", selfInviteCode='" + selfInviteCode + '\'' +
                    ", wapLoginStatus=" + wapLoginStatus +
                    ", webchatLoginStatus=" + webchatLoginStatus +
                    '}';
        }

        /**
         * accountType : 1
         * activateMember : 1
         * activitySource : baidu
         * androidLoginStatus : 2
         * custEmail :
         * custLevel : 1
         * custMobile : 18110962902
         * custName : hrd_18110962902
         * custOrigin : 3
         * custPwd : 917be34183d6425242a450399d0bc5df
         * custStatus : 2
         * custType : 1
         * emailAuth : 1
         * errorCount : 0
         * errorTime : 20180316115817
         * headIcon : install/images/image/20180306/9e5fb69bf7574af496dabfaa40502451.jpg
         * id : 3a3b9ec3d48e421f80d98b64092d9ab8
         * introInviteCode :
         * iosLoginStatus : 1
         * nickname : hrd_18110962902
         * openStatus : 4
         * payPasswordStatus : 1
         * pcLoginStatus : 1
         * registIp : 36.60.228.38
         * registTime : 20180302101950
         * selfInviteCode : qLnFZG1Z
         * wapLoginStatus : 2
         * webchatLoginStatus : 1
         */

        private int accountType;
        private int activateMember;
        private String activitySource;
        private int androidLoginStatus;
        private String custEmail;
        private int custLevel;
        private String custMobile;
        private String custName;
        private int custOrigin;
        private String custPwd;
        private int custStatus;
        private int custType;
        private int emailAuth;
        private int errorCount;
        private String errorTime;
        private String headIcon;
        private String id;
        private String introInviteCode;
        private int iosLoginStatus;
        private String nickname;
        private int openStatus;
        private int payPasswordStatus;
        private int pcLoginStatus;
        private String registIp;
        private String registTime;
        private String selfInviteCode;
        private int wapLoginStatus;
        private int webchatLoginStatus;

        public int getAccountType() {
            return accountType;
        }

        public void setAccountType(int accountType) {
            this.accountType = accountType;
        }

        public int getActivateMember() {
            return activateMember;
        }

        public void setActivateMember(int activateMember) {
            this.activateMember = activateMember;
        }

        public String getActivitySource() {
            return activitySource;
        }

        public void setActivitySource(String activitySource) {
            this.activitySource = activitySource;
        }

        public int getAndroidLoginStatus() {
            return androidLoginStatus;
        }

        public void setAndroidLoginStatus(int androidLoginStatus) {
            this.androidLoginStatus = androidLoginStatus;
        }

        public String getCustEmail() {
            return custEmail;
        }

        public void setCustEmail(String custEmail) {
            this.custEmail = custEmail;
        }

        public int getCustLevel() {
            return custLevel;
        }

        public void setCustLevel(int custLevel) {
            this.custLevel = custLevel;
        }

        public String getCustMobile() {
            return custMobile;
        }

        public void setCustMobile(String custMobile) {
            this.custMobile = custMobile;
        }

        public String getCustName() {
            return custName;
        }

        public void setCustName(String custName) {
            this.custName = custName;
        }

        public int getCustOrigin() {
            return custOrigin;
        }

        public void setCustOrigin(int custOrigin) {
            this.custOrigin = custOrigin;
        }

        public String getCustPwd() {
            return custPwd;
        }

        public void setCustPwd(String custPwd) {
            this.custPwd = custPwd;
        }

        public int getCustStatus() {
            return custStatus;
        }

        public void setCustStatus(int custStatus) {
            this.custStatus = custStatus;
        }

        public int getCustType() {
            return custType;
        }

        public void setCustType(int custType) {
            this.custType = custType;
        }

        public int getEmailAuth() {
            return emailAuth;
        }

        public void setEmailAuth(int emailAuth) {
            this.emailAuth = emailAuth;
        }

        public int getErrorCount() {
            return errorCount;
        }

        public void setErrorCount(int errorCount) {
            this.errorCount = errorCount;
        }

        public String getErrorTime() {
            return errorTime;
        }

        public void setErrorTime(String errorTime) {
            this.errorTime = errorTime;
        }

        public String getHeadIcon() {
            return headIcon;
        }

        public void setHeadIcon(String headIcon) {
            this.headIcon = headIcon;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIntroInviteCode() {
            return introInviteCode;
        }

        public void setIntroInviteCode(String introInviteCode) {
            this.introInviteCode = introInviteCode;
        }

        public int getIosLoginStatus() {
            return iosLoginStatus;
        }

        public void setIosLoginStatus(int iosLoginStatus) {
            this.iosLoginStatus = iosLoginStatus;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getOpenStatus() {
            return openStatus;
        }

        public void setOpenStatus(int openStatus) {
            this.openStatus = openStatus;
        }

        public int getPayPasswordStatus() {
            return payPasswordStatus;
        }

        public void setPayPasswordStatus(int payPasswordStatus) {
            this.payPasswordStatus = payPasswordStatus;
        }

        public int getPcLoginStatus() {
            return pcLoginStatus;
        }

        public void setPcLoginStatus(int pcLoginStatus) {
            this.pcLoginStatus = pcLoginStatus;
        }

        public String getRegistIp() {
            return registIp;
        }

        public void setRegistIp(String registIp) {
            this.registIp = registIp;
        }

        public String getRegistTime() {
            return registTime;
        }

        public void setRegistTime(String registTime) {
            this.registTime = registTime;
        }

        public String getSelfInviteCode() {
            return selfInviteCode;
        }

        public void setSelfInviteCode(String selfInviteCode) {
            this.selfInviteCode = selfInviteCode;
        }

        public int getWapLoginStatus() {
            return wapLoginStatus;
        }

        public void setWapLoginStatus(int wapLoginStatus) {
            this.wapLoginStatus = wapLoginStatus;
        }

        public int getWebchatLoginStatus() {
            return webchatLoginStatus;
        }

        public void setWebchatLoginStatus(int webchatLoginStatus) {
            this.webchatLoginStatus = webchatLoginStatus;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.accountType);
            dest.writeInt(this.activateMember);
            dest.writeString(this.activitySource);
            dest.writeInt(this.androidLoginStatus);
            dest.writeString(this.custEmail);
            dest.writeInt(this.custLevel);
            dest.writeString(this.custMobile);
            dest.writeString(this.custName);
            dest.writeInt(this.custOrigin);
            dest.writeString(this.custPwd);
            dest.writeInt(this.custStatus);
            dest.writeInt(this.custType);
            dest.writeInt(this.emailAuth);
            dest.writeInt(this.errorCount);
            dest.writeString(this.errorTime);
            dest.writeString(this.headIcon);
            dest.writeString(this.id);
            dest.writeString(this.introInviteCode);
            dest.writeInt(this.iosLoginStatus);
            dest.writeString(this.nickname);
            dest.writeInt(this.openStatus);
            dest.writeInt(this.payPasswordStatus);
            dest.writeInt(this.pcLoginStatus);
            dest.writeString(this.registIp);
            dest.writeString(this.registTime);
            dest.writeString(this.selfInviteCode);
            dest.writeInt(this.wapLoginStatus);
            dest.writeInt(this.webchatLoginStatus);
        }

        public CustomerBean() {
        }

        protected CustomerBean(Parcel in) {
            this.accountType = in.readInt();
            this.activateMember = in.readInt();
            this.activitySource = in.readString();
            this.androidLoginStatus = in.readInt();
            this.custEmail = in.readString();
            this.custLevel = in.readInt();
            this.custMobile = in.readString();
            this.custName = in.readString();
            this.custOrigin = in.readInt();
            this.custPwd = in.readString();
            this.custStatus = in.readInt();
            this.custType = in.readInt();
            this.emailAuth = in.readInt();
            this.errorCount = in.readInt();
            this.errorTime = in.readString();
            this.headIcon = in.readString();
            this.id = in.readString();
            this.introInviteCode = in.readString();
            this.iosLoginStatus = in.readInt();
            this.nickname = in.readString();
            this.openStatus = in.readInt();
            this.payPasswordStatus = in.readInt();
            this.pcLoginStatus = in.readInt();
            this.registIp = in.readString();
            this.registTime = in.readString();
            this.selfInviteCode = in.readString();
            this.wapLoginStatus = in.readInt();
            this.webchatLoginStatus = in.readInt();
        }

        public static final Parcelable.Creator<CustomerBean> CREATOR = new Parcelable.Creator<CustomerBean>() {
            @Override
            public CustomerBean createFromParcel(Parcel source) {
                return new CustomerBean(source);
            }

            @Override
            public CustomerBean[] newArray(int size) {
                return new CustomerBean[size];
            }
        };
    }
}
