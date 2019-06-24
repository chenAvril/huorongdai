package com.example.administrator.huorongdai.gsonbean;

import java.util.List;

/**
 * Created by Administrator on 2018/5/30 0030.
 */

public class ExpectEarnBean {

    /**
     * expectEarn : 177.53
     * message : 177.53
     * redPacketList : [{"addTime":"20180118000000","applicateScope":2,"custId":"9e41bd6f0f594fb6ad9d89d48d075786","delFlag":1,"endTime":"20181201000000","id":"36114f2ea3f84ba499f94cc8749bc832","ivstredpId":"3248f10c99ca4a669ec68d9e2e8fc99d","redpAmt":100,"redpRemark":"100元投资红包,一个黄芽兑换","redpStatus":1,"redpType":"cc6834dff53242d8b5c2c679968455d8","redpTypeName":"活动投资红包","useRedpMinAmt":10000},{"addTime":"20171031000000","applicateScope":2,"custId":"9e41bd6f0f594fb6ad9d89d48d075786","delFlag":1,"endTime":"20181201000000","id":"bee1cacba03f4466956c9ea5fdb96e21","ivstredpId":"3248f10c99ca4a669ec68d9e2e8fc99d","redpAmt":100,"redpRemark":"100元投资红包,一个黄芽兑换","redpStatus":1,"redpType":"1b71e29f19b14ba2bb81f69cc030b0c6","redpTypeName":"投资红包","useRedpMinAmt":10000},{"addTime":"20180118000000","applicateScope":2,"custId":"9e41bd6f0f594fb6ad9d89d48d075786","delFlag":1,"endTime":"20181201000000","id":"d49c8c26739b4791b6645317f9ca3db8","ivstredpId":"3248f10c99ca4a669ec68d9e2e8fc99d","redpAmt":100,"redpRemark":"100元投资红包,一个黄芽兑换","redpStatus":1,"redpType":"1b71e29f19b14ba2bb81f69cc030b0c6","redpTypeName":"投资红包","useRedpMinAmt":10000},{"addTime":"20171031000000","applicateScope":2,"custId":"9e41bd6f0f594fb6ad9d89d48d075786","delFlag":1,"endTime":"20181201000000","id":"f031cbe1a0f04e849f6b964d683191ef","ivstredpId":"3248f10c99ca4a669ec68d9e2e8fc99d","redpAmt":100,"redpRemark":"100元投资红包,一个黄芽兑换","redpStatus":1,"redpType":"1b71e29f19b14ba2bb81f69cc030b0c6","redpTypeName":"投资红包","useRedpMinAmt":10000}]
     * status : true
     */

    private double expectEarn;
    private double message;
    private boolean status;
    private List<RedPacketListBean> redPacketList;

    public double getExpectEarn() {
        return expectEarn;
    }

    public void setExpectEarn(double expectEarn) {
        this.expectEarn = expectEarn;
    }

    public double getMessage() {
        return message;
    }

    public void setMessage(double message) {
        this.message = message;
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
         * addTime : 20180118000000
         * applicateScope : 2
         * custId : 9e41bd6f0f594fb6ad9d89d48d075786
         * delFlag : 1
         * endTime : 20181201000000
         * id : 36114f2ea3f84ba499f94cc8749bc832
         * ivstredpId : 3248f10c99ca4a669ec68d9e2e8fc99d
         * redpAmt : 100
         * redpRemark : 100元投资红包,一个黄芽兑换
         * redpStatus : 1
         * redpType : cc6834dff53242d8b5c2c679968455d8
         * redpTypeName : 活动投资红包
         * useRedpMinAmt : 10000
         */

        private String addTime;
        private int applicateScope;
        private String custId;
        private int delFlag;
        private String endTime;
        private String id;
        private String ivstredpId;
        private double redpAmt;
        private String redpRemark;
        private int redpStatus;
        private String redpType;
        private String redpTypeName;
        private int useRedpMinAmt;

        public String getAddTime() {
            return addTime;
        }

        public void setAddTime(String addTime) {
            this.addTime = addTime;
        }

        public int getApplicateScope() {
            return applicateScope;
        }

        public void setApplicateScope(int applicateScope) {
            this.applicateScope = applicateScope;
        }

        public String getCustId() {
            return custId;
        }

        public void setCustId(String custId) {
            this.custId = custId;
        }

        public int getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(int delFlag) {
            this.delFlag = delFlag;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIvstredpId() {
            return ivstredpId;
        }

        public void setIvstredpId(String ivstredpId) {
            this.ivstredpId = ivstredpId;
        }

        public double getRedpAmt() {
            return redpAmt;
        }

        public void setRedpAmt(double redpAmt) {
            this.redpAmt = redpAmt;
        }

        public String getRedpRemark() {
            return redpRemark;
        }

        public void setRedpRemark(String redpRemark) {
            this.redpRemark = redpRemark;
        }

        public int getRedpStatus() {
            return redpStatus;
        }

        public void setRedpStatus(int redpStatus) {
            this.redpStatus = redpStatus;
        }

        public String getRedpType() {
            return redpType;
        }

        public void setRedpType(String redpType) {
            this.redpType = redpType;
        }

        public String getRedpTypeName() {
            return redpTypeName;
        }

        public void setRedpTypeName(String redpTypeName) {
            this.redpTypeName = redpTypeName;
        }

        public int getUseRedpMinAmt() {
            return useRedpMinAmt;
        }

        public void setUseRedpMinAmt(int useRedpMinAmt) {
            this.useRedpMinAmt = useRedpMinAmt;
        }
    }
}
