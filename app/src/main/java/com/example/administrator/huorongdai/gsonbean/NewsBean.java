package com.example.administrator.huorongdai.gsonbean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Administrator on 2018/4/9 0009.
 */

public class NewsBean {

    /**
     * newsList : [{"addTime":"20170724110144","addUserId":"83e90ae214e34f769b07672fdd0c7a4d","id":"bb4301c529414f298b1725b907f55ae1","newsContent":"<style>img{max-width:80%;height:auto;}<\/style>111","newsIcon":"nstall/images/20170724/face_1.jpg","newsScource":"ddd","newsSummaey":"ddd","newsTitle":"ddd","newsType":"83f6e33a99f34b2faea9509a02a18252","orderBy":"0009","readCount":8}]
     * pageNum : 1
     * pageSize : 20
     * status : true
     * totalCount : 6
     * totalPages : 1
     */

    private String pageNum;
    private String pageSize;
    private boolean status;
    private String totalCount;
    private String totalPages;
    private List<NewsListBean> newsList;

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

    public List<NewsListBean> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<NewsListBean> newsList) {
        this.newsList = newsList;
    }

    public static class NewsListBean implements Parcelable {

        /**
         * addTime : 20170724110144
         * addUserId : 83e90ae214e34f769b07672fdd0c7a4d
         * id : bb4301c529414f298b1725b907f55ae1
         * newsContent : <style>img{max-width:80%;height:auto;}</style>111
         * newsIcon : nstall/images/20170724/face_1.jpg
         * newsScource : ddd
         * newsSummaey : ddd
         * newsTitle : ddd
         * newsType : 83f6e33a99f34b2faea9509a02a18252
         * orderBy : 0009
         * readCount : 8
         */

        private String addTime;
        private String addUserId;
        private String id;
        private String newsContent;
        private String newsIcon;
        private String newsScource;
        private String newsSummaey;
        private String newsTitle;
        private String newsType;
        private String orderBy;
        private int readCount;

        public String getAddTime() {
            return addTime;
        }

        public void setAddTime(String addTime) {
            this.addTime = addTime;
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

        public String getNewsContent() {
            return newsContent;
        }

        public void setNewsContent(String newsContent) {
            this.newsContent = newsContent;
        }

        public String getNewsIcon() {
            return newsIcon;
        }

        public void setNewsIcon(String newsIcon) {
            this.newsIcon = newsIcon;
        }

        public String getNewsScource() {
            return newsScource;
        }

        public void setNewsScource(String newsScource) {
            this.newsScource = newsScource;
        }

        public String getNewsSummaey() {
            return newsSummaey;
        }

        public void setNewsSummaey(String newsSummaey) {
            this.newsSummaey = newsSummaey;
        }

        public String getNewsTitle() {
            return newsTitle;
        }

        public void setNewsTitle(String newsTitle) {
            this.newsTitle = newsTitle;
        }

        public String getNewsType() {
            return newsType;
        }

        public void setNewsType(String newsType) {
            this.newsType = newsType;
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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.addTime);
            dest.writeString(this.addUserId);
            dest.writeString(this.id);
            dest.writeString(this.newsContent);
            dest.writeString(this.newsIcon);
            dest.writeString(this.newsScource);
            dest.writeString(this.newsSummaey);
            dest.writeString(this.newsTitle);
            dest.writeString(this.newsType);
            dest.writeString(this.orderBy);
            dest.writeInt(this.readCount);
        }

        public NewsListBean() {
        }

        protected NewsListBean(Parcel in) {
            this.addTime = in.readString();
            this.addUserId = in.readString();
            this.id = in.readString();
            this.newsContent = in.readString();
            this.newsIcon = in.readString();
            this.newsScource = in.readString();
            this.newsSummaey = in.readString();
            this.newsTitle = in.readString();
            this.newsType = in.readString();
            this.orderBy = in.readString();
            this.readCount = in.readInt();
        }

        public static final Parcelable.Creator<NewsListBean> CREATOR = new Parcelable.Creator<NewsListBean>() {
            @Override
            public NewsListBean createFromParcel(Parcel source) {
                return new NewsListBean(source);
            }

            @Override
            public NewsListBean[] newArray(int size) {
                return new NewsListBean[size];
            }
        };
    }
}
