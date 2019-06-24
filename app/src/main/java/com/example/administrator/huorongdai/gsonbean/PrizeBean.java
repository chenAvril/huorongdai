package com.example.administrator.huorongdai.gsonbean;

import java.util.List;

/**
 * Created by Administrator on 2018/6/6 0006.
 */

public class PrizeBean {


    /**
     * DiscountList : [{"addTime":"2018-06-02","address":"安徽合肥","cashdiscountName":"泰弗仕TABOSH真美系列保温杯","custId":"e656da0b0cd848979ea9ec0e26755f6b","custMobile":"15155118003","custName":"hrd_15155118003","custRealName":"童恩","id":"5edc9ee844d748e3bde0403603d38b5e","ivstAmt":10000,"ivstId":"5e45b49547774d0496cda547ab27ba47","ivstTime":"2018-06-02","loanExpiry":30,"loanId":"8b6087bf72b44ff58765f1dbef4b83b3","loanTitle":"测试合规存管项目001","status":1,"uname":"张三","updateTime":"2018-06-04"},{"addTime":"2018-05-30","cashdiscountName":"泰弗仕TABOSH真美系列保温杯","custId":"e656da0b0cd848979ea9ec0e26755f6b","custMobile":"15155118003","custName":"hrd_15155118003","custRealName":"童恩","id":"4fcab35fc45f4535b3d7c4b02cd934a6","ivstAmt":10000,"ivstId":"8ee6485beda541d58073de5abe634a18","ivstTime":"2018-05-30","loanExpiry":30,"loanId":"8b6087bf72b44ff58765f1dbef4b83b3","loanTitle":"测试合规存管项目001","status":2,"updateTime":"2018-05-31"},{"addTime":"2018-05-30","cashdiscountName":"运动空调扇/1088元现金奖励","custId":"e656da0b0cd848979ea9ec0e26755f6b","custMobile":"15155118003","custName":"hrd_15155118003","custRealName":"童恩","id":"8f28a02b43c3463b806adcb796fd7cc0","ivstAmt":10000,"ivstId":"3812927987c941cb9d7f54163b36ca9c","ivstTime":"2018-05-30","loanExpiry":30,"loanId":"8b6087bf72b44ff58765f1dbef4b83b3","loanTitle":"测试合规存管项目001","status":2,"updateTime":"2018-05-31"},{"addTime":"2018-05-30","cashdiscountName":"运动空调扇/1088元现金奖励","custId":"e656da0b0cd848979ea9ec0e26755f6b","custMobile":"15155118003","custName":"hrd_15155118003","custRealName":"童恩","id":"278ead5dde8e458ba847ed2e1e29bbbd","ivstAmt":10000,"ivstId":"c8f6a46121d344b6b926e53b2418885f","ivstTime":"2018-05-30","loanExpiry":30,"loanId":"8b6087bf72b44ff58765f1dbef4b83b3","loanTitle":"测试合规存管项目001"}]
     * pageNum : 1
     * pageSize : 10
     * status : true
     * totalCount : 4
     * totalPages : 1
     */

    private String pageNum;
    private String pageSize;
    private boolean status;
    private int totalCount;
    private int totalPages;
    private List<DiscountListBean> DiscountList;

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

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<DiscountListBean> getDiscountList() {
        return DiscountList;
    }

    public void setDiscountList(List<DiscountListBean> DiscountList) {
        this.DiscountList = DiscountList;
    }

    public static class DiscountListBean {
        /**
         * addTime : 2018-06-02
         * address : 安徽合肥
         * cashdiscountName : 泰弗仕TABOSH真美系列保温杯
         * custId : e656da0b0cd848979ea9ec0e26755f6b
         * custMobile : 15155118003
         * custName : hrd_15155118003
         * custRealName : 童恩
         * id : 5edc9ee844d748e3bde0403603d38b5e
         * ivstAmt : 10000
         * ivstId : 5e45b49547774d0496cda547ab27ba47
         * ivstTime : 2018-06-02
         * loanExpiry : 30
         * loanId : 8b6087bf72b44ff58765f1dbef4b83b3
         * loanTitle : 测试合规存管项目001
         * status : 1
         * uname : 张三
         * updateTime : 2018-06-04
         */

        private String addTime;
        private String address;
        private String cashdiscountName;
        private String custId;
        private String custMobile;
        private String custName;
        private String custRealName;
        private String id;
        private double ivstAmt;
        private String ivstId;
        private String ivstTime;
        private double loanExpiry;
        private String loanId;
        private String loanTitle;
        private int status;
        private String uname;
        private String updateTime;

        public String getAddTime() {
            return addTime;
        }

        public void setAddTime(String addTime) {
            this.addTime = addTime;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCashdiscountName() {
            return cashdiscountName;
        }

        public void setCashdiscountName(String cashdiscountName) {
            this.cashdiscountName = cashdiscountName;
        }

        public String getCustId() {
            return custId;
        }

        public void setCustId(String custId) {
            this.custId = custId;
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

        public String getCustRealName() {
            return custRealName;
        }

        public void setCustRealName(String custRealName) {
            this.custRealName = custRealName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public double getIvstAmt() {
            return ivstAmt;
        }

        public void setIvstAmt(double ivstAmt) {
            this.ivstAmt = ivstAmt;
        }

        public String getIvstId() {
            return ivstId;
        }

        public void setIvstId(String ivstId) {
            this.ivstId = ivstId;
        }

        public String getIvstTime() {
            return ivstTime;
        }

        public void setIvstTime(String ivstTime) {
            this.ivstTime = ivstTime;
        }

        public double getLoanExpiry() {
            return loanExpiry;
        }

        public void setLoanExpiry(double loanExpiry) {
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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getUname() {
            return uname;
        }

        public void setUname(String uname) {
            this.uname = uname;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }
}
