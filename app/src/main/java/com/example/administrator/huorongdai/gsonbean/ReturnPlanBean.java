package com.example.administrator.huorongdai.gsonbean;

/**
 * Created by Administrator on 2018/5/21 0021.
 */

public class ReturnPlanBean {

    /**
     * returnedMoneyDetail : {"custId":"12e3c1be7c634c50a96acca264ff9eeb","earnCapital":100000,"earnIint":916.67,"earnIssue":6,"earnStatus":1,"id":"b3cecde2c11447e89a9c3ba334287c37","invest":{"custId":"12e3c1be7c634c50a96acca264ff9eeb","id":"de7112bed2824357b3d51f71e1f7bf56","ivstAmt":100000,"ivstMode":1,"ivstOrderNum":"201805141028196rwBTqIu","ivstSerialNum":"000072883579","ivstSource":1,"ivstStatus":9,"ivstTime":"20180514102819","ivstType":1,"loanId":"09d21dd0aa344d9ba3787f92e7af5087","moveCount":0},"ivstId":"de7112bed2824357b3d51f71e1f7bf56","lateFine":0,"lateIint":0,"loan":{"addTime":"20180514101508","addUser":"66b465710ad143ac9e7301671d7facb8","advanceApply":0,"autoRepay":0,"borCard":"91340100348869698H","borrower":"安徽校星科技有限公司","contractNum":"ZB20180514101508084","endTime":"20180515101703","endTimeMill":1526350623000,"expiryUnit":1,"fullLoanAduitUser":"e927a4b42d454b7a96028b359952fa94","fullLoanAuditOpinion":"通过","fullLoanAuditResult":1,"fullLoanAuditTime":"20180514102904","id":"09d21dd0aa344d9ba3787f92e7af5087","loanAddRate":0,"loanAmt":200000,"loanArp":11,"loanAttri":"","loanCust":"06ccdec8b97a451eaae0d28ca98a3cd9","loanDesc":"<p>测试&nbsp;<\/p>","loanExpiry":180,"loanIcon":"","loanIdentity":"f154e6cb586047628922567e945475cc","loanRealAmt":200000,"loanStatus":12,"loanTitle":"测试还款计划test011","loanUse":"测试","maxIvst":200000,"minIvst":100,"newLoan":2,"pointCustList":"","produId":"ba7d375889bf4d5394e03f65b4474a40","realPlatfFee":0,"releaseTime":"20180514101703","remIvstAmt":0,"rpmtType":"53ba38b3aa45484382295c5357c40466","shdPlatfFee":986.3,"sinaLoanStatus":1,"startTime":"20180514101618","startTimeMill":1526264178000,"totalInvestMoney":200000,"transfTime":"20180514102922","transfUser":"e927a4b42d454b7a96028b359952fa94"},"loanId":"09d21dd0aa344d9ba3787f92e7af5087","orderNum":"20180514102917YRWXhhaS12","platfFee":0,"shdEarnDate":"20181110000000"}
     * status : true
     */

    private ReturnedMoneyDetailBean returnedMoneyDetail;
    private boolean status;

    public ReturnedMoneyDetailBean getReturnedMoneyDetail() {
        return returnedMoneyDetail;
    }

    public void setReturnedMoneyDetail(ReturnedMoneyDetailBean returnedMoneyDetail) {
        this.returnedMoneyDetail = returnedMoneyDetail;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public static class ReturnedMoneyDetailBean {
        /**
         * custId : 12e3c1be7c634c50a96acca264ff9eeb
         * earnCapital : 100000
         * earnIint : 916.67
         * earnIssue : 6
         * earnStatus : 1
         * id : b3cecde2c11447e89a9c3ba334287c37
         * invest : {"custId":"12e3c1be7c634c50a96acca264ff9eeb","id":"de7112bed2824357b3d51f71e1f7bf56","ivstAmt":100000,"ivstMode":1,"ivstOrderNum":"201805141028196rwBTqIu","ivstSerialNum":"000072883579","ivstSource":1,"ivstStatus":9,"ivstTime":"20180514102819","ivstType":1,"loanId":"09d21dd0aa344d9ba3787f92e7af5087","moveCount":0}
         * ivstId : de7112bed2824357b3d51f71e1f7bf56
         * lateFine : 0
         * lateIint : 0
         * loan : {"addTime":"20180514101508","addUser":"66b465710ad143ac9e7301671d7facb8","advanceApply":0,"autoRepay":0,"borCard":"91340100348869698H","borrower":"安徽校星科技有限公司","contractNum":"ZB20180514101508084","endTime":"20180515101703","endTimeMill":1526350623000,"expiryUnit":1,"fullLoanAduitUser":"e927a4b42d454b7a96028b359952fa94","fullLoanAuditOpinion":"通过","fullLoanAuditResult":1,"fullLoanAuditTime":"20180514102904","id":"09d21dd0aa344d9ba3787f92e7af5087","loanAddRate":0,"loanAmt":200000,"loanArp":11,"loanAttri":"","loanCust":"06ccdec8b97a451eaae0d28ca98a3cd9","loanDesc":"<p>测试&nbsp;<\/p>","loanExpiry":180,"loanIcon":"","loanIdentity":"f154e6cb586047628922567e945475cc","loanRealAmt":200000,"loanStatus":12,"loanTitle":"测试还款计划test011","loanUse":"测试","maxIvst":200000,"minIvst":100,"newLoan":2,"pointCustList":"","produId":"ba7d375889bf4d5394e03f65b4474a40","realPlatfFee":0,"releaseTime":"20180514101703","remIvstAmt":0,"rpmtType":"53ba38b3aa45484382295c5357c40466","shdPlatfFee":986.3,"sinaLoanStatus":1,"startTime":"20180514101618","startTimeMill":1526264178000,"totalInvestMoney":200000,"transfTime":"20180514102922","transfUser":"e927a4b42d454b7a96028b359952fa94"}
         * loanId : 09d21dd0aa344d9ba3787f92e7af5087
         * orderNum : 20180514102917YRWXhhaS12
         * platfFee : 0
         * shdEarnDate : 20181110000000
         */

        private String custId;
        private int earnCapital;//投资金额
        private double earnIint;//投资利息
        private int earnIssue;//期数
        private int earnStatus;
        private String id;
        private InvestBean invest;
        private String ivstId;
        private int lateFine;
        private double lateIint;//逾期罚息
        private LoanBean loan;
        private String loanId;
        private String orderNum;
        private int platfFee;
        private String shdEarnDate;
        private String realEanDate;
        private String netShdEarnDate;

        public String getNetShdEarnDate() {
            return netShdEarnDate;
        }

        public void setNetShdEarnDate(String netShdEarnDate) {
            this.netShdEarnDate = netShdEarnDate;
        }

        public String getCustId() {
            return custId;
        }

        public void setCustId(String custId) {
            this.custId = custId;
        }

        public int getEarnCapital() {
            return earnCapital;
        }

        public void setEarnCapital(int earnCapital) {
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

        public InvestBean getInvest() {
            return invest;
        }

        public void setInvest(InvestBean invest) {
            this.invest = invest;
        }

        public String getIvstId() {
            return ivstId;
        }

        public void setIvstId(String ivstId) {
            this.ivstId = ivstId;
        }

        public int getLateFine() {
            return lateFine;
        }

        public void setLateFine(int lateFine) {
            this.lateFine = lateFine;
        }

        public double getLateIint() {
            return lateIint;
        }

        public void setLateIint(double lateIint) {
            this.lateIint = lateIint;
        }

        public LoanBean getLoan() {
            return loan;
        }

        public void setLoan(LoanBean loan) {
            this.loan = loan;
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

        public String getShdEarnDate() {
            return shdEarnDate;
        }

        public void setShdEarnDate(String shdEarnDate) {
            this.shdEarnDate = shdEarnDate;
        }
        public String getRealEanDate() {
            return realEanDate;
        }

        public void setRealEanDate(String realEanDate) {
            this.realEanDate = realEanDate;
        }

        public static class InvestBean {
            /**
             * custId : 12e3c1be7c634c50a96acca264ff9eeb
             * id : de7112bed2824357b3d51f71e1f7bf56
             * ivstAmt : 100000
             * ivstMode : 1
             * ivstOrderNum : 201805141028196rwBTqIu
             * ivstSerialNum : 000072883579
             * ivstSource : 1
             * ivstStatus : 9
             * ivstTime : 20180514102819
             * ivstType : 1
             * loanId : 09d21dd0aa344d9ba3787f92e7af5087
             * moveCount : 0
             */

            private String custId;
            private String id;
            private int ivstAmt;
            private int ivstMode;
            private String ivstOrderNum;
            private String ivstSerialNum;
            private int ivstSource;
            private int ivstStatus;
            private String ivstTime;
            private int ivstType;
            private String loanId;
            private int moveCount;

            public String getCustId() {
                return custId;
            }

            public void setCustId(String custId) {
                this.custId = custId;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public int getIvstAmt() {
                return ivstAmt;
            }

            public void setIvstAmt(int ivstAmt) {
                this.ivstAmt = ivstAmt;
            }

            public int getIvstMode() {
                return ivstMode;
            }

            public void setIvstMode(int ivstMode) {
                this.ivstMode = ivstMode;
            }

            public String getIvstOrderNum() {
                return ivstOrderNum;
            }

            public void setIvstOrderNum(String ivstOrderNum) {
                this.ivstOrderNum = ivstOrderNum;
            }

            public String getIvstSerialNum() {
                return ivstSerialNum;
            }

            public void setIvstSerialNum(String ivstSerialNum) {
                this.ivstSerialNum = ivstSerialNum;
            }

            public int getIvstSource() {
                return ivstSource;
            }

            public void setIvstSource(int ivstSource) {
                this.ivstSource = ivstSource;
            }

            public int getIvstStatus() {
                return ivstStatus;
            }

            public void setIvstStatus(int ivstStatus) {
                this.ivstStatus = ivstStatus;
            }

            public String getIvstTime() {
                return ivstTime;
            }

            public void setIvstTime(String ivstTime) {
                this.ivstTime = ivstTime;
            }

            public int getIvstType() {
                return ivstType;
            }

            public void setIvstType(int ivstType) {
                this.ivstType = ivstType;
            }

            public String getLoanId() {
                return loanId;
            }

            public void setLoanId(String loanId) {
                this.loanId = loanId;
            }

            public int getMoveCount() {
                return moveCount;
            }

            public void setMoveCount(int moveCount) {
                this.moveCount = moveCount;
            }
        }

        public static class LoanBean {
            /**
             * addTime : 20180514101508
             * addUser : 66b465710ad143ac9e7301671d7facb8
             * advanceApply : 0
             * autoRepay : 0
             * borCard : 91340100348869698H
             * borrower : 安徽校星科技有限公司
             * contractNum : ZB20180514101508084
             * endTime : 20180515101703
             * endTimeMill : 1526350623000
             * expiryUnit : 1
             * fullLoanAduitUser : e927a4b42d454b7a96028b359952fa94
             * fullLoanAuditOpinion : 通过
             * fullLoanAuditResult : 1
             * fullLoanAuditTime : 20180514102904
             * id : 09d21dd0aa344d9ba3787f92e7af5087
             * loanAddRate : 0
             * loanAmt : 200000
             * loanArp : 11
             * loanAttri :
             * loanCust : 06ccdec8b97a451eaae0d28ca98a3cd9
             * loanDesc : <p>测试&nbsp;</p>
             * loanExpiry : 180
             * loanIcon :
             * loanIdentity : f154e6cb586047628922567e945475cc
             * loanRealAmt : 200000
             * loanStatus : 12
             * loanTitle : 测试还款计划test011
             * loanUse : 测试
             * maxIvst : 200000
             * minIvst : 100
             * newLoan : 2
             * pointCustList :
             * produId : ba7d375889bf4d5394e03f65b4474a40
             * realPlatfFee : 0
             * releaseTime : 20180514101703
             * remIvstAmt : 0
             * rpmtType : 53ba38b3aa45484382295c5357c40466
             * shdPlatfFee : 986.3
             * sinaLoanStatus : 1
             * startTime : 20180514101618
             * startTimeMill : 1526264178000
             * totalInvestMoney : 200000
             * transfTime : 20180514102922
             * transfUser : e927a4b42d454b7a96028b359952fa94
             */

            private String addTime;
            private String addUser;
            private int advanceApply;
            private int autoRepay;
            private String borCard;
            private String borrower;
            private String contractNum;
            private String endTime;
            private long endTimeMill;
            private int expiryUnit;
            private String fullLoanAduitUser;
            private String fullLoanAuditOpinion;
            private int fullLoanAuditResult;
            private String fullLoanAuditTime;
            private String id;
            private int loanAddRate;
            private int loanAmt;
            private double loanArp;
            private String loanAttri;
            private String loanCust;
            private String loanDesc;
            private int loanExpiry;
            private String loanIcon;
            private String loanIdentity;
            private int loanRealAmt;
            private int loanStatus;
            private String loanTitle;
            private String loanUse;
            private int maxIvst;
            private int minIvst;
            private int newLoan;
            private String pointCustList;
            private String produId;
            private double realPlatfFee;
            private String releaseTime;
            private int remIvstAmt;
            private String rpmtType;
            private double shdPlatfFee;
            private int sinaLoanStatus;
            private String startTime;
            private long startTimeMill;
            private int totalInvestMoney;
            private String transfTime;
            private String transfUser;

            public String getAddTime() {
                return addTime;
            }

            public void setAddTime(String addTime) {
                this.addTime = addTime;
            }

            public String getAddUser() {
                return addUser;
            }

            public void setAddUser(String addUser) {
                this.addUser = addUser;
            }

            public int getAdvanceApply() {
                return advanceApply;
            }

            public void setAdvanceApply(int advanceApply) {
                this.advanceApply = advanceApply;
            }

            public int getAutoRepay() {
                return autoRepay;
            }

            public void setAutoRepay(int autoRepay) {
                this.autoRepay = autoRepay;
            }

            public String getBorCard() {
                return borCard;
            }

            public void setBorCard(String borCard) {
                this.borCard = borCard;
            }

            public String getBorrower() {
                return borrower;
            }

            public void setBorrower(String borrower) {
                this.borrower = borrower;
            }

            public String getContractNum() {
                return contractNum;
            }

            public void setContractNum(String contractNum) {
                this.contractNum = contractNum;
            }

            public String getEndTime() {
                return endTime;
            }

            public void setEndTime(String endTime) {
                this.endTime = endTime;
            }

            public long getEndTimeMill() {
                return endTimeMill;
            }

            public void setEndTimeMill(long endTimeMill) {
                this.endTimeMill = endTimeMill;
            }

            public int getExpiryUnit() {
                return expiryUnit;
            }

            public void setExpiryUnit(int expiryUnit) {
                this.expiryUnit = expiryUnit;
            }

            public String getFullLoanAduitUser() {
                return fullLoanAduitUser;
            }

            public void setFullLoanAduitUser(String fullLoanAduitUser) {
                this.fullLoanAduitUser = fullLoanAduitUser;
            }

            public String getFullLoanAuditOpinion() {
                return fullLoanAuditOpinion;
            }

            public void setFullLoanAuditOpinion(String fullLoanAuditOpinion) {
                this.fullLoanAuditOpinion = fullLoanAuditOpinion;
            }

            public int getFullLoanAuditResult() {
                return fullLoanAuditResult;
            }

            public void setFullLoanAuditResult(int fullLoanAuditResult) {
                this.fullLoanAuditResult = fullLoanAuditResult;
            }

            public String getFullLoanAuditTime() {
                return fullLoanAuditTime;
            }

            public void setFullLoanAuditTime(String fullLoanAuditTime) {
                this.fullLoanAuditTime = fullLoanAuditTime;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public int getLoanAddRate() {
                return loanAddRate;
            }

            public void setLoanAddRate(int loanAddRate) {
                this.loanAddRate = loanAddRate;
            }

            public int getLoanAmt() {
                return loanAmt;
            }

            public void setLoanAmt(int loanAmt) {
                this.loanAmt = loanAmt;
            }

            public double getLoanArp() {
                return loanArp;
            }

            public void setLoanArp(double loanArp) {
                this.loanArp = loanArp;
            }

            public String getLoanAttri() {
                return loanAttri;
            }

            public void setLoanAttri(String loanAttri) {
                this.loanAttri = loanAttri;
            }

            public String getLoanCust() {
                return loanCust;
            }

            public void setLoanCust(String loanCust) {
                this.loanCust = loanCust;
            }

            public String getLoanDesc() {
                return loanDesc;
            }

            public void setLoanDesc(String loanDesc) {
                this.loanDesc = loanDesc;
            }

            public int getLoanExpiry() {
                return loanExpiry;
            }

            public void setLoanExpiry(int loanExpiry) {
                this.loanExpiry = loanExpiry;
            }

            public String getLoanIcon() {
                return loanIcon;
            }

            public void setLoanIcon(String loanIcon) {
                this.loanIcon = loanIcon;
            }

            public String getLoanIdentity() {
                return loanIdentity;
            }

            public void setLoanIdentity(String loanIdentity) {
                this.loanIdentity = loanIdentity;
            }

            public int getLoanRealAmt() {
                return loanRealAmt;
            }

            public void setLoanRealAmt(int loanRealAmt) {
                this.loanRealAmt = loanRealAmt;
            }

            public int getLoanStatus() {
                return loanStatus;
            }

            public void setLoanStatus(int loanStatus) {
                this.loanStatus = loanStatus;
            }

            public String getLoanTitle() {
                return loanTitle;
            }

            public void setLoanTitle(String loanTitle) {
                this.loanTitle = loanTitle;
            }

            public String getLoanUse() {
                return loanUse;
            }

            public void setLoanUse(String loanUse) {
                this.loanUse = loanUse;
            }

            public int getMaxIvst() {
                return maxIvst;
            }

            public void setMaxIvst(int maxIvst) {
                this.maxIvst = maxIvst;
            }

            public int getMinIvst() {
                return minIvst;
            }

            public void setMinIvst(int minIvst) {
                this.minIvst = minIvst;
            }

            public int getNewLoan() {
                return newLoan;
            }

            public void setNewLoan(int newLoan) {
                this.newLoan = newLoan;
            }

            public String getPointCustList() {
                return pointCustList;
            }

            public void setPointCustList(String pointCustList) {
                this.pointCustList = pointCustList;
            }

            public String getProduId() {
                return produId;
            }

            public void setProduId(String produId) {
                this.produId = produId;
            }

            public double getRealPlatfFee() {
                return realPlatfFee;
            }

            public void setRealPlatfFee(double realPlatfFee) {
                this.realPlatfFee = realPlatfFee;
            }

            public String getReleaseTime() {
                return releaseTime;
            }

            public void setReleaseTime(String releaseTime) {
                this.releaseTime = releaseTime;
            }

            public int getRemIvstAmt() {
                return remIvstAmt;
            }

            public void setRemIvstAmt(int remIvstAmt) {
                this.remIvstAmt = remIvstAmt;
            }

            public String getRpmtType() {
                return rpmtType;
            }

            public void setRpmtType(String rpmtType) {
                this.rpmtType = rpmtType;
            }

            public double getShdPlatfFee() {
                return shdPlatfFee;
            }

            public void setShdPlatfFee(double shdPlatfFee) {
                this.shdPlatfFee = shdPlatfFee;
            }

            public int getSinaLoanStatus() {
                return sinaLoanStatus;
            }

            public void setSinaLoanStatus(int sinaLoanStatus) {
                this.sinaLoanStatus = sinaLoanStatus;
            }

            public String getStartTime() {
                return startTime;
            }

            public void setStartTime(String startTime) {
                this.startTime = startTime;
            }

            public long getStartTimeMill() {
                return startTimeMill;
            }

            public void setStartTimeMill(long startTimeMill) {
                this.startTimeMill = startTimeMill;
            }

            public int getTotalInvestMoney() {
                return totalInvestMoney;
            }

            public void setTotalInvestMoney(int totalInvestMoney) {
                this.totalInvestMoney = totalInvestMoney;
            }

            public String getTransfTime() {
                return transfTime;
            }

            public void setTransfTime(String transfTime) {
                this.transfTime = transfTime;
            }

            public String getTransfUser() {
                return transfUser;
            }

            public void setTransfUser(String transfUser) {
                this.transfUser = transfUser;
            }
        }
    }
}
