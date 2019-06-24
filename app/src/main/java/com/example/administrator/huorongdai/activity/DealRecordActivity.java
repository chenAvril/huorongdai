package com.example.administrator.huorongdai.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.adapter.SimpleAdapter;
import com.example.administrator.huorongdai.base.BaseActivity;
import com.example.administrator.huorongdai.base.LazyLoadFragment;
import com.example.administrator.huorongdai.fragment.MoneyRecordFragment;

import java.util.ArrayList;
import java.util.List;

public class DealRecordActivity extends BaseActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<LazyLoadFragment> fragmentList;
    private MoneyRecordFragment fragment1,fragment2,fragment3,fragment4;

    @Override
    protected int getContentView() {
        return R.layout.activity_deal_record;
    }

    @Override
    protected void initView() {
        setTitle("交易记录");

        tabLayout=findViewById(R.id.tab_deal_record);
        viewPager=findViewById(R.id.vp_deal_record);

        //tradeType：交易类型，1-充值，2-提现，3-投资，4-受让，5-转让，6-放款，7-还款，8-收款，9-红包，10-积分（暂时不显示），11-转账；
        //tradeStatus：交易状态，1-成功，2-失败，3-冻结，4-解冻；
        fragment1=new MoneyRecordFragment("3");//3-投资
        fragment2=new MoneyRecordFragment("9");//9-红包
        fragment3=new MoneyRecordFragment("8");//8-收款
        fragment4=new MoneyRecordFragment("6");//6-放款

        fragmentList=new ArrayList<>();
        fragmentList.add(fragment1);
        fragmentList.add(fragment2);
        fragmentList.add(fragment3);
        fragmentList.add(fragment4);

        List<String> titles=new ArrayList<>();
        titles.add("投资记录");
        titles.add("红包记录");
        titles.add("收款记录");
        titles.add("放款记录");

        // 设置ViewPager布局
        SimpleAdapter adapter = new SimpleAdapter(getSupportFragmentManager(),fragmentList,titles);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
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
