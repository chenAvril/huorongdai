package com.example.administrator.huorongdai.gsonbean;

import java.util.List;

public class LinkListBean {

    /**
     * totalPages : 6
     * pageSize : 1
     * list : [{"coopSiteType":1,"coopSiteName":"网贷之家","coopSiteLogo":"nstall/images/20170805/网贷之家.png","coopSiteUrl":"http://www.wdzj.com/dangan/hrd6/","orderBy":"2","addUserId":"8aa06eb7312146559c450212db09f10a","addTime":"20170722090359","coopDescr":"网贷之家是第三方网贷资讯平台，于2011年10月上线。网贷之家致力推动P2P网贷行业发展，网贷之家打造网贷行业最有影响力的资讯门户.\r\n网贷之家是投资人身边的网贷咨询专家，为投资者的网贷之路保驾护航。\r\n","id":"182afb5c41ae42bd96fee969e70c9757"}]
     * totalCount : 6
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
         * coopSiteType : 1
         * coopSiteName : 网贷之家
         * coopSiteLogo : nstall/images/20170805/网贷之家.png
         * coopSiteUrl : http://www.wdzj.com/dangan/hrd6/
         * orderBy : 2
         * addUserId : 8aa06eb7312146559c450212db09f10a
         * addTime : 20170722090359
         * coopDescr : 网贷之家是第三方网贷资讯平台，于2011年10月上线。网贷之家致力推动P2P网贷行业发展，网贷之家打造网贷行业最有影响力的资讯门户.
         网贷之家是投资人身边的网贷咨询专家，为投资者的网贷之路保驾护航。

         * id : 182afb5c41ae42bd96fee969e70c9757
         */

        private int coopSiteType;
        private String coopSiteName;
        private String coopSiteLogo;
        private String coopSiteUrl;
        private String orderBy;
        private String addUserId;
        private String addTime;
        private String coopDescr;
        private String id;

        public int getCoopSiteType() {
            return coopSiteType;
        }

        public void setCoopSiteType(int coopSiteType) {
            this.coopSiteType = coopSiteType;
        }

        public String getCoopSiteName() {
            return coopSiteName;
        }

        public void setCoopSiteName(String coopSiteName) {
            this.coopSiteName = coopSiteName;
        }

        public String getCoopSiteLogo() {
            return coopSiteLogo;
        }

        public void setCoopSiteLogo(String coopSiteLogo) {
            this.coopSiteLogo = coopSiteLogo;
        }

        public String getCoopSiteUrl() {
            return coopSiteUrl;
        }

        public void setCoopSiteUrl(String coopSiteUrl) {
            this.coopSiteUrl = coopSiteUrl;
        }

        public String getOrderBy() {
            return orderBy;
        }

        public void setOrderBy(String orderBy) {
            this.orderBy = orderBy;
        }

        public String getAddUserId() {
            return addUserId;
        }

        public void setAddUserId(String addUserId) {
            this.addUserId = addUserId;
        }

        public String getAddTime() {
            return addTime;
        }

        public void setAddTime(String addTime) {
            this.addTime = addTime;
        }

        public String getCoopDescr() {
            return coopDescr;
        }

        public void setCoopDescr(String coopDescr) {
            this.coopDescr = coopDescr;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
