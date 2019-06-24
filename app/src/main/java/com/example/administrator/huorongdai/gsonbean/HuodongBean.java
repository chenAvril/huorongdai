package com.example.administrator.huorongdai.gsonbean;

import java.util.List;

/**
 * Created by Administrator on 2018/3/15 0015.
 */

public class HuodongBean {


    /**
     * list : [{"activesDesc":"","activesFlag":1,"activesImage":"nstall/images/20180314/o_1c8i1vblv16lgcns132f1fnlgfjc.png","activesName":"龍抬頭","activesStatus":1,"activesUrl":"https://www.huorongdai.com/activity/dragUp/index.html","activesUrlw":"https://www.huorongdai.com/activity/dragUp/index.html","endTime":"2018-03-05","id":"225f8e0889be48eb9e42a0fc5cbaca20","startTime":"2018-03-15"}]
     * pa : {"pageCount":1,"pageSize":30,"recordCount":26,"showPage":1}
     */

    private PaBean pa;
    private List<ListBean> list;

    public PaBean getPa() {
        return pa;
    }

    public void setPa(PaBean pa) {
        this.pa = pa;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class PaBean {
        /**
         * pageCount : 1
         * pageSize : 30
         * recordCount : 26
         * showPage : 1
         */

        private int pageCount;
        private int pageSize;
        private int recordCount;
        private int showPage;

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getRecordCount() {
            return recordCount;
        }

        public void setRecordCount(int recordCount) {
            this.recordCount = recordCount;
        }

        public int getShowPage() {
            return showPage;
        }

        public void setShowPage(int showPage) {
            this.showPage = showPage;
        }
    }

    public static class ListBean {
        /**
         * activesDesc :
         * activesFlag : 1
         * activesImage : nstall/images/20180314/o_1c8i1vblv16lgcns132f1fnlgfjc.png
         * activesName : 龍抬頭
         * activesStatus : 1
         * activesUrl : https://www.huorongdai.com/activity/dragUp/index.html
         * activesUrlw : https://www.huorongdai.com/activity/dragUp/index.html
         * endTime : 2018-03-05
         * id : 225f8e0889be48eb9e42a0fc5cbaca20
         * startTime : 2018-03-15
         */

        private String activesDesc;
        private int activesFlag;
        private String activesImage;
        private String activesName;
        private int activesStatus;
        private String activesUrl;
        private String activesUrlw;
        private String endTime;
        private String id;
        private String startTime;

        public String getActivesDesc() {
            return activesDesc;
        }

        public void setActivesDesc(String activesDesc) {
            this.activesDesc = activesDesc;
        }

        public int getActivesFlag() {
            return activesFlag;
        }

        public void setActivesFlag(int activesFlag) {
            this.activesFlag = activesFlag;
        }

        public String getActivesImage() {
            return activesImage;
        }

        public void setActivesImage(String activesImage) {
            this.activesImage = activesImage;
        }

        public String getActivesName() {
            return activesName;
        }

        public void setActivesName(String activesName) {
            this.activesName = activesName;
        }

        public int getActivesStatus() {
            return activesStatus;
        }

        public void setActivesStatus(int activesStatus) {
            this.activesStatus = activesStatus;
        }

        public String getActivesUrl() {
            return activesUrl;
        }

        public void setActivesUrl(String activesUrl) {
            this.activesUrl = activesUrl;
        }

        public String getActivesUrlw() {
            return activesUrlw;
        }

        public void setActivesUrlw(String activesUrlw) {
            this.activesUrlw = activesUrlw;
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

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }
    }
}
