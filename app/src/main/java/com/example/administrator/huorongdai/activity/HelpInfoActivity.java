package com.example.administrator.huorongdai.activity;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.huorongdai.Path;
import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.base.BaseActivity;
import com.example.administrator.huorongdai.gsonbean.QuestionBean;
import com.example.administrator.huorongdai.xframe.adapter.XRecyclerViewAdapter;
import com.example.administrator.huorongdai.xframe.adapter.XViewHolder;
import com.example.administrator.huorongdai.xframe.adapter.decoration.DividerDecoration;
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

public class HelpInfoActivity extends BaseActivity {

    private XLoadingView xLoadingView;
    private RecyclerView mRecyclerView;
    private MyAdapter adapter;
    private List<Map<String,String>> listBeans;

    private String type;
    private String title;

    private Handler handler=new Handler(new MyCallback());
    private final class MyCallback implements Handler.Callback{

        @Override
        public boolean handleMessage(Message message) {
            if (XNetworkUtils.isConnected()){
                if(!XEmptyUtils.isEmpty(type)){
                    requestGetQuestion();
                }
            }else {
                xLoadingView.showNoNetwork();
            }
            return true;
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_help_info;
    }

    @Override
    protected void initView() {
        type=getIntent().getStringExtra("type");
        title=getIntent().getStringExtra("title");
        setTitle("帮助信息-"+title);
        mRecyclerView = findViewById(R.id.recycler_view);
        xLoadingView=findViewById(R.id.xloading_view);
        xLoadingView.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xLoadingView.showLoading();
                handler.sendMessageDelayed(Message.obtain(),600);
            }
        });

        DividerDecoration decoration=new DividerDecoration(R.color.gray_bg,1);
        decoration.setDrawLastItem(false);
        mRecyclerView.addItemDecoration(decoration);

        listBeans=new ArrayList<>();
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        adapter=new MyAdapter(mRecyclerView,listBeans);
        mRecyclerView.setAdapter(adapter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //如果参数为null的话，会将所有的Callbacks和Messages全部清除掉。
        handler.removeCallbacksAndMessages( null );
    }

    private void requestGetQuestion() {
        Map<String,Object > params =new HashMap<>();
        params.put("type",type);
        XHttp.obtain().post(Path.get_question, params, new HttpCallBack<QuestionBean>() {
            @Override
            public void onSuccess(QuestionBean bean) {
                if(bean.isStatus()){
                    for(QuestionBean.ListBean listBean:bean.getList()){
                        Map<String,String> map=new HashMap<>();
                        map.put("status","0");
                        map.put("title",listBean.getArticleTitle());
                        map.put("info",listBean.getArticleDescription());
                        listBeans.add(map);
                    }
                    adapter.notifyDataSetChanged();
                    xLoadingView.showContent();
                }else {
                    xLoadingView.showError();
                }
            }

            @Override
            public void onFailed(String error) {

            }
        });
    }

    @Override
    protected void loadData() {
        handler.sendMessage(Message.obtain());
    }

    @Override
    public void onClick(View view) {

    }

    class MyAdapter extends XRecyclerViewAdapter<Map<String,String>> {

        public MyAdapter(@NonNull RecyclerView mRecyclerView, List<Map<String,String>> dataLists) {
            super(mRecyclerView, dataLists,R.layout.item_help_info);
        }

        @Override
        protected void bindData(XViewHolder holder, final Map<String,String> data, final int position) {
            final RelativeLayout rlTitle=holder.getView(R.id.rl_help_title);
            TextView tvTitle=holder.getView(R.id.tv_help_title);
            final LinearLayout llInfo=holder.getView(R.id.ll_info);
            final ImageView ivGray=holder.getView(R.id.iv_gray);
            TextView tvInfo=holder.getView(R.id.tv_help_info);

            tvTitle.setText(data.get("title"));
            //tvInfo.setText(data.get("info"));
            tvInfo.setText(Html.fromHtml(data.get("info")));
            if("0".equals(data.get("status"))){//不显示
                ivGray.setBackgroundResource(R.drawable.right_gray);

                if(position==0 ){
                    rlTitle.setBackgroundResource(R.drawable.bg_online_customer_service_white);
                }else if(position==listBeans.size()-1) {
                    rlTitle.setBackgroundResource(R.drawable.bg_online_customer_service_white);
                }else{
                    rlTitle.setBackgroundColor(getResources().getColor(R.color.white));
                }
                llInfo.setVisibility(View.GONE);
                ivGray.setBackgroundResource(R.drawable.right_gray);
            }else {
                ivGray.setBackgroundResource(R.drawable.top_gray);

                if(position==0){
                    rlTitle.setBackgroundResource(R.drawable.bg_gray_top);
                }else {
                    rlTitle.setBackgroundColor(getResources().getColor(R.color.bg_help_gray));
                }
                llInfo.setVisibility(View.VISIBLE);
                ivGray.setBackgroundResource(R.drawable.top_gray);
            }
            rlTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if("0".equals(data.get("status"))){//不显示
                        if(position==0){
                            rlTitle.setBackgroundResource(R.drawable.bg_gray_top);
                        }else {
                            rlTitle.setBackgroundColor(getResources().getColor(R.color.bg_help_gray));
                        }

                        data.put("status","1");
                        llInfo.setVisibility(View.VISIBLE);
                        ivGray.setBackgroundResource(R.drawable.top_gray);
                    }else {
                        if(position==0 ){
                            rlTitle.setBackgroundResource(R.drawable.bg_online_customer_service_white);
                        }else if(position==listBeans.size()-1) {
                            rlTitle.setBackgroundResource(R.drawable.bg_online_customer_service_white);
                        }else{
                            rlTitle.setBackgroundColor(getResources().getColor(R.color.white));
                        }

                        data.put("status","0");
                        llInfo.setVisibility(View.GONE);
                        ivGray.setBackgroundResource(R.drawable.right_gray);
                    }

                }
            });
        }
    }
}
