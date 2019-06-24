package com.example.administrator.huorongdai.gsonbean;

import java.util.List;

/**
 * Created by Administrator on 2018/3/29 0029.
 */

public class GetSignBean {

    /**
     * listDate : ["8","16","17","18","23","27","29"]
     * dateNow : 2018-03-29
     * status : true
     */

    private String dateNow;
    private boolean status;
    private List<String> listDate;

    public String getDateNow() {
        return dateNow;
    }

    public void setDateNow(String dateNow) {
        this.dateNow = dateNow;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<String> getListDate() {
        return listDate;
    }

    public void setListDate(List<String> listDate) {
        this.listDate = listDate;
    }
}
