package com.example.administrator.huorongdai.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.huorongdai.Path;
import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.base.BaseActivity;
import com.example.administrator.huorongdai.gsonbean.BankCardBean;
import com.example.administrator.huorongdai.gsonbean.ChangePwdBean;
import com.example.administrator.huorongdai.gsonbean.CustIconBean;
import com.example.administrator.huorongdai.gsonbean.CustInfoBean;
import com.example.administrator.huorongdai.util.OssManager;
import com.example.administrator.huorongdai.view.GlideCircleTransform;
import com.example.administrator.huorongdai.xframe.utils.XEmptyUtils;
import com.example.administrator.huorongdai.xframe.utils.XNetworkUtils;
import com.example.administrator.huorongdai.xframe.utils.XPreferencesUtils;
import com.example.administrator.huorongdai.xframe.utils.XRegexUtils;
import com.example.administrator.huorongdai.xframe.utils.http.HttpCallBack;
import com.example.administrator.huorongdai.xframe.utils.http.XHttp;
import com.example.administrator.huorongdai.xframe.utils.imageload.XImage;
import com.example.administrator.huorongdai.xframe.widget.XLoadingDialog;
import com.example.administrator.huorongdai.xframe.widget.XToast;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.model.TakePhotoOptions;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AccountInfoActivity extends BaseActivity implements TakePhoto.TakeResultListener,InvokeListener {

    private SwipeRefreshLayout refreshLayout;
    private ImageView iconIv;//头像
    private EditText cusNameTv;//账户名
    private TextView realNameTv;//真实姓名
    private TextView IDCardTv;//身份证号码
    private TextView bankCardId;//银行卡卡号
    private TextView bankNameTv;//银行名称
    private TextView cusMobileTv;//手机号码
    private Button exitLoginBtn;//保存

    private String custName;//客户名称
    private String custMobile;//客户手机号码
    private String headIcon;//客户头像URL
    private String custPwd;//密文密码
    private GlideCircleTransform circleTransform;

    private Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if(message.what==1){//用户真实姓名和银行卡信息
                requestID();
                BankCardBean bean= (BankCardBean) message.obj;
                if(!XEmptyUtils.isEmpty(bean.getBindcard())){
                    String bankCard= XRegexUtils.cardIdHide(bean.getBindcard().getCardNum());
                    bankCardId.setText(bankCard);
                    bankNameTv.setText(bean.getBindcard().getBankName());
                }

            }else if(message.what==2){//用户身份证信息
                CustInfoBean bean= (CustInfoBean) message.obj;

                String realName=bean.getCustomer().getCustRealName();
                if(realName.length()>1){
                    int num=realName.length()-1;
                    realName=realName.substring(0,1);
                    for (int i = 0; i < num; i++) {
                        realName=realName+"*";
                    }
                    realNameTv.setText(realName);
                }else {
                    realNameTv.setText(realName);
                }
                IDCardTv.setText(XRegexUtils.idHide(bean.getCustomer().getCardNum()));
            }else if(message.what==3){//更新头像
                if(dialogIcon!=null){
                    dialogIcon.dismiss();
                }
                CustIconBean bean= (CustIconBean) message.obj;
                if(!XEmptyUtils.isEmpty(bean.getCustomer().getHeadIcon())){
                    XPreferencesUtils.put("headIcon",Path.BASE_ImgURL+bean.getCustomer().getHeadIcon());
                    headIcon= (String) XPreferencesUtils.get("headIcon","");
                    XImage.getInstance().load(iconIv, headIcon,circleTransform);
                }
            }
            return true;
        }
    });

    private SwipeRefreshLayout.OnRefreshListener refreshListener=new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            cusNameTv.setText(custName);
            cusMobileTv.setText(XRegexUtils.phoneNoHide(custMobile));

            if(XNetworkUtils.isConnected()){
                if(XEmptyUtils.isEmpty(headIcon)){
                    XImage.getInstance().load(iconIv, R.drawable.header);
                }else {
                    XImage.getInstance().load(iconIv, headIcon,circleTransform);
                }
                requestBankCard();
            }else {
                XToast.normal("网络连接失败!");
            }

            if(refreshLayout.isRefreshing()){
                refreshLayout.setRefreshing(false);
            }
        }
    };

    @Override
    protected int getContentView() {
        return R.layout.activity_account_info;
    }

    @Override
    protected void initView() {
        setTitle("账户信息");

        refreshLayout=findViewById(R.id.srl_account_info);
        refreshLayout.setColorScheme(R.color.word_red);
        refreshLayout.setOnRefreshListener(refreshListener);
        iconIv=findViewById(R.id.iv_account_icon);
        exitLoginBtn=findViewById(R.id.btn_exit_login);
        IDCardTv=findViewById(R.id.tv_ID_card);
        realNameTv=findViewById(R.id.tv_real_name);
        bankNameTv=findViewById(R.id.tv_bank_card_name);
        bankCardId=findViewById(R.id.tv_bank_card_id);
        cusNameTv=findViewById(R.id.tv_cus_name);
        cusMobileTv=findViewById(R.id.tv_cus_mobile);
        iconIv.setOnClickListener(this);
        exitLoginBtn.setOnClickListener(this);

        circleTransform=new GlideCircleTransform(AccountInfoActivity.this);
        custName = (String) XPreferencesUtils.get("custName","");
        custMobile= (String) XPreferencesUtils.get("custMobile","");
        headIcon= (String) XPreferencesUtils.get("headIcon","");
        custPwd = (String) XPreferencesUtils.get("custPwd","");

    }

    @Override
    protected void loadData() {

        refreshLayout.setRefreshing(true);
        refreshListener.onRefresh();
    }

    //请求银行卡信息
    private void requestBankCard() {
        //未开户是4，开户是2
        int openStatus= (int) XPreferencesUtils.get("openStatus",4);
        if(2==openStatus){
            Map<String,Object > params =new HashMap<>();
            params.put("custMobile",custMobile);
            params.put("custPwd",custPwd);
            XHttp.obtain().post(Path.bank_card_list, params, new HttpCallBack<BankCardBean>() {
                @Override
                public void onSuccess(BankCardBean bean) {
                    if(bean.isStatus()){
                        Message message=Message.obtain();
                        message.what=1;
                        message.obj=bean;
                        handler.sendMessage(message);
                    }
                }

                @Override
                public void onFailed(String error) {

                }
            });
        }
    }

    //请求身份证号码
    private void requestID(){
        Map<String,Object > params2 =new HashMap<>();
        params2.put("custMobile",custMobile);
        params2.put("custPwd",custPwd);
        XHttp.obtain().post(Path.cust_info, params2, new HttpCallBack<CustInfoBean>() {
            @Override
            public void onSuccess(CustInfoBean bean) {
                if(bean.isStatus()){
                    Message message=Message.obtain();
                    message.what=2;
                    message.obj=bean;
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
            case R.id.iv_account_icon://设置头像
                showIconDialog();
                break;
            case R.id.btn_exit_login://退出登录
                String name=cusNameTv.getText().toString().trim();
                if(!XEmptyUtils.isEmpty(name)){
                    changeCustName(name);
                }
                break;
        }
    }

    //修改登录密码
    private void changeCustName(final String name) {
        Map<String,Object > params =new HashMap<>();
        params .put("custMobile",custMobile);
        params .put("custPwd",custPwd);
        params .put("custName",name);
        XHttp.obtain().post(Path.alter_customer_info, params, new HttpCallBack<ChangePwdBean>() {
            @Override
            public void onSuccess(ChangePwdBean bean) {
                XToast.normal(bean.getMessage());
                if(bean.isStatus()){
                    XPreferencesUtils.put("custName",name);
                }
            }

            @Override
            public void onFailed(String error) {

            }
        });
    }

    //TakePhoto设置
    private static final String TAG = AccountInfoActivity.class.getName();
    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type= PermissionManager.onRequestPermissionsResult(requestCode,permissions,grantResults);
        PermissionManager.handlePermissionsResult(this,type,invokeParam,this);
    }

    /**
     *  获取TakePhoto实例
     * @return
     */
    public TakePhoto getTakePhoto(){
        if (takePhoto==null){
            takePhoto= (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this,this));
        }
        return takePhoto;
    }

    //成功获取照片的回调
    @Override
    public void takeSuccess(TResult result) {
        //XLog.e(result.getImage().getCompressPath());
        XLoadingDialog.with(AccountInfoActivity.this)
                .setCanceled(false) //设置手动不可取消
                .setOrientation(XLoadingDialog.HORIZONTAL) //设置显示方式（水平或者垂直）
                .setBackgroundColor(Color.parseColor("#aa000000"))//设置对话框背景
                .setMessageColor(Color.WHITE)//设置消息字体颜色
                .setMessage("上传中...")//设置消息文本
                .show();//显示对话框
//        oss_accessKeyId=LTAIHLos2R9aRH17
//        oss_accessKeySecret=5wrqcQHnV1xxgvSfDSrZw5l7uSqLgF
//        oss_endpoint=http://oss-cn-shanghai.aliyuncs.com
//        oss_load_url=http://enze.oss-cn-shanghai.aliyuncs.com/
//        oss_object=enze
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        final String  date = formatter.format(curDate);
        final String iconPath="install/images/image/"+date+"/"+custMobile;
        OssManager ossManager=OssManager.getInstance().init(this, "http://oss-cn-shanghai.aliyuncs.com",
                "enze","LTAIHLos2R9aRH17","5wrqcQHnV1xxgvSfDSrZw5l7uSqLgF");
        ossManager.upload(iconPath,result.getImage().getCompressPath());
        ossManager.setUpLoad(new OssManager.UpLoad() {
            @Override
            public void onSuccess() {
                Map<String,Object > params2 =new HashMap<>();
                params2.put("custPwd",custPwd);
                params2.put("custName",custName);
                params2.put("custMobile",custMobile);
                params2.put("headIcon",iconPath+".jpg");
                XHttp.obtain().post(Path.cust_icon, params2, new HttpCallBack<CustIconBean>() {
                    @Override
                    public void onSuccess(CustIconBean bean) {
                        XLoadingDialog.with(AccountInfoActivity.this).dismiss();
                        if(bean.isStatus()){
                            Message message=Message.obtain();
                            message.what=3;
                            message.obj=bean;
                            handler.sendMessage(message);
                        }
                    }

                    @Override
                    public void onFailed(String error) {
                        XLoadingDialog.with(AccountInfoActivity.this).dismiss();
                    }
                });
            }
        });
    }

    //失败
    @Override
    public void takeFail(TResult result,String msg) {
        Log.i(TAG, "takeFail:" + msg);
    }

    //取消
    @Override
    public void takeCancel() {
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type=PermissionManager.checkPermission(TContextWrap.of(this),invokeParam.getMethod());
        if(PermissionManager.TPermissionType.WAIT.equals(type)){
            this.invokeParam=invokeParam;
        }
        return type;
    }

    private Dialog dialogIcon;
    public void showIconDialog(){
        if(dialogIcon==null){
            dialogIcon = new Dialog(this,R.style.ActionSheetDialogStyle);
            //填充对话框的布局
            View inflate = LayoutInflater.from(this).inflate(R.layout.dialog_set_icon, null);
            ScreenAdapterTools.getInstance().loadView(inflate);
            //将布局设置给Dialog
            dialogIcon.setContentView(inflate);
            //获取当前Activity所在的窗体
            Window dialogWindow = dialogIcon.getWindow();
            //设置Dialog从窗体底部弹出
            dialogWindow.setGravity( Gravity.BOTTOM);
            //获得窗体的属性
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.y = 20;//设置Dialog距离底部的距离
            //将属性设置给窗体
            dialogWindow.setAttributes(lp);
        }

        dialogIcon.show();//显示对话框
        TextView takePhotoTv=dialogIcon.findViewById(R.id.tv_icon_take_photo);//拍照
        TextView photoAlbumTv=dialogIcon.findViewById(R.id.tv_icon_photo_album);//相册
        TextView cancelTv=dialogIcon.findViewById(R.id.tv_icon_cancel);//取消

        takePhotoTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//拍照
                dialogIcon.dismiss();
                File file=new File(Environment.getExternalStorageDirectory(), "/temp/"+System.currentTimeMillis() + ".jpg");
                if (!file.getParentFile().exists())file.getParentFile().mkdirs();
                Uri imageUri = Uri.fromFile(file);
                configCompress(takePhoto);
                configTakePhotoOption(takePhoto);
                takePhoto.onPickFromCapture(imageUri);
            }
        });
        photoAlbumTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//相册
                dialogIcon.dismiss();
                File file=new File(Environment.getExternalStorageDirectory(), "/temp/"+System.currentTimeMillis() + ".jpg");
                if (!file.getParentFile().exists())file.getParentFile().mkdirs();
                configCompress(takePhoto);
                configTakePhotoOption(takePhoto);
                takePhoto.onPickFromGallery();
            }
        });

        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogIcon.dismiss();
            }
        });
    }

    private void configTakePhotoOption(TakePhoto takePhoto){
        TakePhotoOptions.Builder builder=new TakePhotoOptions.Builder();
        //使用TakePhoto自带相册
        builder.setWithOwnGallery(true);
        //纠正拍照的照片旋转角度
        builder.setCorrectImage(true);
        takePhoto.setTakePhotoOptions(builder.create());

    }
    private void configCompress(TakePhoto takePhoto){
        //是否压缩
        takePhoto.onEnableCompress(null,false);

        int maxSize=2097152;//大小不超过
        int width= 800;//宽
        int height= 800;//高
        boolean showProgressBar=false;//显示压缩进度条
        boolean enableRawFile =  false;//拍照压缩后是否保存原图
        CompressConfig config=new CompressConfig.Builder()
                .setMaxSize(maxSize)
                .setMaxPixel(width>=height? width:height)
                .enableReserveRaw(enableRawFile)
                .create();

        takePhoto.onEnableCompress(config,showProgressBar);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
