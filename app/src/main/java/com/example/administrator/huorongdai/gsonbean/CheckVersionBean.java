package com.example.administrator.huorongdai.gsonbean;

/**
 * Created by Administrator on 2018/4/14 0014.
 */

public class CheckVersionBean {


    /**
     * data : {"appFile":"http://enze.oss-cn-shanghai.aliyuncs.com/nstall/images/20170805/H583A2CB6_0805140750.apk","status":true,"updateReason":"<p>账户新增积分商城，投资奖品兑换<\/p>","verCode":9,"verName":"货融贷2.8.0","message":""}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * appFile : http://enze.oss-cn-shanghai.aliyuncs.com/nstall/images/20170805/H583A2CB6_0805140750.apk
         * status : true
         * updateReason : <p>账户新增积分商城，投资奖品兑换</p>
         * verCode : 9
         * verName : 货融贷2.8.0
         * message :
         */

        private String appFile;
        private boolean status;
        private String updateReason;
        private int verCode;
        private String verName;
        private String message;

        public String getAppFile() {
            return appFile;
        }

        public void setAppFile(String appFile) {
            this.appFile = appFile;
        }

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }

        public String getUpdateReason() {
            return updateReason;
        }

        public void setUpdateReason(String updateReason) {
            this.updateReason = updateReason;
        }

        public int getVerCode() {
            return verCode;
        }

        public void setVerCode(int verCode) {
            this.verCode = verCode;
        }

        public String getVerName() {
            return verName;
        }

        public void setVerName(String verName) {
            this.verName = verName;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
