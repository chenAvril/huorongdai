package com.example.administrator.huorongdai.gsonbean;

/**
 * Created by Administrator on 2018/3/27 0027.
 */

public class CustInfoBean {

    /**
     * customer : {"custOrigin":1,"custName":"hrd_15155118888","custPwd":"e10adc3949ba59abbe56e057f20f883e","nickname":"hrd_15155118888","custRealName":"温琼娇","custMobile":"15155118888","custEmail":"","emailAuth":1,"custLevel":1,"registTime":"20180326140557","registIp":"192.168.0.192","selfInviteCode":"cUNwC54y","introInviteCode":"","custStatus":2,"pcLoginStatus":1,"wapLoginStatus":1,"iosLoginStatus":1,"androidLoginStatus":1,"webchatLoginStatus":1,"errorCount":0,"cardType":"0","cardNum":"360302198709082021","openStatus":2,"custNum":"8d364423dc5f41c282413ffb68e83cb2","custType":1,"payPasswordStatus":2,"activateMember":1,"accountType":1,"tradePwd":"e10adc3949ba59abbe56e057f20f883e","updateMobile":"15155118888","id":"8d364423dc5f41c282413ffb68e83cb2"}
     * status : true
     * message :
     */

    private CustomerBean customer;
    private boolean status;
    private String message;

    public CustomerBean getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerBean customer) {
        this.customer = customer;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class CustomerBean {
        /**
         * custOrigin : 1
         * custName : hrd_15155118888
         * custPwd : e10adc3949ba59abbe56e057f20f883e
         * nickname : hrd_15155118888
         * custRealName : 温琼娇
         * custMobile : 15155118888
         * custEmail :
         * emailAuth : 1
         * custLevel : 1
         * registTime : 20180326140557
         * registIp : 192.168.0.192
         * selfInviteCode : cUNwC54y
         * introInviteCode :
         * custStatus : 2
         * pcLoginStatus : 1
         * wapLoginStatus : 1
         * iosLoginStatus : 1
         * androidLoginStatus : 1
         * webchatLoginStatus : 1
         * errorCount : 0
         * cardType : 0
         * cardNum : 360302198709082021
         * openStatus : 2
         * custNum : 8d364423dc5f41c282413ffb68e83cb2
         * custType : 1
         * payPasswordStatus : 2
         * activateMember : 1
         * accountType : 1
         * tradePwd : e10adc3949ba59abbe56e057f20f883e
         * updateMobile : 15155118888
         * id : 8d364423dc5f41c282413ffb68e83cb2
         */

        private int custOrigin;
        private String custName;
        private String custPwd;
        private String nickname;
        private String custRealName;
        private String custMobile;
        private String custEmail;
        private int emailAuth;
        private int custLevel;
        private String registTime;
        private String registIp;
        private String selfInviteCode;
        private String introInviteCode;
        private int custStatus;
        private int pcLoginStatus;
        private int wapLoginStatus;
        private int iosLoginStatus;
        private int androidLoginStatus;
        private int webchatLoginStatus;
        private int errorCount;
        private String cardType;
        private String cardNum;
        private int openStatus;
        private String custNum;
        private int custType;
        private int payPasswordStatus;
        private int activateMember;
        private int accountType;
        private String tradePwd;
        private String updateMobile;
        private String id;

        public int getCustOrigin() {
            return custOrigin;
        }

        public void setCustOrigin(int custOrigin) {
            this.custOrigin = custOrigin;
        }

        public String getCustName() {
            return custName;
        }

        public void setCustName(String custName) {
            this.custName = custName;
        }

        public String getCustPwd() {
            return custPwd;
        }

        public void setCustPwd(String custPwd) {
            this.custPwd = custPwd;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getCustRealName() {
            return custRealName;
        }

        public void setCustRealName(String custRealName) {
            this.custRealName = custRealName;
        }

        public String getCustMobile() {
            return custMobile;
        }

        public void setCustMobile(String custMobile) {
            this.custMobile = custMobile;
        }

        public String getCustEmail() {
            return custEmail;
        }

        public void setCustEmail(String custEmail) {
            this.custEmail = custEmail;
        }

        public int getEmailAuth() {
            return emailAuth;
        }

        public void setEmailAuth(int emailAuth) {
            this.emailAuth = emailAuth;
        }

        public int getCustLevel() {
            return custLevel;
        }

        public void setCustLevel(int custLevel) {
            this.custLevel = custLevel;
        }

        public String getRegistTime() {
            return registTime;
        }

        public void setRegistTime(String registTime) {
            this.registTime = registTime;
        }

        public String getRegistIp() {
            return registIp;
        }

        public void setRegistIp(String registIp) {
            this.registIp = registIp;
        }

        public String getSelfInviteCode() {
            return selfInviteCode;
        }

        public void setSelfInviteCode(String selfInviteCode) {
            this.selfInviteCode = selfInviteCode;
        }

        public String getIntroInviteCode() {
            return introInviteCode;
        }

        public void setIntroInviteCode(String introInviteCode) {
            this.introInviteCode = introInviteCode;
        }

        public int getCustStatus() {
            return custStatus;
        }

        public void setCustStatus(int custStatus) {
            this.custStatus = custStatus;
        }

        public int getPcLoginStatus() {
            return pcLoginStatus;
        }

        public void setPcLoginStatus(int pcLoginStatus) {
            this.pcLoginStatus = pcLoginStatus;
        }

        public int getWapLoginStatus() {
            return wapLoginStatus;
        }

        public void setWapLoginStatus(int wapLoginStatus) {
            this.wapLoginStatus = wapLoginStatus;
        }

        public int getIosLoginStatus() {
            return iosLoginStatus;
        }

        public void setIosLoginStatus(int iosLoginStatus) {
            this.iosLoginStatus = iosLoginStatus;
        }

        public int getAndroidLoginStatus() {
            return androidLoginStatus;
        }

        public void setAndroidLoginStatus(int androidLoginStatus) {
            this.androidLoginStatus = androidLoginStatus;
        }

        public int getWebchatLoginStatus() {
            return webchatLoginStatus;
        }

        public void setWebchatLoginStatus(int webchatLoginStatus) {
            this.webchatLoginStatus = webchatLoginStatus;
        }

        public int getErrorCount() {
            return errorCount;
        }

        public void setErrorCount(int errorCount) {
            this.errorCount = errorCount;
        }

        public String getCardType() {
            return cardType;
        }

        public void setCardType(String cardType) {
            this.cardType = cardType;
        }

        public String getCardNum() {
            return cardNum;
        }

        public void setCardNum(String cardNum) {
            this.cardNum = cardNum;
        }

        public int getOpenStatus() {
            return openStatus;
        }

        public void setOpenStatus(int openStatus) {
            this.openStatus = openStatus;
        }

        public String getCustNum() {
            return custNum;
        }

        public void setCustNum(String custNum) {
            this.custNum = custNum;
        }

        public int getCustType() {
            return custType;
        }

        public void setCustType(int custType) {
            this.custType = custType;
        }

        public int getPayPasswordStatus() {
            return payPasswordStatus;
        }

        public void setPayPasswordStatus(int payPasswordStatus) {
            this.payPasswordStatus = payPasswordStatus;
        }

        public int getActivateMember() {
            return activateMember;
        }

        public void setActivateMember(int activateMember) {
            this.activateMember = activateMember;
        }

        public int getAccountType() {
            return accountType;
        }

        public void setAccountType(int accountType) {
            this.accountType = accountType;
        }

        public String getTradePwd() {
            return tradePwd;
        }

        public void setTradePwd(String tradePwd) {
            this.tradePwd = tradePwd;
        }

        public String getUpdateMobile() {
            return updateMobile;
        }

        public void setUpdateMobile(String updateMobile) {
            this.updateMobile = updateMobile;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
