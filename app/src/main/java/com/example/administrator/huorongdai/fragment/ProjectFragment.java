package com.example.administrator.huorongdai.fragment;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.base.LazyLoadFragment;
import com.example.administrator.huorongdai.xframe.utils.XPreferencesUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProjectFragment extends LazyLoadFragment implements View.OnClickListener {

    private static ProjectFragment fragment;
    public ProjectFragment(){

    }
    public static ProjectFragment getInstance() {
        if (fragment == null) {
            synchronized (ProjectFragment.class) {
                if (fragment == null) {
                    fragment = new ProjectFragment();
                }
            }
        }
        return fragment;
    }

    private TextView textview2,textview3,textview4;
    private List<Fragment> list = new ArrayList<>();
    public ViewPager viewPager;
    private FragmentAdapter fragmentAdapter;
    public SmallMicroFragment smallMicroFragment;//小微
    public ComprehensiveFragment comprehensiveFragment;//综合
    public NewHandFragment newHandFragment;//新手

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_project;
    }

    @Override
    protected void initViews() {

        smallMicroFragment=SmallMicroFragment.getInstance();
        comprehensiveFragment=ComprehensiveFragment.getInstance();
        newHandFragment=NewHandFragment.getInstance();

        textview2 = findViewById(R.id.textview2);
        textview3 = findViewById(R.id.textview3);
        textview4 = findViewById(R.id.textview4);
        viewPager = findViewById(R.id.viewpager);
        textview2.setOnClickListener(this);
        textview3.setOnClickListener(this);
        textview4.setOnClickListener(this);

        if(!smallMicroFragment.isAdded()){
            list.add(smallMicroFragment);
        }
        if(!comprehensiveFragment.isAdded()){
            list.add(comprehensiveFragment);
        }
        if(!newHandFragment.isAdded()){
            list.add(newHandFragment);
        }
        fragmentAdapter=new FragmentAdapter(this.getChildFragmentManager(),list);
        viewPager.setAdapter(fragmentAdapter);

        viewPager.setOffscreenPageLimit(2);//预加载
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                changeTv(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    public void lazyLoad() {
        XPreferencesUtils.put("isRefreshFlag","project");

        switch (viewPager.getCurrentItem()){
            case 0:
                smallMicroFragment.lazyLoad();
                break;
            case 1:
                comprehensiveFragment.lazyLoad();
                break;
            case 2:
                newHandFragment.lazyLoad();
                break;
        }

    }

    @Override
    protected void stopLoad() {

    }

    //顶部文字选择
    private void changeTv(int num){
        textview2.setTextColor(getResources().getColor(R.color.white));
        textview2.setBackground(getResources().getDrawable(R.drawable.bg_left_round_off));
        textview3.setTextColor(getResources().getColor(R.color.white));
        textview3.setBackground(getResources().getDrawable(R.drawable.bg_normal_off));
        textview4.setTextColor(getResources().getColor(R.color.white));
        textview4.setBackground(getResources().getDrawable(R.drawable.bg_right_round_off));
        if(0==num){
            textview2.setTextColor(getResources().getColor(R.color.word_red));
            textview2.setBackground(getResources().getDrawable(R.drawable.bg_left_round_on));
        }
        if(1==num){
            textview3.setTextColor(getResources().getColor(R.color.word_red));
            textview3.setBackground(getResources().getDrawable(R.drawable.bg_normal_on));
        }
        if(2==num){
            textview4.setTextColor(getResources().getColor(R.color.word_red));
            textview4.setBackground(getResources().getDrawable(R.drawable.bg_right_round_on));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textview2:
                viewPager.setCurrentItem(0);
                break;
            case R.id.textview3:
                viewPager.setCurrentItem(1);
                break;
            case R.id.textview4:
                viewPager.setCurrentItem(2);
                break;
        }
    }

    class FragmentAdapter extends FragmentPagerAdapter {

        private   List<Fragment> lists;
        public FragmentAdapter(FragmentManager fm,List<Fragment> lists) {
            super(fm);
            this.lists=lists;
        }

        @Override
        public Fragment getItem(int position) {
            return lists.get(position);
        }

        @Override
        public int getCount() {
            return lists.size();
        }


        // 当某子项被摧毁时
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }

        // 判断该view是否来自对象
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return super.isViewFromObject(view, object);
        }
    }
}
