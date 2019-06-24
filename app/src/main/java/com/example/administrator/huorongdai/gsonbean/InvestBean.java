package com.example.administrator.huorongdai.gsonbean;

/**
 * Created by Administrator on 2018/4/3 0003.
 */

public class InvestBean {

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getInveststatus() {
        return investstatus;
    }

    public void setInveststatus(String investstatus) {
        this.investstatus = investstatus;
    }

    /**
     * message : 投资密码不正确!
     * optResultMsg : 恭喜您投资项目：测试还款12月标03成功，投资金额为：1000.0
     * status : true
     */

    private String url;
    private String investstatus;
    private String message;
    private String optResultMsg;
    private boolean status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOptResultMsg() {
        return optResultMsg;
    }

    public void setOptResultMsg(String optResultMsg) {
        this.optResultMsg = optResultMsg;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
