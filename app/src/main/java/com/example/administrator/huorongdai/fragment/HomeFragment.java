package com.example.administrator.huorongdai.fragment;


import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Html;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.administrator.huorongdai.MainActivity;
import com.example.administrator.huorongdai.Path;
import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.activity.BidDetailActivity;
import com.example.administrator.huorongdai.activity.HelpCenterActivity;
import com.example.administrator.huorongdai.activity.HtmlActivity;
import com.example.administrator.huorongdai.activity.HuodongActivity;
import com.example.administrator.huorongdai.activity.LoginActivity;
import com.example.administrator.huorongdai.activity.MyMessageActivity;
import com.example.administrator.huorongdai.activity.NoticeDetailActivity;
import com.example.administrator.huorongdai.activity.OnlineCustomerServiceActivity;
import com.example.administrator.huorongdai.adapter.AllFragmentAdapter;
import com.example.administrator.huorongdai.adapter.MyGridLayoutManager;
import com.example.administrator.huorongdai.adapter.NoticeAdapter;
import com.example.administrator.huorongdai.base.LazyLoadFragment;
import com.example.administrator.huorongdai.eventbusbean.Msg;
import com.example.administrator.huorongdai.eventbusbean.NoticeModel;
import com.example.administrator.huorongdai.eventbusbean.UpdateBean;
import com.example.administrator.huorongdai.gsonbean.BannerBean;
import com.example.administrator.huorongdai.gsonbean.CheckVersionBean;
import com.example.administrator.huorongdai.gsonbean.LoanAllBean;
import com.example.administrator.huorongdai.gsonbean.NoticeBean;
import com.example.administrator.huorongdai.gsonbean.RecommendLoanBean;
import com.example.administrator.huorongdai.util.ButtonUtils;
import com.example.administrator.huorongdai.util.GImageLoader;
import com.example.administrator.huorongdai.util.update.InstallUtils;
import com.example.administrator.huorongdai.view.CustomDialog;
import com.example.administrator.huorongdai.view.TranslucentActionBar;
import com.example.administrator.huorongdai.view.TranslucentScrollView;
import com.example.administrator.huorongdai.view.banner.Banner;
import com.example.administrator.huorongdai.view.banner.listener.OnBannerListener;
import com.example.administrator.huorongdai.view.noticeview.VerticalBannerView;
import com.example.administrator.huorongdai.xframe.adapter.XRecyclerViewAdapter;
import com.example.administrator.huorongdai.xframe.utils.XAppUtils;
import com.example.administrator.huorongdai.xframe.utils.XEmptyUtils;
import com.example.administrator.huorongdai.xframe.utils.XNetworkUtils;
import com.example.administrator.huorongdai.xframe.utils.XPreferencesUtils;
import com.example.administrator.huorongdai.xframe.utils.http.HttpCallBack;
import com.example.administrator.huorongdai.xframe.utils.http.XHttp;
import com.example.administrator.huorongdai.xframe.utils.permission.XPermission;
import com.example.administrator.huorongdai.xframe.utils.statusbar.XStatusBar;
import com.example.administrator.huorongdai.xframe.widget.XToast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.administrator.huorongdai.xframe.utils.statusbar.XStatusBar.getStatusBarHeight;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends LazyLoadFragment implements View.OnClickListener {

    public HomeFragment() {
    }

    private Banner banner;//banner控件
    private GImageLoader imageLoader=new GImageLoader();
    private VerticalBannerView noticeBanner;//通知控件
    private LinearLayout llHotActivity;//热门活动
    private LinearLayout llReport;//媒体报道
    private LinearLayout llHelpCenter;//帮助中心
    private LinearLayout llOnlineCustomer;//在线客服
    private RecyclerView recyclerView;//新手项目
    private AllFragmentAdapter allFragmentAdapter;
    private List<LoanAllBean.LoanListBean> loanListBeans=new ArrayList<>();
    private TextView tvLoanArp;//推荐项目预计年利率
    private TextView tvLoanExpiry;//推荐项目剩余天数
    private TextView tvExpiryUnit;//推荐项目(天/月)
    private TextView tvTotalInvestMoney;//推荐项目总投资金额
    private TextView tvQitou;//推荐项目起投金额
    private Button btnTouzi;//推荐项目投资
    private List<String> bannerImages=new ArrayList<>();//banner图片list
    private List<String> bannerUrls=new ArrayList<>();//banner详情list
    private List<String> bannerTitles=new ArrayList<>();//banner标题list
    private List<NoticeModel> noticeMs=new ArrayList<>();//notice通知标题list
    private NoticeAdapter adapter;
    private MainActivity mainActivity;
    private SwipeRefreshLayout homeRefresh;

    private String loanID="";
    private String loanTitle="";
    private String loanStatus="";

    private Handler handler=new Handler(new MyCallable());
    private final class MyCallable implements Handler.Callback{

        @Override
        public boolean handleMessage(Message msg) {
            if(msg.what==0){
                //banner请求
                XHttp.obtain().get(Path.banner_url, new HashMap<String, Object>(), new HttpCallBack<BannerBean>() {
                    @Override
                    public void onSuccess(BannerBean bannerBean) {
                        if(bannerImages!=null){
                            bannerImages.clear();
                        }
                        if(bannerUrls!=null){
                            bannerUrls.clear();
                        }
                        if(bannerTitles!=null){
                            bannerTitles.clear();
                        }
                        for (BannerBean.DataBean dataBean : bannerBean.getData()) {
                            bannerImages.add(Path.BASE_ImgURL+dataBean.getBannerIcon());
                            bannerUrls.add(dataBean.getBannerUrl());
                            bannerTitles.add(dataBean.getBannerTitle());
                        }
                        Message message=Message.obtain();
                        message.what=1;
                        handler.sendMessage(message);
                    }

                    @Override
                    public void onFailed(String error) {

                    }
                });
            }else if(msg.what==1){//banner设置数据
                requestNotice();
                //设置图片加载器
                banner.setImageLoader(imageLoader);
                //设置图片集合
                banner.setImages(bannerImages);
                //设置轮播时间
                banner.setDelayTime(3000);
                banner.setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        if(XEmptyUtils.isEmpty(bannerUrls.get(position))){
                            return;
                        }
                        if(!ButtonUtils.isFastDoubleClick()&&bannerImages.size()>0){
                            Intent intent = new Intent(getActivity(), HtmlActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("url", bannerUrls.get(position));
                            bundle.putString("title", bannerTitles.get(position));
                            bundle.putString("name", "activity");
                            intent.putExtra("bundle", bundle);
                            startActivity(intent);
                        }
                    }
                });
                //banner设置方法全部调用完毕时最后调用
                banner.start();
                banner.startAutoPlay();
            }else if(msg.what==2){//通知banner设置数据
                requestRecommendLoan();
                if(noticeMs.size()>0){
                    if(adapter==null){
                        adapter=new NoticeAdapter(noticeMs);
                        noticeBanner.setAdapter(adapter);
                    }

                    if(noticeBanner.getAdapter()==null){
                        if(adapter==null){
                            adapter=new NoticeAdapter(noticeMs);
                        }
                        noticeBanner.setAdapter(adapter);
                    }

                    adapter.setOnItemClickListener(new NoticeAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, NoticeModel data) {
                            if(!ButtonUtils.isFastDoubleClick()){
                                Intent intent=new Intent(getActivity(), NoticeDetailActivity.class);
                                intent.putExtra("notice_id",data.url);
                                startActivity(intent);
                            }
                        }
                    });
                    noticeBanner.start();
                }


            }else if(msg.what==3){//推荐项目设置数据
                requestNewHand();
                RecommendLoanBean.ComLoanListBean comLoanListBean= (RecommendLoanBean.ComLoanListBean) msg.obj;
                tvLoanArp.setText(comLoanListBean.getLoanArp()+"");
                if(1==comLoanListBean.getExpiryUnit()){
                    tvExpiryUnit.setText("天");
                }else {
                    tvExpiryUnit.setText("月");
                }
                tvLoanExpiry.setText(comLoanListBean.getLoanExpiry()+"");
                int maxIvst=comLoanListBean.getMaxIvst();
                int totalInvestMoney =comLoanListBean.getTotalInvestMoney();
                String money=String.valueOf(maxIvst-totalInvestMoney);
//                if(money.length()>3){
//                    StringBuffer stringBuffer=new StringBuffer(money);
//                    stringBuffer.insert(money.length()-3,",");
//                    money=stringBuffer.toString();
//                }
                tvTotalInvestMoney.setText(money);
                String title=comLoanListBean.getLoanTitle();
                if(title.contains("[")&&title.contains("]")){
                    title=title.substring(title.indexOf("[")+1,title.indexOf("]"));
                }

                btnTouzi.setText(title);
                tvQitou.setText(""+comLoanListBean.getMinIvst());
                if(homeRefresh.isRefreshing()){
                    homeRefresh.setRefreshing(false);
                }
            }
            //返回true表明事件被消费掉了，返回false表明事件没有被消费掉喇叭
            return true;
        }
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_home;
    }

    private TranslucentScrollView scrollView;
    private TranslucentActionBar actionBar;
    @Override
    protected void initViews() {
        EventBus.getDefault().register(this);
        mainActivity= (MainActivity) getActivity();
        XStatusBar.setTranslucentForImageView(mainActivity, 0, banner);
        actionBar=findViewById(R.id.actionbar_main);
        scrollView=findViewById(R.id.scrollview_main);
        recyclerView=findViewById(R.id.rl_home_fragment);
        llHotActivity=findViewById(R.id.ll_hot_activity);
        llReport=findViewById(R.id.ll_report);
        llHelpCenter=findViewById(R.id.ll_help_center);
        llOnlineCustomer=findViewById(R.id.ll_online_customer);
        llHotActivity.setOnClickListener(this);
        llReport.setOnClickListener(this);
        llHelpCenter.setOnClickListener(this);
        llOnlineCustomer.setOnClickListener(this);
        tvExpiryUnit=findViewById(R.id.tv_expiryUnit);
        btnTouzi=findViewById(R.id.btn_touzi);
        btnTouzi.setOnClickListener(this);
        banner=findViewById(R.id.home_banner);
        banner.setFocusable(true);
        banner.setFocusableInTouchMode(true);
        noticeBanner=findViewById(R.id.notice_banner);
        tvLoanArp=findViewById(R.id.tv_loanArp);
        tvLoanExpiry=findViewById(R.id.tv_loanExpiry);
        tvTotalInvestMoney=findViewById(R.id.tv_totalInvestMoney);
        tvQitou=findViewById(R.id.tv_qitou);
        homeRefresh=findViewById(R.id.home_refresh);
        homeRefresh.setColorScheme(R.color.word_red);
        homeRefresh.setOnRefreshListener(refreshListener);

        //解决swiperefreshlayout与scrollview的冲突问题
        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if ( homeRefresh!= null) {
                    homeRefresh.setEnabled(scrollView.getScrollY() == 0);
                }
            }
        });

        dialog = new CustomDialog(getActivity(), R.style.custom_dialog2, R.layout.update_notice);
        dialogDownloading = new CustomDialog(getActivity(), R.style.custom_dialog2, R.layout.dialog_downloading);

        allFragmentAdapter=new AllFragmentAdapter(recyclerView,loanListBeans);
        recyclerView.setLayoutManager(new MyGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(allFragmentAdapter);
        recyclerView.setNestedScrollingEnabled(false);//禁止rcyc嵌套滑动
        allFragmentAdapter.setOnItemClickListener(new XRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                boolean isLogin= (boolean) XPreferencesUtils.get("isLogin",false);
                if(isLogin){
                    if(!ButtonUtils.isFastDoubleClick()){
                        if(!XEmptyUtils.isEmpty(loanID)){
                            Intent intentProductDetail=new Intent(getActivity(), BidDetailActivity.class);
                            intentProductDetail.putExtra("loanID",allFragmentAdapter.getItem(position).getId());
                            intentProductDetail.putExtra("loanTitle",allFragmentAdapter.getItem(position).getLoanTitle());
                            intentProductDetail.putExtra("loanStatus",allFragmentAdapter.getItem(position).getLoanStatus()+"");
                            startActivity(intentProductDetail);
                        }
                    }
                }else {//去登录
                    Intent intentLogin=new Intent(getActivity(), LoginActivity.class);
                    startActivity(intentLogin);
                }
            }
        });

        if (XNetworkUtils.isConnected()) {
            check();
        }
        init();

        //有网络的情况下请求数据
        homeRefresh.setRefreshing(true);
        refreshListener.onRefresh();
    }

    private void init() {//初始actionBar
        actionBar.setData("首页");
        //开启渐变
        actionBar.setNeedTranslucent();
        //设置状态栏高度
        actionBar.setStatusBarHeight(getStatusBarHeight(getActivity()));

        //设置透明度变化监听
        scrollView.setTranslucentChangedListener(new TranslucentScrollView.TranslucentChangedListener() {
            @Override
            public void onTranslucentChanged(int transAlpha) {
                actionBar.tvTitle.setVisibility(transAlpha > 20 ? View.VISIBLE : View.GONE);
            }
        });
        //关联需要渐变的视图
        scrollView.setTransView(actionBar);
        //设置ActionBar键渐变颜色
        scrollView.setTransColor(getResources().getColor(R.color.word_red));

        //关联伸缩的视图
        //scrollView.setPullZoomView(banner);
    }

    @Override
    public void lazyLoad() {

    }

    //投标数据请求
    private void requestNewHand() {
        Map<String,Object > params =new HashMap<>();
        params.put("ivstCategory","5");//5是全部，小微是2，综合是3，新手是1
        params .put("loanStatusScope","");
        params .put("produId","");
        params .put("loanTypeStr","");
        params .put("loanExpiryScope","");
        params .put("loanArpScope","");
        params .put("pageNum","1");
        params .put("pageSize","3");
        XHttp.obtain().post(Path.project_list_url, params, new HttpCallBack<LoanAllBean>() {
            @Override
            public void onSuccess(LoanAllBean allBean) {
                if(allBean.getLoanList().size()>0){
                    if(allBean.isStatus()){
                        if(allFragmentAdapter!=null){
                            allFragmentAdapter.clear();
                        }
                        List<LoanAllBean.LoanListBean> list=allBean.getLoanList();
                        list.remove(0);
                        allFragmentAdapter.addAll(list);
                    }
                }
            }

            @Override
            public void onFailed(String error) {

            }
        });
    }

    //下拉刷新数据
    SwipeRefreshLayout.OnRefreshListener refreshListener=new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            //有网络的情况下请求数据
            if (XNetworkUtils.isConnected()) {
                Message message=Message.obtain();
                message.what=0;
                handler.sendMessage(message);
            }else {
                if(homeRefresh.isRefreshing()){
                    homeRefresh.setRefreshing(false);
                }
                XToast.normal("网络连接失败!");
            }
        }
    };

    //notice通知请求
    private void requestNotice(){
        Map<String,Object > params =new HashMap<>();
        params .put("pageNum","1");
        params .put("pageSize","5");
        XHttp.obtain().post(Path.notice_url, params, new HttpCallBack<NoticeBean>() {
            @Override
            public void onSuccess(NoticeBean bean) {
                if(bean.isStatus()){
                    if(noticeMs!=null){
                        noticeMs.clear();
                    }
                    for (NoticeBean.NoticeListBean noticeBean : bean.getNoticeList()) {
                        NoticeModel noticeModel=new NoticeModel(noticeBean.getNoticeTitle(),noticeBean.getId());
                        noticeMs.add(noticeModel);
                    }
                    Message message=Message.obtain();
                    message.what=2;
                    handler.sendMessage(message);
                }
            }

            @Override
            public void onFailed(String error) {

            }
        });
    }

    //推荐项目请求
    private void requestRecommendLoan(){
        Map<String,Object > params =new HashMap<>();
        params .put("pageNum","1");
        params .put("pageSize","5");
        XHttp.obtain().post(Path.recommend_loan_url, params, new HttpCallBack<RecommendLoanBean>() {
            @Override
            public void onSuccess(RecommendLoanBean loanBean) {
                if(loanBean.isStatus()){
                    RecommendLoanBean.ComLoanListBean comLoanListBean=  loanBean.getComLoanList().get(0);
                    loanID=comLoanListBean.getId();
                    loanTitle=comLoanListBean.getLoanTitle();
                    loanStatus=comLoanListBean.getLoanStatus()+"";
                    Message message=Message.obtain();
                    message.obj=comLoanListBean;
                    message.what=3;
                    handler.sendMessage(message);
                }
            }

            @Override
            public void onFailed(String error) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_online_customer://在线客服
                Intent intentOnline=new Intent(getActivity(), OnlineCustomerServiceActivity.class);
                startActivity(intentOnline);
                break;
            case R.id.ll_help_center://帮助中心
                Intent intentHelp=new Intent(getActivity(), HelpCenterActivity.class);
                startActivity(intentHelp);
                break;
            case R.id.ll_hot_activity://热门活动
                Intent intentHuodong=new Intent(getActivity(), HuodongActivity.class);
                startActivity(intentHuodong);
                break;
            case R.id.ll_report://货融资讯
                Intent intent1=new Intent(getActivity(),MyMessageActivity.class);
                startActivity(intent1);
                break;
            case R.id.btn_touzi:
                boolean isLogin= (boolean) XPreferencesUtils.get("isLogin",false);
                if(isLogin){
                    if(!ButtonUtils.isFastDoubleClick()){
                        if(!XEmptyUtils.isEmpty(loanID)){
                            //Intent intentProductDetail=new Intent(getActivity(), ProductDetailActivity.class);
                            Intent intentProductDetail=new Intent(getActivity(), BidDetailActivity.class);
                            intentProductDetail.putExtra("loanID",loanID);
                            intentProductDetail.putExtra("loanTitle",loanTitle);
                            intentProductDetail.putExtra("loanStatus",loanStatus);
                            startActivity(intentProductDetail);
                        }
                    }
                }else {//去登录
                    Intent intentLogin=new Intent(getActivity(), LoginActivity.class);
                    startActivity(intentLogin);
                }

                break;
        }
    }

    private CustomDialog dialog;//更新提示框
    private CustomDialog dialogDownloading;//下载框

    //适配Android8.0未知来源应用安装权限方案
    private void check() {
        boolean haveInstallPermission;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //先获取是否有安装未知来源应用的权限
            haveInstallPermission = getActivity().getPackageManager().canRequestPackageInstalls();
            if (!haveInstallPermission) {//没有权限
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("安装应用需要打开未知来源权限，请去设置中开启权限");
                builder.setPositiveButton("开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            startInstallPermissionSettingActivity();
                        }
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create().show();
                return;
            }
        }
        //有权限，开始安装应用程序
        checkVersion();
    }

    //版本更新
    public void checkVersion() {
        XPermission.requestPermissions(getActivity(), 101, new String[]{Manifest.permission
                .WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, new XPermission.OnPermissionListener() {
            //权限申请成功时调用
            @Override
            public void onPermissionGranted() {
                XHttp.obtain().get(Path.check_android_ver, new HashMap<String, Object>(), new HttpCallBack<CheckVersionBean>() {
                    @Override
                    public void onSuccess(CheckVersionBean bean) {
                        if(bean.getData().isStatus()){
                            if(bean.getData().getVerCode()> XAppUtils.getVersionCode(getActivity())){
                                normalUpdate(getActivity(),bean.getData().getVerName(),bean.getData().getAppFile(),bean.getData().getUpdateReason());
                            }
                        }
                    }

                    @Override
                    public void onFailed(String error) {

                    }
                });
            }
            //权限被用户禁止时调用
            @Override
            public void onPermissionDenied() {
                //给出友好提示，并且提示启动当前应用设置页面打开权限
                XPermission.showTipsDialog(getActivity());
            }
        });
    }

    /**
     * 正常更新
     */
    private void normalUpdate(final Context context, final String appName, final String downUrl,  String updateinfo) {
        dialog.show();
        TextView titleTv=dialog.findViewById(R.id.tv_update_title);
        TextView infoTv=dialog.findViewById(R.id.tv_update_info);
        TextView cancelTv=dialog.findViewById(R.id.tv_update_cancel);
        TextView sureTv=dialog.findViewById(R.id.tv_update_sure);
        titleTv.setText(appName+"更新");
        infoTv.setText(Html.fromHtml(updateinfo));
        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //提示框取消
                dialog.dismiss();
            }
        });
        sureTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                downloading(context,appName,downUrl);
            }
        });
    }

    //下载中
    private void downloading(final Context context, final String appName, final String downUrl){
        dialogDownloading.show();
        dialogDownloading.setCancelable(false);
        final SeekBar seekBar=dialogDownloading.findViewById(R.id.download_bar);
        TextView tvCancel=dialogDownloading.findViewById(R.id.tv_cancel);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InstallUtils.cancleDownload();
            }
        });
        InstallUtils.with(context)
                .setApkUrl(downUrl)
                .setApkName(appName)
                .setCallBack(new InstallUtils.DownloadCallBack() {
                    @Override
                    public void onStart() {//下载开始

                    }

                    @Override
                    public void onComplete(String path) {//下载完成
                        dialogDownloading.dismiss();
                        InstallUtils.installAPK(context, path, new InstallUtils.InstallCallBack() {//安装APK
                            @Override
                            public void onSuccess() {
                            }

                            @Override
                            public void onFail(Exception e) {
                            }
                        });
                    }

                    @Override
                    public void onLoading(long total, long current) {//下载中
                        seekBar.setMax((int) total);
                        seekBar.setProgress((int) current);
                    }

                    @Override
                    public void onFail(Exception e) {//下载失败

                    }

                    @Override
                    public void cancle() {//下载取消
                        dialogDownloading.dismiss();
                    }
                }).startDownload();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startInstallPermissionSettingActivity() {
        Uri packageURI = Uri.parse("package:" + getActivity().getPackageName());
        //注意这个是8.0新API
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
        startActivityForResult(intent, 120);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMsg(UpdateBean bean){
       checkVersion();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Override
    protected void stopLoad() {
        if(banner!=null){
            //结束轮播
            banner.stopAutoPlay();
        }
        if(noticeBanner!=null){
            noticeBanner.stop();
        }
        if(dialog!=null && dialog.isShowing()){
            dialog.dismiss();
        }
    }

}
