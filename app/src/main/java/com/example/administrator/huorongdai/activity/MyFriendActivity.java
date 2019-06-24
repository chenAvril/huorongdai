package com.example.administrator.huorongdai.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.huorongdai.Path;
import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.base.BaseActivity;
import com.example.administrator.huorongdai.gsonbean.InviteFriendBean;
import com.example.administrator.huorongdai.xframe.utils.XNetworkUtils;
import com.example.administrator.huorongdai.xframe.utils.XPreferencesUtils;
import com.example.administrator.huorongdai.xframe.utils.http.HttpCallBack;
import com.example.administrator.huorongdai.xframe.utils.http.XHttp;
import com.example.administrator.huorongdai.xframe.utils.imageload.XImage;
import com.example.administrator.huorongdai.xframe.widget.XToast;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class MyFriendActivity extends BaseActivity {
    private TextView tvCode;//我的邀请码
    private TextView tvShare;//分享
    private ImageView ivOrCode;//二维码

    private String custMobile;//手机号码
    private String custPwd;//密文密码
    private String selfInviteCode;//自己邀请码
    private String myLink;//我的邀请注册链接
    private String codePath;//二维码路径

    private UMShareListener mShareListener;
    private ShareAction mShareAction;

    private Handler handler=new Handler(new MyCallable());
    private final class MyCallable implements Handler.Callback{
        @Override
        public boolean handleMessage(Message msg) {
            mShareListener = new CustomShareListener(MyFriendActivity.this);
            /*增加自定义按钮的分享面板*/
            mShareAction = new ShareAction(MyFriendActivity.this).setDisplayList(
                    SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE)
                    .setShareboardclickCallback(new ShareBoardlistener() {

                        @Override
                        public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                            UMWeb web = new UMWeb(myLink);
                            web.setTitle("会员招募");
                            web.setDescription("会员等级计算根据本金的代收金额，会员升级从升级后的等级计算奖励");
                            web.setThumb(new UMImage(MyFriendActivity.this, codePath));
                            new ShareAction(MyFriendActivity.this).withMedia(web)
                                    .setPlatform(share_media)
                                    .setCallback(mShareListener)
                                    .share();
                        }
                    });
            return true;
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_my_friend;
    }

    @Override
    protected void initView() {
        setTitle("我的好友");
        setRightBtnVisible(true);
        setRtTitle("邀请记录");

        tvCode=findViewById(R.id.tv_my_code);
        tvShare=findViewById(R.id.tv_share);
        tvShare.setOnClickListener(this);
        ivOrCode=findViewById(R.id.iv_or_code);

        custMobile = (String) XPreferencesUtils.get("custMobile","");
        custPwd = (String) XPreferencesUtils.get("custPwd","");
    }

    @Override
    protected void loadData() {
        if(XNetworkUtils.isConnected()){
            Map<String,Object > params =new HashMap<>();
            params .put("custMobile",custMobile);
            params .put("custPwd",custPwd);
            XHttp.obtain().post(Path.invite_friend, params, new HttpCallBack<InviteFriendBean>() {
                @Override
                public void onSuccess(InviteFriendBean bean) {
                    if(bean.isStatus()){
                        selfInviteCode=bean.getCustomer().getSelfInviteCode();
                        myLink=Path.BASE_URL+"wap/index/regist?introInviteCode="+selfInviteCode;
                        tvCode.setText(selfInviteCode);
                        //wap/index/regist?introInviteCode=
                        //URL+'/weixin/common/imgGene?url='+url+"&logoPath="+URL+'/images/logo2weima.jpg'
                        codePath=Path.BASE_URL+"weixin/common/imgGene?url="+myLink+"&logoPath="+Path.BASE_URL+"/images/logo2weima.jpg";
                        XImage.getInstance().load(ivOrCode,codePath);
                        handler.sendMessage(Message.obtain());
                    }
                }

                @Override
                public void onFailed(String error) {

                }
            });
        }else {
            XToast.normal("网络连接失败!");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_share:
                if(mShareAction!=null){
                    mShareAction.open();
                }
                break;
        }
    }

    @Override
    protected void onClickRight() {
        //super.onClickRight();
        Intent intent=new Intent(this,InviteRecordActivity.class);
        startActivity(intent);
    }

    private static class CustomShareListener implements UMShareListener {

        private WeakReference<MyFriendActivity> mActivity;

        private CustomShareListener(MyFriendActivity activity) {
            mActivity = new WeakReference(activity);
        }

        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        @Override
        public void onResult(SHARE_MEDIA platform) {

            if (platform.name().equals("WEIXIN_FAVORITE")) {
                XToast.normal("收藏成功");
            } else {
                if (platform != SHARE_MEDIA.MORE && platform != SHARE_MEDIA.SMS
                        && platform != SHARE_MEDIA.EMAIL
                        && platform != SHARE_MEDIA.FLICKR
                        && platform != SHARE_MEDIA.FOURSQUARE
                        && platform != SHARE_MEDIA.TUMBLR
                        && platform != SHARE_MEDIA.POCKET
                        && platform != SHARE_MEDIA.PINTEREST
                        && platform != SHARE_MEDIA.INSTAGRAM
                        && platform != SHARE_MEDIA.GOOGLEPLUS
                        && platform != SHARE_MEDIA.YNOTE
                        && platform != SHARE_MEDIA.EVERNOTE) {
                    XToast.normal("分享成功");
                }
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            if (platform != SHARE_MEDIA.MORE && platform != SHARE_MEDIA.SMS
                    && platform != SHARE_MEDIA.EMAIL
                    && platform != SHARE_MEDIA.FLICKR
                    && platform != SHARE_MEDIA.FOURSQUARE
                    && platform != SHARE_MEDIA.TUMBLR
                    && platform != SHARE_MEDIA.POCKET
                    && platform != SHARE_MEDIA.PINTEREST
                    && platform != SHARE_MEDIA.INSTAGRAM
                    && platform != SHARE_MEDIA.GOOGLEPLUS
                    && platform != SHARE_MEDIA.YNOTE
                    && platform != SHARE_MEDIA.EVERNOTE) {
                XToast.normal("分享失败");
            }

        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            XToast.normal("分享取消了");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** attention to this below ,must add this**/
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 屏幕横竖屏切换时避免出现window leak的问题
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mShareAction.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
        //如果参数为null的话，会将所有的Callbacks和Messages全部清除掉。
        handler.removeCallbacksAndMessages( null );
    }
}
