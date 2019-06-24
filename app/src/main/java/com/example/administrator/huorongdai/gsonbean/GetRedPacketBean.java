package com.example.administrator.huorongdai.gsonbean;

import java.util.List;

/**
 * Created by Administrator on 2018/4/10 0010.
 */

public class GetRedPacketBean {

    /**
     * redPacketList : [{"redpAmt":10,"redpType":"6c8aab57f0f7406cb435190410f1701e","redpRemark":"1188积分兑换10.0元红包","addTime":"20180410154505","endTime":"20180510154505","redpStatus":1,"custId":"12e3c1be7c634c50a96acca264ff9eeb","applicateScope":2,"redpTypeName":"兑换红包","useRedpMinAmt":1000,"delFlag":1,"id":"8c8368a4b8914dd9aca2cc51aa9f73c9"},{"redpAmt":20,"redpType":"6c8aab57f0f7406cb435190410f1701e","redpRemark":"2288积分兑换20.0元红包","addTime":"20180410154450","endTime":"20180510154450","redpStatus":1,"custId":"12e3c1be7c634c50a96acca264ff9eeb","applicateScope":2,"redpTypeName":"兑换红包","useRedpMinAmt":2000,"delFlag":1,"id":"51fcad5367de48589bf1da3e6dbfa605"},{"redpAmt":20,"redpType":"6c8aab57f0f7406cb435190410f1701e","redpRemark":"2288积分兑换20.0元红包","addTime":"20180410153935","endTime":"20180510153935","redpStatus":1,"custId":"12e3c1be7c634c50a96acca264ff9eeb","applicateScope":2,"redpTypeName":"兑换红包","useRedpMinAmt":2000,"delFlag":1,"id":"ffcf1392b743414c8edcded3a58c4be3"},{"redpAmt":10,"redpType":"6c8aab57f0f7406cb435190410f1701e","redpRemark":"1188积分兑换10.0元红包","addTime":"20180410153926","endTime":"20180510153926","redpStatus":1,"custId":"12e3c1be7c634c50a96acca264ff9eeb","applicateScope":2,"redpTypeName":"兑换红包","useRedpMinAmt":1000,"delFlag":1,"id":"d4e35ccc30984f03937d94644227718d"},{"redpAmt":10,"redpType":"6c8aab57f0f7406cb435190410f1701e","redpRemark":"1188积分兑换10.0元红包","addTime":"20180410153607","endTime":"20180510153607","redpStatus":1,"custId":"12e3c1be7c634c50a96acca264ff9eeb","applicateScope":2,"redpTypeName":"兑换红包","useRedpMinAmt":1000,"delFlag":1,"id":"ec08493804f94f17a4ea43c392ad2cc4"},{"redpAmt":100,"redpType":"6c8aab57f0f7406cb435190410f1701e","redpRemark":"9088积分兑换100.0元红包","addTime":"20180410153550","endTime":"20180510153550","redpStatus":1,"custId":"12e3c1be7c634c50a96acca264ff9eeb","applicateScope":2,"redpTypeName":"兑换红包","useRedpMinAmt":10000,"delFlag":1,"id":"90eb9149edc046e8aefd5344bce1b5c6"},{"redpAmt":10,"redpType":"6c8aab57f0f7406cb435190410f1701e","redpRemark":"1188积分兑换10.0元红包","addTime":"20180410153448","endTime":"20180510153448","redpStatus":1,"custId":"12e3c1be7c634c50a96acca264ff9eeb","applicateScope":2,"redpTypeName":"兑换红包","useRedpMinAmt":1000,"delFlag":1,"id":"b52d2e50bfbe441dbb068dc6dd44b689"},{"redpAmt":20,"redpType":"6c8aab57f0f7406cb435190410f1701e","redpRemark":"2288积分兑换20.0元红包","addTime":"20180410153332","endTime":"20180510153332","redpStatus":1,"custId":"12e3c1be7c634c50a96acca264ff9eeb","applicateScope":2,"redpTypeName":"兑换红包","useRedpMinAmt":2000,"delFlag":1,"id":"5a6382e71b2d46aa97a58facfa17079d"}]
     * redpStatus : 1
     * totalPages : 11
     * pageSize : 8
     * totalCount : 87
     * pageNum : 1
     * status : true
     */

    private String redpStatus;
    private String totalPages;
    private String pageSize;
    private String totalCount;
    private String pageNum;
    private boolean status;
    private List<RedPacketListBean> redPacketList;

    public String getRedpStatus() {
        return redpStatus;
    }

    public void setRedpStatus(String redpStatus) {
        this.redpStatus = redpStatus;
    }

    public String getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(String totalPages) {
        this.totalPages = totalPages;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public String getPageNum() {
        return pageNum;
    }

    public void setPageNum(String pageNum) {
        this.pageNum = pageNum;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<RedPacketListBean> getRedPacketList() {
        return redPacketList;
    }

    public void setRedPacketList(List<RedPacketListBean> redPacketList) {
        this.redPacketList = redPacketList;
    }

    public static class RedPacketListBean {
        /**
         * redpAmt : 10
         * redpType : 6c8aab57f0f7406cb435190410f1701e
         * redpRemark : 1188积分兑换10.0元红包
         * addTime : 20180410154505
         * endTime : 20180510154505
         * redpStatus : 1
         * custId : 12e3c1be7c634c50a96acca264ff9eeb
         * applicateScope : 2
         * redpTypeName : 兑换红包
         * useRedpMinAmt : 1000
         * delFlag : 1
         * id : 8c8368a4b8914dd9aca2cc51aa9f73c9
         */

        private double redpAmt;
        private String redpType;
        private String redpRemark;
        private String addTime;
        private String endTime;
        private int redpStatus;
        private String custId;
        private int applicateScope;
        private String redpTypeName;
        private double useRedpMinAmt;
        private int delFlag;
        private String id;

        public double getRedpAmt() {
            return redpAmt;
        }

        public void setRedpAmt(double redpAmt) {
            this.redpAmt = redpAmt;
        }

        public String getRedpType() {
            return redpType;
        }

        public void setRedpType(String redpType) {
            this.redpType = redpType;
        }

        public String getRedpRemark() {
            return redpRemark;
        }

        public void setRedpRemark(String redpRemark) {
            this.redpRemark = redpRemark;
        }

        public String getAddTime() {
            return addTime;
        }

        public void setAddTime(String addTime) {
            this.addTime = addTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public int getRedpStatus() {
            return redpStatus;
        }

        public void setRedpStatus(int redpStatus) {
            this.redpStatus = redpStatus;
        }

        public String getCustId() {
            return custId;
        }

        public void setCustId(String custId) {
            this.custId = custId;
        }

        public int getApplicateScope() {
            return applicateScope;
        }

        public void setApplicateScope(int applicateScope) {
            this.applicateScope = applicateScope;
        }

        public String getRedpTypeName() {
            return redpTypeName;
        }

        public void setRedpTypeName(String redpTypeName) {
            this.redpTypeName = redpTypeName;
        }

        public double getUseRedpMinAmt() {
            return useRedpMinAmt;
        }

        public void setUseRedpMinAmt(double useRedpMinAmt) {
            this.useRedpMinAmt = useRedpMinAmt;
        }

        public int getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(int delFlag) {
            this.delFlag = delFlag;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
