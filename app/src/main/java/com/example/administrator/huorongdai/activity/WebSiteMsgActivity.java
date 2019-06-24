package com.example.administrator.huorongdai.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.huorongdai.Path;
import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.base.BaseActivity;
import com.example.administrator.huorongdai.eventbusbean.ReadLetterMsg;
import com.example.administrator.huorongdai.gsonbean.GetLetterListBean;
import com.example.administrator.huorongdai.xframe.adapter.XRecyclerViewAdapter;
import com.example.administrator.huorongdai.xframe.adapter.XViewHolder;
import com.example.administrator.huorongdai.xframe.adapter.decoration.DividerDecoration;
import com.example.administrator.huorongdai.xframe.utils.XNetworkUtils;
import com.example.administrator.huorongdai.xframe.utils.XPreferencesUtils;
import com.example.administrator.huorongdai.xframe.utils.http.HttpCallBack;
import com.example.administrator.huorongdai.xframe.utils.http.XHttp;
import com.example.administrator.huorongdai.xframe.widget.loadingview.XLoadingView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebSiteMsgActivity extends BaseActivity {

    private XLoadingView xLoadingView;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private List<GetLetterListBean.LetterListBean> listBeans;

    private  String custMobile;//客户手机号码
    private String custPwd;//密文密码
    private int pageNum=1;

    private Handler handler=new Handler(new MyCallable());
    private final class MyCallable implements Handler.Callback{
        @Override
        public boolean handleMessage(Message msg) {
            if(XNetworkUtils.isConnected()){
                pageNum=1;
                requestData();
                xLoadingView.showContent();
            }else {
                if(refreshLayout.isRefreshing()){
                    refreshLayout.setRefreshing(false);
                }
                xLoadingView.showNoNetwork();
            }
            return true;
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_web_site_msg;
    }

    @Override
    protected void initView() {
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        setTitle("站内信");
        xLoadingView=findViewById(R.id.xloading_view);
        refreshLayout=findViewById(R.id.srl_web_site_msg);
        recyclerView=findViewById(R.id.rl_web_site_msg);
        refreshLayout.setColorScheme(R.color.word_red);
        refreshLayout.setOnRefreshListener(refreshListener);

        xLoadingView.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xLoadingView.showLoading();
                handler.sendMessageDelayed(Message.obtain(),600);
            }
        });

        custMobile= (String) XPreferencesUtils.get("custMobile","");
        custPwd = (String) XPreferencesUtils.get("custPwd","");
        //dialog = new CustomDialog(this, R.style.custom_dialog2, R.layout.login_notice);

        listBeans=new ArrayList<>();
        DividerDecoration decoration=new DividerDecoration(R.color.light_gray,1);
        decoration.setDrawLastItem(false);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        adapter=new MyAdapter(recyclerView,listBeans);
        recyclerView.setAdapter(adapter);

        adapter.setOnLoadMoreListener(new XRecyclerViewAdapter.OnLoadMoreListener() {
            @Override
            public void onRetry() {

            }

            @Override
            public void onLoadMore() {
                requestData();
            }
        });

        //item点击事件
        adapter.setOnItemClickListener(new XRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                GetLetterListBean.LetterListBean letterListBean=adapter.getItem(position);

                Intent intent=new Intent(WebSiteMsgActivity.this,ReadLetterActivity.class);
                intent.putExtra("position",position);
                intent.putExtra("letterId",letterListBean.getId());
                intent.putExtra("title",letterListBean.getLetrTitle());
                String date=letterListBean.getSendTime();
                String time=date.substring(0,4)+"-"+date.substring(4,6)+"-"+date.substring(6,8)+" "+date.substring(8,10)+":"+date.substring(10,12)+":"+date.substring(12,14);
                intent.putExtra("time",time);
                intent.putExtra("cont",letterListBean.getLetrCont());
                startActivityForResult(intent,1000);
            }
        });

//        //item长按删除
//        adapter.setOnItemLongClickListener(new XRecyclerViewAdapter.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(View v, int position) {
//                initDialog(position);
//                return false;
//            }
//        });
    }
//
//    private CustomDialog dialog;//删除提示框
//    private void initDialog(final int position){
//        GetLetterListBean.LetterListBean letterListBean=adapter.getItem(position);
//        final String letterId=letterListBean.getId();
//
//        dialog.show();
//        TextView titleTv=dialog.findViewById(R.id.tv_login_notice_title);
//        TextView cancelTv=dialog.findViewById(R.id.tv_login_notice_cancel);
//        TextView sureTv=dialog.findViewById(R.id.tv_login_notice_sure);
//        titleTv.setText("确认删除该条信息吗？");
//        cancelTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //提示框取消
//                dialog.dismiss();
//            }
//        });
//        sureTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Map<String,Object > params =new HashMap<>();
//                params.put("custMobile",custMobile);
//                params.put("custPwd",custPwd);
//                params.put("letterId",letterId);
//                XHttp.obtain().post(Path.delete_letter, params, new HttpCallBack<ResCodeBean>() {
//                    @Override
//                    public void onSuccess(ResCodeBean bean) {
//                           if (bean.isStatus()){
//                               adapter.remove(position);
//                               adapter.notifyDataSetChanged();
//                               dialog.dismiss();
//                               XToast.normal("删除成功!");
//                           }else {
//                               dialog.dismiss();
//                               XToast.normal("删除失败!");
//                           }
//                    }
//
//                    @Override
//                    public void onFailed(String error) {
//
//                    }
//                });
//
//            }
//        });
//
//    }

    private SwipeRefreshLayout.OnRefreshListener refreshListener=new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            handler.sendMessage(Message.obtain());
        }
    };

    private void requestData() {
        Map<String,Object > params =new HashMap<>();
        params.put("custMobile",custMobile);
        params.put("custPwd",custPwd);
        //params.put("letterStatus","3");//不传这个参数，查询所有站内信，1-未读，2-已读
        params.put("pageNum",""+pageNum);
        params.put("pageSize","20");
        XHttp.obtain().post(Path.get_letter_list, params, new HttpCallBack<GetLetterListBean>() {
            @Override
            public void onSuccess(GetLetterListBean bean) {
                if(bean.isStatus()){
                    if(bean.getLetterList().size()>0){
                        if(pageNum==1){
                            adapter.addAll(bean.getLetterList());
                            adapter.isLoadMore(true);
                        }else {
                            adapter.addAll(bean.getLetterList());
                        }
                    }

                    if("1".equals(bean.getPageNum()) && "0".equals(bean.getTotalPages()) && bean.getLetterList().size()==0){
                        xLoadingView.showEmpty();
                    }
                    if(bean.getPageNum().equals(bean.getTotalPages()) || "0".equals(bean.getTotalPages())){
                        adapter.showLoadComplete();
                    }else {
                        pageNum=pageNum+1;
                    }
                }else {
                    xLoadingView.showError();
                }

                if(refreshLayout.isRefreshing()){
                    refreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailed(String error) {

            }
        });
    }

    @Override
    protected void loadData() {
        refreshLayout.setRefreshing(true);
        refreshListener.onRefresh();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMsg(ReadLetterMsg msg){
        int status=msg.getStatus();
        if(status==1){
            //1-未读，2-已读
            GetLetterListBean.LetterListBean letterListBean=adapter.getItem(msg.getPosition());
            letterListBean.setLetrStatus(2);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onDestroy() {
        //如果参数为null的话，会将所有的Callbacks和Messages全部清除掉。
        handler.removeCallbacksAndMessages( null );
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    class MyAdapter extends XRecyclerViewAdapter<GetLetterListBean.LetterListBean>{

        public MyAdapter(@NonNull RecyclerView mRecyclerView, List<GetLetterListBean.LetterListBean> dataLists) {
            super(mRecyclerView, dataLists,R.layout.item_rl_web_site_msg);
        }

        @Override
        protected void bindData(XViewHolder holder, GetLetterListBean.LetterListBean data, int position) {
            TextView tvTitle=holder.getView(R.id.tv_title);
            TextView tvTime=holder.getView(R.id.tv_time);
            TextView tvType=holder.getView(R.id.tv_type);

            tvTitle.setText(data.getLetrTitle());

            String date=data.getSendTime();
            String time=date.substring(0,4)+"-"+date.substring(4,6)+"-"+date.substring(6,8)+" "+date.substring(8,10)+":"+date.substring(10,12)+":"+date.substring(12,14);
            //20170729154945
            tvTime.setText(time);

            //1-未读，2-已读
            int letrStatus=data.getLetrStatus();
            if(letrStatus==1){
                tvType.setText("未读");
                tvType.setTextColor(getResources().getColor(R.color.word_red));
            }else if(letrStatus==2){
                tvType.setText("已读");
                tvType.setTextColor(getResources().getColor(R.color.word_gray));
            }
        }
    }

}
