package com.example.administrator.huorongdai.gsonbean;

import java.util.List;

/**
 * Created by Administrator on 2018/5/22 0022.
 */

public class RpmtViewBean {

    /**
     * list : [{"addTime":"20170801093203","addUser":"f414eefd5eb64a559dc13dcee2e7f5a7","advanceApply":0,"autoLoanRate":0,"autoRepay":0,"borCard":"防守打法","borrower":"范德萨发","contractNum":"5646546454464","endTime":"20170803093550","endTimeMill":1501724150000,"expiryUnit":2,"fullLoanAduitUser":"83e90ae214e34f769b07672fdd0c7a4d","fullLoanAuditOpinion":"11","fullLoanAuditResult":1,"fullLoanAuditTime":"20170804123707","id":"1e1354b2e04041d9be1d1e539a54f578","joinProfit":0,"loanAddRate":2,"loanAmt":50000,"loanArp":10,"loanAttri":"","loanCust":"9e41bd6f0f594fb6ad9d89d48d075786","loanCustName":"hrd_18375333610","loanDesc":"饭随爱豆","loanExpiry":3,"loanIcon":"nstall/images/20170728/o_1bm3vmtf3dik11ik1fo91tpv1sgd8.png","loanIdentity":"b8c608618ca04914ba807fc7274e77c6","loanRealAmt":50000,"loanStatus":12,"loanTitle":"验证加息","loanUse":"买车","lstRptPlan":[{"custId":"9e41bd6f0f594fb6ad9d89d48d075786","floatRate":0,"id":"01113762730c46aea61ff4dc52d0a6f3","lateFine":0,"lateIint":0,"loanId":"1e1354b2e04041d9be1d1e539a54f578","orderNum":"20170804123718hP1mc6vQ1","paidFee":0,"realRpmtDate":"20170804145209","realRpmtDateStr":"2017-08-04 14:52:09","rpmtCapital":0,"rpmtIint":500,"rpmtIssue":1,"rpmtStatus":2,"shdRpmtDate":"20170904123718","shdRpmtDateStr":"2017-09-04 12:37:18","thisIssueFlag":0},{"custId":"9e41bd6f0f594fb6ad9d89d48d075786","id":"e6279b66c2b9470e8c396576a9464528","lateFine":0,"lateIint":0,"loanId":"1e1354b2e04041d9be1d1e539a54f578","orderNum":"20170804123718hP1mc6vQ2","paidFee":0,"realRpmtDate":"20170823141112","realRpmtDateStr":"2017-08-23 14:11:12","rpmtCapital":0,"rpmtIint":500,"rpmtIssue":2,"rpmtStatus":2,"shdRpmtDate":"20171004123718","shdRpmtDateStr":"2017-10-04 12:37:18","thisIssueFlag":0},{"custId":"9e41bd6f0f594fb6ad9d89d48d075786","id":"77c237abbf5e44d2ab9b45b7082926eb","lateFine":0,"lateIint":5024.75,"loanId":"1e1354b2e04041d9be1d1e539a54f578","orderNum":"20170804123718hP1mc6vQ3","rpmtCapital":50000,"rpmtIint":500,"rpmtIssue":3,"rpmtStatus":1,"shdRpmtDate":"20171104123718","shdRpmtDateStr":"2017-11-04 12:37:18","thisIssueFlag":1}],"maxIvst":50000,"minIvst":100,"newLoan":2,"nextRepayTime":"20171104123718","nextRpmtDays":-199,"pointCustList":"","produId":"84b933faf2d742e08ee7571f0c05581b","produName":"信用标","realPlatfFee":123.29,"releaseTime":"20170801093349","repayAmt":50500,"repayedIssue":2,"rpmtType":"53ba38b3aa45484382295c5357c40466","rpmtTypeName":"每期还息,到期还本","shdPlatfFee":123.29,"sinaLoanStatus":1,"startTime":"20170801093547","startTimeMill":1501551347000,"transfTime":"20170804123736","transfUser":"83e90ae214e34f769b07672fdd0c7a4d","unRepayIssue":1}]
     * loan : {"addTime":"20170801093203","addUser":"f414eefd5eb64a559dc13dcee2e7f5a7","advanceApply":0,"autoLoanRate":0,"autoRepay":0,"borCard":"防守打法","borrower":"范德萨发","contractNum":"5646546454464","endTime":"20170803093550","endTimeMill":1501724150000,"expiryUnit":2,"fullLoanAduitUser":"83e90ae214e34f769b07672fdd0c7a4d","fullLoanAuditOpinion":"11","fullLoanAuditResult":1,"fullLoanAuditTime":"20170804123707","id":"1e1354b2e04041d9be1d1e539a54f578","joinProfit":0,"loanAddRate":2,"loanAmt":50000,"loanArp":10,"loanAttri":"","loanCust":"9e41bd6f0f594fb6ad9d89d48d075786","loanCustName":"hrd_18375333610","loanCustPhone":"18375333610","loanDesc":"饭随爱豆","loanExpiry":3,"loanIcon":"nstall/images/20170728/o_1bm3vmtf3dik11ik1fo91tpv1sgd8.png","loanIdentity":"b8c608618ca04914ba807fc7274e77c6","loanIdentityName":"信用标","loanRealAmt":50000,"loanStatus":12,"loanTitle":"验证加息","loanUse":"买车","maxIvst":50000,"minIvst":100,"newLoan":2,"pointCustList":"","produId":"84b933faf2d742e08ee7571f0c05581b","produName":"信用标","realPlatfFee":123.29,"releaseTime":"20170801093349","remIvstAmt":0,"rpmtType":"53ba38b3aa45484382295c5357c40466","rpmtTypeName":"每期还息,到期还本","shdPlatfFee":123.29,"sinaLoanStatus":1,"startTime":"20170801093547","startTimeMill":1501551347000,"totalInvestMoney":50000,"transfTime":"20170804123736","transfUser":"83e90ae214e34f769b07672fdd0c7a4d"}
     * status : true
     */

    private LoanBean loan;
    private boolean status;
    private List<ListBean> list;

    public LoanBean getLoan() {
        return loan;
    }

    public void setLoan(LoanBean loan) {
        this.loan = loan;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class LoanBean {
        /**
         * addTime : 20170801093203
         * addUser : f414eefd5eb64a559dc13dcee2e7f5a7
         * advanceApply : 0
         * autoLoanRate : 0
         * autoRepay : 0
         * borCard : 防守打法
         * borrower : 范德萨发
         * contractNum : 5646546454464
         * endTime : 20170803093550
         * endTimeMill : 1501724150000
         * expiryUnit : 2
         * fullLoanAduitUser : 83e90ae214e34f769b07672fdd0c7a4d
         * fullLoanAuditOpinion : 11
         * fullLoanAuditResult : 1
         * fullLoanAuditTime : 20170804123707
         * id : 1e1354b2e04041d9be1d1e539a54f578
         * joinProfit : 0
         * loanAddRate : 2
         * loanAmt : 50000
         * loanArp : 10
         * loanAttri :
         * loanCust : 9e41bd6f0f594fb6ad9d89d48d075786
         * loanCustName : hrd_18375333610
         * loanCustPhone : 18375333610
         * loanDesc : 饭随爱豆
         * loanExpiry : 3
         * loanIcon : nstall/images/20170728/o_1bm3vmtf3dik11ik1fo91tpv1sgd8.png
         * loanIdentity : b8c608618ca04914ba807fc7274e77c6
         * loanIdentityName : 信用标
         * loanRealAmt : 50000
         * loanStatus : 12
         * loanTitle : 验证加息
         * loanUse : 买车
         * maxIvst : 50000
         * minIvst : 100
         * newLoan : 2
         * pointCustList :
         * produId : 84b933faf2d742e08ee7571f0c05581b
         * produName : 信用标
         * realPlatfFee : 123.29
         * releaseTime : 20170801093349
         * remIvstAmt : 0
         * rpmtType : 53ba38b3aa45484382295c5357c40466
         * rpmtTypeName : 每期还息,到期还本
         * shdPlatfFee : 123.29
         * sinaLoanStatus : 1
         * startTime : 20170801093547
         * startTimeMill : 1501551347000
         * totalInvestMoney : 50000
         * transfTime : 20170804123736
         * transfUser : 83e90ae214e34f769b07672fdd0c7a4d
         */

        private String addTime;
        private String addUser;
        private int advanceApply;
        private int autoLoanRate;
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
        private int joinProfit;
        private double loanAddRate;
        private int loanAmt;
        private double loanArp;
        private String loanAttri;
        private String loanCust;
        private String loanCustName;
        private String loanCustPhone;
        private String loanDesc;
        private int loanExpiry;
        private String loanIcon;
        private String loanIdentity;
        private String loanIdentityName;
        private int loanRealAmt;
        private int loanStatus;
        private String loanTitle;
        private String loanUse;
        private int maxIvst;
        private int minIvst;
        private int newLoan;
        private String pointCustList;
        private String produId;
        private String produName;
        private double realPlatfFee;
        private String releaseTime;
        private int remIvstAmt;
        private String rpmtType;
        private String rpmtTypeName;
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

        public int getAutoLoanRate() {
            return autoLoanRate;
        }

        public void setAutoLoanRate(int autoLoanRate) {
            this.autoLoanRate = autoLoanRate;
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

        public int getJoinProfit() {
            return joinProfit;
        }

        public void setJoinProfit(int joinProfit) {
            this.joinProfit = joinProfit;
        }

        public double getLoanAddRate() {
            return loanAddRate;
        }

        public void setLoanAddRate(double loanAddRate) {
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

        public String getLoanCustName() {
            return loanCustName;
        }

        public void setLoanCustName(String loanCustName) {
            this.loanCustName = loanCustName;
        }

        public String getLoanCustPhone() {
            return loanCustPhone;
        }

        public void setLoanCustPhone(String loanCustPhone) {
            this.loanCustPhone = loanCustPhone;
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

        public String getLoanIdentityName() {
            return loanIdentityName;
        }

        public void setLoanIdentityName(String loanIdentityName) {
            this.loanIdentityName = loanIdentityName;
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

        public String getProduName() {
            return produName;
        }

        public void setProduName(String produName) {
            this.produName = produName;
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

        public String getRpmtTypeName() {
            return rpmtTypeName;
        }

        public void setRpmtTypeName(String rpmtTypeName) {
            this.rpmtTypeName = rpmtTypeName;
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

    public static class ListBean {
        /**
         * addTime : 20170801093203
         * addUser : f414eefd5eb64a559dc13dcee2e7f5a7
         * advanceApply : 0
         * autoLoanRate : 0
         * autoRepay : 0
         * borCard : 防守打法
         * borrower : 范德萨发
         * contractNum : 5646546454464
         * endTime : 20170803093550
         * endTimeMill : 1501724150000
         * expiryUnit : 2
         * fullLoanAduitUser : 83e90ae214e34f769b07672fdd0c7a4d
         * fullLoanAuditOpinion : 11
         * fullLoanAuditResult : 1
         * fullLoanAuditTime : 20170804123707
         * id : 1e1354b2e04041d9be1d1e539a54f578
         * joinProfit : 0
         * loanAddRate : 2
         * loanAmt : 50000
         * loanArp : 10
         * loanAttri :
         * loanCust : 9e41bd6f0f594fb6ad9d89d48d075786
         * loanCustName : hrd_18375333610
         * loanDesc : 饭随爱豆
         * loanExpiry : 3
         * loanIcon : nstall/images/20170728/o_1bm3vmtf3dik11ik1fo91tpv1sgd8.png
         * loanIdentity : b8c608618ca04914ba807fc7274e77c6
         * loanRealAmt : 50000
         * loanStatus : 12
         * loanTitle : 验证加息
         * loanUse : 买车
         * lstRptPlan : [{"custId":"9e41bd6f0f594fb6ad9d89d48d075786","floatRate":0,"id":"01113762730c46aea61ff4dc52d0a6f3","lateFine":0,"lateIint":0,"loanId":"1e1354b2e04041d9be1d1e539a54f578","orderNum":"20170804123718hP1mc6vQ1","paidFee":0,"realRpmtDate":"20170804145209","realRpmtDateStr":"2017-08-04 14:52:09","rpmtCapital":0,"rpmtIint":500,"rpmtIssue":1,"rpmtStatus":2,"shdRpmtDate":"20170904123718","shdRpmtDateStr":"2017-09-04 12:37:18","thisIssueFlag":0},{"custId":"9e41bd6f0f594fb6ad9d89d48d075786","id":"e6279b66c2b9470e8c396576a9464528","lateFine":0,"lateIint":0,"loanId":"1e1354b2e04041d9be1d1e539a54f578","orderNum":"20170804123718hP1mc6vQ2","paidFee":0,"realRpmtDate":"20170823141112","realRpmtDateStr":"2017-08-23 14:11:12","rpmtCapital":0,"rpmtIint":500,"rpmtIssue":2,"rpmtStatus":2,"shdRpmtDate":"20171004123718","shdRpmtDateStr":"2017-10-04 12:37:18","thisIssueFlag":0},{"custId":"9e41bd6f0f594fb6ad9d89d48d075786","id":"77c237abbf5e44d2ab9b45b7082926eb","lateFine":0,"lateIint":5024.75,"loanId":"1e1354b2e04041d9be1d1e539a54f578","orderNum":"20170804123718hP1mc6vQ3","rpmtCapital":50000,"rpmtIint":500,"rpmtIssue":3,"rpmtStatus":1,"shdRpmtDate":"20171104123718","shdRpmtDateStr":"2017-11-04 12:37:18","thisIssueFlag":1}]
         * maxIvst : 50000
         * minIvst : 100
         * newLoan : 2
         * nextRepayTime : 20171104123718
         * nextRpmtDays : -199
         * pointCustList :
         * produId : 84b933faf2d742e08ee7571f0c05581b
         * produName : 信用标
         * realPlatfFee : 123.29
         * releaseTime : 20170801093349
         * repayAmt : 50500
         * repayedIssue : 2
         * rpmtType : 53ba38b3aa45484382295c5357c40466
         * rpmtTypeName : 每期还息,到期还本
         * shdPlatfFee : 123.29
         * sinaLoanStatus : 1
         * startTime : 20170801093547
         * startTimeMill : 1501551347000
         * transfTime : 20170804123736
         * transfUser : 83e90ae214e34f769b07672fdd0c7a4d
         * unRepayIssue : 1
         */

        private String addTime;
        private String addUser;
        private int advanceApply;
        private int autoLoanRate;
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
        private int joinProfit;
        private double loanAddRate;
        private int loanAmt;
        private double loanArp;
        private String loanAttri;
        private String loanCust;
        private String loanCustName;
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
        private String nextRepayTime;
        private int nextRpmtDays;
        private String pointCustList;
        private String produId;
        private String produName;
        private double realPlatfFee;
        private String releaseTime;
        private double repayAmt;
        private int repayedIssue;
        private String rpmtType;
        private String rpmtTypeName;
        private double shdPlatfFee;
        private int sinaLoanStatus;
        private String startTime;
        private long startTimeMill;
        private String transfTime;
        private String transfUser;
        private int unRepayIssue;
        private List<LstRptPlanBean> lstRptPlan;

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

        public int getAutoLoanRate() {
            return autoLoanRate;
        }

        public void setAutoLoanRate(int autoLoanRate) {
            this.autoLoanRate = autoLoanRate;
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

        public int getJoinProfit() {
            return joinProfit;
        }

        public void setJoinProfit(int joinProfit) {
            this.joinProfit = joinProfit;
        }

        public double getLoanAddRate() {
            return loanAddRate;
        }

        public void setLoanAddRate(double loanAddRate) {
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

        public String getLoanCustName() {
            return loanCustName;
        }

        public void setLoanCustName(String loanCustName) {
            this.loanCustName = loanCustName;
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

        public String getNextRepayTime() {
            return nextRepayTime;
        }

        public void setNextRepayTime(String nextRepayTime) {
            this.nextRepayTime = nextRepayTime;
        }

        public int getNextRpmtDays() {
            return nextRpmtDays;
        }

        public void setNextRpmtDays(int nextRpmtDays) {
            this.nextRpmtDays = nextRpmtDays;
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

        public String getProduName() {
            return produName;
        }

        public void setProduName(String produName) {
            this.produName = produName;
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

        public double getRepayAmt() {
            return repayAmt;
        }

        public void setRepayAmt(double repayAmt) {
            this.repayAmt = repayAmt;
        }

        public int getRepayedIssue() {
            return repayedIssue;
        }

        public void setRepayedIssue(int repayedIssue) {
            this.repayedIssue = repayedIssue;
        }

        public String getRpmtType() {
            return rpmtType;
        }

        public void setRpmtType(String rpmtType) {
            this.rpmtType = rpmtType;
        }

        public String getRpmtTypeName() {
            return rpmtTypeName;
        }

        public void setRpmtTypeName(String rpmtTypeName) {
            this.rpmtTypeName = rpmtTypeName;
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

        public int getUnRepayIssue() {
            return unRepayIssue;
        }

        public void setUnRepayIssue(int unRepayIssue) {
            this.unRepayIssue = unRepayIssue;
        }

        public List<LstRptPlanBean> getLstRptPlan() {
            return lstRptPlan;
        }

        public void setLstRptPlan(List<LstRptPlanBean> lstRptPlan) {
            this.lstRptPlan = lstRptPlan;
        }

        public static class LstRptPlanBean {
            /**
             * custId : 9e41bd6f0f594fb6ad9d89d48d075786
             * floatRate : 0
             * id : 01113762730c46aea61ff4dc52d0a6f3
             * lateFine : 0
             * lateIint : 0
             * loanId : 1e1354b2e04041d9be1d1e539a54f578
             * orderNum : 20170804123718hP1mc6vQ1
             * paidFee : 0
             * realRpmtDate : 20170804145209
             * realRpmtDateStr : 2017-08-04 14:52:09
             * rpmtCapital : 0
             * rpmtIint : 500
             * rpmtIssue : 1
             * rpmtStatus : 2
             * shdRpmtDate : 20170904123718
             * shdRpmtDateStr : 2017-09-04 12:37:18
             * thisIssueFlag : 0
             */

            private String custId;
            private int floatRate;
            private String id;
            private int lateFine;
            private double lateIint;
            private String loanId;
            private String orderNum;
            private int paidFee;
            private String realRpmtDate;
            private String realRpmtDateStr;
            private double rpmtCapital;
            private double rpmtIint;
            private int rpmtIssue;
            private int rpmtStatus;
            private String shdRpmtDate;
            private String shdRpmtDateStr;
            private int thisIssueFlag;

            public String getCustId() {
                return custId;
            }

            public void setCustId(String custId) {
                this.custId = custId;
            }

            public int getFloatRate() {
                return floatRate;
            }

            public void setFloatRate(int floatRate) {
                this.floatRate = floatRate;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
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

            public int getPaidFee() {
                return paidFee;
            }

            public void setPaidFee(int paidFee) {
                this.paidFee = paidFee;
            }

            public String getRealRpmtDate() {
                return realRpmtDate;
            }

            public void setRealRpmtDate(String realRpmtDate) {
                this.realRpmtDate = realRpmtDate;
            }

            public String getRealRpmtDateStr() {
                return realRpmtDateStr;
            }

            public void setRealRpmtDateStr(String realRpmtDateStr) {
                this.realRpmtDateStr = realRpmtDateStr;
            }

            public double getRpmtCapital() {
                return rpmtCapital;
            }

            public void setRpmtCapital(double rpmtCapital) {
                this.rpmtCapital = rpmtCapital;
            }

            public double getRpmtIint() {
                return rpmtIint;
            }

            public void setRpmtIint(double rpmtIint) {
                this.rpmtIint = rpmtIint;
            }

            public int getRpmtIssue() {
                return rpmtIssue;
            }

            public void setRpmtIssue(int rpmtIssue) {
                this.rpmtIssue = rpmtIssue;
            }

            public int getRpmtStatus() {
                return rpmtStatus;
            }

            public void setRpmtStatus(int rpmtStatus) {
                this.rpmtStatus = rpmtStatus;
            }

            public String getShdRpmtDate() {
                return shdRpmtDate;
            }

            public void setShdRpmtDate(String shdRpmtDate) {
                this.shdRpmtDate = shdRpmtDate;
            }

            public String getShdRpmtDateStr() {
                return shdRpmtDateStr;
            }

            public void setShdRpmtDateStr(String shdRpmtDateStr) {
                this.shdRpmtDateStr = shdRpmtDateStr;
            }

            public int getThisIssueFlag() {
                return thisIssueFlag;
            }

            public void setThisIssueFlag(int thisIssueFlag) {
                this.thisIssueFlag = thisIssueFlag;
            }
        }
    }
}
