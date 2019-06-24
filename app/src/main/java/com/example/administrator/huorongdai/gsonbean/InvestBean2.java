package com.example.administrator.huorongdai.gsonbean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2018/5/31 0031.
 */

public class InvestBean2 {

    /**
     * investstatus : 2
     * map : {"amt":"10000","back_notify_url":"http://192.168.0.194:8080/hrd_web//trade/invest/passwordFreezeRet","busi_tp":"00","client_tp":"1","code":"passwordFreeze","login_id":"15155118003","login_id_in":"15155118080","mchnt_cd":"0002900F0354242","mchnt_txn_ssn":"20180531163737k5TNuajm","page_notify_url":"http://192.168.0.194:8080/hrd_web//app/customer/investRet","project_no":"ZB20180530140251219","signature":"sBMxQ2tkn9nNqU3s8//GX84cj0lyXnKLnslzyFwqV2N1OldTd+eBnon+7nqlD5A93o4untqa8PP1shUPDe+geGaz6lxjOBy8Bp07dXL/J7aTQN7PT0AJumGe9mXmGz3OtpJBWt499p2HS8koRxR9I/GUQyhogh+rBwaoabALdnY=","url":"http://180.168.100.156:8090/control.action","ver":"1.00"}
     * status : true
     * url : http://180.168.100.156:8090/control.action?page_notify_url=http://192.168.0.194:8080/hrd_web//app/customer/investRet&login_id=15155118003&ver=1.00&code=passwordFreeze&login_id_in=15155118080&signature=sBMxQ2tkn9nNqU3s8//GX84cj0lyXnKLnslzyFwqV2N1OldTd+eBnon+7nqlD5A93o4untqa8PP1shUPDe+geGaz6lxjOBy8Bp07dXL/J7aTQN7PT0AJumGe9mXmGz3OtpJBWt499p2HS8koRxR9I/GUQyhogh+rBwaoabALdnY=&amt=10000&mchnt_txn_ssn=20180531163737k5TNuajm&mchnt_cd=0002900F0354242&url=http://180.168.100.156:8090/control.action&client_tp=1&project_no=ZB20180530140251219&back_notify_url=http://192.168.0.194:8080/hrd_web//trade/invest/passwordFreezeRet&busi_tp=00
     */

    private String investstatus;
    private MapBean map;
    private String status;
    private String url;

    public String getInveststatus() {
        return investstatus;
    }

    public void setInveststatus(String investstatus) {
        this.investstatus = investstatus;
    }

    public MapBean getMap() {
        return map;
    }

    public void setMap(MapBean map) {
        this.map = map;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static class MapBean implements Parcelable {

        /**
         * amt : 10000
         * back_notify_url : http://192.168.0.194:8080/hrd_web//trade/invest/passwordFreezeRet
         * busi_tp : 00
         * client_tp : 1
         * code : passwordFreeze
         * login_id : 15155118003
         * login_id_in : 15155118080
         * mchnt_cd : 0002900F0354242
         * mchnt_txn_ssn : 20180531163737k5TNuajm
         * page_notify_url : http://192.168.0.194:8080/hrd_web//app/customer/investRet
         * project_no : ZB20180530140251219
         * signature : sBMxQ2tkn9nNqU3s8//GX84cj0lyXnKLnslzyFwqV2N1OldTd+eBnon+7nqlD5A93o4untqa8PP1shUPDe+geGaz6lxjOBy8Bp07dXL/J7aTQN7PT0AJumGe9mXmGz3OtpJBWt499p2HS8koRxR9I/GUQyhogh+rBwaoabALdnY=
         * url : http://180.168.100.156:8090/control.action
         * ver : 1.00
         */

        private String amt;
        private String back_notify_url;
        private String busi_tp;
        private String client_tp;
        private String code;
        private String login_id;
        private String login_id_in;
        private String mchnt_cd;
        private String mchnt_txn_ssn;
        private String page_notify_url;
        private String project_no;
        private String signature;
        private String url;
        private String ver;

        public String getAmt() {
            return amt;
        }

        public void setAmt(String amt) {
            this.amt = amt;
        }

        public String getBack_notify_url() {
            return back_notify_url;
        }

        public void setBack_notify_url(String back_notify_url) {
            this.back_notify_url = back_notify_url;
        }

        public String getBusi_tp() {
            return busi_tp;
        }

        public void setBusi_tp(String busi_tp) {
            this.busi_tp = busi_tp;
        }

        public String getClient_tp() {
            return client_tp;
        }

        public void setClient_tp(String client_tp) {
            this.client_tp = client_tp;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getLogin_id() {
            return login_id;
        }

        public void setLogin_id(String login_id) {
            this.login_id = login_id;
        }

        public String getLogin_id_in() {
            return login_id_in;
        }

        public void setLogin_id_in(String login_id_in) {
            this.login_id_in = login_id_in;
        }

        public String getMchnt_cd() {
            return mchnt_cd;
        }

        public void setMchnt_cd(String mchnt_cd) {
            this.mchnt_cd = mchnt_cd;
        }

        public String getMchnt_txn_ssn() {
            return mchnt_txn_ssn;
        }

        public void setMchnt_txn_ssn(String mchnt_txn_ssn) {
            this.mchnt_txn_ssn = mchnt_txn_ssn;
        }

        public String getPage_notify_url() {
            return page_notify_url;
        }

        public void setPage_notify_url(String page_notify_url) {
            this.page_notify_url = page_notify_url;
        }

        public String getProject_no() {
            return project_no;
        }

        public void setProject_no(String project_no) {
            this.project_no = project_no;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getVer() {
            return ver;
        }

        public void setVer(String ver) {
            this.ver = ver;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.amt);
            dest.writeString(this.back_notify_url);
            dest.writeString(this.busi_tp);
            dest.writeString(this.client_tp);
            dest.writeString(this.code);
            dest.writeString(this.login_id);
            dest.writeString(this.login_id_in);
            dest.writeString(this.mchnt_cd);
            dest.writeString(this.mchnt_txn_ssn);
            dest.writeString(this.page_notify_url);
            dest.writeString(this.project_no);
            dest.writeString(this.signature);
            dest.writeString(this.url);
            dest.writeString(this.ver);
        }

        public MapBean() {
        }

        protected MapBean(Parcel in) {
            this.amt = in.readString();
            this.back_notify_url = in.readString();
            this.busi_tp = in.readString();
            this.client_tp = in.readString();
            this.code = in.readString();
            this.login_id = in.readString();
            this.login_id_in = in.readString();
            this.mchnt_cd = in.readString();
            this.mchnt_txn_ssn = in.readString();
            this.page_notify_url = in.readString();
            this.project_no = in.readString();
            this.signature = in.readString();
            this.url = in.readString();
            this.ver = in.readString();
        }

        public static final Parcelable.Creator<MapBean> CREATOR = new Parcelable.Creator<MapBean>() {
            @Override
            public MapBean createFromParcel(Parcel source) {
                return new MapBean(source);
            }

            @Override
            public MapBean[] newArray(int size) {
                return new MapBean[size];
            }
        };
    }
}
