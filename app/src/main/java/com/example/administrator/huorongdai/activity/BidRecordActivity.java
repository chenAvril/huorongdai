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
import com.example.administrator.huorongdai.fragment.BidRecord9Fragment;
import com.example.administrator.huorongdai.fragment.BidRecordFragment;
import com.example.administrator.huorongdai.gsonbean.AccountInfo;
import com.example.administrator.huorongdai.xframe.utils.XNetworkUtils;
import com.example.administrator.huorongdai.xframe.utils.XPreferencesUtils;
import com.example.administrator.huorongdai.xframe.utils.http.HttpCallBack;
import com.example.administrator.huorongdai.xframe.utils.http.XHttp;
import com.example.administrator.huorongdai.xframe.widget.XToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BidRecordActivity extends BaseActivity {

    private TextView tvInterest;//累计待收利息
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<LazyLoadFragment> fragmentList;
    private BidRecordFragment fragment1,fragment2;
    private BidRecord9Fragment fragment3;
    private BidRecord9Fragment fragment4;

    private String custMobile;
    private String custPwd;//密文密码

    @Override
    protected int getContentView() {
        return R.layout.activity_bid_record;
    }

    @Override
    protected void initView() {
        setTitle("投标记录");

        tvInterest=findViewById(R.id.tv_interest);
        tabLayout=findViewById(R.id.tab_bid_record);
        viewPager=findViewById(R.id.vp_bid_record);

        custMobile = (String) XPreferencesUtils.get("custMobile","");
        custPwd = (String) XPreferencesUtils.get("custPwd","");

        ////不传默认查询9，2-投标中的投资，3-失败的投资，5-满标待审的投资，9-收款中的投资，10-已结清的投资
        fragment1=new BidRecordFragment("2");//投资中
        fragment2=new BidRecordFragment("5");//投资完成
        fragment3=new BidRecord9Fragment("9");//回款中
        fragment4=new BidRecord9Fragment("10");//已回款

        fragmentList=new ArrayList<>();
        fragmentList.add(fragment1);
        fragmentList.add(fragment2);
        fragmentList.add(fragment3);
        fragmentList.add(fragment4);

        List<String> titles=new ArrayList<>();
        titles.add("投资中");
        titles.add("投资完成");
        titles.add("回款中");
        titles.add("已回款");

        // 设置ViewPager布局
        SimpleAdapter adapter = new SimpleAdapter(getSupportFragmentManager(),fragmentList,titles);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(viewPager); // 注意在Toolbar中关联ViewPager
    }

    @Override
    protected void loadData() {
        if(XNetworkUtils.isConnected()){
            Map<String,Object > params =new HashMap<>();
            params .put("custMobile",custMobile);
            params .put("custPwd",custPwd);
            XHttp.obtain().post(Path.account_info, params, new HttpCallBack<AccountInfo>() {
                @Override
                public void onSuccess(AccountInfo bean) {
                    if(bean.isStatus()){
                        tvInterest.setText(bean.getUnEarnIint()+"");
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
