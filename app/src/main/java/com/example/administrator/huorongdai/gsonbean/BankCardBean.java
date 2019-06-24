package com.example.administrator.huorongdai.gsonbean;

/**
 * Created by Administrator on 2018/3/27 0027.
 */

public class BankCardBean {

    /**
     * bindcard : {"custId":"12e3c1be7c634c50a96acca264ff9eeb","cardType":"","cardNum":"6217001630004930158","bankCode":"0105","subBank":"","cardName":"王义郎","cityNum":"3610","bindTime":"20170725092315","orderNum":"20170804163413PuTPnxSa","serialNum":"","bindcardStatus":2,"bankName":"中国建设银行","id":"1500945795115"}
     * status : true
     * message :
     */

    private BindcardBean bindcard;
    private boolean status;
    private String message;

    public BindcardBean getBindcard() {
        return bindcard;
    }

    public void setBindcard(BindcardBean bindcard) {
        this.bindcard = bindcard;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class BindcardBean {
        /**
         * custId : 12e3c1be7c634c50a96acca264ff9eeb
         * cardType :
         * cardNum : 6217001630004930158
         * bankCode : 0105
         * subBank :
         * cardName : 王义郎
         * cityNum : 3610
         * bindTime : 20170725092315
         * orderNum : 20170804163413PuTPnxSa
         * serialNum :
         * bindcardStatus : 2
         * bankName : 中国建设银行
         * id : 1500945795115
         */

        private String custId;
        private String cardType;
        private String cardNum;
        private String bankCode;
        private String subBank;
        private String cardName;
        private String cityNum;
        private String bindTime;
        private String orderNum;
        private String serialNum;
        private int bindcardStatus;
        private String bankName;
        private String id;

        public String getCustId() {
            return custId;
        }

        public void setCustId(String custId) {
            this.custId = custId;
        }

        public String getCardType() {
            return cardType;
        }

        public void setCardType(String cardType) {
            this.cardType = cardType;
        }

        public String getCardNum() {
            return cardNum;
        }

        public void setCardNum(String cardNum) {
            this.cardNum = cardNum;
        }

        public String getBankCode() {
            return bankCode;
        }

        public void setBankCode(String bankCode) {
            this.bankCode = bankCode;
        }

        public String getSubBank() {
            return subBank;
        }

        public void setSubBank(String subBank) {
            this.subBank = subBank;
        }

        public String getCardName() {
            return cardName;
        }

        public void setCardName(String cardName) {
            this.cardName = cardName;
        }

        public String getCityNum() {
            return cityNum;
        }

        public void setCityNum(String cityNum) {
            this.cityNum = cityNum;
        }

        public String getBindTime() {
            return bindTime;
        }

        public void setBindTime(String bindTime) {
            this.bindTime = bindTime;
        }

        public String getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(String orderNum) {
            this.orderNum = orderNum;
        }

        public String getSerialNum() {
            return serialNum;
        }

        public void setSerialNum(String serialNum) {
            this.serialNum = serialNum;
        }

        public int getBindcardStatus() {
            return bindcardStatus;
        }

        public void setBindcardStatus(int bindcardStatus) {
            this.bindcardStatus = bindcardStatus;
        }

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
