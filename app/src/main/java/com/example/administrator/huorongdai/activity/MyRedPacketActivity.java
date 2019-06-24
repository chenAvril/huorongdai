package com.example.administrator.huorongdai.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.adapter.SimpleAdapter;
import com.example.administrator.huorongdai.base.BaseActivity;
import com.example.administrator.huorongdai.base.LazyLoadFragment;
import com.example.administrator.huorongdai.fragment.RedPackageFragment;

import java.util.ArrayList;
import java.util.List;

public class MyRedPacketActivity extends BaseActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private List<LazyLoadFragment> fragmentList;
    private RedPackageFragment fragment1,fragment2,fragment3;

    @Override
    protected int getContentView() {
        return R.layout.activity_my_red_packet;
    }

    @Override
    protected void initView() {
        tabLayout=findViewById(R.id.tab_my_red_package);
        viewPager=findViewById(R.id.vp_my_red_package);

        setTitle("我的红包");

        //红包状态，1-有效，2-已使用，3-划转中，4-还款成功，5-还款失败，6-过期；
        fragment1=new RedPackageFragment("1");
        fragment2=new RedPackageFragment("2");
        fragment3=new RedPackageFragment("6");

        fragmentList=new ArrayList<>();
        fragmentList.add(fragment1);
        fragmentList.add(fragment2);
        fragmentList.add(fragment3);

        List<String> titles=new ArrayList<>();
        titles.add("未使用");
        titles.add("已使用");
        titles.add("已过期");

        // 设置ViewPager布局
        SimpleAdapter adapter = new SimpleAdapter(getSupportFragmentManager(),fragmentList,titles);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(viewPager); // 注意在Toolbar中关联ViewPager
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View view) {

    }
}
