package com.example.administrator.huorongdai.gsonbean;

import java.util.List;

/**
 * Created by Administrator on 2018/4/11 0011.
 */

public class SelfLoanBean {


    /**
     * loanStatusScope : 12
     * pageNum : 1
     * pageSize : 20
     * selfLoanList : [{"addTime":"20170921091157","addUser":"f414eefd5eb64a559dc13dcee2e7f5a7","advanceApply":0,"autoRepay":0,"borCard":"范德萨","borrower":"发士大夫撒","contractNum":"56465464554464152121","endTime":"20170923091245","endTimeMill":1506129165000,"expiryUnit":2,"fullLoanAduitUser":"e927a4b42d454b7a96028b359952fa94","fullLoanAuditOpinion":"tg","fullLoanAuditResult":1,"fullLoanAuditTime":"20180108113018","id":"95a2c3b98ecb41c08baccdadd05931df","loanAddRate":0,"loanAmt":5000000,"loanArp":10,"loanAttri":"","loanCust":"9e41bd6f0f594fb6ad9d89d48d075786","loanCustName":"hrd_18375333610","loanDesc":"<p>发生大发生大<\/p>","loanExpiry":1,"loanIcon":"nstall/images/20170728/o_1bm3vmtf3dik11ik1fo91tpv1sgd8.png","loanIdentity":"b8c608618ca04914ba807fc7274e77c6","loanRealAmt":5000000,"loanStatus":12,"loanTitle":"测试大转盘","loanUse":"买车","lstRptPlan":[{"custId":"9e41bd6f0f594fb6ad9d89d48d075786","id":"06e9770f16f34147bacec5db06600a8a","lateFine":0,"lateIint":161333.33,"loanId":"95a2c3b98ecb41c08baccdadd05931df","orderNum":"20180108113331P770Gx3S1","rpmtCapital":5000000,"rpmtIint":41666.67,"rpmtIssue":1,"rpmtStatus":1,"shdRpmtDate":"20180207113324","shdRpmtDateStr":"2018-02-07 11:33:24","thisIssueFlag":1}],"maxIvst":5000000,"minIvst":100,"newLoan":2,"nextRepayTime":"20180207113324","nextRpmtDays":-64,"pointCustList":"","produId":"84b933faf2d742e08ee7571f0c05581b","produName":"信用标","realPlatfFee":65000,"releaseTime":"20170921091245","repayAmt":5041666.67,"repayedIssue":0,"rpmtType":"53ba38b3aa45484382295c5357c40466","rpmtTypeName":"每期还息,到期还本","shdPlatfFee":8219.18,"sinaLoanStatus":1,"startTime":"20170921091250","startTimeMill":1505956370000,"transfTime":"20180108113343","transfUser":"e927a4b42d454b7a96028b359952fa94","unRepayIssue":1}]
     * status : true
     * totalCount : 5
     * totalPages : 1
     */

    private String loanStatusScope;
    private String pageNum;
    private String pageSize;
    private boolean status;
    private String totalCount;
    private String totalPages;
    private List<SelfLoanListBean> selfLoanList;

    public String getLoanStatusScope() {
        return loanStatusScope;
    }

    public void setLoanStatusScope(String loanStatusScope) {
        this.loanStatusScope = loanStatusScope;
    }

    public String getPageNum() {
        return pageNum;
    }

    public void setPageNum(String pageNum) {
        this.pageNum = pageNum;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public String getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(String totalPages) {
        this.totalPages = totalPages;
    }

    public List<SelfLoanListBean> getSelfLoanList() {
        return selfLoanList;
    }

    public void setSelfLoanList(List<SelfLoanListBean> selfLoanList) {
        this.selfLoanList = selfLoanList;
    }

    public static class SelfLoanListBean {
        /**
         * addTime : 20170921091157
         * addUser : f414eefd5eb64a559dc13dcee2e7f5a7
         * advanceApply : 0
         * autoRepay : 0
         * borCard : 范德萨
         * borrower : 发士大夫撒
         * contractNum : 56465464554464152121
         * endTime : 20170923091245
         * endTimeMill : 1506129165000
         * expiryUnit : 2
         * fullLoanAduitUser : e927a4b42d454b7a96028b359952fa94
         * fullLoanAuditOpinion : tg
         * fullLoanAuditResult : 1
         * fullLoanAuditTime : 20180108113018
         * id : 95a2c3b98ecb41c08baccdadd05931df
         * loanAddRate : 0
         * loanAmt : 5000000
         * loanArp : 10
         * loanAttri :
         * loanCust : 9e41bd6f0f594fb6ad9d89d48d075786
         * loanCustName : hrd_18375333610
         * loanDesc : <p>发生大发生大</p>
         * loanExpiry : 1
         * loanIcon : nstall/images/20170728/o_1bm3vmtf3dik11ik1fo91tpv1sgd8.png
         * loanIdentity : b8c608618ca04914ba807fc7274e77c6
         * loanRealAmt : 5000000
         * loanStatus : 12
         * loanTitle : 测试大转盘
         * loanUse : 买车
         * lstRptPlan : [{"custId":"9e41bd6f0f594fb6ad9d89d48d075786","id":"06e9770f16f34147bacec5db06600a8a","lateFine":0,"lateIint":161333.33,"loanId":"95a2c3b98ecb41c08baccdadd05931df","orderNum":"20180108113331P770Gx3S1","rpmtCapital":5000000,"rpmtIint":41666.67,"rpmtIssue":1,"rpmtStatus":1,"shdRpmtDate":"20180207113324","shdRpmtDateStr":"2018-02-07 11:33:24","thisIssueFlag":1}]
         * maxIvst : 5000000
         * minIvst : 100
         * newLoan : 2
         * nextRepayTime : 20180207113324
         * nextRpmtDays : -64
         * pointCustList :
         * produId : 84b933faf2d742e08ee7571f0c05581b
         * produName : 信用标
         * realPlatfFee : 65000
         * releaseTime : 20170921091245
         * repayAmt : 5041666.67
         * repayedIssue : 0
         * rpmtType : 53ba38b3aa45484382295c5357c40466
         * rpmtTypeName : 每期还息,到期还本
         * shdPlatfFee : 8219.18
         * sinaLoanStatus : 1
         * startTime : 20170921091250
         * startTimeMill : 1505956370000
         * transfTime : 20180108113343
         * transfUser : e927a4b42d454b7a96028b359952fa94
         * unRepayIssue : 1
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
        private double loanAddRate;
        private double loanAmt;
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

        public double getLoanAddRate() {
            return loanAddRate;
        }

        public void setLoanAddRate(double loanAddRate) {
            this.loanAddRate = loanAddRate;
        }

        public double getLoanAmt() {
            return loanAmt;
        }

        public void setLoanAmt(double loanAmt) {
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
             * id : 06e9770f16f34147bacec5db06600a8a
             * lateFine : 0
             * lateIint : 161333.33
             * loanId : 95a2c3b98ecb41c08baccdadd05931df
             * orderNum : 20180108113331P770Gx3S1
             * rpmtCapital : 5000000
             * rpmtIint : 41666.67
             * rpmtIssue : 1
             * rpmtStatus : 1
             * shdRpmtDate : 20180207113324
             * shdRpmtDateStr : 2018-02-07 11:33:24
             * thisIssueFlag : 1
             */

            private String custId;
            private String id;
            private int lateFine;
            private double lateIint;
            private String loanId;
            private String orderNum;
            private int rpmtCapital;
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

            public int getRpmtCapital() {
                return rpmtCapital;
            }

            public void setRpmtCapital(int rpmtCapital) {
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
