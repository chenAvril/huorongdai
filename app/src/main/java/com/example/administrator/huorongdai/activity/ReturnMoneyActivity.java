package com.example.administrator.huorongdai.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.huorongdai.Path;
import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.adapter.SimpleAdapter;
import com.example.administrator.huorongdai.base.BaseActivity;
import com.example.administrator.huorongdai.base.LazyLoadFragment;
import com.example.administrator.huorongdai.fragment.ReturnMoneyFragment;
import com.example.administrator.huorongdai.gsonbean.TradeAmtBean;
import com.example.administrator.huorongdai.xframe.utils.XNetworkUtils;
import com.example.administrator.huorongdai.xframe.utils.XPreferencesUtils;
import com.example.administrator.huorongdai.xframe.utils.http.HttpCallBack;
import com.example.administrator.huorongdai.xframe.utils.http.XHttp;
import com.example.administrator.huorongdai.xframe.widget.XToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReturnMoneyActivity extends BaseActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TextView tvTradeAmt;
    private List<LazyLoadFragment> fragmentList;
    private ReturnMoneyFragment fragment1,fragment2;

    private String custMobile;
    private String custPwd;//密文密码

    @Override
    protected int getContentView() {
        return R.layout.activity_return_money;
    }

    @Override
    protected void initView() {
        setTitle("回款计划");

        custMobile = (String) XPreferencesUtils.get("custMobile","");
        custPwd = (String) XPreferencesUtils.get("custPwd","");

        tvTradeAmt=findViewById(R.id.tv_trade_amt);
        tabLayout=findViewById(R.id.tab_return_money);
        viewPager=findViewById(R.id.vp_return_money);
        //回款状态===>不传查全部，1-待收，2-已收
        fragment1=new ReturnMoneyFragment("1");
        fragment2=new ReturnMoneyFragment("2");

        fragmentList=new ArrayList<>();
        fragmentList.add(fragment1);
        fragmentList.add(fragment2);

        List<String> titles=new ArrayList<>();
        titles.add("待收");
        titles.add("已收");

        // 设置ViewPager布局
        SimpleAdapter adapter = new SimpleAdapter(getSupportFragmentManager(),fragmentList,titles);
        viewPager.setAdapter(adapter);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(viewPager); // 注意在Toolbar中关联ViewPager
    }

    @Override
    protected void loadData() {
        if(XNetworkUtils.isConnected()){
            Map<String,Object > params =new HashMap<>();
            params .put("custMobile",custMobile);
            params .put("custPwd",custPwd);
            params .put("acctAmtType","1");//1-累计净收益，2-待收总额，3-待还总额，4-投资总额，5-融资总额
            XHttp.obtain().post(Path.get_tarde_amt, params, new HttpCallBack<TradeAmtBean>() {
                @Override
                public void onSuccess(TradeAmtBean bean) {
                    if(bean.isStatus()){
                        tvTradeAmt.setText(bean.getAmount()+"");
                    }
                }

                @Override
                public void onFailed(String error) {

                }
            });
        }else {
            XToast.normal("网络连接失败!");
        }
    }

    @Override
    public void onClick(View view) {

    }
}
