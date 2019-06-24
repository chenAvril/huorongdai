package com.example.administrator.huorongdai;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.huorongdai.adapter.TabLayoutFragmentAdapter;
import com.example.administrator.huorongdai.eventbusbean.Msg;
import com.example.administrator.huorongdai.fragment.DiscoverFragment;
import com.example.administrator.huorongdai.fragment.HomeFragment;
import com.example.administrator.huorongdai.fragment.MyFragment;
import com.example.administrator.huorongdai.fragment.ProjectFragment;
import com.example.administrator.huorongdai.xframe.utils.XPreferencesUtils;
import com.example.administrator.huorongdai.xframe.utils.statusbar.XStatusBar;
import com.example.administrator.huorongdai.xframe.widget.XToast;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    private int[] mTabImgs = new int[]{R.drawable.home_gray, R.drawable.touzi_gray,R.drawable.discover_gray, R.drawable.wo_gray};
    private List<Fragment> list = new ArrayList<>();
    private List<String> mTabList = new ArrayList<>();
    private ViewPager viewPager;
    private TabLayout tabs;
    private TabLayoutFragmentAdapter mAdapter;
    private HomeFragment homeFragment;//首页
    public ProjectFragment projectFragment;//项目
    public DiscoverFragment discoverFragment;//发现
    private MyFragment accountFragment;//我的

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        XStatusBar.setTranslucentForImageViewInFragment(MainActivity.this, viewPager);
        //在setContentView();后面加上适配语句
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        initView();
        loadData();
    }


    @Override
    public void onResume() {
        super.onResume();
        if(isRefreash){
            String isRefreshFlag= (String) XPreferencesUtils.get("isRefreshFlag","");
            if("home".equals(isRefreshFlag)){
                homeFragment.lazyLoad();
            }
            if("project".equals(isRefreshFlag)){
                String isProjectFlag= (String) XPreferencesUtils.get("isProjectFlag","");
                if("small".equals(isProjectFlag)){
                    projectFragment.smallMicroFragment.lazyLoad();
                }
                if("comprehensive".equals(isProjectFlag)){
                    projectFragment.comprehensiveFragment.lazyLoad();
                }
                if("newhand".equals(isProjectFlag)){
                    projectFragment.newHandFragment.lazyLoad();
                }

            }
            if("my".equals(isRefreshFlag)){
                accountFragment.lazyLoad();
            }
            isRefreash=false;
        }
    }

    private boolean isRefreash=false;
    @Override
    public void onPause() {
        super.onPause();
        isRefreash=true;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    private void initView() {
        viewPager= findViewById(R.id.viewPager);
        tabs= findViewById(R.id.tabs);
    }

    private void loadData() {
        initList();
        mAdapter = new TabLayoutFragmentAdapter(getSupportFragmentManager(), mTabList, this, list, mTabImgs);
        viewPager.setAdapter(mAdapter);
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(3);
        tabs.setupWithViewPager(viewPager);
        tabs.setTabMode(TabLayout.MODE_FIXED);
        for (int i = 0; i < tabs.getTabCount(); i++) {
            tabs.getTabAt(i).setCustomView(mAdapter.getTabView(i));
        }

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View customView = tab.getCustomView();
                TextView tabText = customView.findViewById(R.id.tv_tab_text);
                ImageView tabIcon = customView.findViewById(R.id.iv_tab_icon);
                tabText.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.word_red));
                String s = tabText.getText().toString();
                if ("首页".equals(s)) {
                    tabIcon.setImageResource(R.drawable.home_red);
                } else if ("理财".equals(s)) {
                    tabIcon.setImageResource(R.drawable.touzi_red);
                }else if("发现".equals(s)){
                    tabIcon.setImageResource(R.drawable.discover_red);
                } else if ("我的".equals(s)) {
                    tabIcon.setImageResource(R.drawable.wo_red);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View customView = tab.getCustomView();
                TextView tabText =customView.findViewById(R.id.tv_tab_text);
                ImageView tabIcon = customView.findViewById(R.id.iv_tab_icon);
                tabText.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.word_gray));
                String s = tabText.getText().toString();
                if ("首页".equals(s)) {
                    tabIcon.setImageResource(R.drawable.home_gray);
                } else if ("理财".equals(s)) {
                    tabIcon.setImageResource(R.drawable.touzi_gray);
                }else if("发现".equals(s)){
                    tabIcon.setImageResource(R.drawable.discover_gray);
                } else if ("我的".equals(s)) {
                    tabIcon.setImageResource(R.drawable.wo_gray);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initList() {
        homeFragment=HomeFragment.getInstance();
        projectFragment=ProjectFragment.getInstance();
        discoverFragment=DiscoverFragment.getInstance();
        accountFragment=MyFragment.getInstance();

        list.add(homeFragment);
        list.add(projectFragment);
        list.add(discoverFragment);
        list.add(accountFragment);
        mTabList.clear();
        mTabList.add("首页");
        mTabList.add("理财");
        mTabList.add("发现");
        mTabList.add("我的");
    }


    @Subscribe(threadMode = ThreadMode.POSTING)
    public void getMsg(Msg message){
        String msg=message.getMsg();
        if("5".equals(msg)){//活动详情HtmlActivity页面跳转到投资页面
            viewPager.setCurrentItem(1);
            projectFragment.viewPager.setCurrentItem(0);
        }else if("4".equals(msg)){//LoginActivity和OpenAccountSuccessActivity页面跳转到首页
            viewPager.setCurrentItem(0);
        }else if("3".equals(msg)){//OpenAccountSuccessActivity页面跳转到账户页面
            viewPager.setCurrentItem(3);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    //记录用户首次点击返回键的时间
    private long firstTime=0;
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:
                long secondTime=System.currentTimeMillis();
                if(secondTime-firstTime>2000){
                    XToast.normal("再按一次退出程序");
                    firstTime=secondTime;
                    return true;
                }else{
                    System.exit(0);
                }
                break;
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //if (requestCode == 65656 &&  resultCode == RESULT_OK) {
        if (resultCode == RESULT_OK) {
            homeFragment.checkVersion();
        }
    }
}