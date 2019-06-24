package com.example.administrator.huorongdai.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.administrator.huorongdai.Path;
import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.base.BaseActivity;
import com.example.administrator.huorongdai.gsonbean.BankCardBean;
import com.example.administrator.huorongdai.xframe.utils.http.HttpCallBack;
import com.example.administrator.huorongdai.xframe.utils.http.XHttp;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class HelpCenterActivity extends BaseActivity {

    private RelativeLayout rlLlicai;//投资理财
    private RelativeLayout rlChongzhi;//充值
    private RelativeLayout rlTixian;//提现
    private RelativeLayout rlNewHand;//新手标
    private RelativeLayout rlBankCard;//银行卡
    private RelativeLayout rlLicai;//我要理财
    private RelativeLayout rlJiekuan;//我要借款
    private RelativeLayout rlManagement;//账户管理
    private RelativeLayout rlExplain;//名词解释
    private RelativeLayout rlMall;//积分商城
    private RelativeLayout rlContactUs;//积分商城
    private RelativeLayout rlTuijian;//推荐
    private RelativeLayout rlOther;//其他问题

    private LinearLayout llItems;
    
    @Override
    protected int getContentView() {
        return R.layout.activity_help_center;
    }

    @Override
    protected void initView() {
        setTitle("帮助中心");
        llItems=findViewById(R.id.ll_help_items);
        rlLlicai=findViewById(R.id.rl_touzilicai);
        rlChongzhi=findViewById(R.id.rl_chongzhi);
        rlTixian=findViewById(R.id.rl_tixian);
        rlNewHand=findViewById(R.id.rl_new_hand);
        rlBankCard=findViewById(R.id.rl_bank_card);
        rlLicai=findViewById(R.id.rl_licai);
        rlJiekuan=findViewById(R.id.rl_jiekuan);
        rlManagement=findViewById(R.id.rl_management);
        rlExplain=findViewById(R.id.rl_explain);
        rlMall=findViewById(R.id.rl_mall);
        rlContactUs=findViewById(R.id.rl_contact_us);
        rlTuijian=findViewById(R.id.rl_tuijian);
        rlOther=findViewById(R.id.rl_other);

        rlLlicai.setOnClickListener(this);
        rlChongzhi.setOnClickListener(this);
        rlTixian.setOnClickListener(this);
        rlNewHand.setOnClickListener(this);
        rlBankCard.setOnClickListener(this);
        rlLicai.setOnClickListener(this);
        rlJiekuan.setOnClickListener(this);
        rlManagement.setOnClickListener(this);
        rlExplain.setOnClickListener(this);
        rlMall.setOnClickListener(this);
        rlContactUs.setOnClickListener(this);
        rlTuijian.setOnClickListener(this);
        rlOther.setOnClickListener(this);

        Drawable drawable = new BitmapDrawable(readBitMap(getApplicationContext(),R.mipmap.gold));
        llItems.setBackground(drawable);
    }

    /**
    * 以最省内存的方式读取本地资源的图片
    * @param context* @param resId
    * @return
    */
    public Bitmap readBitMap(Context context, int resId){
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
               //获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is,null,opt);
    }


    @Override
    protected void loadData() {
        //requestHelpList();
    }

    private void requestHelpList() {
        XHttp.obtain().get(Path.help_list, new HashMap<String, Object>(), new HttpCallBack<BankCardBean>() {
            @Override
            public void onSuccess(BankCardBean bean) {

            }

            @Override
            public void onFailed(String error) {

            }
        });
    }
    
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this,HelpInfoActivity.class);
        String type = "";
        String title="";
        switch (view.getId()){
            case R.id.rl_touzilicai://投资理财
                title="投资理财";
                type="1";
                break;
            case R.id.rl_chongzhi://充值
                title="充值";
                type="2";
                break;
            case R.id.rl_tixian://提现
                title="提现";
                type="3";
                break;
            case R.id.rl_new_hand://新手标
                title="新手标";
                type="4";
                break;
            case R.id.rl_bank_card://银行卡
                title="银行卡";
                type="5";
                break;
            case R.id.rl_mall://积分商城
                title="积分商城";
                type="6";
                break;
            case R.id.rl_contact_us://联系我们
                title="联系我们";
                type="7";
                break;
            case R.id.rl_tuijian://推荐
                type="8";
                title="推荐";
                break;
            case R.id.rl_other://其他问题
                title="其他问题";
                type="9";
                break;
            case R.id.rl_licai://我要理财
                title="我要理财";
                type="10";
                break;
            case R.id.rl_jiekuan://我要借款
                title="我要借款";
                type="11";
                break;
            case R.id.rl_explain://名词解释
                title="名词解释";
                type="12";
                break;
            case R.id.rl_management://账户管理
                title="账户管理";
                type="13";
                break;

        }
        intent.putExtra("title",title);
        intent.putExtra("type",type);
        startActivity(intent);
    }
}
