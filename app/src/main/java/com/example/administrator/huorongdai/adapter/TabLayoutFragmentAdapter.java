package com.example.administrator.huorongdai.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.huorongdai.R;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

import java.util.List;


public class TabLayoutFragmentAdapter extends FragmentPagerAdapter {
    private List<String> mTabList;
    private Context mContext;
    private List<Fragment> mFragments;
    private ImageView mTabIcon;
    private TextView mTabText;
    private int[] mTabImgs;
    private LinearLayout mTabView;

    public TabLayoutFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public TabLayoutFragmentAdapter(FragmentManager fm, List<String> tabList, Context context, List<Fragment> fragments, int[] tabImgs) {
        super(fm);
        mTabList = tabList;
        mContext = context;
        mFragments = fragments;
        mTabImgs = tabImgs;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mTabList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabList.get(position);
    }


    public View getTabView(int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_tab_view, null);
        //拿到布局填充器返回的view后
        ScreenAdapterTools.getInstance().loadView(view);
        mTabView = view.findViewById(R.id.ll_tab_view);
        mTabText = view.findViewById(R.id.tv_tab_text);
        mTabIcon = view.findViewById(R.id.iv_tab_icon);
        mTabText.setText(mTabList.get(position));
        mTabIcon.setImageResource(mTabImgs[position]);
        if (0 == position) {
            mTabText.setTextColor(ContextCompat.getColor(mContext, R.color.word_red));
            mTabIcon.setImageResource(R.drawable.home_red);
        }
        return view;
    }
}
