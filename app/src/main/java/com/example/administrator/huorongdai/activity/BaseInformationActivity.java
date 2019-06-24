package com.example.administrator.huorongdai.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.adapter.SimpleAdapter;
import com.example.administrator.huorongdai.base.BaseActivity;
import com.example.administrator.huorongdai.base.LazyLoadFragment;
import com.example.administrator.huorongdai.fragment.AuditInformationFragment;
import com.example.administrator.huorongdai.fragment.FileInformationFragment;

import java.util.ArrayList;
import java.util.List;

public class BaseInformationActivity extends BaseActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private List<LazyLoadFragment> fragmentList;
    private FileInformationFragment fileInformationFragment;//备案信息
    private FileInformationFragment organizationInformationFragment;//组织信息
    private AuditInformationFragment auditInformationFragment;//审核信息

    @Override
    protected int getContentView() {
        return R.layout.activity_base_information;
    }

    @Override
    protected void initView() {
        setTitle("基本信息");
        tabLayout=findViewById(R.id.tab_my_red_package);
        viewPager=findViewById(R.id.vp_my_red_package);
    }

    @Override
    protected void loadData() {
        fileInformationFragment=new FileInformationFragment("http://enze.oss-cn-shanghai.aliyuncs.com/app/xxpljbxx.jpg");
        organizationInformationFragment=new FileInformationFragment("http://enze.oss-cn-shanghai.aliyuncs.com/app/xxplbaxx.jpg");
        auditInformationFragment=new AuditInformationFragment();
        fragmentList=new ArrayList<>();
        fragmentList.add(fileInformationFragment);
        fragmentList.add(organizationInformationFragment);
        fragmentList.add(auditInformationFragment);

        List<String> titles=new ArrayList<>();
        titles.add("备案信息");
        titles.add("组织信息");
        titles.add("审核信息");

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
