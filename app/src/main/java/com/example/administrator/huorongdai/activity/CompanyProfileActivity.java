package com.example.administrator.huorongdai.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.adapter.SimpleAdapter;
import com.example.administrator.huorongdai.base.BaseActivity;
import com.example.administrator.huorongdai.base.LazyLoadFragment;
import com.example.administrator.huorongdai.fragment.AboutUsFragment;
import com.example.administrator.huorongdai.fragment.BigThingsFragment;
import com.example.administrator.huorongdai.fragment.CompanyEnvironmentFragment;

import java.util.ArrayList;
import java.util.List;

public class CompanyProfileActivity extends BaseActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private List<LazyLoadFragment> fragmentList;
    private AboutUsFragment aboutUsFragment;
    private CompanyEnvironmentFragment companyEnvironmentFragment;
    private BigThingsFragment bigThingsFragment;

    @Override
    protected int getContentView() {
        return R.layout.activity_company_profile;
    }

    @Override
    protected void initView() {
        setTitle("公司简介");
        tabLayout=findViewById(R.id.tab_my_red_package);
        viewPager=findViewById(R.id.vp_my_red_package);
    }

    @Override
    protected void loadData() {
        aboutUsFragment=new AboutUsFragment();
        companyEnvironmentFragment=new CompanyEnvironmentFragment();
        bigThingsFragment=new BigThingsFragment();
        fragmentList=new ArrayList<>();
        fragmentList.add(aboutUsFragment);
        fragmentList.add(companyEnvironmentFragment);
        fragmentList.add(bigThingsFragment);

        List<String> titles=new ArrayList<>();
        titles.add("关于我们");
        titles.add("公司环境");
        titles.add("大事记");

        // 设置ViewPager布局
        SimpleAdapter adapter = new SimpleAdapter(getSupportFragmentManager(),fragmentList,titles);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(viewPager); // 注意在Toolbar中关联ViewPager
    }

    @Override
    public void onClick(View view) {

    }
}
