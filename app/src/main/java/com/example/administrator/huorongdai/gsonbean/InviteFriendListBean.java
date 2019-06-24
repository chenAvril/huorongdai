package com.example.administrator.huorongdai.gsonbean;

import java.util.List;

/**
 * Created by Administrator on 2018/4/10 0010.
 */

public class InviteFriendListBean {

    /**
     * inviteList : [{"custName":"hrd_18325388888","inviteIvstRedp":"-","invitePoint":"-","inviteRegistRedp":"-","registTimeStr":"2017-08-01 09:22:28"},{"custName":"hrd_18388888888","inviteIvstRedp":"-","invitePoint":"-","inviteRegistRedp":"-","registTimeStr":"2017-07-29 15:27:26"},{"custName":"hrd_18375333620","inviteIvstRedp":"-","invitePoint":"-","inviteRegistRedp":"-","registTimeStr":"2017-07-27 16:27:07"},{"custName":"hrd_18375333612","inviteIvstRedp":"-","invitePoint":"-","inviteRegistRedp":"-","registTimeStr":"2017-07-27 16:23:36"},{"custName":"hrd_18325383861","inviteIvstRedp":"-","invitePoint":"-","inviteRegistRedp":"-","registTimeStr":"2017-07-26 15:57:31"},{"custName":"hrd_18325383860","inviteIvstRedp":"-","invitePoint":"-","inviteRegistRedp":"-","registTimeStr":"2017-07-26 09:17:07"},{"custName":"hrd_18375333611","inviteIvstRedp":"-","invitePoint":"-","inviteRegistRedp":"-","registTimeStr":"2017-07-25 09:35:11"}]
     * pageNum : 1
     * pageSize : 20
     * status : true
     * totalCount : 7
     * totalPages : 1
     */

    private String pageNum;
    private String pageSize;
    private boolean status;
    private String totalCount;
    private String totalPages;
    private List<InviteListBean> inviteList;

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

    public List<InviteListBean> getInviteList() {
        return inviteList;
    }

    public void setInviteList(List<InviteListBean> inviteList) {
        this.inviteList = inviteList;
    }

    public static class InviteListBean {
        /**
         * custName : hrd_18325388888
         * inviteIvstRedp : -
         * invitePoint : -
         * inviteRegistRedp : -
         * registTimeStr : 2017-08-01 09:22:28
         */

        private String custName;
        private String inviteIvstRedp;
        private String invitePoint;
        private String inviteRegistRedp;
        private String registTimeStr;

        public String getCustName() {
            return custName;
        }

        public void setCustName(String custName) {
            this.custName = custName;
        }

        public String getInviteIvstRedp() {
            return inviteIvstRedp;
        }

        public void setInviteIvstRedp(String inviteIvstRedp) {
            this.inviteIvstRedp = inviteIvstRedp;
        }

        public String getInvitePoint() {
            return invitePoint;
        }

        public void setInvitePoint(String invitePoint) {
            this.invitePoint = invitePoint;
        }

        public String getInviteRegistRedp() {
            return inviteRegistRedp;
        }

        public void setInviteRegistRedp(String inviteRegistRedp) {
            this.inviteRegistRedp = inviteRegistRedp;
        }

        public String getRegistTimeStr() {
            return registTimeStr;
        }

        public void setRegistTimeStr(String registTimeStr) {
            this.registTimeStr = registTimeStr;
        }
    }
}
