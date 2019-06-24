package com.example.administrator.huorongdai.fragment;


import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.base.LazyLoadFragment;
import com.example.administrator.huorongdai.view.ZoomImageView;
import com.example.administrator.huorongdai.xframe.utils.XEmptyUtils;
import com.example.administrator.huorongdai.xframe.utils.XNetworkUtils;
import com.example.administrator.huorongdai.xframe.utils.imageload.XImage;
import com.example.administrator.huorongdai.xframe.widget.loadingview.XLoadingView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FileInformationFragment extends LazyLoadFragment {

    private XLoadingView xLoadingView;
    private WebView mWebView;
    private String imgUrl;

    @SuppressLint("ValidFragment")
    public FileInformationFragment(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public FileInformationFragment() {
        // Required empty public constructor
    }

    private Handler handler=new Handler(new MyCallback());
    private final class MyCallback implements Handler.Callback{

        @Override
        public boolean handleMessage(Message message) {
            if (XNetworkUtils.isConnected()){
                if(!XEmptyUtils.isEmpty(imgUrl)){
                    mWebView.loadUrl(imgUrl);
                    xLoadingView.showContent();
                }
            }else {
                xLoadingView.showNoNetwork();
            }
            return true;
        }
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_file_information;
    }

    @Override
    protected void initViews() {
        xLoadingView=findViewById(R.id.xloading_view);
        mWebView= findViewById(R.id.wb_img);
        xLoadingView.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xLoadingView.showLoading();
                handler.sendMessageDelayed(Message.obtain(),600);
            }
        });

        WebSettings settings = mWebView.getSettings();
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setBuiltInZoomControls(true); //选择内置缩放机制与单独缩放控件；
        settings.setDisplayZoomControls(false); //是否显示缩放控件
        settings.setSupportZoom(true);  //是否支持缩放
        //使用下面代码，则自适应屏幕居中显示
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        mWebView.getSettings().setBlockNetworkImage(false);//解决图片不显示
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        handler.sendMessage(Message.obtain());
    }

    @Override
    public void lazyLoad() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        //如果参数为null的话，会将所有的Callbacks和Messages全部清除掉。
        handler.removeCallbacksAndMessages( null );

        if(mWebView != null) {
            mWebView.stopLoading();
            mWebView.clearHistory();
            mWebView.clearCache(true);
            mWebView.loadUrl("about:blank"); // clearView() should be changed to loadUrl("about:blank"), since clearView() is deprecated now
            mWebView.destroy();
            mWebView = null; // Note that mWebView.destroy() and mWebView = null do the exact same thing
        }

    }
}
