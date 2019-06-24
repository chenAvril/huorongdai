package com.example.administrator.huorongdai.activity;

import android.app.Dialog;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.huorongdai.MainActivity;
import com.example.administrator.huorongdai.Path;
import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.base.BaseActivity;
import com.example.administrator.huorongdai.gsonbean.QueryLockBean;
import com.example.administrator.huorongdai.view.GlideCircleTransform;
import com.example.administrator.huorongdai.view.lockview.CustomLockView;
import com.example.administrator.huorongdai.view.lockview.LockMode;
import com.example.administrator.huorongdai.xframe.utils.XEmptyUtils;
import com.example.administrator.huorongdai.xframe.utils.XPreferencesUtils;
import com.example.administrator.huorongdai.xframe.utils.http.HttpCallBack;
import com.example.administrator.huorongdai.xframe.utils.http.XHttp;
import com.example.administrator.huorongdai.xframe.utils.imageload.XImage;
import com.example.administrator.huorongdai.xframe.utils.statusbar.XStatusBar;
import com.example.administrator.huorongdai.xframe.widget.XToast;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

import java.util.HashMap;
import java.util.Map;

import static com.example.administrator.huorongdai.view.lockview.LockMode.EDIT_PASSWORD;
import static com.example.administrator.huorongdai.view.lockview.LockMode.SETTING_PASSWORD;
import static com.example.administrator.huorongdai.view.lockview.LockMode.VERIFY_PASSWORD;

public class HandLockActivity extends BaseActivity {

    private ImageView ivHeader;//头像
    private TextView tvNote;
    private TextView tvPhone;
    private TextView tvForget;//忘记密码
    private TextView tvLogin;//使用账号登录
    private CustomLockView lockView;

    private String custMobile;//客户手机号码
    private String headIcon;//客户头像URL
    private String custId;//用户id
    private String hangLock;//手势密码的密码
    private String mode;//不为空是spareActivity的验证手势密码
    private GlideCircleTransform circleTransform;
    private LockMode lockMode;

    //密码输入监听
    CustomLockView.OnCompleteListener onCompleteListener = new CustomLockView.OnCompleteListener() {
        @Override
        public void onComplete(String password, int[] indexs) {
            String lock="";
            for(int num:indexs){
                lock=lock+num;
            }
            switch (lockView.getMode()) {
                case SETTING_PASSWORD:
                    changePwd(lock,2);
                    break;
                case EDIT_PASSWORD:
                    changePwd(lock,2);
                    break;
                case VERIFY_PASSWORD:
                    if(XEmptyUtils.isEmpty(mode)){
                        changePwd(hangLock,1);
                    }else {
                        startActivity(new Intent(HandLockActivity.this, MainActivity.class));
                        finish();
                    }
                    break;
            }
        }

        @Override
        public void onError(String errorTimes) {
            tvNote.setText("密码错误，还可以输入" + errorTimes + "次");
        }

        @Override
        public void onPasswordIsShort(int passwordMinLength) {
            tvNote.setText("密码不能少于" + passwordMinLength + "个点");
        }

        @Override
        public void onAginInputPassword(LockMode mode, String password, int[] indexs) {
            tvNote.setText("请再次输入密码");
        }


        @Override
        public void onInputNewPassword() {
            tvNote.setText("请输入新密码");
        }

        @Override
        public void onEnteredPasswordsDiffer() {
            tvNote.setText("两次输入的密码不一致");
        }

        @Override
        public void onErrorNumberMany() {
            XToast.normal("密码错误次数超过限制");
            login();
        }

    };

    @Override
    protected void setStatusBar() {
        if(XEmptyUtils.isEmpty(mode)){
            super.setStatusBar();
        }else {
            XStatusBar.setColor(this,getResources().getColor(R.color.white),30);
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_hand_lock;
    }

    @Override
    protected void initView() {
        mode=getIntent().getStringExtra("mode");
        if(XEmptyUtils.isEmpty(mode)){
            setTitle("手势密码");
        }else {
            getTitleBar().setVisibility(View.GONE);
        }

        ivHeader=findViewById(R.id.image_header);
        tvNote=findViewById(R.id.tv_note);
        tvPhone=findViewById(R.id.tv_phone);
        lockView=findViewById(R.id.clv_lock);
        tvForget=findViewById(R.id.tv_forget);
        tvLogin=findViewById(R.id.tv_account_login);
        tvForget.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
        circleTransform=new GlideCircleTransform(this);
        custMobile= (String) XPreferencesUtils.get("custMobile","");
        headIcon= (String) XPreferencesUtils.get("headIcon","");
        custId= (String) XPreferencesUtils.get("custId","");//用户ID
        tvPhone.setText(custMobile);
        if(XEmptyUtils.isEmpty(headIcon)){
            ivHeader.setBackgroundResource(R.drawable.header);
        }else {
            XImage.getInstance().load(ivHeader, headIcon,circleTransform);
        }

        //显示绘制方向
        lockView.setShow(true);
        //允许最大输入次数
        lockView.setErrorNumber(5);
        //密码最少位数
        lockView.setPasswordMinLength(4);
        lockView.setOnCompleteListener(onCompleteListener);
    }

    @Override
    protected void loadData() {
        queryPwd();
    }

    //查询当前手势密码信息
    private void queryPwd(){
        Map<String , Object> map =new HashMap<>();
        map.put("custId",custId);
        XHttp.obtain().post(Path.query_hand_lock, map, new HttpCallBack<QueryLockBean>() {
            @Override
            public void onSuccess(QueryLockBean bean) {
                //imeitype手势密码类型   1 未设置 2 已设置
                if(bean.isStatus()){
                    if(!XEmptyUtils.isEmpty(bean.getImei())){
                        hangLock=bean.getImei();
                    }
                    if(!XEmptyUtils.isEmpty(bean.getImeitype())){
                        if(bean.getImeitype()==1){
                            lockMode = LockMode.SETTING_PASSWORD;
                            setLockMode(lockMode); //设置模式
                        }else if(bean.getImeitype()==2){
                            if (XEmptyUtils.isEmpty(mode)){
                                showIconDialog();
                            }else {
                                lockMode = LockMode.VERIFY_PASSWORD;
                                setLockMode(lockMode); //设置模式
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailed(String error) {

            }
        });
    }

    private void changePwd(String imei, final int type){
        Map<String , Object> map =new HashMap<>();
        map.put("custId",custId);
        map.put("imei",imei);//密码
        map.put("imeitype",type+"");//手势密码类型   1 未设置 2 已设置
        XHttp.obtain().post(Path.change_hand_lock, map, new HttpCallBack<QueryLockBean>() {
            @Override
            public void onSuccess(QueryLockBean bean) {
                if(bean.isStatus()){
                    switch (lockView.getMode()) {
                        case SETTING_PASSWORD:
                            XToast.normal("设置成功");
                            finish();
                            break;
                        case EDIT_PASSWORD:
                            XToast.normal("修改成功");
                            finish();
                            break;
                        case VERIFY_PASSWORD:
                            if(XEmptyUtils.isEmpty(mode)){
                                XToast.normal("清除成功");
                            }
                            finish();
                            break;
                    }
                }
            }

            @Override
            public void onFailed(String error) {

            }
        });
    }

    //设置解锁模式
    private void setLockMode(LockMode mode) {
        switch (mode) {
            case EDIT_PASSWORD://修改密码
                if(XEmptyUtils.isEmpty(hangLock)){
                    return;
                }
                setLockMode(EDIT_PASSWORD, hangLock);
                break;
            case SETTING_PASSWORD://设置手势密码
                setLockMode(SETTING_PASSWORD, null);
                break;
            case VERIFY_PASSWORD://验证密码
                tvForget.setVisibility(View.VISIBLE);
                tvLogin.setVisibility(View.VISIBLE);
                if(XEmptyUtils.isEmpty(hangLock)){
                    return;
                }
                setLockMode(VERIFY_PASSWORD, hangLock);
                break;
        }

        lockView.setVisibility(View.VISIBLE);
    }

    //密码输入模式
    private void setLockMode(LockMode mode, String password) {
        lockView.setMode(mode);
        lockView.setClearPasssword(false);
        if (mode != SETTING_PASSWORD) {
            tvNote.setText("请输入已经设置过的密码");
            lockView.setOldPassword(password);
        } else {
            tvNote.setText("请输入要设置的密码");
        }
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
        dialogIcon.setCancelable(false);
        TextView tvTitle=dialogIcon.findViewById(R.id.tv_icon_title);
        TextView tvClear=dialogIcon.findViewById(R.id.tv_icon_take_photo);//清除手势密码
        TextView tvEdit=dialogIcon.findViewById(R.id.tv_icon_photo_album);//修改手势密码
        TextView cancelTv=dialogIcon.findViewById(R.id.tv_icon_cancel);//取消
        tvTitle.setText("您已设置手势密码，还可以");
        tvClear.setText("清除手势密码");
        tvClear.setTextColor(getResources().getColor(R.color.word_red));
        tvEdit.setText("修改手势密码");

        tvClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //清除手势密码   先已设置验证手势密码，然后再清除
                lockMode = LockMode.VERIFY_PASSWORD;
                setLockMode(lockMode);
                dialogIcon.dismiss();
            }
        });
        tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //修改手势密码
                lockMode = LockMode.EDIT_PASSWORD;
                setLockMode(lockMode);
                dialogIcon.dismiss();
            }
        });

        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogIcon.dismiss();
                finish();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_forget://忘记密码
                forget(hangLock);
                break;
            case R.id.tv_account_login://使用账号登录
                login();
                break;
        }
    }

    private void forget(String imei){
        Map<String , Object> map =new HashMap<>();
        map.put("custId",custId);
        map.put("imei",imei);//密码
        map.put("imeitype","1");//手势密码类型   1 未设置 2 已设置
        XHttp.obtain().post(Path.change_hand_lock, map, new HttpCallBack<QueryLockBean>() {
            @Override
            public void onSuccess(QueryLockBean bean) {
                if(bean.isStatus()){
                    login();
                }
            }

            @Override
            public void onFailed(String error) {

            }
        });
    }

    private void login() {
        XPreferencesUtils.put("isLogin",false);
        XPreferencesUtils.put("custMobile","");
        XPreferencesUtils.put("custName","");
        XPreferencesUtils.put("headIcon", "");
        XPreferencesUtils.put("custPwd","");//密文密码
        XPreferencesUtils.put("custId","");//用户ID
        //未开户是4，开户是2
        XPreferencesUtils.put("openStatus",4);
        //1-未认证 2-认证
        XPreferencesUtils.put("emailAuth",1);
        XPreferencesUtils.put("custEmail","");//电子邮箱
        XPreferencesUtils.put("login_cookies","");
        Intent intent=new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
