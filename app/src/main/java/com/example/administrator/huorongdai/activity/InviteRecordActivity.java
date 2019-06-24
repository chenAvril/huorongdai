package com.example.administrator.huorongdai.activity;

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
import com.example.administrator.huorongdai.gsonbean.InviteDataBean;
import com.example.administrator.huorongdai.gsonbean.InviteFriendListBean;
import com.example.administrator.huorongdai.xframe.adapter.XRecyclerViewAdapter;
import com.example.administrator.huorongdai.xframe.adapter.XViewHolder;
import com.example.administrator.huorongdai.xframe.adapter.decoration.DividerDecoration;
import com.example.administrator.huorongdai.xframe.utils.XNetworkUtils;
import com.example.administrator.huorongdai.xframe.utils.XPreferencesUtils;
import com.example.administrator.huorongdai.xframe.utils.http.HttpCallBack;
import com.example.administrator.huorongdai.xframe.utils.http.XHttp;
import com.example.administrator.huorongdai.xframe.widget.loadingview.XLoadingView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InviteRecordActivity extends BaseActivity {

    private TextView tvPeopleNumber;//邀请人数
    private TextView tvRedPackage;//邀请红包
    private TextView tvIntegral;//邀请积分
    private TextView tvReward;//邀请奖励

    private SwipeRefreshLayout refreshLayout;
    private XLoadingView xLoadingView;
    private RecyclerView recyclerView;

    private MyAdapter adapter;
    private List<InviteFriendListBean.InviteListBean> listBeans=new ArrayList<>();

    private String custMobile;//手机号码
    private String custPwd;//密文密码
    private int pageNum=1;

    private Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            pageNum=1;
            inviteFriend();
            xLoadingView.showContent();
            return true;
        }
    });



    @Override
    protected int getContentView() {
        return R.layout.activity_invite_record;
    }

    @Override
    protected void initView() {
        tvPeopleNumber=findViewById(R.id.tv_people_number);
        tvRedPackage=findViewById(R.id.tv_red_package);
        tvIntegral=findViewById(R.id.tv_integral);
        tvReward=findViewById(R.id.tv_reward);
        refreshLayout=findViewById(R.id.srl_invite_record);
        refreshLayout.setColorScheme(R.color.word_red);
        refreshLayout.setOnRefreshListener(refreshListener);
        xLoadingView=findViewById(R.id.xloading_view);
        recyclerView=findViewById(R.id.rv_invite_record);

        xLoadingView.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xLoadingView.showLoading();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(XNetworkUtils.isConnected()){
                            refreshLayout.setRefreshing(true);
                            refreshListener.onRefresh();
                        }else {
                            xLoadingView.showNoNetwork();
                        }
                    }
                }, 600);
            }
        });

        setTitle("邀请记录");

        custMobile = (String) XPreferencesUtils.get("custMobile","");
        custPwd = (String) XPreferencesUtils.get("custPwd","");

        adapter=new MyAdapter(recyclerView,listBeans);
        DividerDecoration decoration=new DividerDecoration(R.color.gray_bg,1);
        decoration.setDrawLastItem(false);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);

        adapter.setOnLoadMoreListener(new XRecyclerViewAdapter.OnLoadMoreListener() {
            @Override
            public void onRetry() {

            }

            @Override
            public void onLoadMore() {
                inviteFriend();
            }
        });
    }

    @Override
    protected void loadData() {
        if(XNetworkUtils.isConnected()){
            refreshLayout.setRefreshing(true);
            refreshListener.onRefresh();
        }else {
            xLoadingView.showNoNetwork();
        }
    }

    private SwipeRefreshLayout.OnRefreshListener refreshListener=new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            inviteData();
            adapter.isLoadMore(false);
            handler.sendMessage(Message.obtain());
        }
    };

    //统计邀请接口
    private void inviteData() {
        Map<String,Object > params =new HashMap<>();
        params .put("custMobile",custMobile);
        params .put("custPwd",custPwd);
        XHttp.obtain().post(Path.static_invite_data, params, new HttpCallBack<InviteDataBean>() {
            @Override
            public void onSuccess(InviteDataBean bean) {
                if(bean.isStatus()){
                    tvPeopleNumber.setText(bean.getInviteCustNum()+"人");
                    tvRedPackage.setText(new DecimalFormat("0").format(bean.getInviteRegistRedpAmt())+"元");
                    tvIntegral.setText(bean.getInvitePoint()+"分");
                    tvReward.setText(new DecimalFormat("0").format(bean.getInviteIvstRedpAmt())+"元");
                }
            }

            @Override
            public void onFailed(String error) {

            }
        });
    }

    //查询邀请好友列表接口
    private void inviteFriend() {
        Map<String,Object > params =new HashMap<>();
        params .put("custMobile",custMobile);
        params .put("custPwd",custPwd);
        params .put("pageNum",""+pageNum);
        params .put("pageSize","20");
        XHttp.obtain().post(Path.invite_friend_list, params, new HttpCallBack<InviteFriendListBean>() {
            @Override
            public void onSuccess(InviteFriendListBean bean) {
                if(bean.isStatus()){
                    if(bean.getInviteList().size()>0){
                        if(pageNum==1){
                            adapter.setDataLists(bean.getInviteList());
                            adapter.isLoadMore(true);
                        }else {
                            adapter.addAll(bean.getInviteList());
                        }
                    }

                    if("1".equals(bean.getPageNum()) && "0".equals(bean.getTotalPages()) && bean.getInviteList().size()==0){
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
    public void onClick(View view) {

    }

    class MyAdapter extends XRecyclerViewAdapter<InviteFriendListBean.InviteListBean>{

        public MyAdapter(@NonNull RecyclerView mRecyclerView, List<InviteFriendListBean.InviteListBean> dataLists) {
            super(mRecyclerView, dataLists,R.layout.item_rv_invite_record);
        }

        @Override
        protected void bindData(XViewHolder holder, InviteFriendListBean.InviteListBean data, int position) {
            TextView tvName=holder.getView(R.id.tv_cus_name);//名字
            TextView tvPackage=holder.getView(R.id.tv_red_package);//红包
            TextView tvJifen=holder.getView(R.id.tv_jifen);//积分
            TextView tvMoney=holder.getView(R.id.tv_money);//佣金

            String name=data.getCustName();
            if(name.length()>0){
                if(name.length()>4){
                    name=name.substring(0,2)+"**"+name.substring(name.length()-3,name.length());
                }else {
                    name=name.substring(0,1)+"**";
                }
            }
            tvName.setText(name);
            tvPackage.setText(data.getInviteRegistRedp());
            tvJifen.setText(data.getInvitePoint());
            tvMoney.setText(data.getInviteIvstRedp());

        }
    }
}
