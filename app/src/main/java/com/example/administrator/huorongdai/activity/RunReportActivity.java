package com.example.administrator.huorongdai.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.huorongdai.Path;
import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.base.BaseActivity;
import com.example.administrator.huorongdai.gsonbean.CheckVersionBean;
import com.example.administrator.huorongdai.gsonbean.RunReportBean;
import com.example.administrator.huorongdai.view.CustomDialog;
import com.example.administrator.huorongdai.xframe.adapter.XRecyclerViewAdapter;
import com.example.administrator.huorongdai.xframe.adapter.XViewHolder;
import com.example.administrator.huorongdai.xframe.utils.XAppUtils;
import com.example.administrator.huorongdai.xframe.utils.XEmptyUtils;
import com.example.administrator.huorongdai.xframe.utils.XNetworkUtils;
import com.example.administrator.huorongdai.xframe.utils.http.HttpCallBack;
import com.example.administrator.huorongdai.xframe.utils.http.XHttp;
import com.example.administrator.huorongdai.xframe.utils.imageload.XImage;
import com.example.administrator.huorongdai.xframe.utils.permission.XPermission;
import com.example.administrator.huorongdai.xframe.widget.XLoadingDialog;
import com.example.administrator.huorongdai.xframe.widget.XToast;
import com.example.administrator.huorongdai.xframe.widget.loadingview.XLoadingView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.voghdev.pdfviewpager.library.PDFViewPager;
import es.voghdev.pdfviewpager.library.RemotePDFViewPager;
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter;
import es.voghdev.pdfviewpager.library.remote.DownloadFile;
import es.voghdev.pdfviewpager.library.util.FileUtil;

public class RunReportActivity extends BaseActivity {
    private XLoadingView xLoadingView;
    private RecyclerView mRecyclerView;
    private MyAdapter adapter;
    private List<RunReportBean.ListBean> listBeans;
    private int pageNum=1;

    private Handler handler=new Handler(new MyCallback());
    private final class MyCallback implements Handler.Callback{

        @Override
        public boolean handleMessage(Message message) {
            if (XNetworkUtils.isConnected()){
                pageNum=1;
                requestData();
            }else {
                xLoadingView.showNoNetwork();
            }

            return true;
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_run_report;
    }

    @Override
    protected void initView() {
        setTitle("运营报告");
        mRecyclerView = findViewById(R.id.run_report_recycler_view);
        xLoadingView=findViewById(R.id.xloading_view);
        xLoadingView.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xLoadingView.showLoading();
                handler.sendMessageDelayed(Message.obtain(),600);
            }
        });
        dialogPDF = new CustomDialog(this, R.style.custom_dialog2, R.layout.dialog_pdf);

        listBeans=new ArrayList<>();
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        adapter=new MyAdapter(mRecyclerView,listBeans);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnLoadMoreListener(new XRecyclerViewAdapter.OnLoadMoreListener() {
            @Override
            public void onRetry() {

            }

            @Override
            public void onLoadMore() {
                requestData();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(pdfPagerAdapter!=null){
            pdfPagerAdapter.close();
        }
        //如果参数为null的话，会将所有的Callbacks和Messages全部清除掉。
        handler.removeCallbacksAndMessages( null );
    }

    @Override
    protected void loadData() {
        adapter.isLoadMore(false);
        handler.sendMessage(Message.obtain());
    }

    @Override
    public void onClick(View view) {

    }

    private void requestData() {
        Map<String,Object > params =new HashMap<>();
        params.put("totalpages","10");
        params.put("pageNum",""+pageNum);
        XHttp.obtain().post(Path.run_report, params, new HttpCallBack<RunReportBean>() {
            @Override
            public void onSuccess(RunReportBean bean) {
                if(!XEmptyUtils.isEmpty(bean) ){
                    if(XEmptyUtils.isEmpty(bean.getList())){
                        xLoadingView.showError();
                        return;
                    }
                    if(bean.getList().size()>0){
                        if(pageNum==1){
                            adapter.setDataLists(bean.getList());
                            adapter.isLoadMore(true);
                        }else {
                            adapter.addAll(bean.getList());
                        }
                        xLoadingView.showContent();
                    }

                    if("1".equals(bean.getPageNum()) && "0".equals(bean.getTotalPages()) && bean.getList().size()==0){
                        xLoadingView.showEmpty();
                    }
                    if(bean.getTotalPages().equals(bean.getPageNum()+"") || "0".equals(bean.getTotalPages())){
                        adapter.showLoadComplete();
                    }else {
                        pageNum=pageNum+1;
                    }
                }else {
                    xLoadingView.showError();
                }
            }

            @Override
            public void onFailed(String error) {

            }
        });
    }

    private CustomDialog dialogPDF;//pdf弹框
    private RemotePDFViewPager remotePDFViewPager;
    private PDFPagerAdapter pdfPagerAdapter;
    private XLoadingView loadingViewDialog;
    private RelativeLayout pdf_root;

    //弹框
    private void initDialog(String pdfUrl){
        dialogPDF.show();

        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialogPDF.getWindow().getAttributes();
        lp.width =display.getWidth(); //设置宽度
        lp.height = display.getHeight();
        dialogPDF.getWindow().setAttributes(lp);

        pdf_root =dialogPDF.findViewById(R.id.pdf_root);
        loadingViewDialog =dialogPDF.findViewById(R.id.xloading_view);

        if(loadingViewDialog!=null){
            loadingViewDialog.showLoading();
        }

        remotePDFViewPager = new RemotePDFViewPager(this, pdfUrl, new MyFileListener());
        remotePDFViewPager.setId(R.id.pdfViewPager);
    }

    class MyFileListener implements DownloadFile.Listener {

        @Override
        public void onSuccess(String url, String destinationPath) {
            if(remotePDFViewPager!=null){
                pdfPagerAdapter =new PDFPagerAdapter(RunReportActivity.this, FileUtil.extractFileNameFromURL(url));
                remotePDFViewPager.setAdapter(pdfPagerAdapter);
            }

            if(pdf_root!=null){
                pdf_root.removeAllViewsInLayout();
                pdf_root.addView(remotePDFViewPager, LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
            }
            if(loadingViewDialog!=null){
                loadingViewDialog.showContent();
            }
        }

        @Override
        public void onFailure(Exception e) {
            if(loadingViewDialog!=null){
                loadingViewDialog.showEmpty();
            }
        }

        @Override
        public void onProgressUpdate(int progress, int total) {

        }
    }

    class MyAdapter extends XRecyclerViewAdapter<RunReportBean.ListBean> {

        public MyAdapter(@NonNull RecyclerView mRecyclerView, List<RunReportBean.ListBean> dataLists) {
            super(mRecyclerView, dataLists,R.layout.item_activity_run_report);
        }

        @Override
        protected void bindData(XViewHolder holder, final RunReportBean.ListBean data, int position) {
            ImageView ivIcon=holder.getView(R.id.iv_icon);
            TextView tvTitle=holder.getView(R.id.tv_title);
            TextView tvSee=holder.getView(R.id.tv_see);

            String imageUrl= Path.BASE_ImgURL+data.getInfopubImg();
            XImage.getInstance().load(ivIcon,imageUrl,R.mipmap.icon404);

            tvTitle.setText(data.getInfopubTitle());
            tvSee.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(data.getInfopubFile().contains(".pdf")){
                        initDialog(Path.BASE_URL+"common/fileRead?isOnLine=online&fname=infoPub.pdf&filePath="+data.getInfopubFile());
                    }
                }
            });
        }
    }
}
