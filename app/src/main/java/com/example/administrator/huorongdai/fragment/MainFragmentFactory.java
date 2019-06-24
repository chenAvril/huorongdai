package com.example.administrator.huorongdai.fragment;

import android.os.Bundle;

import com.example.administrator.huorongdai.R;

import java.util.ArrayList;
import java.util.List;

public class MainFragmentFactory {
    private static volatile MainFragmentFactory mInstace;
    List<FragmentInfo> mList;
    private FragmentInfo mPerson;

    String[] mTiltles=new String[]{
            "首页", "理财", "发现", "我的"};


    int [] mColors=new int[]{
            R.color.word_gray  ,R.color.word_red
    };
    private String  tag = "tag";

    private MainFragmentFactory() {

    }

    public static MainFragmentFactory getInstance() {
        if (mInstace == null) {
            synchronized (MainFragmentFactory.class) {
                if (mInstace == null) {
                    mInstace = new MainFragmentFactory();
                }
            }
        }
        return mInstace;

    }

    public void add(FragmentInfo entity) {
        if (mList == null) {
            mList = new ArrayList<>();
        }
        mList.add(entity);
    }

    public List<FragmentInfo> getList() {
        if (mList == null) {
            mList = new ArrayList<>();
            Bundle bundle = new Bundle();
            bundle.putString(tag,mTiltles[0]);
            FragmentInfo home = new FragmentInfo(HomeFragment.class, "首页",  bundle,new int[]{
                    R.drawable.home_gray,R.drawable.home_red
            },mColors);
            Bundle bundle1 = new Bundle();
            bundle1.putString(tag,mTiltles[1]);
            FragmentInfo course = new FragmentInfo(ProjectFragment.class, "理财",  bundle1,new int[]{
                    R.drawable.touzi_gray,R.drawable.touzi_red
            },mColors);
            Bundle bundle2 = new Bundle();
            bundle2.putString(tag,mTiltles[2]);
            FragmentInfo zhibo = new FragmentInfo(DiscoverFragment.class, "发现",  bundle2,new int[]{
                    R.drawable.discover_gray,R.drawable.discover_red
            },mColors);
            Bundle bundle3 = new Bundle();
            bundle3.putString(tag,mTiltles[3]);
            FragmentInfo person = new FragmentInfo(MyFragment.class, "我的",  bundle3,new int[]{
                    R.drawable.wo_gray,R.drawable.wo_red
            },mColors);

            mList.add(home);
            mList.add(course);
            mList.add(zhibo);
            mList.add(person);
        }
        return mList;
    }
}
