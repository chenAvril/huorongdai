package com.example.administrator.huorongdai.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.example.administrator.huorongdai.Path;
import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.adapter.SimpleAdapter;
import com.example.administrator.huorongdai.base.BaseActivity;
import com.example.administrator.huorongdai.base.LazyLoadFragment;
import com.example.administrator.huorongdai.fragment.IntegralShopFragment;
import com.example.administrator.huorongdai.gsonbean.GoodsImgBean;
import com.example.administrator.huorongdai.util.GImageLoader;
import com.example.administrator.huorongdai.view.banner.Banner;
import com.example.administrator.huorongdai.xframe.utils.XEmptyUtils;
import com.example.administrator.huorongdai.xframe.utils.XNetworkUtils;
import com.example.administrator.huorongdai.xframe.utils.http.HttpCallBack;
import com.example.administrator.huorongdai.xframe.utils.http.XHttp;
import com.example.administrator.huorongdai.xframe.widget.loadingview.XLoadingView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//积分商城
public class IntegralShopActivity extends BaseActivity {

    private XLoadingView xLoadingView;
    private SwipeRefreshLayout refreshLayout;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Banner banner;
    private List<LazyLoadFragment> fragmentList;
    private List<String> titles;
    private List<String> bannerImages=new ArrayList<>();//banner图片list
    private SimpleAdapter adapter;
    private GImageLoader imageLoader=new GImageLoader();

    private Handler handler=new Handler(new MyCallable());
    private final class MyCallable implements Handler.Callback{

        @Override
        public boolean handleMessage(Message msg) {
            if(msg.what==1){
                //设置图片加载器
                banner.setImageLoader(imageLoader);
                //设置图片集合
                banner.setImages(bannerImages);
                //设置轮播时间
                banner.setDelayTime(3000);
                //banner设置方法全部调用完毕时最后调用
                banner.start();
                banner.startAutoPlay();

                if(adapter==null){
                    adapter = new SimpleAdapter(getSupportFragmentManager(),fragmentList,titles);
                }
                viewPager.setOffscreenPageLimit(titles.size()-1);
                viewPager.setAdapter(adapter);
                tabLayout.setupWithViewPager(viewPager); //注意在Toolbar中关联ViewPager

                xLoadingView.showContent();
                if(refreshLayout.isRefreshing()){
                    refreshLayout.setRefreshing(false);
                }
            }else if(msg.what==2){
                if (XNetworkUtils.isConnected()){
                    requestInfo();
                }else {
                    if(refreshLayout.isRefreshing()){
                        refreshLayout.setRefreshing(false);
                    }
                    xLoadingView.showNoNetwork();
                }
            }else if(msg.what==3){
                int pos=tabLayout.getSelectedTabPosition();
                IntegralShopFragment fragment= (IntegralShopFragment) fragmentList.get(pos);
                fragment.refresh();
            }
            return true;
        }
    }

    private IntegralShopFragment.OnRefresh onRefresh=new IntegralShopFragment.OnRefresh() {
        @Override
        public void setOnRefresh() {
            if(refreshLayout.isRefreshing()){
                refreshLayout.setRefreshing(false);
            }
        }
    };

    private SwipeRefreshLayout.OnRefreshListener refreshListener=new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            Message message=Message.obtain();
            message.what=3;
            handler.sendMessage(message);
        }
    };

    @Override
    protected int getContentView() {
        return R.layout.activity_integral_shop;
    }

    @Override
    protected void initView() {
        setTitle("积分商城");
        setRightBtnVisible(true);
        setRtTitle("我的积分");

        xLoadingView=findViewById(R.id.xloading_view);
        refreshLayout=findViewById(R.id.srl_integral_shop);
        refreshLayout.setColorScheme(R.color.word_red);
        refreshLayout.setOnRefreshListener(refreshListener);
        banner=findViewById(R.id.integral_shop_banner);
        tabLayout=findViewById(R.id.tab_integral_shop);
        viewPager=findViewById(R.id.vp_integral_shop);
        xLoadingView.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xLoadingView.showLoading();
                Message message=Message.obtain();
                message.what=2;
                handler.sendMessageDelayed(message,600);
            }
        });

        titles=new ArrayList<>();
        fragmentList=new ArrayList<>();
    }

    @Override
    protected void onClickRight() {
        //super.onClickRight();
        Intent intent=new Intent(this, MyIntegralActivity.class);
        startActivity(intent);
    }

    @Override
    protected void loadData() {
        if (XNetworkUtils.isConnected()){
            requestInfo();
        }else {
            if(refreshLayout.isRefreshing()){
                refreshLayout.setRefreshing(false);
            }
            xLoadingView.showNoNetwork();
        }
    }

    private void requestInfo(){
        Map<String,Object > params =new HashMap<>();
        params.put("","");
        XHttp.obtain().post(Path.goods_img, params, new HttpCallBack<GoodsImgBean>() {
            @Override
            public void onSuccess(GoodsImgBean bean) {
               if("true".equals(bean.getStatus())){
                   titles.clear();
                   fragmentList.clear();
                   for(GoodsImgBean.ShanglistBean shangBean:bean.getShanglist()){
                       titles.add(shangBean.getDictCont());
                       IntegralShopFragment shopFragment=new IntegralShopFragment(shangBean.getId(),shangBean.getDictCont());
                       shopFragment.setOnRefresh(onRefresh);
                       fragmentList.add(shopFragment);
                   }
                   bannerImages.clear();
                   for(GoodsImgBean.BannerlistBean bannerBean:bean.getBannerlist()){
                       String url=bannerBean.getBannerIcon();
                       if(!XEmptyUtils.isEmpty(url)){
                           bannerImages.add(Path.BASE_ImgURL+url);
                       }
                   }
                   Message message=Message.obtain();
                   message.what=1;
                   handler.sendMessage(message);
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
    public void onClick(View view) {
//        switch (view.getId()){
//            case R.id.activity_main:
//
//                break;
//        }
    }

}
