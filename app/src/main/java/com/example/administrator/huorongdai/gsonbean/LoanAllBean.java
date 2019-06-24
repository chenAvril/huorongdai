package com.example.administrator.huorongdai.gsonbean;

import java.util.List;

/**
 * Created by Administrator on 2018/3/17 0017.
 */

public class LoanAllBean {

    /**
     * loanList : [{"contractNum":"ZB20180316155235326","endTime":"20180324133223","endTimeMill":1521869543000,"expiryUnit":1,"expiryUnitStr":"天","id":"a2953470f2e347ce81cf5ab384f4f5fe","loanAddRate":0,"loanAmt":200000,"loanArp":8,"loanCust":"297c44e02a4d42ba9798b32d9c70efa1","loanDesc":"","loanExpiry":45,"loanIcon":"","loanIdentity":"f154e6cb586047628922567e945475cc","loanIdentityName":"抵押标","loanStatus":7,"loanTitle":"[公益-抵押标]房-BR20180012房产抵押第一期","newLoan":2,"produId":"ba7d375889bf4d5394e03f65b4474a40","produName":"小微项目","releaseTime":"20180317133223","rpmtType":"53ba38b3aa45484382295c5357c40466","rpmtTypeName":"每期还息,到期还本","sinaLoanStatus":1,"startTime":"20180317133012","startTimeMill":1521264612000,"totalInvestMoney":110000},{"contractNum":"ZB20180306141703880","endTime":"20180324102158","endTimeMill":1521858118000,"expiryUnit":1,"expiryUnitStr":"天","id":"e01038da8ad343af879eedaa6f4373a7","loanAddRate":0,"loanAmt":200000,"loanArp":10.8,"loanCust":"97dbf44086a446e2a34359f7e7433722","loanDesc":"","loanExpiry":180,"loanIcon":"","loanIdentity":"f154e6cb586047628922567e945475cc","loanIdentityName":"抵押标","loanStatus":7,"loanTitle":"[抵押标]房6-20180027房产抵押第一期","newLoan":2,"produId":"ba7d375889bf4d5394e03f65b4474a40","produName":"小微项目","releaseTime":"20180317102158","rpmtType":"53ba38b3aa45484382295c5357c40466","rpmtTypeName":"每期还息,到期还本","sinaLoanStatus":1,"startTime":"20180317101947","startTimeMill":1521253187000,"totalInvestMoney":100000},{"contractNum":"ZB20180313153817133","endTime":"20180324084358","endTimeMill":1521852238000,"expiryUnit":1,"expiryUnitStr":"天","id":"eefeaa13bfc34917805d28a38c5f0502","loanAddRate":0,"loanAmt":200000,"loanArp":7.2,"loanCust":"297c44e02a4d42ba9798b32d9c70efa1","loanDesc":"","loanExpiry":30,"loanIcon":"","loanIdentity":"f154e6cb586047628922567e945475cc","loanIdentityName":"抵押标","loanStatus":7,"loanTitle":"[抵押标]房1-BR20180292房产抵押第一期","newLoan":2,"produId":"ba7d375889bf4d5394e03f65b4474a40","produName":"小微项目","releaseTime":"20180317084358","rpmtType":"53ba38b3aa45484382295c5357c40466","rpmtTypeName":"每期还息,到期还本","sinaLoanStatus":1,"startTime":"20180317084350","startTimeMill":1521247430000,"totalInvestMoney":120000},{"contractNum":"ZB20180228101147439","endTime":"20180323132352","endTimeMill":1521782632000,"expiryUnit":1,"expiryUnitStr":"天","id":"e438f185494d464091a4b2a6410e66fa","loanAddRate":0,"loanAmt":200000,"loanArp":13.8,"loanCust":"97dbf44086a446e2a34359f7e7433722","loanDesc":"","loanExpiry":360,"loanIcon":"","loanIdentity":"f154e6cb586047628922567e945475cc","loanIdentityName":"抵押标","loanStatus":7,"loanTitle":"[抵押标]房12-20180042房产抵押第一期","newLoan":2,"produId":"ba7d375889bf4d5394e03f65b4474a40","produName":"小微项目","releaseTime":"20180316132352","rpmtType":"53ba38b3aa45484382295c5357c40466","rpmtTypeName":"每期还息,到期还本","sinaLoanStatus":1,"startTime":"20180316132105","startTimeMill":1521177665000,"totalInvestMoney":20000},{"contractNum":"ZB20180316154421971","endTime":"20180324111522","endTimeMill":1521861322000,"expiryUnit":1,"expiryUnitStr":"天","id":"1cbed438bf1b49b78d8e7d102a13597e","loanAddRate":0,"loanAmt":200000,"loanArp":8,"loanCust":"297c44e02a4d42ba9798b32d9c70efa1","loanDesc":"","loanExpiry":45,"loanIcon":"","loanIdentity":"f154e6cb586047628922567e945475cc","loanIdentityName":"抵押标","loanStatus":8,"loanTitle":"[公益-抵押标]房-BR20180011房产抵押第一期","newLoan":2,"produId":"ba7d375889bf4d5394e03f65b4474a40","produName":"小微项目","releaseTime":"20180317111522","rpmtType":"53ba38b3aa45484382295c5357c40466","rpmtTypeName":"每期还息,到期还本","sinaLoanStatus":1,"startTime":"20180317111311","startTimeMill":1521256391000,"totalInvestMoney":200000}]
     * message : 请求参数非法
     * nowMill : 1521267184445
     * pageNum : 1
     * pageSize : 5
     * status : true
     * totalCount : 4263
     * totalPages : 853
     */

    private String message;
    private long nowMill;
    private String pageNum;
    private String pageSize;
    private boolean status;
    private String totalCount;
    private String totalPages;
    private List<LoanListBean> loanList;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getNowMill() {
        return nowMill;
    }

    public void setNowMill(long nowMill) {
        this.nowMill = nowMill;
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

    public List<LoanListBean> getLoanList() {
        return loanList;
    }

    public void setLoanList(List<LoanListBean> loanList) {
        this.loanList = loanList;
    }

    public static class LoanListBean {
        /**
         * contractNum : ZB20180316155235326
         * endTime : 20180324133223
         * endTimeMill : 1521869543000
         * expiryUnit : 1
         * expiryUnitStr : 天
         * id : a2953470f2e347ce81cf5ab384f4f5fe
         * loanAddRate : 0
         * loanAmt : 200000
         * loanArp : 8
         * loanCust : 297c44e02a4d42ba9798b32d9c70efa1
         * loanDesc :
         * loanExpiry : 45
         * loanIcon :
         * loanIdentity : f154e6cb586047628922567e945475cc
         * loanIdentityName : 抵押标
         * loanStatus : 7
         * loanTitle : [公益-抵押标]房-BR20180012房产抵押第一期
         * newLoan : 2
         * produId : ba7d375889bf4d5394e03f65b4474a40
         * produName : 小微项目
         * releaseTime : 20180317133223
         * rpmtType : 53ba38b3aa45484382295c5357c40466
         * rpmtTypeName : 每期还息,到期还本
         * sinaLoanStatus : 1
         * startTime : 20180317133012
         * startTimeMill : 1521264612000
         * totalInvestMoney : 110000
         */

        private String contractNum;
        private String endTime;
        private long endTimeMill;
        private int expiryUnit;
        private String expiryUnitStr;
        private String id;
        private double loanAddRate;
        private long loanAmt;
        private double loanArp;
        private String loanCust;
        private String loanDesc;
        private int loanExpiry;
        private String loanIcon;
        private String loanIdentity;
        private String loanIdentityName;
        private int loanStatus;
        private String loanTitle;
        private int newLoan;
        private String produId;
        private String produName;
        private String releaseTime;
        private String rpmtType;
        private String rpmtTypeName;
        private int sinaLoanStatus;
        private String startTime;
        private long startTimeMill;
        private long totalInvestMoney;

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

        public double getLoanAddRate() {
            return loanAddRate;
        }

        public void setLoanAddRate(double loanAddRate) {
            this.loanAddRate = loanAddRate;
        }

        public long getLoanAmt() {
            return loanAmt;
        }

        public void setLoanAmt(long loanAmt) {
            this.loanAmt = loanAmt;
        }

        public double getLoanArp() {
            return loanArp;
        }

        public void setLoanArp(double loanArp) {
            this.loanArp = loanArp;
        }

        public String getLoanCust() {
            return loanCust;
        }

        public void setLoanCust(String loanCust) {
            this.loanCust = loanCust;
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

        public String getLoanIdentityName() {
            return loanIdentityName;
        }

        public void setLoanIdentityName(String loanIdentityName) {
            this.loanIdentityName = loanIdentityName;
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

        public int getNewLoan() {
            return newLoan;
        }

        public void setNewLoan(int newLoan) {
            this.newLoan = newLoan;
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

        public String getReleaseTime() {
            return releaseTime;
        }

        public void setReleaseTime(String releaseTime) {
            this.releaseTime = releaseTime;
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

        public long getTotalInvestMoney() {
            return totalInvestMoney;
        }

        public void setTotalInvestMoney(long totalInvestMoney) {
            this.totalInvestMoney = totalInvestMoney;
        }
    }
}
