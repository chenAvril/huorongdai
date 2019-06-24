package com.example.administrator.huorongdai.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.administrator.huorongdai.MainActivity;
import com.example.administrator.huorongdai.Path;
import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.adapter.SimpleAdapter;
import com.example.administrator.huorongdai.base.BaseActivity;
import com.example.administrator.huorongdai.base.LazyLoadFragment;
import com.example.administrator.huorongdai.eventbusbean.RedPackageMsg;
import com.example.administrator.huorongdai.fragment.LoanInvestListFragment;
import com.example.administrator.huorongdai.fragment.RepaymentPlanFragment;
import com.example.administrator.huorongdai.gsonbean.InvestBean;
import com.example.administrator.huorongdai.gsonbean.LoanViewBean;
import com.example.administrator.huorongdai.gsonbean.RealTimeCustBean;
import com.example.administrator.huorongdai.util.ButtonUtils;
import com.example.administrator.huorongdai.util.GImageLoader;
import com.example.administrator.huorongdai.view.AmountView;
import com.example.administrator.huorongdai.view.AutofitViewPager;
import com.example.administrator.huorongdai.view.MyScrollView;
import com.example.administrator.huorongdai.view.banner.Banner;
import com.example.administrator.huorongdai.view.banner.listener.OnBannerListener;
import com.example.administrator.huorongdai.xframe.utils.XDateUtils;
import com.example.administrator.huorongdai.xframe.utils.XEmptyUtils;
import com.example.administrator.huorongdai.xframe.utils.XNetworkUtils;
import com.example.administrator.huorongdai.xframe.utils.XPreferencesUtils;
import com.example.administrator.huorongdai.xframe.utils.http.HttpCallBack;
import com.example.administrator.huorongdai.xframe.utils.http.XHttp;
import com.example.administrator.huorongdai.xframe.utils.log.XLog;
import com.example.administrator.huorongdai.xframe.widget.XLoadingDialog;
import com.example.administrator.huorongdai.xframe.widget.XToast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDetailActivity extends BaseActivity {

    private TextView tvLoanArp;//预计年利率
    private TextView tvProgress;//投资进度
    private SeekBar seekbarProgress;
    private TextView tvLoanAmt;//总投资金额
    private TextView tvLoanExpiry;//还款期限A
    private TextView tvRemainingAmount;//剩余金额
    private TextView tvMinIvst;//起投金额
    private TextView tvMaxIvst;//单笔最大投资金额
    private TextView tvPaymentStyle;//付款方式
    private TextView tvStartTime;//开始时间
    private TextView tvEndTime;//截止时间
    private TextView tvContractNum;//合同编号
    private TextView tvProduName;//所属产品
    private TextView tvToBorrow;//借款期限
    private TextView tvRpmtTypeName;//付款方式
    private TextView tvLoanDesc;//项目描述
    private TextView tvLoanUse;//项目用途
    private TextView tvDanbaoMeasure;//担保措施
    private TextView tvDiyawu;//抵押物性质
    private LinearLayout llDiyawu;
    private TextView tvRmptResource;//还款来源
    private Banner dataTypeBanner;//证件类型
    private LinearLayout llDataType;
    private Button btnInvestment;//立即投资

    private LinearLayout hideView1Ll;
    private LinearLayout hide1Ll;
    private ImageView hideOneIv;

    private LinearLayout hideView2Ll;
    private LinearLayout hide2Ll;
    private ImageView hideTwoIv;

    private MyScrollView scrollView;
    //private XLoadingView xLoadingView;
    private SwipeRefreshLayout refreshLayout;
    private TabLayout mTlTab;
    private AutofitViewPager viewPager;
    private List<LazyLoadFragment> fragmentList;

    private RepaymentPlanFragment repaymentPlanFragment;//还款记录
    private LoanInvestListFragment loanInvestListFragment;//投标记录

    private Dialog mCameraDialog;//投资对话框

    private String custMobile;
    private String custName;
    private String custPwd;//密文密码
    private String loanID;
    private String loanTitle;
    private String loanStatus;//除了“7”，立即投资均不能点击
    private String canBeCast;//当前标的可投金额

    @Override
    protected int getContentView() {
        return R.layout.activity_product_detail;
    }

    @Override
    protected void initView() {
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }

        Intent intent=getIntent();
        if(intent!=null){
            loanID=intent.getStringExtra("loanID");
            loanTitle=intent.getStringExtra("loanTitle");
            loanStatus=intent.getStringExtra("loanStatus");

            setTitle(loanTitle);
        }

        tvLoanArp=findViewById(R.id.tv_loan_arp);
        tvProgress=findViewById(R.id.tv_progress);
        seekbarProgress=findViewById(R.id.seekbar_progress);
        tvLoanAmt=findViewById(R.id.tv_loan_amt);
        tvLoanExpiry=findViewById(R.id.tv_loan_expiry);
        tvRemainingAmount=findViewById(R.id.tv_remaining_amount);
        tvMinIvst=findViewById(R.id.tv_min_ivst);
        tvMaxIvst=findViewById(R.id.tv_max_ivst);
        tvPaymentStyle=findViewById(R.id.tv_payment_style);
        tvStartTime=findViewById(R.id.tv_start_time);
        tvEndTime=findViewById(R.id.tv_end_time);
        tvContractNum=findViewById(R.id.tv_contract_num);
        tvProduName=findViewById(R.id.tv_produ_name);
        tvToBorrow=findViewById(R.id.tv_to_borrow);
        tvRpmtTypeName=findViewById(R.id.tv_rpmt_typeName);
        tvLoanDesc=findViewById(R.id.tv_loan_desc);
        tvLoanUse=findViewById(R.id.tv_loan_use);
        tvDanbaoMeasure=findViewById(R.id.tv_danbao_measure);
        tvRmptResource=findViewById(R.id.tv_rmpt_resource);
        tvDiyawu=findViewById(R.id.tv_diyawu);
        llDiyawu=findViewById(R.id.ll_diyawu);
        dataTypeBanner=findViewById(R.id.data_type_banner);
        llDataType=findViewById(R.id.ll_data_type);
        btnInvestment=findViewById(R.id.btn_investment);
        btnInvestment.setOnClickListener(this);
        hideView1Ll =  findViewById(R.id.ll_hide_view1);
        hideOneIv=findViewById(R.id.iv_hide_one);
        hide1Ll=findViewById(R.id.ll_hide1);
        hide1Ll.setOnClickListener(this);
        hideView2Ll =  findViewById(R.id.ll_hide_view2);
        hideTwoIv=findViewById(R.id.iv_hide_two);
        hide2Ll=findViewById(R.id.ll_hide2);
        hide2Ll.setOnClickListener(this);
        mTlTab=findViewById(R.id.pd_tab);
        viewPager=findViewById(R.id.main_vp_container);
        //xLoadingView=findViewById(R.id.xloading_view);
        refreshLayout=findViewById(R.id.srl_product_detail);
        scrollView=findViewById(R.id.product_detail_scroll_view);
        refreshLayout.setColorScheme(R.color.word_red);
        refreshLayout.setOnRefreshListener(refreshListener);

        //解决swiperefreshlayout与scrollview的冲突问题
        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if (refreshLayout != null) {
                    refreshLayout.setEnabled(scrollView.getScrollY() == 0);
                }
            }
        });

        //重新加载
//        xLoadingView.setOnRetryClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                xLoadingView.showLoading();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        xLoadingView.showContent();
//                        refreshLayout.setRefreshing(true);
//                        refreshListener.onRefresh();
//                    }
//                },2000);
//            }
//        });

        repaymentPlanFragment=new RepaymentPlanFragment();
        loanInvestListFragment=new LoanInvestListFragment();

        fragmentList=new ArrayList<>();
        fragmentList.add(repaymentPlanFragment);
        fragmentList.add(loanInvestListFragment);
        List<String> titles=new ArrayList<>();
        titles.add("还款计划");
        titles.add("投标记录");

        // 设置ViewPager布局
        SimpleAdapter adapter = new SimpleAdapter(getSupportFragmentManager(),fragmentList,titles);
        viewPager.setAdapter(adapter);
        mTlTab.setTabMode(TabLayout.MODE_FIXED);
        mTlTab.setupWithViewPager(viewPager); // 注意在Toolbar中关联ViewPager

        custMobile = (String) XPreferencesUtils.get("custMobile","");
        custPwd = (String) XPreferencesUtils.get("custPwd","");
        custName= (String) XPreferencesUtils.get("custName","");

        if(!"7".equals(loanStatus)){
            btnInvestment.setVisibility(View.GONE);
        }
    }

    private Handler handler=new Handler(new MyCallable());
    private final class MyCallable implements Handler.Callback{

        @Override
        public boolean handleMessage(Message msg) {
            if(msg.what==1){//投资成功后的动作
                if(mCameraDialog.isShowing()){
                    mCameraDialog.dismiss();
                    refreshLayout.setRefreshing(true);
                    refreshListener.onRefresh();
                }
            }
            return true;
        }
    }


    private SwipeRefreshLayout.OnRefreshListener refreshListener=new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            if(XNetworkUtils.isConnected()){
                requestData();
            }else {
                XToast.normal("网络连接失败!");
                //xLoadingView.showNoNetwork();
            }

            //停止小球刷新
            if(refreshLayout.isRefreshing()){
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.setRefreshing(false);
                    }
                },1000);
            }
        }
    };

    @Override
    protected void loadData() {
        //xLoadingView.showContent();
        refreshLayout.setRefreshing(true);
        refreshListener.onRefresh();
    }
    private ArrayList<String> bannerImages=new ArrayList<>();
    private boolean isHide1=true;
    private boolean isHide2=true;
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_investment://立即投资
                //未开户是4，开户是2
                int openStatus= (int) XPreferencesUtils.get("openStatus",4);
                if(openStatus==4){
                    Intent intent=new Intent(this,OpenAccountActivity.class);
                    startActivity(intent);
                }else if(openStatus==2){
                    setDialog();
                }
                break;
            case R.id.ll_hide1://项目信息的显示和隐藏
                if(isHide1){
                    hideOneIv.setBackgroundResource(R.drawable.jiantou_up);
                    hideView1Ll.setVisibility(View.VISIBLE);
                    isHide1=false;
                }else {
                    hideOneIv.setBackgroundResource(R.drawable.jiantou_down);
                    hideView1Ll.setVisibility(View.GONE);
                    isHide1=true;
                }
                break;
            case R.id.ll_hide2://风控信息的显示和隐藏
                if(isHide2){
                    hideTwoIv.setBackgroundResource(R.drawable.jiantou_up);
                    hideView2Ll.setVisibility(View.VISIBLE);
                    isHide2=false;
                    if(dataTypeBanner!=null){
                        //banner设置方法全部调用完毕时最后调用
                        dataTypeBanner.start();
                        dataTypeBanner.startAutoPlay();
                    }
                }else {
                    hideTwoIv.setBackgroundResource(R.drawable.jiantou_down);
                    hideView2Ll.setVisibility(View.GONE);
                    isHide2=true;
                    if(dataTypeBanner!=null){
                        dataTypeBanner.stopAutoPlay();
                    }
                }
                break;
        }
    }

    private GImageLoader imageLoader=new GImageLoader();
    //请求数据
    private void requestData(){
        Map<String,Object > params =new HashMap<>();
        params .put("custMobile",custMobile);
        params .put("custPwd",custPwd);
        params .put("loanId",loanID);
        XHttp.obtain().post(Path.loan_view, params, new HttpCallBack<LoanViewBean>() {
            @Override
            public void onSuccess(LoanViewBean bean) {
                if(bean.isStatus()){
                    LoanViewBean.LoanBean loanBean=bean.getLoan();

                    yearRate=loanBean.getLoanArp();
                    tvLoanArp.setText(loanBean.getLoanArp()+"");//预计年利率

                    float progress=((float)loanBean.getTotalInvestMoney()/loanBean.getLoanAmt())*100;
                    tvProgress.setText(progress+"%");
                    seekbarProgress.setProgress((int) progress);

                    String amt=loanBean.getLoanAmt()+"";//总金额
                    String loanAmt=amt;
                    if(amt.endsWith("0000")){
                        amt=amt.substring(0,amt.length()-4);
                        tvLoanAmt.setText(amt+"万元");
                    }

                    year=loanBean.getLoanExpiry();
                    if(loanBean.getLoanExpiry()==1){//还款期限
                        tvLoanExpiry.setText(loanBean.getLoanExpiry()+"个月");
                    }else {
                        tvLoanExpiry.setText(loanBean.getLoanExpiry()+"天");
                    }

                    String  remainingAmount=(loanBean.getLoanAmt()-loanBean.getTotalInvestMoney())+"";//剩余金额
                    canBeCast=remainingAmount;
                    if(remainingAmount.endsWith("000")){
                        remainingAmount=remainingAmount.substring(0,remainingAmount.length()-3);
                        tvRemainingAmount.setText(remainingAmount+",000元");
                    }else {
                        tvRemainingAmount.setText(remainingAmount+"元");
                    }

                    tvMinIvst.setText(loanBean.getMinIvst()+"元");//起投金额

                    String maxIvst=loanBean.getMaxIvst()+"";//单笔最大投资金额
                    if(maxIvst.endsWith("000")){
                        maxIvst=maxIvst.substring(0,maxIvst.length()-3);
                        tvMaxIvst.setText(maxIvst+",000元");
                    }else {
                        tvMaxIvst.setText(maxIvst+"元");
                    }

                    tvPaymentStyle.setText("按日计息/"+loanBean.getRpmtTypeName());//付款方式
                    tvStartTime.setText(XDateUtils.millis2String(loanBean.getStartTimeMill()));//开始时间
                    tvEndTime.setText(XDateUtils.millis2String(loanBean.getEndTimeMill()));//截止时间

                    tvContractNum.setText(loanBean.getContractNum());//合同编号
                    tvProduName.setText(loanBean.getProduName());//所属产品

                    if(loanBean.getLoanExpiry()==1){//借款期限
                        tvToBorrow.setText(loanBean.getLoanExpiry()+"个月");
                    }else {
                        tvToBorrow.setText(loanBean.getLoanExpiry()+"天");
                    }

                    tvRpmtTypeName.setText(loanBean.getRpmtTypeName());//还款方式
                    String loanDesc=loanBean.getLoanDesc();//项目描述
                    loanDesc=loanDesc.replace("<p>","");
                    loanDesc=loanDesc.replace("</p>","");
                    tvLoanDesc.setText(Html.fromHtml(loanDesc));
                    tvLoanUse.setText(loanBean.getLoanUse());//项目用途

                    String danbaoInfo=bean.getAttrDataMap().getDANBAO_MEASURE();//担保措施
                    if(XEmptyUtils.isEmpty(danbaoInfo)){
                        tvDanbaoMeasure.setText("--");
                    }else {
                        danbaoInfo=danbaoInfo.replace("<p><br/></p>","");
                        danbaoInfo=danbaoInfo.replace("</p><p>","<br/>");
                        danbaoInfo=danbaoInfo.replace("<p>","");
                        danbaoInfo=danbaoInfo.replace("</p>","");
                        tvDanbaoMeasure.setText(Html.fromHtml(danbaoInfo));
                    }

                    String diyawu=bean.getAttrDataMap().getDI_YA_WU();//抵押物性质
                    if(XEmptyUtils.isEmpty(diyawu)){
                        llDiyawu.setVisibility(View.GONE);
                    }else {
                        llDiyawu.setVisibility(View.VISIBLE);
                        tvDiyawu.setText(diyawu);
                    }

                    String rmptResource=bean.getAttrDataMap().getRMPT_RESOURCE();//还款来源
                    if(XEmptyUtils.isEmpty(rmptResource)){
                        tvRmptResource.setText("--");
                    }else {
                        rmptResource=rmptResource.replace("</p><p><br/></p>","");
                        rmptResource=rmptResource.replace("</p><p>","<br/>");
                        rmptResource=rmptResource.replace("<p>","");
                        tvRmptResource.setText(Html.fromHtml(rmptResource));
                    }

                    String imgPath=bean.getAttrDataMap().getREL_DATUM();
                    if(XEmptyUtils.isEmpty(imgPath)){
                        llDataType.setVisibility(View.GONE);
                    }else {
                        String[] paths=imgPath.split(",");
                        if(paths.length>0){
                            llDataType.setVisibility(View.VISIBLE);
                            for (String path : paths) {
                                if(path.contains(".")){
                                    bannerImages.add(Path.BASE_ImgURL+path);
                                }
                            }

                            //设置图片加载器
                            dataTypeBanner.setImageLoader(imageLoader);
                            //设置图片集合
                            dataTypeBanner.setImages(bannerImages);
                            //设置轮播时间
                            dataTypeBanner.setDelayTime(3000);
//                                dataTypeBanner.start();
//                                dataTypeBanner.startAutoPlay();
                            dataTypeBanner.setOnBannerListener(new OnBannerListener() {
                                @Override
                                public void OnBannerClick(int position) {
                                    Intent intent = new Intent(ProductDetailActivity.this, ImagePagerActivity.class);
                                    intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, bannerImages);
                                    intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
                                    startActivity(intent);
                                }
                            });
                        }else {
                            llDataType.setVisibility(View.GONE);
                        }
                    }

                    List<LoanViewBean.RpmtPlanListBean> planListBeans= bean.getRpmtPlanList();
                    List<Map<String, String>> arrayList=new ArrayList<>();
                    for (LoanViewBean.RpmtPlanListBean planListBean : planListBeans) {
                        Map<String,String> map=new HashMap<>();
                        map.put("rpmtIssue",planListBean.getRpmtIssue()+"");
                        map.put("rpmtIint",planListBean.getRpmtIint()+"");
                        map.put("rpmtCapital",planListBean.getRpmtCapital()+"");
                        arrayList.add(map);
                    }
                    repaymentPlanFragment.setData(arrayList,loanAmt);

                    loanInvestListFragment.setData(loanID);
                }else {
                    //xLoadingView.showError();
                }
            }

            @Override
            public void onFailed(String error) {

            }
        });
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void getMsg(RedPackageMsg message){
        XLog.e(message.toString());
        useRedpMinAmt=message.getUseRedpMinAmt();
        redPackageId=message.getId();
        if(tvRedPackage!=null){
            if(!XEmptyUtils.isEmpty(redPackageId)){
                tvRedPackage.setTextColor(getResources().getColor(R.color.word_red));
                tvRedPackage.setText(message.getRedpAmt()+"元"+message.getRedpTypeName());
            }else {
                tvRedPackage.setTextColor(getResources().getColor(R.color.word_gray));
                tvRedPackage.setText("请选择红包");
            }
        }
    }

    private int useRedpMinAmt=0;//选择红包的起投金额
    private String redPackageId="";//红包的id
    private TextView tvRedPackage;//红包
    private int amountNum;//投资数量
    private String balance ;//当前账户剩余金额
    private double yearRate;//年利率
    private int year;//投资时间
    //投资弹框
    private void setDialog() {
        mCameraDialog = new Dialog(this, R.style.custom_dialog2);
        View view = LayoutInflater.from(this).inflate(
                R.layout.dialog_immediate_investment, null);
        //初始化视图
        mCameraDialog.setContentView(view);
        Window dialogWindow = mCameraDialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setWindowAnimations(R.style.dialog_rightIn_rightOut); // 添加动画
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.x = 0; // 新位置X坐标
        lp.y = 0; // 新位置Y坐标
        lp.width = getResources().getDisplayMetrics().widthPixels; // 宽度
        view.measure(0, 0);
        lp.height = view.getMeasuredHeight();

        lp.alpha = 9f; // 透明度
        dialogWindow.setAttributes(lp);
        mCameraDialog.show();

        final TextView tvBalance=view.findViewById(R.id.tv_account_balance);//账户余额
        TextView tvCast=view.findViewById(R.id.tv_can_be_cast);//可投金额
        final TextView tvEarning=view.findViewById(R.id.tv_earning);//预计收益
        final EditText etTradePsw=view.findViewById(R.id.et_trade_psw);//交易密码
        Button btnSure=view.findViewById(R.id.btn_investment_sure);//确认投资
        LinearLayout llChoose=view.findViewById(R.id.ll_choose_red_package);//选择红包
        tvRedPackage=view.findViewById(R.id.tv_red_package);

        int goods=1;
        if(!XEmptyUtils.isEmpty(canBeCast)){
            goods=Integer.parseInt(canBeCast)/100;

            String cast=canBeCast;
            if(cast.endsWith("000")){
                cast=cast.substring(0,cast.length()-3);
                tvCast.setText(cast+",000");
            }else {
                tvCast.setText(cast);
            }
        }
        AmountView amountView=view.findViewById(R.id.amount_view);
        amountView.setGoods_storage(goods);
        amountView.etAmount.setText(0+"");
        amountView.setOnAmountChangeListener(new AmountView.OnAmountChangeListener() {
            @Override
            public void onAmountChange(View view, int amount) {
                amountNum=amount;
                //投资金额*收益利率*标的期限/360
                int touziMoney=amountNum*100;
                double Y;
                if(year==1){
                    Y=(((double)touziMoney)*yearRate*((double)year/12))/100;
                }else {
                    Y=(((double)touziMoney)*yearRate*((double)year/360))/100;
                }
                tvEarning.setText(new DecimalFormat("0.00").format(Y)+"");
            }
        });



        Map<String,Object > params =new HashMap<>();
        params .put("custMobile",custMobile);
        params .put("custName",custName);
        params .put("custPwd",custPwd);
        XHttp.obtain().post(Path.get_geal_time_cust, params, new HttpCallBack<RealTimeCustBean>() {
            @Override
            public void onSuccess(RealTimeCustBean bean) {
                if(bean.isStatus()){
                    balance=bean.getAvailAmt();
                    tvBalance.setText(balance);
                }
            }

            @Override
            public void onFailed(String error) {

            }
        });

        //选择红包
        llChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ProductDetailActivity.this,ChooseRedPackageActivity.class);
                startActivity(intent);
            }
        });

        //投资按钮
        btnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int touziMoney=amountNum*100;
                if(XEmptyUtils.isEmpty(balance)){
                    return;
                }
                if(balance.contains(".")){
                    balance=balance.substring(0,balance.indexOf("."));
                }
                if(Integer.valueOf(balance)>touziMoney || Integer.valueOf(balance)==touziMoney){
                    String tradePwd=etTradePsw.getText().toString().trim();
                    if(XEmptyUtils.isEmpty(tradePwd)){
                        XToast.normal("交易密码不能为空");
                        return;
                    }
                    if(touziMoney==0){
                        XToast.normal("投资金额必须大于0");
                        return;
                    }
                    if(useRedpMinAmt!=0){
                        if(touziMoney<useRedpMinAmt){
                            XToast.normal("当前红包最小起投金额是"+useRedpMinAmt+"元");
                            return;
                        }
                    }
                    if(!ButtonUtils.isFastDoubleClick()){
                        XLoadingDialog.with(ProductDetailActivity.this)
                                .setCanceled(false) //设置手动不可取消
                                .setOrientation(XLoadingDialog.HORIZONTAL) //设置显示方式（水平或者垂直）
                                .setBackgroundColor(Color.parseColor("#aa000000"))//设置对话框背景
                                .setMessageColor(Color.WHITE)//设置消息字体颜色
                                .setMessage("投资中...")//设置消息文本
                                .show();//显示对话框
                        requestInvest(tradePwd,touziMoney);
                    }
                }else {
                    XToast.normal("您的账户余额不足,请进行充值!");
                }
            }
        });
    }

    //投资
    private void requestInvest(String tradePwd,int investAmt){
        Map<String,Object > params =new HashMap<>();
        params .put("custMobile",custMobile);
        params .put("custPwd",custPwd);
        params .put("backUrl","http://www.huorongdai.com");//必给
        params .put("optSource","4");
        params .put("loanId",loanID);
        params .put("agreePwd","");
        params .put("tradePwd",tradePwd);
        params .put("investAmt",investAmt+"");
        params .put("redPacketId",redPackageId);
        XLog.map(params);
        XHttp.obtain().post(Path.invest, params, new HttpCallBack<InvestBean>() {
            @Override
            public void onSuccess(InvestBean bean) {
                XLoadingDialog.with(ProductDetailActivity.this).dismiss();
                if(bean.isStatus()){
                    Message message=Message.obtain();
                    message.what=1;
                    handler.sendMessage(message);
                    XToast.normal(bean.getOptResultMsg());
                }else {
                    XToast.normal(bean.getMessage());
                }
            }

            @Override
            public void onFailed(String error) {
                XLoadingDialog.with(ProductDetailActivity.this).dismiss();
            }
        });
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        //如果参数为null的话，会将所有的Callbacks和Messages全部清除掉。
        handler.removeCallbacksAndMessages( null );
        super.onDestroy();
    }

    @Override
    protected void onClickLeft() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
        //super.onClickLeft();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
