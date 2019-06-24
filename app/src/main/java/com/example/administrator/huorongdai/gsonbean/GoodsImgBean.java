package com.example.administrator.huorongdai.gsonbean;

import java.util.List;

/**
 * Created by Administrator on 2018/6/5 0005.
 */

public class GoodsImgBean {

    /**
     * shanglist : [{"indexPinyin":"shangping","indexName":"商品类型","dictCont":"汽车用品","dictRemark":"","dictReser":"","orderBy":"0001","contIndex":"qicheyongpin","id":"ffdcdea5c44c466b94a9f165afab810a"},{"indexPinyin":"shangping","indexName":"商品类型","dictCont":"家用物品","dictRemark":"","dictReser":"","orderBy":"0002","contIndex":"jiayong","id":"c6f6cd1494b54f0e8b95209c2ca8cb47"},{"indexPinyin":"shangping","indexName":"商品类型","dictCont":"包类物品","dictRemark":"","dictReser":"","orderBy":"0003","contIndex":"baolei","id":"f0ab9182d37149419aeeb82132a55316"},{"indexPinyin":"shangping","indexName":"商品类型","dictCont":"虚礼物品","dictRemark":"","dictReser":"","orderBy":"0004","contIndex":"xuni","id":"253658c820ac40f9ae7a13df208b3c28"},{"indexPinyin":"shangping","indexName":"商品类型","dictCont":"电子物品","dictRemark":"","dictReser":"","orderBy":"0005","contIndex":"dianzi","id":"e6b6bf5d080248ea80848dfaff458285"},{"indexPinyin":"shangping","indexName":"商品类型","dictCont":"投资红包","dictRemark":"","dictReser":"","orderBy":"0006","contIndex":"tzhongbao","id":"f63db808e554413d83d2ce91c6adf7ff"}]
     * bannerlist : [{"bannerTitle":"积分banner1","bannerIcon":"nstall/images/20180604/o_1cf4eu27jjr31uec123j1jnd10b7c.jpg","orderBy":"9991","bannerUrl":"#","bannerFlag":1,"addTime":"20180604115534","bannerType":3,"startTime":"20180604115503","endTime":"20880601000000","id":"e5d8d42ffbf046cdad1be35129b2c514"},{"bannerTitle":"积分banner2","bannerIcon":"nstall/images/20180604/o_1cf4evb74ie2uok1quhpvu1gsjc.jpg","orderBy":"9992","bannerUrl":"#","bannerFlag":1,"addTime":"20180604115608","bannerType":3,"startTime":"20180604115543","endTime":"20180604115545","id":"b863207ad7f84262980c5efc03ce9ace"},{"bannerTitle":"积分banner3","bannerIcon":"nstall/images/20180604/o_1cf4f0buh1h1l1d8v1l131vkslrec.jpg","orderBy":"9993","bannerUrl":"#","bannerFlag":1,"addTime":"20180604115643","bannerType":3,"startTime":"20180604115617","endTime":"20180604115619","id":"55aa2999b667466781bf5efcabd56aea"}]
     * message : 查询成功
     * status : true
     */

    private String message;
    private String status;
    private List<ShanglistBean> shanglist;
    private List<BannerlistBean> bannerlist;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ShanglistBean> getShanglist() {
        return shanglist;
    }

    public void setShanglist(List<ShanglistBean> shanglist) {
        this.shanglist = shanglist;
    }

    public List<BannerlistBean> getBannerlist() {
        return bannerlist;
    }

    public void setBannerlist(List<BannerlistBean> bannerlist) {
        this.bannerlist = bannerlist;
    }

    public static class ShanglistBean {
        /**
         * indexPinyin : shangping
         * indexName : 商品类型
         * dictCont : 汽车用品
         * dictRemark :
         * dictReser :
         * orderBy : 0001
         * contIndex : qicheyongpin
         * id : ffdcdea5c44c466b94a9f165afab810a
         */

        private String indexPinyin;
        private String indexName;
        private String dictCont;
        private String dictRemark;
        private String dictReser;
        private String orderBy;
        private String contIndex;
        private String id;

        public String getIndexPinyin() {
            return indexPinyin;
        }

        public void setIndexPinyin(String indexPinyin) {
            this.indexPinyin = indexPinyin;
        }

        public String getIndexName() {
            return indexName;
        }

        public void setIndexName(String indexName) {
            this.indexName = indexName;
        }

        public String getDictCont() {
            return dictCont;
        }

        public void setDictCont(String dictCont) {
            this.dictCont = dictCont;
        }

        public String getDictRemark() {
            return dictRemark;
        }

        public void setDictRemark(String dictRemark) {
            this.dictRemark = dictRemark;
        }

        public String getDictReser() {
            return dictReser;
        }

        public void setDictReser(String dictReser) {
            this.dictReser = dictReser;
        }

        public String getOrderBy() {
            return orderBy;
        }

        public void setOrderBy(String orderBy) {
            this.orderBy = orderBy;
        }

        public String getContIndex() {
            return contIndex;
        }

        public void setContIndex(String contIndex) {
            this.contIndex = contIndex;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public static class BannerlistBean {
        /**
         * bannerTitle : 积分banner1
         * bannerIcon : nstall/images/20180604/o_1cf4eu27jjr31uec123j1jnd10b7c.jpg
         * orderBy : 9991
         * bannerUrl : #
         * bannerFlag : 1
         * addTime : 20180604115534
         * bannerType : 3
         * startTime : 20180604115503
         * endTime : 20880601000000
         * id : e5d8d42ffbf046cdad1be35129b2c514
         */

        private String bannerTitle;
        private String bannerIcon;
        private String orderBy;
        private String bannerUrl;
        private int bannerFlag;
        private String addTime;
        private int bannerType;
        private String startTime;
        private String endTime;
        private String id;

        public String getBannerTitle() {
            return bannerTitle;
        }

        public void setBannerTitle(String bannerTitle) {
            this.bannerTitle = bannerTitle;
        }

        public String getBannerIcon() {
            return bannerIcon;
        }

        public void setBannerIcon(String bannerIcon) {
            this.bannerIcon = bannerIcon;
        }

        public String getOrderBy() {
            return orderBy;
        }

        public void setOrderBy(String orderBy) {
            this.orderBy = orderBy;
        }

        public String getBannerUrl() {
            return bannerUrl;
        }

        public void setBannerUrl(String bannerUrl) {
            this.bannerUrl = bannerUrl;
        }

        public int getBannerFlag() {
            return bannerFlag;
        }

        public void setBannerFlag(int bannerFlag) {
            this.bannerFlag = bannerFlag;
        }

        public String getAddTime() {
            return addTime;
        }

        public void setAddTime(String addTime) {
            this.addTime = addTime;
        }

        public int getBannerType() {
            return bannerType;
        }

        public void setBannerType(int bannerType) {
            this.bannerType = bannerType;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
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
    }
}
