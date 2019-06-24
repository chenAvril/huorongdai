package com.example.administrator.huorongdai.gsonbean;

import java.util.List;

/**
 * Created by Administrator on 2018/4/4 0004.
 */

public class SelfInvestBean9 {
    private int investStatus;
    private String pageNum;
    private String pageSize;
    private boolean status;
    private String totalCount;
    private String totalPages;
    private List<SelfInvestListBean> selfInvestList;

    public int getInvestStatus() {
        return investStatus;
    }

    public void setInvestStatus(int investStatus) {
        this.investStatus = investStatus;
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

    public List<SelfInvestListBean> getSelfInvestList() {
        return selfInvestList;
    }

    public void setSelfInvestList(List<SelfInvestListBean> selfInvestList) {
        this.selfInvestList = selfInvestList;
    }

    public static class SelfInvestListBean {

        private double allinterest;
        private int curLoanStock;
        private String custId;
        private double earningTotalCapIint;
        private String id;
        private int isTransfNow;
        private int ivstAmt;
        private int ivstMode;
        private String ivstOrderNum;
        private String ivstSerialNum;
        private int ivstSource;
        private int ivstStatus;
        private String ivstTime;
        private String ivstTimeStr;
        private int ivstType;
        private int loanAmt;
        private double loanArp;
        private int loanExpiry;
        private String loanId;
        private String loanTitle;
        private int moveCount;
        private int nextEarnDays;
        private int receivedIssue;
        private String redpId;
        private String repayTypeName;
        private double repdAmt;
        private int totalTransfAmt;
        private int unReceiveIssue;
        private List<EarnListBean> earnList;

        public double getAllinterest() {
            return allinterest;
        }

        public void setAllinterest(double allinterest) {
            this.allinterest = allinterest;
        }

        public int getCurLoanStock() {
            return curLoanStock;
        }

        public void setCurLoanStock(int curLoanStock) {
            this.curLoanStock = curLoanStock;
        }

        public String getCustId() {
            return custId;
        }

        public void setCustId(String custId) {
            this.custId = custId;
        }

        public double getEarningTotalCapIint() {
            return earningTotalCapIint;
        }

        public void setEarningTotalCapIint(double earningTotalCapIint) {
            this.earningTotalCapIint = earningTotalCapIint;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getIsTransfNow() {
            return isTransfNow;
        }

        public void setIsTransfNow(int isTransfNow) {
            this.isTransfNow = isTransfNow;
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

        public String getIvstTimeStr() {
            return ivstTimeStr;
        }

        public void setIvstTimeStr(String ivstTimeStr) {
            this.ivstTimeStr = ivstTimeStr;
        }

        public int getIvstType() {
            return ivstType;
        }

        public void setIvstType(int ivstType) {
            this.ivstType = ivstType;
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

        public int getLoanExpiry() {
            return loanExpiry;
        }

        public void setLoanExpiry(int loanExpiry) {
            this.loanExpiry = loanExpiry;
        }

        public String getLoanId() {
            return loanId;
        }

        public void setLoanId(String loanId) {
            this.loanId = loanId;
        }

        public String getLoanTitle() {
            return loanTitle;
        }

        public void setLoanTitle(String loanTitle) {
            this.loanTitle = loanTitle;
        }

        public int getMoveCount() {
            return moveCount;
        }

        public void setMoveCount(int moveCount) {
            this.moveCount = moveCount;
        }

        public int getNextEarnDays() {
            return nextEarnDays;
        }

        public void setNextEarnDays(int nextEarnDays) {
            this.nextEarnDays = nextEarnDays;
        }

        public int getReceivedIssue() {
            return receivedIssue;
        }

        public void setReceivedIssue(int receivedIssue) {
            this.receivedIssue = receivedIssue;
        }

        public String getRedpId() {
            return redpId;
        }

        public void setRedpId(String redpId) {
            this.redpId = redpId;
        }

        public String getRepayTypeName() {
            return repayTypeName;
        }

        public void setRepayTypeName(String repayTypeName) {
            this.repayTypeName = repayTypeName;
        }

        public double getRepdAmt() {
            return repdAmt;
        }

        public void setRepdAmt(double repdAmt) {
            this.repdAmt = repdAmt;
        }

        public int getTotalTransfAmt() {
            return totalTransfAmt;
        }

        public void setTotalTransfAmt(int totalTransfAmt) {
            this.totalTransfAmt = totalTransfAmt;
        }

        public int getUnReceiveIssue() {
            return unReceiveIssue;
        }

        public void setUnReceiveIssue(int unReceiveIssue) {
            this.unReceiveIssue = unReceiveIssue;
        }

        public List<EarnListBean> getEarnList() {
            return earnList;
        }

        public void setEarnList(List<EarnListBean> earnList) {
            this.earnList = earnList;
        }

        public static class EarnListBean {

            private String custId;
            private double earnCapital;
            private double earnIint;
            private int earnIssue;
            private int earnStatus;
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
        }
    }
}
