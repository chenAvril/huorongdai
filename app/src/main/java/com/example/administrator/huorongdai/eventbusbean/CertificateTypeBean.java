package com.example.administrator.huorongdai.eventbusbean;

import com.example.administrator.huorongdai.view.wheelview.interfaces.IPickerViewData;

/**
 * Created by Administrator on 2018/3/22 0022.
 *  证件类型
 * 0:身份证7：其他证件
 */

public class CertificateTypeBean implements IPickerViewData{
    private String name;
    private String value;

    public CertificateTypeBean(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getPickerViewText() {
        return name;
    }
}
