package com.example.administrator.huorongdai.gsonbean;

import java.util.List;

/**
 * Created by Administrator on 2018/4/8 0008.
 */

public class GetLetterListBean {

    /**
     * letterList : [{"id":"6fdda4b6d0054f19bd9a6a600d0db990","letrCont":"尊敬的用户杨**，恭喜您成功投资项目测试还款3月标02成功，投资金额为10000.0元，详情请登录账户查看。","letrStatus":2,"letrTitle":"投资","letrType":"d08f3d11a2a14e3d8db947e8b1afe19a","letrTypeName":"投资","rcvdCustId":"9e41bd6f0f594fb6ad9d89d48d075786","sendCustId":"system","sendTime":"20180403105834"},{"id":"317fa7fc2fcd44e4b28a38002cb5ad33","letrCont":"尊敬的用户杨**，恭喜您成功投资项目测试180天标0400成功，投资金额为10000.0元，详情请登录账户查看。","letrStatus":2,"letrTitle":"投资","letrType":"d08f3d11a2a14e3d8db947e8b1afe19a","letrTypeName":"投资","rcvdCustId":"9e41bd6f0f594fb6ad9d89d48d075786","sendCustId":"system","sendTime":"20180309172931"},{"id":"05e84d65dd0a4b52afa8adac8a05b183","letrCont":"尊敬的用户杨**，您投资的项目测试60天标00005第30期收款成功，已还本金：0.0元，利息：27.39元，详情请登录账户查看。","letrStatus":2,"letrTitle":"还款","letrType":"3bec67e2a15d4e80b6d607977c836d9e","letrTypeName":"还款","rcvdCustId":"9e41bd6f0f594fb6ad9d89d48d075786","sendCustId":"system","sendTime":"20180309111147"},{"id":"073554b4b40d4fb987729740df1f3f1e","letrCont":"尊敬的用户杨**，您的融资项目小微贷001第3期还款成功，详情请登录账户查看。","letrStatus":2,"letrTitle":"还款","letrType":"3bec67e2a15d4e80b6d607977c836d9e","letrTypeName":"还款","rcvdCustId":"9e41bd6f0f594fb6ad9d89d48d075786","sendCustId":"system","sendTime":"20180309110904"},{"id":"dc5e39d654584e6d97db40e0d8712095","letrCont":"尊敬的用户杨**，您投资的项目测试30天标的004第26期收款成功，已还本金：0.0元，利息：0.55元，详情请登录账户查看。","letrStatus":2,"letrTitle":"还款","letrType":"3bec67e2a15d4e80b6d607977c836d9e","letrTypeName":"还款","rcvdCustId":"9e41bd6f0f594fb6ad9d89d48d075786","sendCustId":"system","sendTime":"20180308102733"},{"id":"c5aa727909d24e05a183e6d3d6d75302","letrCont":"尊敬的用户杨**，恭喜您成功投资项目测试大转盘2成功，投资金额为950000.0元，详情请登录账户查看。","letrStatus":2,"letrTitle":"投资","letrType":"d08f3d11a2a14e3d8db947e8b1afe19a","letrTypeName":"投资","rcvdCustId":"9e41bd6f0f594fb6ad9d89d48d075786","sendCustId":"system","sendTime":"20170921094347"},{"id":"93ade85e747849cfba8fd6d56e4db959","letrCont":"尊敬的用户杨**，恭喜您成功投资项目测试大转盘2成功，投资金额为1000000.0元，详情请登录账户查看。","letrStatus":2,"letrTitle":"投资","letrType":"d08f3d11a2a14e3d8db947e8b1afe19a","letrTypeName":"投资","rcvdCustId":"9e41bd6f0f594fb6ad9d89d48d075786","sendCustId":"system","sendTime":"20170921094332"},{"id":"edad5487191c4cb3a998784028afe694","letrCont":"尊敬的货融贷用户hrd_18375333610,您于2017-09-21 09:11:57申请的测试大转盘借款项目已经通过项目发布，进入招标中。","letrStatus":2,"letrTitle":"项目审核","letrType":"66559e7c224b48208c359f542ab458d5","letrTypeName":"项目审核","rcvdCustId":"9e41bd6f0f594fb6ad9d89d48d075786","sendCustId":"system","sendTime":"20170921091245"},{"id":"5f71c8ac140d406bbfc10d9d4849a09d","letrCont":"尊敬的货融贷用户hrd_18375333610,您于2017-09-21 09:11:57申请的测试大转盘借款项目已经通过项目初审，进入项目复审。","letrStatus":2,"letrTitle":"项目审核","letrType":"66559e7c224b48208c359f542ab458d5","letrTypeName":"项目审核","rcvdCustId":"9e41bd6f0f594fb6ad9d89d48d075786","sendCustId":"system","sendTime":"20170921091158"}]
     * pageNum : 1
     * pageSize : 20
     * status : true
     * totalCount : 9
     * totalPages : 1
     */

    private String pageNum;
    private String pageSize;
    private boolean status;
    private String totalCount;
    private String totalPages;
    private List<LetterListBean> letterList;

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

    public List<LetterListBean> getLetterList() {
        return letterList;
    }

    public void setLetterList(List<LetterListBean> letterList) {
        this.letterList = letterList;
    }

    public static class LetterListBean {
        /**
         * id : 6fdda4b6d0054f19bd9a6a600d0db990
         * letrCont : 尊敬的用户杨**，恭喜您成功投资项目测试还款3月标02成功，投资金额为10000.0元，详情请登录账户查看。
         * letrStatus : 2
         * letrTitle : 投资
         * letrType : d08f3d11a2a14e3d8db947e8b1afe19a
         * letrTypeName : 投资
         * rcvdCustId : 9e41bd6f0f594fb6ad9d89d48d075786
         * sendCustId : system
         * sendTime : 20180403105834
         */

        private String id;
        private String letrCont;
        private int letrStatus;
        private String letrTitle;
        private String letrType;
        private String letrTypeName;
        private String rcvdCustId;
        private String sendCustId;
        private String sendTime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLetrCont() {
            return letrCont;
        }

        public void setLetrCont(String letrCont) {
            this.letrCont = letrCont;
        }

        public int getLetrStatus() {
            return letrStatus;
        }

        public void setLetrStatus(int letrStatus) {
            this.letrStatus = letrStatus;
        }

        public String getLetrTitle() {
            return letrTitle;
        }

        public void setLetrTitle(String letrTitle) {
            this.letrTitle = letrTitle;
        }

        public String getLetrType() {
            return letrType;
        }

        public void setLetrType(String letrType) {
            this.letrType = letrType;
        }

        public String getLetrTypeName() {
            return letrTypeName;
        }

        public void setLetrTypeName(String letrTypeName) {
            this.letrTypeName = letrTypeName;
        }

        public String getRcvdCustId() {
            return rcvdCustId;
        }

        public void setRcvdCustId(String rcvdCustId) {
            this.rcvdCustId = rcvdCustId;
        }

        public String getSendCustId() {
            return sendCustId;
        }

        public void setSendCustId(String sendCustId) {
            this.sendCustId = sendCustId;
        }

        public String getSendTime() {
            return sendTime;
        }

        public void setSendTime(String sendTime) {
            this.sendTime = sendTime;
        }
    }
}
