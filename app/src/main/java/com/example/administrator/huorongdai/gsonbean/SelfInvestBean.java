package com.example.administrator.huorongdai.gsonbean;

import java.util.List;

/**
 * Created by Administrator on 2018/4/3 0003.
 */

public class SelfInvestBean {
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
        private String custId;
        private String expiryUnitStr;
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
        private double loanPercent;
        private String loanTitle;
        private int moveCount;
        private String repayTypeName;
        private int totalTransfAmt;

        public double getAllinterest() {
            return allinterest;
        }

        public void setAllinterest(double allinterest) {
            this.allinterest = allinterest;
        }

        public String getCustId() {
            return custId;
        }

        public void setCustId(String custId) {
            this.custId = custId;
        }

        public String getExpiryUnitStr() {
            return expiryUnitStr;
        }

        public void setExpiryUnitStr(String expiryUnitStr) {
            this.expiryUnitStr = expiryUnitStr;
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

        public double getLoanPercent() {
            return loanPercent;
        }

        public void setLoanPercent(double loanPercent) {
            this.loanPercent = loanPercent;
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

        public String getRepayTypeName() {
            return repayTypeName;
        }

        public void setRepayTypeName(String repayTypeName) {
            this.repayTypeName = repayTypeName;
        }

        public int getTotalTransfAmt() {
            return totalTransfAmt;
        }

        public void setTotalTransfAmt(int totalTransfAmt) {
            this.totalTransfAmt = totalTransfAmt;
        }
    }
}
