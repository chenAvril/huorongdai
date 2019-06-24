package com.example.administrator.huorongdai.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.administrator.huorongdai.MainActivity;
import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.base.BaseActivity;
import com.example.administrator.huorongdai.base.Html5WebView;
import com.example.administrator.huorongdai.eventbusbean.Msg;
import com.example.administrator.huorongdai.xframe.utils.XEmptyUtils;
import com.example.administrator.huorongdai.xframe.utils.XNetworkUtils;
import com.example.administrator.huorongdai.xframe.utils.XPreferencesUtils;
import com.example.administrator.huorongdai.xframe.widget.loadingview.XLoadingView;

import org.greenrobot.eventbus.EventBus;
import org.w3c.dom.Document;

import java.io.IOException;

public class HtmlActivity extends BaseActivity {

    private FrameLayout mLayout;
    private SeekBar mSeekBar;
    private Html5WebView mWebView;
    private XLoadingView xLoadingView;
    private String mUrl;
    private String title;
    private String name;

    private Handler handler=new Handler(new MyCallable());
    private final class MyCallable implements Handler.Callback{

        @Override
        public boolean handleMessage(Message msg) {
            if (XNetworkUtils.isConnected()){
                requestData();
            }else {
                xLoadingView.showNoNetwork();
            }
            return true;
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_html;
    }

    @Override
    protected void initView() {
        getParameter();
        setTitle(title);
        mLayout = findViewById(R.id.web_layout);
        mSeekBar = findViewById(R.id.web_sbr);
        xLoadingView=findViewById(R.id.xloading_view);
        xLoadingView.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xLoadingView.showLoading();
                handler.sendMessageDelayed(Message.obtain(),600);
            }
        });
        // 创建 WebView       SHAREJSESSIONID=eedc580e-ff4f-4ff9-9f56-c92e16df5432
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        //不在xml中定义 Webview ，而是在需要的时候在Activity中创建，并且Context使用getApplicationgContext(),
        // 注：Context使用getApplicationgContext()，webview无法弹出select选择框
        mWebView = new Html5WebView(this);
        mWebView.setWebChromeClient(new Html5WebChromeClient());
        mWebView.setWebViewClient(new Html5WebViewClient());
        mWebView.setLayoutParams(params);
        mLayout.addView(mWebView);

    }

    public void getParameter() {
        Bundle bundle = getIntent().getBundleExtra("bundle");
        if (bundle != null) {
            mUrl = bundle.getString("url");
            title=bundle.getString("title");
            if(bundle.containsKey("name")){
                name=bundle.getString("name");
            }
        } else {
            mUrl = "http://www.huorongdai.com/";
        }
    }

    @Override
    protected void loadData() {
        xLoadingView.showLoading();
        handler.sendMessage(Message.obtain());
    }

    private void requestData() {
        if(XEmptyUtils.isEmpty(mUrl)){
            xLoadingView.showEmpty();
        }else {
            String loginCookies= (String) XPreferencesUtils.get("login_cookies","");
            if("activity".equals(name)){
                CookieSyncManager.createInstance(getApplicationContext());
                CookieManager cookieManager = CookieManager.getInstance();
                cookieManager.setAcceptCookie(true);
                cookieManager.removeSessionCookie();// 移除
                if(!XEmptyUtils.isEmpty(loginCookies)){
                    cookieManager.setCookie(mUrl, loginCookies);// 指定要修改的cookies
                }
                CookieSyncManager.getInstance().sync();
            }

            mWebView.loadUrl(mUrl);
            mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        }

    }


    @Override
    public void onClick(View view) {
    }

    class Html5WebViewClient extends Html5WebView.BaseWebViewClient{
        //拦截 url 跳转,在里边添加点击链接跳转或者操作
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //Log.e("chen","shouldOverrideUrlLoading===>"+url);
            //toLoanList?ivstCategory=5 loanList?ivstCategory=5
            if(url.endsWith("oanList?ivstCategory=5")){
                Intent intent=new Intent(HtmlActivity.this,MainActivity.class);
                startActivity(intent);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        EventBus.getDefault().post(new Msg("5"));
                        finish();
                    }
                },100);
            }else if(url.endsWith("login")){
                Intent intent=new Intent(HtmlActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }else {
                view.loadUrl(url);
            }
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            //编写 javaScript方法
            String javascript =  "javascript:function hideOther() {" +
                    "if(document.getElementsByTagName('header')[0]){"+
                    "document.getElementsByTagName('header')[0].remove();}"+
                    "}";

            //创建方法
            view.loadUrl(javascript);
            //加载方法
            view.loadUrl("javascript:hideOther();");
            xLoadingView.showContent();
        }
    }

    // 继承 WebView 里面实现的基类
    class Html5WebChromeClient extends Html5WebView.BaseWebChromeClient {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            //setTitle(title);
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            // 顶部显示网页加载进度
            mSeekBar.setProgress(newProgress);
        }

        //设置响应js 的Alert()函数
        @Override
        public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
            //return super.onJsAlert(view, url, message, result);
            AlertDialog.Builder b = new AlertDialog.Builder(HtmlActivity.this);
            b.setTitle("提示");
            b.setMessage(message);
            b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    result.confirm();
                }
            });
            b.setCancelable(false);
            b.create().show();

            return true;
        }

        //设置响应js 的Confirm()函数
        @Override
        public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
            //return super.onJsConfirm(view, url, message, result);
            AlertDialog.Builder b = new AlertDialog.Builder(HtmlActivity.this);
            b.setTitle("确认");
            b.setMessage(message);
            b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    result.confirm();
                }
            });
            b.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    result.cancel();
                }
            });
            b.create().show();
            return true;
        }

        //设置响应js 的Prompt()函数
        @Override
        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, final JsPromptResult result) {
            //return super.onJsPrompt(view, url, message, defaultValue, result);
            final View v = View.inflate(HtmlActivity.this, R.layout.prompt_dialog, null);
            ((TextView) v.findViewById(R.id.prompt_message_text)).setText(message);
            ((EditText) v.findViewById(R.id.prompt_input_field)).setText(defaultValue);
            AlertDialog.Builder b = new AlertDialog.Builder(HtmlActivity.this);
            b.setTitle("提示");
            b.setView(v);
            b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String value = ((EditText) v.findViewById(R.id.prompt_input_field)).getText().toString();
                    result.confirm(value);
                }
            });
            b.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    result.cancel();
                }
            });
            b.create().show();
            return true;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 销毁 WebView
        if (mWebView != null) {
            mWebView.setWebChromeClient(null);
            mWebView.setWebViewClient(null);
            mWebView.clearCache(true);

            // 如果先调用destroy()方法，则会命中if (isDestroyed()) return;这一行代码，需要先onDetachedFromWindow()，再
            // destory()
            ViewParent parent = mWebView.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(mWebView);
            }

            mWebView.stopLoading();
            // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            mWebView.getSettings().setJavaScriptEnabled(false);
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebView.clearHistory();
            mWebView.clearView();
            mWebView.removeAllViews();
            mWebView.destroy();
            mWebView=null;
        }

        //如果参数为null的话，会将所有的Callbacks和Messages全部清除掉。
        handler.removeCallbacksAndMessages( null );
    }


}
