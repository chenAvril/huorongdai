package com.example.administrator.huorongdai.gsonbean;

import java.util.List;

public class RunReportBean {


    /**
     * totalPages : 4
     * pageSize : 1
     * list : [{"infopubTitle":"1111","infopubImg":"nstall/images/20180404/o_1ca7lnoj81afb124r1h0kfd6tqhc.jpg","releaseTime":"20180404143256","infopubFile":"nstall/video/20180412/o_1cas16m3vqu9lkcdjlk36s0fc.pdf","addUserId":"e927a4b42d454b7a96028b359952fa94","id":"71441b98295b4eab92d8eb903249d46e"}]
     * totalCount : 4
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
         * infopubTitle : 1111
         * infopubImg : nstall/images/20180404/o_1ca7lnoj81afb124r1h0kfd6tqhc.jpg
         * releaseTime : 20180404143256
         * infopubFile : nstall/video/20180412/o_1cas16m3vqu9lkcdjlk36s0fc.pdf
         * addUserId : e927a4b42d454b7a96028b359952fa94
         * id : 71441b98295b4eab92d8eb903249d46e
         */

        private String infopubTitle;
        private String infopubImg;
        private String releaseTime;
        private String infopubFile;
        private String addUserId;
        private String id;

        public String getInfopubTitle() {
            return infopubTitle;
        }

        public void setInfopubTitle(String infopubTitle) {
            this.infopubTitle = infopubTitle;
        }

        public String getInfopubImg() {
            return infopubImg;
        }

        public void setInfopubImg(String infopubImg) {
            this.infopubImg = infopubImg;
        }

        public String getReleaseTime() {
            return releaseTime;
        }

        public void setReleaseTime(String releaseTime) {
            this.releaseTime = releaseTime;
        }

        public String getInfopubFile() {
            return infopubFile;
        }

        public void setInfopubFile(String infopubFile) {
            this.infopubFile = infopubFile;
        }

        public String getAddUserId() {
            return addUserId;
        }

        public void setAddUserId(String addUserId) {
            this.addUserId = addUserId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
