package com.example.administrator.huorongdai.activity;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.huorongdai.Path;
import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.adapter.MyGridLayoutManager;
import com.example.administrator.huorongdai.base.BaseActivity;
import com.example.administrator.huorongdai.gsonbean.LoanAllBean;
import com.example.administrator.huorongdai.gsonbean.ProgressListBean;
import com.example.administrator.huorongdai.gsonbean.TradeRecordBean;
import com.example.administrator.huorongdai.xframe.adapter.XRecyclerViewAdapter;
import com.example.administrator.huorongdai.xframe.adapter.XViewHolder;
import com.example.administrator.huorongdai.xframe.utils.XEmptyUtils;
import com.example.administrator.huorongdai.xframe.utils.XNetworkUtils;
import com.example.administrator.huorongdai.xframe.utils.http.HttpCallBack;
import com.example.administrator.huorongdai.xframe.utils.http.XHttp;
import com.example.administrator.huorongdai.xframe.widget.XToast;
import com.example.administrator.huorongdai.xframe.widget.loadingview.XLoadingView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileProgressActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private List<Map<String,String>> listBeans;
    private XLoadingView xLoadingView;

    private Handler handler=new Handler(new MyCallback());
    private final class MyCallback implements Handler.Callback{

        @Override
        public boolean handleMessage(Message message) {
            if (XNetworkUtils.isConnected()){
                requestData();
            }else {
                xLoadingView.showNoNetwork();
            }
            return true;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //如果参数为null的话，会将所有的Callbacks和Messages全部清除掉。
        handler.removeCallbacksAndMessages( null );
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_file_progress;
    }

    @Override
    protected void initView() {
        setTitle("备案进程");
        recyclerView=findViewById(R.id.file_progress_rl);
        xLoadingView=findViewById(R.id.xloading_view);
        xLoadingView.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xLoadingView.showLoading();
                handler.sendMessageDelayed(Message.obtain(),600);
            }
        });

        listBeans=new ArrayList<>();
        recyclerView.setLayoutManager(new MyGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        adapter=new MyAdapter(recyclerView,listBeans);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void loadData() {
        handler.sendMessage(Message.obtain());
    }

    @Override
    public void onClick(View view) {

    }

    //数据请求
    private void requestData() {
        Map<String,Object > params =new HashMap<>();
        params.put("","");
        XHttp.obtain().post(Path.progress_list, params, new HttpCallBack<ProgressListBean>() {
            @Override
            public void onSuccess(ProgressListBean allBean) {
                if(allBean.isStatus()){
                    List<Map<String,String>> list=new ArrayList<>();
                    for (int i = 0; i < allBean.getTitle().size(); i++) {
                        Map<String,String> map=new HashMap<>();
                        map.put("thing",allBean.getTitle().get(i));
                        map.put("state",allBean.getState().get(i));
                        list.add(map);
                    }

                    adapter.addAll(list);
                    xLoadingView.showContent();
                }else {
                    xLoadingView.showError();
                    XToast.normal(allBean.getMessage());
                }
            }

            @Override
            public void onFailed(String error) {

            }
        });
    }

    class MyAdapter extends XRecyclerViewAdapter<Map<String,String>> {

        public MyAdapter(@NonNull RecyclerView mRecyclerView, List<Map<String,String>> dataLists) {
            super(mRecyclerView, dataLists,R.layout.item_activity_file_progress);
        }

        @Override
        protected void bindData(XViewHolder holder, Map<String,String> data, int position) {
            LinearLayout ll=holder.getView(R.id.file_progress_item_ll);
            TextView tvThing=holder.getView(R.id.file_progress_item_tv_thing);//合规事项
            TextView tvDone=holder.getView(R.id.file_progress_item_tv_state_done);//已完成
            TextView tvNo=holder.getView(R.id.file_progress_item_tv_state_no);//未完成

            if(position%2==0){
                ll.setBackgroundResource(R.color.gray_bg);
            }else {
                ll.setBackgroundResource(R.color.gray);
            }

            tvThing.setText(data.get("thing"));
            String state=data.get("state");
            if(state.length()==3){
                tvDone.setText(state);
                tvDone.setVisibility(View.VISIBLE);
                tvNo.setVisibility(View.GONE);
            }else {
                tvDone.setVisibility(View.GONE);
                tvNo.setText(state);
                tvNo.setVisibility(View.VISIBLE);
            }
        }
    }

}
