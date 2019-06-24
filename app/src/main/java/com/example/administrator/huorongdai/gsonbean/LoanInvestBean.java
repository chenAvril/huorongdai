package com.example.administrator.huorongdai.gsonbean;

import java.util.List;

/**
 * Created by Administrator on 2018/4/2 0002.
 */

public class LoanInvestBean {

    /**
     * loanId : 2e565f2fe35c4de582b98768ff0473d8
     * loanInvestList : [{"custId":"69b3bb5a161640ef915cc81d8e69322d","custName":"hrd_17628297510","id":"589fcedc030845ee9289a314f4be2ff1","ivstAmt":1000,"ivstMode":1,"ivstOrderNum":"20180402141029JlIhGJ82","ivstSerialNum":"002528391837","ivstSource":1,"ivstStatus":2,"ivstTime":"20180402141030","ivstType":1,"loanId":"2e565f2fe35c4de582b98768ff0473d8","moveCount":0},{"custId":"b7c78c37d6df4b96b7164014c474441b","custName":"hrd_13218513492","id":"1bd3155fb2f54c89b2d4d9b825971801","ivstAmt":10000,"ivstMode":1,"ivstOrderNum":"201804021408262C6LidgE","ivstSerialNum":"002528391277","ivstSource":4,"ivstStatus":2,"ivstTime":"20180402140827","ivstType":1,"loanId":"2e565f2fe35c4de582b98768ff0473d8","moveCount":0,"redpId":"d3d2ec76178c4a5092160b09aefbfd57"},{"custId":"f4e95b81231445cba2a61c65a17e861e","custName":"bjk123","id":"8eef35361dbd40f1b130ac9e198f25ad","ivstAmt":10000,"ivstMode":1,"ivstOrderNum":"20180402140823uMaLlTnZ","ivstSerialNum":"002528400372","ivstSource":4,"ivstStatus":2,"ivstTime":"20180402140824","ivstType":1,"loanId":"2e565f2fe35c4de582b98768ff0473d8","moveCount":0,"redpId":"f36017d4b44b4075ba5d12b4aaeb6209"}]
     * pageNum : 1
     * pageSize : 100
     * status : true
     * totalCount : 3
     * totalPages : 1
     */

    private String loanId;
    private String pageNum;
    private String pageSize;
    private boolean status;
    private String totalCount;
    private String totalPages;
    private List<LoanInvestListBean> loanInvestList;

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
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

    public List<LoanInvestListBean> getLoanInvestList() {
        return loanInvestList;
    }

    public void setLoanInvestList(List<LoanInvestListBean> loanInvestList) {
        this.loanInvestList = loanInvestList;
    }

    public static class LoanInvestListBean {
        /**
         * custId : 69b3bb5a161640ef915cc81d8e69322d
         * custName : hrd_17628297510
         * id : 589fcedc030845ee9289a314f4be2ff1
         * ivstAmt : 1000
         * ivstMode : 1
         * ivstOrderNum : 20180402141029JlIhGJ82
         * ivstSerialNum : 002528391837
         * ivstSource : 1
         * ivstStatus : 2
         * ivstTime : 20180402141030
         * ivstType : 1
         * loanId : 2e565f2fe35c4de582b98768ff0473d8
         * moveCount : 0
         * redpId : d3d2ec76178c4a5092160b09aefbfd57
         */

        private String custId;
        private String custName;
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
        private String redpId;

        public String getCustId() {
            return custId;
        }

        public void setCustId(String custId) {
            this.custId = custId;
        }

        public String getCustName() {
            return custName;
        }

        public void setCustName(String custName) {
            this.custName = custName;
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

        public String getRedpId() {
            return redpId;
        }

        public void setRedpId(String redpId) {
            this.redpId = redpId;
        }
    }
}
