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

public class MoneyRecordActivity extends BaseActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<LazyLoadFragment> fragmentList;
    private MoneyRecordFragment fragment1,fragment2;

    @Override
    protected int getContentView() {
        return R.layout.activity_money_record;
    }

    @Override
    protected void initView() {
        setTitle("充值提现");

        tabLayout=findViewById(R.id.tab_money_record);
        viewPager=findViewById(R.id.vp_money_record);

        //tradeType：交易类型，1-充值，2-提现，3-投资，4-受让，5-转让，6-放款，7-还款，8-收款，9-红包，10-积分（暂时不显示），11-转账；
        //tradeStatus：交易状态，1-成功，2-失败，3-冻结，4-解冻；
        fragment1=new MoneyRecordFragment("1");//1-充值
        fragment2=new MoneyRecordFragment("2");//2-提现

        fragmentList=new ArrayList<>();
        fragmentList.add(fragment1);
        fragmentList.add(fragment2);

        List<String> titles=new ArrayList<>();
        titles.add("充值记录");
        titles.add("提现记录");

        // 设置ViewPager布局
        SimpleAdapter adapter = new SimpleAdapter(getSupportFragmentManager(),fragmentList,titles);
        viewPager.setAdapter(adapter);
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
