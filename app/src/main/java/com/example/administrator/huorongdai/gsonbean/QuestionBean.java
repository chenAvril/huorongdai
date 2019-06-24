package com.example.administrator.huorongdai.gsonbean;

import java.util.List;

/**
 * Created by Administrator on 2018/5/25 0025.
 */

public class QuestionBean {

    /**
     * list : [{"addTime":"20180524145644","articleDescription":"","articleFlag":1,"articleTitle":"客服电话多少?","articleType":7,"id":"b257ecc93aae4dbaa36feb1461ce2b7a","orderBy":"0027"},{"addTime":"20180524145713","articleDescription":"","articleFlag":1,"articleTitle":"客服QQ多少?","articleType":7,"id":"bb7e766a98034b19ba3d7d6d089db8dc","orderBy":"0028"}]
     * message : 查询成功
     * status : true
     */

    private String message;
    private boolean status;
    private List<ListBean> list;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * addTime : 20180524145644
         * articleDescription :
         * articleFlag : 1
         * articleTitle : 客服电话多少?
         * articleType : 7
         * id : b257ecc93aae4dbaa36feb1461ce2b7a
         * orderBy : 0027
         */

        private String addTime;
        private String articleDescription;
        private int articleFlag;
        private String articleTitle;
        private int articleType;
        private String id;
        private String orderBy;

        public String getAddTime() {
            return addTime;
        }

        public void setAddTime(String addTime) {
            this.addTime = addTime;
        }

        public String getArticleDescription() {
            return articleDescription;
        }

        public void setArticleDescription(String articleDescription) {
            this.articleDescription = articleDescription;
        }

        public int getArticleFlag() {
            return articleFlag;
        }

        public void setArticleFlag(int articleFlag) {
            this.articleFlag = articleFlag;
        }

        public String getArticleTitle() {
            return articleTitle;
        }

        public void setArticleTitle(String articleTitle) {
            this.articleTitle = articleTitle;
        }

        public int getArticleType() {
            return articleType;
        }

        public void setArticleType(int articleType) {
            this.articleType = articleType;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrderBy() {
            return orderBy;
        }

        public void setOrderBy(String orderBy) {
            this.orderBy = orderBy;
        }
    }
}
