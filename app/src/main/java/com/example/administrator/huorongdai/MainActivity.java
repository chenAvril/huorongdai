package com.example.administrator.huorongdai;

import android.content.Intent;
import android.content.res.Resources;
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
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.huorongdai.activity.MyMessageActivity;
import com.example.administrator.huorongdai.adapter.TabLayoutFragmentAdapter;
import com.example.administrator.huorongdai.eventbusbean.Msg;
import com.example.administrator.huorongdai.eventbusbean.UpdateBean;
import com.example.administrator.huorongdai.fragment.DiscoverFragment;
import com.example.administrator.huorongdai.fragment.FragmentInfo;
import com.example.administrator.huorongdai.fragment.HomeFragment;
import com.example.administrator.huorongdai.fragment.MainFragmentFactory;
import com.example.administrator.huorongdai.fragment.MyFragment;
import com.example.administrator.huorongdai.fragment.ProjectFragment;
import com.example.administrator.huorongdai.view.MyFragmentTabHost;
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

    private MyFragmentTabHost mTabHost;
    private TabWidget mTabWidget;
    private List<FragmentInfo> mFragmentEntities;
    private static final String tag = "tag";
    public static final String[] mTiltles = new String[]{"首页", "理财", "发现", "我的"};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //XStatusBar.setTranslucentForImageViewInFragment(MainActivity.this, viewPager);
        //在setContentView();后面加上适配语句
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        EventBus.getDefault().register(this);
        initView();
    }

    private void initView() {
        mTabHost = findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.main_layout_content);
        mTabWidget = mTabHost.getTabWidget();
        //  去掉分割线
        mTabWidget.setDividerDrawable(null);
        mFragmentEntities = MainFragmentFactory.getInstance().getList();
        initListener();
        initData();
    }


    private void initData() {
        int size = mFragmentEntities.size();
        for (int i = 0; i < size; i++) {
            FragmentInfo fragmentInfo = mFragmentEntities.get(i);
            String title = fragmentInfo.getTitle();
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(title).setIndicator(getTabView(i));
            Bundle bundle = new Bundle();
            bundle.putString(tag, mTiltles[i]);

            mTabHost.addTab(tabSpec, fragmentInfo.getClz(), bundle);
        }
        updateTab(0);
    }

    private int tabIndex=0;
    private void initListener() {
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                int currentTab = mTabHost.getCurrentTab();
                tabIndex=currentTab;
                updateTab(currentTab);
            }
        });
    }

    private View getTabView(int i) {
        View view = View.inflate(this, R.layout.tab_layout, null);
        int currentTab = mTabHost.getCurrentTab();
        setSingleView(view, currentTab, i);
        return view;
    }

    private void setSingleView(View view, int currentTab, int index) {
        FragmentInfo fragmentInfo = mFragmentEntities.get(index);
        int[] imagIds = fragmentInfo.getImagIds();
        int[] colors = fragmentInfo.getColors();
        TextView tv = view.findViewById(R.id.tab_tv);
        ImageView iv = view.findViewById(R.id.tab_icon);
        tv.setText(fragmentInfo.getTitle());
        Resources resources = getResources();
        if (index == currentTab) {
            tv.setTextColor(resources.getColor(colors[1]));
            iv.setImageDrawable(resources.getDrawable(imagIds[1]));
        } else {
            tv.setTextColor(getResources().getColor(colors[0]));
            iv.setImageDrawable(resources.getDrawable(imagIds[0]));
        }
    }

    private void updateTab(int currentTab) {
        int childCount = mTabWidget.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = mTabWidget.getChildTabViewAt(i);
            setSingleView(view, currentTab, i);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMsg(Msg message){
        String msg=message.getMsg();
        if("5".equals(msg)){//活动详情HtmlActivity页面跳转到投资页面
            mTabHost.setCurrentTab(1);
//            viewPager.setCurrentItem(1);
//            projectFragment.viewPager.setCurrentItem(0);
        }else if("4".equals(msg)){//LoginActivity和OpenAccountSuccessActivity页面跳转到首页
//            viewPager.setCurrentItem(0);
            mTabHost.setCurrentTab(0);
        }else if("3".equals(msg)){//OpenAccountSuccessActivity页面跳转到账户页面
//            viewPager.setCurrentItem(3);
            mTabHost.setCurrentTab(3);
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
            EventBus.getDefault().post(new UpdateBean());
        }
    }
}