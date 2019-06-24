package com.example.administrator.huorongdai.gsonbean;

/**
 * Created by Administrator on 2018/4/10 0010.
 */

public class InviteFriendBean {

    /**
     * calAmt : 1080
     * customer : {"accountType":1,"androidLoginStatus":2,"cardNum":"340122198807130520","cardType":"0","custEmail":"511854180@qq.com","custLevel":1,"custMobile":"18375333610","custName":"hrd_18375333610","custNum":"9e41bd6f0f594fb6ad9d89d48d075786","custOrigin":1,"custPwd":"e10adc3949ba59abbe56e057f20f883e","custRealName":"杨永平","custStatus":2,"custType":1,"emailAuth":2,"errorCount":0,"errorTime":"20180327102106","evalTime":"20170729104505","headIcon":"install/images/image/20180409/70a15931209a4662bf0be61505040459.jpg","id":"9e41bd6f0f594fb6ad9d89d48d075786","introInviteCode":"13195319919","inviteFlag":1,"inviteMethod":2,"iosLoginStatus":1,"nickname":"hrd_18375333610","payPasswordStatus":2,"pcLoginStatus":2,"registIp":"120.209.233.179","registTime":"20170714160617","selectedEvalopts":"b087c562cb144ee3823bc8782e99aa75,f3666b7247534c56a071016102707f96,94940db288a0458ab303252bf41d67ef,373bb2bd50454cff9670b1e0222c3514,4395ea7b24fd499b804969f69a0703e3,e397612853054b1d8c219e96fa22bd3a,2e41b8233b1b498db99b33f07ad65f18,61f5128663ff4d04a74e260575b367c0","selfInviteCode":"18375333610","totalEvalScore":29,"tradePwd":"7c86e393c4e54748ac52566000620938","updateMobile":"18375333610","wapLoginStatus":2,"webchatLoginStatus":1}
     * inviteCertPointStr : 200
     * inviteRedpAmtStr : 20
     * ivstRewardExpireStr : 3
     * ivstRewardRateStr : 4
     * minIvstAmt : 2000
     * mobileDes : be19f53c762e0301d5dd2337050781c3
     * status : true
     */

    private int calAmt;
    private CustomerBean customer;
    private String inviteCertPointStr;
    private String inviteRedpAmtStr;
    private String ivstRewardExpireStr;
    private String ivstRewardRateStr;
    private int minIvstAmt;
    private String mobileDes;
    private boolean status;

    public int getCalAmt() {
        return calAmt;
    }

    public void setCalAmt(int calAmt) {
        this.calAmt = calAmt;
    }

    public CustomerBean getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerBean customer) {
        this.customer = customer;
    }

    public String getInviteCertPointStr() {
        return inviteCertPointStr;
    }

    public void setInviteCertPointStr(String inviteCertPointStr) {
        this.inviteCertPointStr = inviteCertPointStr;
    }

    public String getInviteRedpAmtStr() {
        return inviteRedpAmtStr;
    }

    public void setInviteRedpAmtStr(String inviteRedpAmtStr) {
        this.inviteRedpAmtStr = inviteRedpAmtStr;
    }

    public String getIvstRewardExpireStr() {
        return ivstRewardExpireStr;
    }

    public void setIvstRewardExpireStr(String ivstRewardExpireStr) {
        this.ivstRewardExpireStr = ivstRewardExpireStr;
    }

    public String getIvstRewardRateStr() {
        return ivstRewardRateStr;
    }

    public void setIvstRewardRateStr(String ivstRewardRateStr) {
        this.ivstRewardRateStr = ivstRewardRateStr;
    }

    public int getMinIvstAmt() {
        return minIvstAmt;
    }

    public void setMinIvstAmt(int minIvstAmt) {
        this.minIvstAmt = minIvstAmt;
    }

    public String getMobileDes() {
        return mobileDes;
    }

    public void setMobileDes(String mobileDes) {
        this.mobileDes = mobileDes;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public static class CustomerBean {
        /**
         * accountType : 1
         * androidLoginStatus : 2
         * cardNum : 340122198807130520
         * cardType : 0
         * custEmail : 511854180@qq.com
         * custLevel : 1
         * custMobile : 18375333610
         * custName : hrd_18375333610
         * custNum : 9e41bd6f0f594fb6ad9d89d48d075786
         * custOrigin : 1
         * custPwd : e10adc3949ba59abbe56e057f20f883e
         * custRealName : 杨永平
         * custStatus : 2
         * custType : 1
         * emailAuth : 2
         * errorCount : 0
         * errorTime : 20180327102106
         * evalTime : 20170729104505
         * headIcon : install/images/image/20180409/70a15931209a4662bf0be61505040459.jpg
         * id : 9e41bd6f0f594fb6ad9d89d48d075786
         * introInviteCode : 13195319919
         * inviteFlag : 1
         * inviteMethod : 2
         * iosLoginStatus : 1
         * nickname : hrd_18375333610
         * payPasswordStatus : 2
         * pcLoginStatus : 2
         * registIp : 120.209.233.179
         * registTime : 20170714160617
         * selectedEvalopts : b087c562cb144ee3823bc8782e99aa75,f3666b7247534c56a071016102707f96,94940db288a0458ab303252bf41d67ef,373bb2bd50454cff9670b1e0222c3514,4395ea7b24fd499b804969f69a0703e3,e397612853054b1d8c219e96fa22bd3a,2e41b8233b1b498db99b33f07ad65f18,61f5128663ff4d04a74e260575b367c0
         * selfInviteCode : 18375333610
         * totalEvalScore : 29
         * tradePwd : 7c86e393c4e54748ac52566000620938
         * updateMobile : 18375333610
         * wapLoginStatus : 2
         * webchatLoginStatus : 1
         */

        private int accountType;
        private int androidLoginStatus;
        private String cardNum;
        private String cardType;
        private String custEmail;
        private int custLevel;
        private String custMobile;
        private String custName;
        private String custNum;
        private int custOrigin;
        private String custPwd;
        private String custRealName;
        private int custStatus;
        private int custType;
        private int emailAuth;
        private int errorCount;
        private String errorTime;
        private String evalTime;
        private String headIcon;
        private String id;
        private String introInviteCode;
        private int inviteFlag;
        private int inviteMethod;
        private int iosLoginStatus;
        private String nickname;
        private int payPasswordStatus;
        private int pcLoginStatus;
        private String registIp;
        private String registTime;
        private String selectedEvalopts;
        private String selfInviteCode;
        private int totalEvalScore;
        private String tradePwd;
        private String updateMobile;
        private int wapLoginStatus;
        private int webchatLoginStatus;

        public int getAccountType() {
            return accountType;
        }

        public void setAccountType(int accountType) {
            this.accountType = accountType;
        }

        public int getAndroidLoginStatus() {
            return androidLoginStatus;
        }

        public void setAndroidLoginStatus(int androidLoginStatus) {
            this.androidLoginStatus = androidLoginStatus;
        }

        public String getCardNum() {
            return cardNum;
        }

        public void setCardNum(String cardNum) {
            this.cardNum = cardNum;
        }

        public String getCardType() {
            return cardType;
        }

        public void setCardType(String cardType) {
            this.cardType = cardType;
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

        public String getCustNum() {
            return custNum;
        }

        public void setCustNum(String custNum) {
            this.custNum = custNum;
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

        public String getCustRealName() {
            return custRealName;
        }

        public void setCustRealName(String custRealName) {
            this.custRealName = custRealName;
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

        public String getEvalTime() {
            return evalTime;
        }

        public void setEvalTime(String evalTime) {
            this.evalTime = evalTime;
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

        public int getInviteFlag() {
            return inviteFlag;
        }

        public void setInviteFlag(int inviteFlag) {
            this.inviteFlag = inviteFlag;
        }

        public int getInviteMethod() {
            return inviteMethod;
        }

        public void setInviteMethod(int inviteMethod) {
            this.inviteMethod = inviteMethod;
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

        public String getSelectedEvalopts() {
            return selectedEvalopts;
        }

        public void setSelectedEvalopts(String selectedEvalopts) {
            this.selectedEvalopts = selectedEvalopts;
        }

        public String getSelfInviteCode() {
            return selfInviteCode;
        }

        public void setSelfInviteCode(String selfInviteCode) {
            this.selfInviteCode = selfInviteCode;
        }

        public int getTotalEvalScore() {
            return totalEvalScore;
        }

        public void setTotalEvalScore(int totalEvalScore) {
            this.totalEvalScore = totalEvalScore;
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
    }
}
