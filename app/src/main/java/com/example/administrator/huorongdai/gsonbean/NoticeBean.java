package com.example.administrator.huorongdai.gsonbean;

import java.util.List;

/**
 * Created by Administrator on 2018/3/13 0013.
 */

public class NoticeBean {

    /**
     * noticeList : [{"addTime":"20180312193735","id":"54d8920a70bc42c2936cb89852f9335a","noticeTitle":"关于2018.3.12的还款公告","orderBy":"0267","readCount":8},{"addTime":"20180311190338","id":"7f0d0ad99ccb4467b94f8afac4b1f5fc","noticeTitle":"关于2018.3.11的还款公告","orderBy":"0226","readCount":4},{"addTime":"20180311075739","id":"5672beea0c7a448fa189a5848acc6ee4","noticeTitle":"关于2018.3.10的还款公告","orderBy":"0225","readCount":3},{"addTime":"20180310113246","id":"3d2a48c577804b09bc3b7b515ad58944","noticeTitle":"关于回款短信取消通知","orderBy":"0224","readCount":8},{"addTime":"20180309203837","id":"e9ffe7b6444a4841a993feb254aa35c9","noticeTitle":"关于2018.3.9的还款公告","orderBy":"0223","readCount":6},{"addTime":"20180309162953","id":"a39fc267be234237ac1f720db55adb52","noticeTitle":"关于2018.3.8的还款公告","orderBy":"0222","readCount":7},{"addTime":"20180307203403","id":"7b4cba8134e64f80ac85333e05d7fd49","noticeTitle":"关于2018.3.7的还款公告","orderBy":"0221","readCount":16},{"addTime":"20180307091027","id":"1f216e531b054f0fb964cb21d01f1161","noticeTitle":"关于2018.3.6的还款公告","orderBy":"0220","readCount":11},{"addTime":"20180305181308","id":"fbd1e962b0e945b48070a913dfd83f97","noticeTitle":"关于2018.3.5的还款公告","orderBy":"0219","readCount":11},{"addTime":"20180304180932","id":"b4109127f41e4fbbbbb9f05ce2f32b73","noticeTitle":"关于2018.3.4的还款公告-2","orderBy":"0218","readCount":12}]
     * pageNum : 1
     * pageSize : 10
     * status : true
     * totalCount : 242
     * totalPages : 25
     */

    private String pageNum;
    private String pageSize;
    private boolean status;
    private String totalCount;
    private String totalPages;
    private List<NoticeListBean> noticeList;

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

    public List<NoticeListBean> getNoticeList() {
        return noticeList;
    }

    public void setNoticeList(List<NoticeListBean> noticeList) {
        this.noticeList = noticeList;
    }

    public static class NoticeListBean {
        /**
         * addTime : 20180312193735
         * id : 54d8920a70bc42c2936cb89852f9335a
         * noticeTitle : 关于2018.3.12的还款公告
         * orderBy : 0267
         * readCount : 8
         */

        private String addTime;
        private String id;
        private String noticeTitle;
        private String orderBy;
        private int readCount;

        public String getAddTime() {
            return addTime;
        }

        public void setAddTime(String addTime) {
            this.addTime = addTime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNoticeTitle() {
            return noticeTitle;
        }

        public void setNoticeTitle(String noticeTitle) {
            this.noticeTitle = noticeTitle;
        }

        public String getOrderBy() {
            return orderBy;
        }

        public void setOrderBy(String orderBy) {
            this.orderBy = orderBy;
        }

        public int getReadCount() {
            return readCount;
        }

        public void setReadCount(int readCount) {
            this.readCount = readCount;
        }
    }
}
