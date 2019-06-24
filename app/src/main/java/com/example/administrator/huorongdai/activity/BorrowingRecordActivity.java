package com.example.administrator.huorongdai.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.adapter.SimpleAdapter;
import com.example.administrator.huorongdai.base.BaseActivity;
import com.example.administrator.huorongdai.base.LazyLoadFragment;
import com.example.administrator.huorongdai.eventbusbean.BorrowMsg;
import com.example.administrator.huorongdai.fragment.BorrowingRecordFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class BorrowingRecordActivity extends BaseActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<LazyLoadFragment> fragmentList;
    private BorrowingRecordFragment fragment1,fragment2,fragment3;

    @Override
    protected int getContentView() {
        return R.layout.activity_borrowing_record;
    }

    @Override
    protected void initView() {
        tabLayout =findViewById(R.id.tab_borrowing_record);
        viewPager=findViewById(R.id.vp_borrowing_record);
        setTitle("借款记录");

        //项目状态，不传为不限，7-招标中、8-已满标，12-还款中，13-已还清
        fragment1=new BorrowingRecordFragment("7");
        fragment2=new BorrowingRecordFragment("12");
        fragment3=new BorrowingRecordFragment("13");

        fragmentList=new ArrayList<>();
        fragmentList.add(fragment1);
        fragmentList.add(fragment2);
        fragmentList.add(fragment3);

        List<String> titles=new ArrayList<>();
        titles.add("融资中");
        titles.add("还款中");
        titles.add("已结清");

        // 设置ViewPager布局
        SimpleAdapter adapter = new SimpleAdapter(getSupportFragmentManager(),fragmentList,titles);
        viewPager.setOffscreenPageLimit(2);
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

    @Override
    public void onStart() {
        super.onStart();
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMsg(BorrowMsg message){
        if(message.isRefresh()){
            fragment2.loadData();
            fragment3.loadData();
        }
    }
}
