package com.example.administrator.huorongdai.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.huorongdai.Path;
import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.base.BaseActivity;
import com.example.administrator.huorongdai.base.Html5WebView;
import com.example.administrator.huorongdai.gsonbean.NewsBean;
import com.example.administrator.huorongdai.gsonbean.ResCodeBean;
import com.example.administrator.huorongdai.view.MyScrollView;
import com.example.administrator.huorongdai.xframe.utils.XEmptyUtils;
import com.example.administrator.huorongdai.xframe.utils.XNetworkUtils;
import com.example.administrator.huorongdai.xframe.utils.http.HttpCallBack;
import com.example.administrator.huorongdai.xframe.utils.http.XHttp;
import com.example.administrator.huorongdai.xframe.widget.loadingview.XLoadingView;

import java.util.HashMap;
import java.util.Map;

public class ReadNewsActivity extends BaseActivity {

    private XLoadingView xLoadingView;
    private MyScrollView scrollView;
    private SwipeRefreshLayout refreshLayout;
    private TextView tvTitle;//标题
    private TextView tvAuthor;//发布者
    private TextView tvTime;//时间
    private FrameLayout mLayout;

    private Html5WebView mWebView;

    private Intent intent;
    private String title;
    private NewsBean.NewsListBean newsBean;

    private Handler handler=new Handler(new MyCallable());
    private final class MyCallable implements Handler.Callback{
        @Override
        public boolean handleMessage(Message msg) {
            if(XNetworkUtils.isConnected()){
                requestData();
            }else {
                xLoadingView.showNoNetwork();
                if(refreshLayout.isRefreshing()){
                    refreshLayout.setRefreshing(false);
                }
            }
            return true;
        }
    }

    private SwipeRefreshLayout.OnRefreshListener refreshListener=new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            handler.sendMessage(Message.obtain());
        }
    };

    @Override
    protected int getContentView() {
        return R.layout.activity_read_news;
    }

    @Override
    protected void initView() {
        intent=getIntent();
        if(intent!=null){
            title=intent.getStringExtra("title");
            setTitle(title);
            newsBean=intent.getBundleExtra("data").getParcelable("news");
        }

        xLoadingView=findViewById(R.id.xloading_view);
        tvTitle=findViewById(R.id.tv_read_news_title);
        tvAuthor=findViewById(R.id.tv_read_news_author);
        tvTime=findViewById(R.id.tv_read_news_time);
        mLayout = findViewById(R.id.web_layout);
        scrollView=findViewById(R.id.sl_read_news);
        refreshLayout=findViewById(R.id.srl_read_news);
        refreshLayout.setColorScheme(R.color.word_red);
        refreshLayout.setOnRefreshListener(refreshListener);
        xLoadingView.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xLoadingView.showLoading();
                handler.sendMessageDelayed(Message.obtain(),600);
            }
        });

        //解决swiperefreshlayout与scrollview的冲突问题
        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if (refreshLayout != null) {
                    refreshLayout.setEnabled(scrollView.getScrollY() == 0);
                }
            }
        });
    }

    @Override
    protected void loadData() {
        refreshLayout.setRefreshing(true);
        refreshListener.onRefresh();
    }

    private void requestData() {
        Map<String,Object > params =new HashMap<>();
        params.put("newsId",newsBean.getId());
        params.put("readCount",newsBean.getReadCount()+"");
        XHttp.obtain().post(Path.read_news, params, new HttpCallBack<ResCodeBean>() {
            @Override
            public void onSuccess(ResCodeBean bean) {
                if(bean.isStatus()){
                    if(newsBean!=null){
                        tvTitle.setText(newsBean.getNewsTitle());
                        tvAuthor.setText(newsBean.getNewsScource());
                        String date=newsBean.getAddTime();
                        String time=date.substring(0,4)+"-"+date.substring(4,6)+"-"+date.substring(6,8)+" "+date.substring(8,10)+":"+date.substring(10,12)+":"+date.substring(12,14);
                        tvTime.setText(time);

                        if(!XEmptyUtils.isEmpty(newsBean.getNewsContent())){
                            // 创建 WebView
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.MATCH_PARENT);
                            mWebView = new Html5WebView(ReadNewsActivity.this);
                            mWebView.setLayoutParams(layoutParams);
                            mLayout.addView(mWebView);
                            mWebView.setWebViewClient(new Html5WebViewClient());
                            mWebView.loadData(getHtmlData(newsBean.getNewsContent()),"text/html;charset=UTF-8", "UTF-8");
                        }
                        xLoadingView.showContent();
                    }else {
                        xLoadingView.showEmpty();
                    }
                }else {
                    xLoadingView.showError();
                }
                if(refreshLayout.isRefreshing()){
                    refreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailed(String error) {

            }
        });
    }

    //格式化html
    private String getHtmlData(String bodyHTML) {
        String head = "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
                "<style>img{max-width: 100%; width:auto; height:auto;}</style>" +
                "</head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }


    @Override
    public void onClick(View view) {

    }

    class Html5WebViewClient extends Html5WebView.BaseWebViewClient{
        //拦截 url 跳转,在里边添加点击链接跳转或者操作
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //view.loadUrl(url);

//            if(url.startsWith("http")){
//                Intent intent = new Intent(ReadNewsActivity.this, HtmlActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("url", url);
//                bundle.putString("title", "活动详情");
//                intent.putExtra("bundle", bundle);
//                startActivity(intent);
//            }

            return true;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 销毁 WebView
        if (mWebView != null) {
            ViewGroup viewGroup = (ViewGroup) mWebView.getParent();
            if(viewGroup!=null){
                viewGroup.removeView(mWebView);
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

        //Process.killProcess(Process.myPid());
    }
}
