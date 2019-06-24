package com.example.administrator.huorongdai.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.adapter.MyGridLayoutManager;
import com.example.administrator.huorongdai.base.LazyLoadFragment;
import com.example.administrator.huorongdai.gsonbean.SelfInvestBean;
import com.example.administrator.huorongdai.xframe.adapter.XRecyclerViewAdapter;
import com.example.administrator.huorongdai.xframe.adapter.XViewHolder;
import com.example.administrator.huorongdai.xframe.adapter.decoration.DividerDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class BigThingsFragment extends LazyLoadFragment {

    private MyAdapter adapter;
    private RecyclerView recyclerView;
    private List<Map<String,String>> list;

    public BigThingsFragment() {
        // Required empty public constructor
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_big_things;
    }

    @Override
    protected void initViews() {
        recyclerView=findViewById(R.id.big_things_recycler);

        list=new ArrayList<>();
        String [] times = getResources().getStringArray(R.array.big_things_time);
        String [] things = getResources().getStringArray(R.array.big_things_thing);
        for (int i = 0; i < times.length; i++) {
            Map<String , String> map=new HashMap<>();
            map.put("time",times[i]);
            map.put("things",things[i]);
            list.add(map);
        }

        MyGridLayoutManager manager=new MyGridLayoutManager(getContext(),1);
        manager.setScrollEnabled(false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setNestedScrollingEnabled(false);
        adapter=new MyAdapter(recyclerView,list);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void lazyLoad() {

    }

    class MyAdapter extends XRecyclerViewAdapter<Map<String,String>> {

        public MyAdapter(@NonNull RecyclerView mRecyclerView, List<Map<String,String>> dataLists) {
            super(mRecyclerView, dataLists,R.layout.item_fragment_big_things);
        }

        @Override
        protected void bindData(XViewHolder holder, Map<String,String> data, int position) {
            TextView tv1=holder.getView(R.id.tv1);
            TextView tv2=holder.getView(R.id.tv2);

            tv1.setText(data.get("time"));
            tv2.setText(data.get("things"));
        }
    }

    class MyGridLayoutManager extends GridLayoutManager {
        private boolean isScrollEnabled = true;

        public MyGridLayoutManager(Context context, int spanCount) {
            super(context, spanCount);
        }
        public MyGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
        }

        public MyGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
            super(context, spanCount, orientation, reverseLayout);
        }

        public void setScrollEnabled(boolean flag) {
            this.isScrollEnabled = flag;
        }

        @Override
        public boolean canScrollVertically() {
            return isScrollEnabled && super.canScrollVertically();
        }
    }
}
