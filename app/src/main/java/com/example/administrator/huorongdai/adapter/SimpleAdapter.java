package com.example.administrator.huorongdai.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.administrator.huorongdai.base.LazyLoadFragment;

import java.util.List;

/**
 * Created by Administrator on 2018/3/31 0031.
 */

public class SimpleAdapter extends FragmentPagerAdapter {

    // 展示信息
    private List<String> titles;

    private List<LazyLoadFragment> list;

    // 默认构造器
    public SimpleAdapter(FragmentManager fm,List<LazyLoadFragment> list,List<String> titles) {
        super(fm);
        this.list=list;
        this.titles=titles;
    }

    // 根据不同位置（position），显示不同的Fragment
    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    // 子页面Fragment的个数
    @Override
    public int getCount() {
        return list.size();
    }

    // 每个页面的标题，当ToolBar联动时，即为Tab的标题
    @Override
    public CharSequence getPageTitle(int position) {
        if (position >= 0 && position < titles.size()) {
            return titles.get(position);
        }
        return null;
    }
}