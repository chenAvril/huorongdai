package com.example.administrator.huorongdai.gsonbean;

import java.util.List;

/**
 * Created by Administrator on 2018/3/13 0013.
 */

public class BannerBean {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * addTime : 20180310103410
         * bannerFlag : 1
         * bannerIcon : nstall/images/20180310/o_1c87jmft411pcm721gn09u0orfc.png
         * bannerTitle : 白色情人节app
         * bannerType : 2
         * bannerUrl : http://www.huorongdai.com/activity/Whiteday/index.html
         * endTime : 20190301000000
         * id : 54204c4272a44ec7bcb943ab9615b590
         * orderBy : 0066
         * startTime : 20180311000000
         */

        private String addTime;
        private int bannerFlag;
        private String bannerIcon;
        private String bannerTitle;
        private int bannerType;
        private String bannerUrl;
        private String endTime;
        private String id;
        private String orderBy;
        private String startTime;

        public String getAddTime() {
            return addTime;
        }

        public void setAddTime(String addTime) {
            this.addTime = addTime;
        }

        public int getBannerFlag() {
            return bannerFlag;
        }

        public void setBannerFlag(int bannerFlag) {
            this.bannerFlag = bannerFlag;
        }

        public String getBannerIcon() {
            return bannerIcon;
        }

        public void setBannerIcon(String bannerIcon) {
            this.bannerIcon = bannerIcon;
        }

        public String getBannerTitle() {
            return bannerTitle;
        }

        public void setBannerTitle(String bannerTitle) {
            this.bannerTitle = bannerTitle;
        }

        public int getBannerType() {
            return bannerType;
        }

        public void setBannerType(int bannerType) {
            this.bannerType = bannerType;
        }

        public String getBannerUrl() {
            return bannerUrl;
        }

        public void setBannerUrl(String bannerUrl) {
            this.bannerUrl = bannerUrl;
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

        public String getOrderBy() {
            return orderBy;
        }

        public void setOrderBy(String orderBy) {
            this.orderBy = orderBy;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }
    }
}
