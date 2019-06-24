package com.example.administrator.huorongdai.gsonbean;

import java.util.List;

/**
 * Created by Administrator on 2018/4/8 0008.
 */

public class TradeRecordBean {

    /**
     * pageNum : 1
     * pageSize : 10
     * status : true
     * totalCount : 2
     * totalPages : 1
     * tradeList : [{"custId":"9e41bd6f0f594fb6ad9d89d48d075786","id":"883cd71525c84a8d8e1ab9e22ae1e033","tradeAmt":1.0E8,"tradeDate":"20170729154945","tradeDetail":"充值金额：1.0E8元","tradeFee":0,"tradeOrderNum":"1501314570845","tradeStatus":1,"tradeType":1},{"custId":"9e41bd6f0f594fb6ad9d89d48d075786","id":"8330c4f376fe454eb242a61ce091866e","tradeAmt":10000,"tradeDate":"20170727160050","tradeDetail":"充值金额：10000.0元","tradeFee":0,"tradeOrderNum":"1501142393816","tradeStatus":1,"tradeType":1}]
     */

    private String pageNum;
    private String pageSize;
    private boolean status;
    private String totalCount;
    private String totalPages;
    private List<TradeListBean> tradeList;

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

    public List<TradeListBean> getTradeList() {
        return tradeList;
    }

    public void setTradeList(List<TradeListBean> tradeList) {
        this.tradeList = tradeList;
    }

    public static class TradeListBean {
        /**
         * custId : 9e41bd6f0f594fb6ad9d89d48d075786
         * id : 883cd71525c84a8d8e1ab9e22ae1e033
         * tradeAmt : 1.0E8
         * tradeDate : 20170729154945
         * tradeDetail : 充值金额：1.0E8元
         * tradeFee : 0
         * tradeOrderNum : 1501314570845
         * tradeStatus : 1
         * tradeType : 1
         */

        private String custId;
        private String id;
        private double tradeAmt;
        private String tradeDate;
        private String tradeDetail;
        private double tradeFee;
        private String tradeOrderNum;
        private int tradeStatus;
        private int tradeType;

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

        public double getTradeAmt() {
            return tradeAmt;
        }

        public void setTradeAmt(double tradeAmt) {
            this.tradeAmt = tradeAmt;
        }

        public String getTradeDate() {
            return tradeDate;
        }

        public void setTradeDate(String tradeDate) {
            this.tradeDate = tradeDate;
        }

        public String getTradeDetail() {
            return tradeDetail;
        }

        public void setTradeDetail(String tradeDetail) {
            this.tradeDetail = tradeDetail;
        }

        public double getTradeFee() {
            return tradeFee;
        }

        public void setTradeFee(double tradeFee) {
            this.tradeFee = tradeFee;
        }

        public String getTradeOrderNum() {
            return tradeOrderNum;
        }

        public void setTradeOrderNum(String tradeOrderNum) {
            this.tradeOrderNum = tradeOrderNum;
        }

        public int getTradeStatus() {
            return tradeStatus;
        }

        public void setTradeStatus(int tradeStatus) {
            this.tradeStatus = tradeStatus;
        }

        public int getTradeType() {
            return tradeType;
        }

        public void setTradeType(int tradeType) {
            this.tradeType = tradeType;
        }
    }
}
