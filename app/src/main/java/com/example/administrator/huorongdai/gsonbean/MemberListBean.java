package com.example.administrator.huorongdai.gsonbean;

import java.util.List;

public class MemberListBean {

    /**
     * totalPages : 9
     * pageSize : 1
     * list : [{"memberName":"李相文","memberPost":"总经理兼运营总监","memberIntro":"数年金融业管理工作经验，从业二十多年，精通金融业务管理，财务管理，风险控制及企业内控管理，熟悉企业融资、重组、上市等工作。有互联网金融、房地产、农林等高管工作经验；长期主抓集团化企业绩效管理和行政费用控制，对体系建立、实施、结果分析与运用，以及人才梯队建设和行政费用管理工作有较为深刻的研究。具有丰富的企业文化塑造，素质提升和员工关系管理经验。","memberHeadimg":"nstall/images/20170722/o_1blk5peqk1sos1dp915g18k0pj7c.png","addTime":"20170722113545","orderBy":"1","memberShow":3,"id":"6512a63addef49c2b8aceef55fab919a"}]
     * totalCount : 9
     * pageNum : 1
     */

    private String totalPages;
    private int pageSize;
    private String totalCount;
    private int pageNum;
    private List<ListBean> list;

    public String getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(String totalPages) {
        this.totalPages = totalPages;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * memberName : 李相文
         * memberPost : 总经理兼运营总监
         * memberIntro : 数年金融业管理工作经验，从业二十多年，精通金融业务管理，财务管理，风险控制及企业内控管理，熟悉企业融资、重组、上市等工作。有互联网金融、房地产、农林等高管工作经验；长期主抓集团化企业绩效管理和行政费用控制，对体系建立、实施、结果分析与运用，以及人才梯队建设和行政费用管理工作有较为深刻的研究。具有丰富的企业文化塑造，素质提升和员工关系管理经验。
         * memberHeadimg : nstall/images/20170722/o_1blk5peqk1sos1dp915g18k0pj7c.png
         * addTime : 20170722113545
         * orderBy : 1
         * memberShow : 3
         * id : 6512a63addef49c2b8aceef55fab919a
         */

        private String memberName;
        private String memberPost;
        private String memberIntro;
        private String memberHeadimg;
        private String addTime;
        private String orderBy;
        private int memberShow;
        private String id;

        public String getMemberName() {
            return memberName;
        }

        public void setMemberName(String memberName) {
            this.memberName = memberName;
        }

        public String getMemberPost() {
            return memberPost;
        }

        public void setMemberPost(String memberPost) {
            this.memberPost = memberPost;
        }

        public String getMemberIntro() {
            return memberIntro;
        }

        public void setMemberIntro(String memberIntro) {
            this.memberIntro = memberIntro;
        }

        public String getMemberHeadimg() {
            return memberHeadimg;
        }

        public void setMemberHeadimg(String memberHeadimg) {
            this.memberHeadimg = memberHeadimg;
        }

        public String getAddTime() {
            return addTime;
        }

        public void setAddTime(String addTime) {
            this.addTime = addTime;
        }

        public String getOrderBy() {
            return orderBy;
        }

        public void setOrderBy(String orderBy) {
            this.orderBy = orderBy;
        }

        public int getMemberShow() {
            return memberShow;
        }

        public void setMemberShow(int memberShow) {
            this.memberShow = memberShow;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
