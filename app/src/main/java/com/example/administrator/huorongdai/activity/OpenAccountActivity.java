package com.example.administrator.huorongdai.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.huorongdai.Path;
import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.base.BaseActivity;
import com.example.administrator.huorongdai.eventbusbean.CertificateTypeBean;
import com.example.administrator.huorongdai.gsonbean.BankNameBean;
import com.example.administrator.huorongdai.gsonbean.CityBean;
import com.example.administrator.huorongdai.gsonbean.OpenAccBean;
import com.example.administrator.huorongdai.view.pickerview.builder.OptionsPickerBuilder;
import com.example.administrator.huorongdai.view.pickerview.listener.OnOptionsSelectListener;
import com.example.administrator.huorongdai.view.pickerview.view.OptionsPickerView;
import com.example.administrator.huorongdai.xframe.utils.XEmptyUtils;
import com.example.administrator.huorongdai.xframe.utils.XKeyboardUtils;
import com.example.administrator.huorongdai.xframe.utils.XNetworkUtils;
import com.example.administrator.huorongdai.xframe.utils.XPreferencesUtils;
import com.example.administrator.huorongdai.xframe.utils.XRegexUtils;
import com.example.administrator.huorongdai.xframe.utils.http.HttpCallBack;
import com.example.administrator.huorongdai.xframe.utils.http.XHttp;
import com.example.administrator.huorongdai.xframe.widget.XToast;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OpenAccountActivity extends BaseActivity {

    private EditText cusRealNameEt;//用户真实姓名
    private LinearLayout certificateTypeLl;//证件类型
    private TextView certificateTypeTv;//证件类型
    private EditText certificateNumberTv;//证件号码
    private LinearLayout bankNameLl;//银行名称
    private TextView bankNameTv;//银行名称
    private LinearLayout bankCityLl;//开户行地区
    private TextView bankCityTv;//开户行地区
    private EditText bankCardNumberEt;//银行卡号
    private EditText tradePasswordEt;//投资密码
    private EditText tradePasswordSuredEt;//确认投资密码
    private TextView openAccBtn;//认证开户

    private OptionsPickerView certificateOptions;//证件类型选择器
    private List<CertificateTypeBean> certificateList;//证件类型（0:身份证7：其他证件）

    private OptionsPickerView bankOptions;//银行选择器
    private List<CertificateTypeBean> bankList;//银行

    private OptionsPickerView cityOptions;//开户行地区选择器
    private List<CertificateTypeBean> oneLevelList;//一级数据
    private List<List<CertificateTypeBean>> twoLevelList;//二级数据

    @Override
    protected int getContentView() {
        return R.layout.activity_open_account;
    }

    @Override
    protected void initView() {
        setTitle("开户");
        cusRealNameEt=findViewById(R.id.cus_real_name_et);
        certificateTypeTv=findViewById(R.id.certificate_type_tv);
        certificateNumberTv=findViewById(R.id.certificate_number_tv);
        certificateTypeLl=findViewById(R.id.certificate_type_ll);
        bankNameLl=findViewById(R.id.bank_name_ll);
        bankNameTv=findViewById(R.id.bank_name_tv);
        bankCityLl=findViewById(R.id.bank_city_ll);
        bankCityTv=findViewById(R.id.bank_city_tv);
        bankCardNumberEt=findViewById(R.id.bank_card_number_et);
        tradePasswordEt=findViewById(R.id.trade_password_et);
        tradePasswordSuredEt=findViewById(R.id.trade_password_sure_et);
        openAccBtn=findViewById(R.id.btn_open_acc);

        certificateTypeLl.setOnClickListener(this);
        bankNameLl.setOnClickListener(this);
        bankCityLl.setOnClickListener(this);
        openAccBtn.setOnClickListener(this);

        certificateList=new ArrayList<>();
        certificateList.add(new CertificateTypeBean("身份证","0"));
        certificateList.add(new CertificateTypeBean("其他证件","7"));
        initCertificateOptionPicker();

        oneLevelList=new ArrayList<>();
        twoLevelList=new ArrayList<>();
        String JsonData = getJson(this, "city.json");//获取assets目录下的json文件数据
        CityBean cityBean= new Gson().fromJson(JsonData, CityBean.class);
        for (CityBean.DataBean dataBean : cityBean.getData()) {
            oneLevelList.add(new CertificateTypeBean(dataBean.getText(),dataBean.getValue()));

            List<CertificateTypeBean> beanList=new ArrayList<>();
            for (CityBean.DataBean.ChildrenBean childrenBean : dataBean.getChildren()) {
                beanList.add(new CertificateTypeBean(childrenBean.getText(),childrenBean.getValue()));
            }
            twoLevelList.add(beanList);
        }
        initCityOptionPicker();
    }

    @Override
    protected void loadData() {

        if(XNetworkUtils.isConnected()){
            bankList=new ArrayList<>();
            XHttp.obtain().get(Path.bank_name, new HashMap<String, Object>(), new HttpCallBack<BankNameBean>() {
                @Override
                public void onSuccess(BankNameBean bankNameBean) {
                    if(bankNameBean.getData().size()>0){
                        for (BankNameBean.DataBean dataBean : bankNameBean.getData()) {
                            bankList.add(new CertificateTypeBean(dataBean.getBankName(),dataBean.getBankCode()));
                        }
                    }
                }

                @Override
                public void onFailed(String error) {

                }
            });
            initBankOptionPicker();
        }else {
            XToast.normal("网络连接失败!");
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.certificate_type_ll://证件类型
                if(certificateOptions!=null){
                    XKeyboardUtils.closeKeyboard(this);
                    certificateOptions.show();
                }
                break;
            case R.id.bank_name_ll://银行名称
                if(bankOptions!=null){
                    XKeyboardUtils.closeKeyboard(this);
                    bankOptions.show();
                }
                break;

            case R.id.bank_city_ll://开户行地区
                if(cityOptions!=null){
                    XKeyboardUtils.closeKeyboard(this);
                    cityOptions.show();
                }
                break;
            case R.id.btn_open_acc://认证开户
                String custMobile= (String) XPreferencesUtils.get("custMobile","");
                String custPwd= (String) XPreferencesUtils.get("custPwd","");
                String tradePassword=tradePasswordEt.getText().toString().trim();//密码
                String tradePasswordSure=tradePasswordSuredEt.getText().toString().trim();//确认密码
                String custRealName=cusRealNameEt.getText().toString().trim();//客户真实姓名
                String cardNum=certificateNumberTv.getText().toString().trim();//证件号码
                String bankCard=bankCardNumberEt.getText().toString().trim();//银行卡卡号

                if(XEmptyUtils.isEmpty(custRealName)){
                    XToast.normal("真实姓名不能为空");
                    return;
                }
                if(!XRegexUtils.checkChinese(custRealName)){
                    XToast.normal("真实姓名必须全部是中文");
                    return;
                }
                if(XEmptyUtils.isEmpty(zjCode)){
                    XToast.normal("证件类型不能为空");
                    return;
                }

                if(XEmptyUtils.isEmpty(cardNum)){
                    XToast.normal("证件号码不能为空");
                    return;
                }

                if(XEmptyUtils.isEmpty(bankCode)){
                    XToast.normal("银行名称不能为空");
                    return;
                }

                if(XEmptyUtils.isEmpty(cityCode)){
                    XToast.normal("开户行地区不能为空");
                    return;
                }

                if(XEmptyUtils.isEmpty(bankCard)){
                    XToast.normal("银行卡号不能为空");
                    return;
                }

                if(XEmptyUtils.isEmpty(tradePassword)){
                    XToast.normal("投资密码不能为空");
                    return;
                }

                if(tradePassword.length()<8 || tradePassword.length()>20){
                    XToast.normal("投资密码长度必须是8-20位");
                    return;
                }
                if(isNumeric(tradePassword)){
                    XToast.normal("投资密码不能是纯数字");
                    return;
                }

                if(XEmptyUtils.isEmpty(tradePasswordSure)){
                    XToast.normal("确认密码不能为空");
                    return;
                }

                if(!tradePassword.equals(tradePasswordSure)){
                    XToast.normal("两次输入的密码不一样");
                    return;
                }

                openAcc(custMobile,custPwd,custRealName,cardNum,bankCode,bankCard,tradePassword,cityCode,zjCode);
                break;
        }
    }

    private void openAcc(String custMobile, String custPwd, String custRealName, String cardNum, String bankCode, String bankCard, String tradePassword, String cityCode, String zjCode) {
        Map<String,Object > params =new HashMap<>();
        params .put("custMobile",custMobile);
        params .put("custPwd",custPwd);
        params .put("custRealName",custRealName);
        params .put("cardNum",cardNum);
        params .put("bankCode",bankCode);
        params .put("bankCard",bankCard);
        params .put("tradePassword",tradePassword);
        params .put("sCity",cityCode);
        params .put("cerType",zjCode);
        params .put("optSource","4");
        XHttp.obtain().post(Path.open_acc, params, new HttpCallBack<OpenAccBean>() {
            @Override
            public void onSuccess(OpenAccBean accBean) {
                XToast.normal(accBean.getOptResultMsg());
                if("true".equals(accBean.getOptResult())){
                    XPreferencesUtils.put("openStatus",2);
                    Intent intent=new Intent(OpenAccountActivity.this,OpenAccountSuccessActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailed(String error) {

            }
        });
    }

    private String zjCode;//证件类型code
    private String bankCode;//银行code
    private String cityCode;//开户行地区code

    //证件类型选择器初始化
    private void initCertificateOptionPicker() {
        certificateOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                certificateTypeTv.setText(certificateList.get(options1).getName());
                zjCode=certificateList.get(options1).getValue();
            }
        })
                .setTitleText("证件类型")
                .setContentTextSize(18)//设置滚轮文字大小
                .setDividerColor(Color.parseColor("#5d5d5d"))//设置分割线的颜色
                .setSelectOptions(0)//默认选中项
                .setBgColor(Color.WHITE)
                .setTitleBgColor(Color.parseColor("#bfbfbf"))
                .setTitleColor(Color.parseColor("#057dff"))
                .setCancelColor(Color.parseColor("#057dff"))
                .setSubmitColor(Color.parseColor("#057dff"))
                .setTextColorCenter(Color.parseColor("#E14D4D"))
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setBackgroundId(0x00000000) //设置外部遮罩颜色
                .build();

        certificateOptions.setPicker(certificateList);//一级选择器
    }

    //银行选择器初始化
    private void initBankOptionPicker() {
        bankOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                bankNameTv.setText(bankList.get(options1).getName());
                bankCode=bankList.get(options1).getValue();
            }
        })
                .setTitleText("银行选择")
                .setContentTextSize(18)//设置滚轮文字大小
                .setDividerColor(Color.parseColor("#5d5d5d"))//设置分割线的颜色
                .setSelectOptions(0)//默认选中项
                .setBgColor(Color.WHITE)
                .setTitleBgColor(Color.parseColor("#bfbfbf"))
                .setTitleColor(Color.parseColor("#057dff"))
                .setCancelColor(Color.parseColor("#057dff"))
                .setSubmitColor(Color.parseColor("#057dff"))
                .setTextColorCenter(Color.parseColor("#E14D4D"))
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setBackgroundId(0x00000000) //设置外部遮罩颜色
                .build();

        bankOptions.setPicker(bankList);//一级选择器
    }

    //开户行地区选择器初始化
    private void initCityOptionPicker() {
        cityOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                bankCityTv.setText(oneLevelList.get(options1).getName()+twoLevelList.get(options1).get(options2).getName());
                cityCode=twoLevelList.get(options1).get(options2).getValue();
            }
        })
                .setTitleText("开户行地区")
                .setContentTextSize(18)//设置滚轮文字大小
                .setDividerColor(Color.parseColor("#5d5d5d"))//设置分割线的颜色
                .setSelectOptions(0)//默认选中项
                .setBgColor(Color.WHITE)
                .setTitleBgColor(Color.parseColor("#bfbfbf"))
                .setTitleColor(Color.parseColor("#057dff"))
                .setCancelColor(Color.parseColor("#057dff"))
                .setSubmitColor(Color.parseColor("#057dff"))
                .setTextColorCenter(Color.parseColor("#E14D4D"))
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setBackgroundId(0x00000000) //设置外部遮罩颜色
                .build();

        cityOptions.setPicker(oneLevelList,twoLevelList);
    }

    public String getJson(Context context, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bf=null;
        try {
            AssetManager assetManager = context.getAssets();
            bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(bf!=null){
                try {
                    bf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return stringBuilder.toString();
    }

    /** * 纯数字
     * @param str
     * @return */
    public boolean isNumeric(String str){
        for (int i = str.length();--i>=0;){
            if (!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return true;
    }
}
