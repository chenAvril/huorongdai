package com.example.administrator.huorongdai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.huorongdai.Path;
import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.base.BaseActivity;
import com.example.administrator.huorongdai.xframe.utils.XPreferencesUtils;
import com.example.administrator.huorongdai.xframe.utils.XRegexUtils;


public class AccountSafeActivity extends BaseActivity {

    private RelativeLayout rlRealName;//实名认证
    private ImageView ivRealName;//实名认证
    private TextView tvRealName;//实名认证

    private TextView tvPhoneNumber;//手机号码C
    private RelativeLayout rlChangePsd;//修改登录密码

    private RelativeLayout rlTradePsd;//投资密码
    private ImageView ivTradePsd;//投资密码
    private TextView tvTradePsd;//投资密码

    private RelativeLayout rlEmail;
    private ImageView ivEmailIcon;//邮箱认证
    private TextView tvEmail;
    private String custMobile="";//手机号码
    private String custPwd;//密文密码

    private int openStatus;//未开户是4，开户是2
    private int emailAuth;//1-未认证 2-认证

    @Override
    protected int getContentView() {
        return R.layout.activity_account_safe;
    }

    @Override
    protected void initView() {
        setTitle("账户安全");

        rlRealName=findViewById(R.id.rl_real_name);
        ivRealName=findViewById(R.id.iv_real_name);
        tvRealName=findViewById(R.id.tv_real_name);
        rlRealName.setOnClickListener(this);

        tvPhoneNumber=findViewById(R.id.tv_phone_number);
        rlChangePsd=findViewById(R.id.rl_change_psd);
        rlChangePsd.setOnClickListener(this);

        rlTradePsd=findViewById(R.id.rl_trade_psd);
        ivTradePsd=findViewById(R.id.iv_trade_psd);
        tvTradePsd=findViewById(R.id.tv_trade_psd);
        rlTradePsd.setOnClickListener(this);

        rlEmail=findViewById(R.id.rl_email);
        rlEmail.setOnClickListener(this);
        ivEmailIcon=findViewById(R.id.iv_email_icon);
        tvEmail=findViewById(R.id.tv_email);
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        openStatus= (int) XPreferencesUtils.get("openStatus",4);
        emailAuth= (int) XPreferencesUtils.get("emailAuth",1);
        if(openStatus==2){
            tvRealName.setText("查看");
            ivRealName.setImageResource(R.drawable.right_yes);

            tvTradePsd.setText("修改");
            ivTradePsd.setImageResource(R.drawable.right_yes);
        }
        //1-未认证 2-认证
        if(emailAuth==2){
            tvEmail.setText("修改");
            ivEmailIcon.setImageResource(R.drawable.right_yes);
        }
        custMobile = (String) XPreferencesUtils.get("custMobile","");
        custPwd = (String) XPreferencesUtils.get("custPwd","");
        tvPhoneNumber.setText(XRegexUtils.phoneNoHide(custMobile));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_email://邮箱认证
                Intent intentEmail=new Intent(this, BindingEmailActivity.class);
                startActivity(intentEmail);
                break;
            case R.id.rl_real_name://实名认证
                if(openStatus==2){
                    Intent intent=new Intent(this, IDCardActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent=new Intent(this,OpenAccountActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.rl_change_psd://修改登录密码
                Intent intent1=new Intent(this, ChangePasswordActivity.class);
                intent1.putExtra("title","login");
                startActivityForResult(intent1,1001);
                break;
            case R.id.rl_trade_psd://投资密码
                if(openStatus==2){
                    Intent intent=new Intent(this, ChangePasswordActivity.class);
                    intent.putExtra("title","trade");
                    startActivity(intent);
                }else{
                    Intent intent=new Intent(this,OpenAccountActivity.class);
                    startActivity(intent);
                }
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1001 && resultCode==1003){
            finish();
        }
    }
}
