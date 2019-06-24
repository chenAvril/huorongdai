package com.example.administrator.huorongdai.gsonbean;

import java.util.List;

/**
 * Created by Administrator on 2018/4/13 0013.
 */

public class LoanApplyBean {


    /**
     * loanApplyList : [{"applyNo":"20180412114157","applyStatus":1,"applyTime":"20180412114157","areaId":"1329","areaIdName":"浙江省 温州市 永嘉县 ","custId":"9e41bd6f0f594fb6ad9d89d48d075786","id":"b5a6cb5909f74d16a370ec024d266336","idCardBackPhoto":"install/images/20180412/8b1619e41d8e4496afbd8fedc204347f.png","idCardFrontPhoto":"install/images/20180412/e545aa559c0f45a3aab772b46e46ec6e.png","linkMan":"杨永平","loanAmt":10000,"loanExpiry":1,"phoneNum":"18375333610","statusName":"申请待审核"}]
     * message : 验证码错误
     * pageNum : 1
     * pageSize : 20
     * status : true
     * totalCount : 8
     * totalPages : 1
     */

    private String message;
    private String pageNum;
    private String pageSize;
    private boolean status;
    private String totalCount;
    private String totalPages;
    private List<LoanApplyListBean> loanApplyList;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public List<LoanApplyListBean> getLoanApplyList() {
        return loanApplyList;
    }

    public void setLoanApplyList(List<LoanApplyListBean> loanApplyList) {
        this.loanApplyList = loanApplyList;
    }

    public static class LoanApplyListBean {
        /**
         * applyNo : 20180412114157
         * applyStatus : 1
         * applyTime : 20180412114157
         * areaId : 1329
         * areaIdName : 浙江省 温州市 永嘉县
         * custId : 9e41bd6f0f594fb6ad9d89d48d075786
         * id : b5a6cb5909f74d16a370ec024d266336
         * idCardBackPhoto : install/images/20180412/8b1619e41d8e4496afbd8fedc204347f.png
         * idCardFrontPhoto : install/images/20180412/e545aa559c0f45a3aab772b46e46ec6e.png
         * linkMan : 杨永平
         * loanAmt : 10000
         * loanExpiry : 1
         * phoneNum : 18375333610
         * statusName : 申请待审核
         */

        private String applyNo;
        private int applyStatus;
        private String applyTime;
        private String areaId;
        private String areaIdName;
        private String custId;
        private String id;
        private String idCardBackPhoto;
        private String idCardFrontPhoto;
        private String linkMan;
        private double loanAmt;
        private int loanExpiry;
        private String phoneNum;
        private String statusName;

        public String getApplyNo() {
            return applyNo;
        }

        public void setApplyNo(String applyNo) {
            this.applyNo = applyNo;
        }

        public int getApplyStatus() {
            return applyStatus;
        }

        public void setApplyStatus(int applyStatus) {
            this.applyStatus = applyStatus;
        }

        public String getApplyTime() {
            return applyTime;
        }

        public void setApplyTime(String applyTime) {
            this.applyTime = applyTime;
        }

        public String getAreaId() {
            return areaId;
        }

        public void setAreaId(String areaId) {
            this.areaId = areaId;
        }

        public String getAreaIdName() {
            return areaIdName;
        }

        public void setAreaIdName(String areaIdName) {
            this.areaIdName = areaIdName;
        }

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

        public String getIdCardBackPhoto() {
            return idCardBackPhoto;
        }

        public void setIdCardBackPhoto(String idCardBackPhoto) {
            this.idCardBackPhoto = idCardBackPhoto;
        }

        public String getIdCardFrontPhoto() {
            return idCardFrontPhoto;
        }

        public void setIdCardFrontPhoto(String idCardFrontPhoto) {
            this.idCardFrontPhoto = idCardFrontPhoto;
        }

        public String getLinkMan() {
            return linkMan;
        }

        public void setLinkMan(String linkMan) {
            this.linkMan = linkMan;
        }

        public double getLoanAmt() {
            return loanAmt;
        }

        public void setLoanAmt(double loanAmt) {
            this.loanAmt = loanAmt;
        }

        public int getLoanExpiry() {
            return loanExpiry;
        }

        public void setLoanExpiry(int loanExpiry) {
            this.loanExpiry = loanExpiry;
        }

        public String getPhoneNum() {
            return phoneNum;
        }

        public void setPhoneNum(String phoneNum) {
            this.phoneNum = phoneNum;
        }

        public String getStatusName() {
            return statusName;
        }

        public void setStatusName(String statusName) {
            this.statusName = statusName;
        }
    }
}
