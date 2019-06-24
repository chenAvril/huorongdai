package com.example.administrator.huorongdai.gsonbean;

import java.util.List;

public class ProgressListBean {

    /**
     * message : 查询成功
     * state : ["已完成","已完成","已完成","已完成","已完成","已完成","已完成","已完成","已完成","已完成","已完成","已完成","已完成","已完成","超额标的整改已完成","系统内测中......","提交备案资料，等待审核","测评通过，等待核发证书"]
     * status : true
     * title : ["网络信息中介银行存管系统","出具项目风险提示书","向有关数据统计部门汇报数据","对出借人进行实名注册","记录并保存网络借贷业务活动数据和资料","对出借人、借款人资格、融资项目等真实情况的审核制度","妥善保管出借人与借款人的资料和交易信息","履行客户身份识别和交易记录保存等","对出借人可获取的渠道提示网贷市场的风险性","平台真实、合法、充分的信息数据披露","对网站经营信息数据进行公示","会计师事务所出具的审计报告","律师事务所出具的法律意见书","对大额项目标的整改","电子签章系统上线","履行网络借贷备案手续","信息系统安全等级保护测评及备案（等保三级）"]
     */

    private String message;
    private boolean status;
    private List<String> state;
    private List<String> title;

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

    public List<String> getState() {
        return state;
    }

    public void setState(List<String> state) {
        this.state = state;
    }

    public List<String> getTitle() {
        return title;
    }

    public void setTitle(List<String> title) {
        this.title = title;
    }
}
