package com.example.administrator.huorongdai.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.administrator.huorongdai.Path;
import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.activity.RunReportActivity;
import com.example.administrator.huorongdai.base.LazyLoadFragment;
import com.example.administrator.huorongdai.view.CustomDialog;
import com.example.administrator.huorongdai.view.ZoomImageView;
import com.example.administrator.huorongdai.xframe.utils.imageload.XImage;
import com.example.administrator.huorongdai.xframe.widget.loadingview.XLoadingView;

import es.voghdev.pdfviewpager.library.RemotePDFViewPager;
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter;
import es.voghdev.pdfviewpager.library.remote.DownloadFile;
import es.voghdev.pdfviewpager.library.util.FileUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class AuditInformationFragment extends LazyLoadFragment implements View.OnClickListener {

    private String[] imgs={"images/gywm/images/001_s.png","images/gywm/images/002_s.png","images/gywm/images/003_s.png",
            "images/cwsjbg.jpg","images/flyjs.png"};
    private String[] bigImgs={"images/gywm/images/001.jpg","images/gywm/images/002.jpg","images/gywm/images/003.jpg",
            "nstall/video/20180404/o_1ca7lo530u0v1lod14gfn0ikb4c.pdf","nstall/video/20180412/o_1cas16m3vqu9lkcdjlk36s0fc.pdf"};
    private ImageView iv1,iv2,iv3,iv4,iv5;

    public AuditInformationFragment() {
        // Required empty public constructor竖
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_audit_information;
    }

    @Override
    protected void initViews() {
        iv1=findViewById(R.id.fragment_audit_information_iv1);
        iv2=findViewById(R.id.fragment_audit_information_iv2);
        iv3=findViewById(R.id.fragment_audit_information_iv3);
        iv4=findViewById(R.id.fragment_audit_information_iv4);
        iv5=findViewById(R.id.fragment_audit_information_iv5);
        iv1.setOnClickListener(this);
        iv2.setOnClickListener(this);
        iv3.setOnClickListener(this);
        iv4.setOnClickListener(this);
        iv5.setOnClickListener(this);

        dialogPDF = new CustomDialog(getActivity(), R.style.custom_dialog2, R.layout.dialog_pdf);

        XImage.getInstance().load(iv1, Path.BASE_URL+imgs[0]);
        XImage.getInstance().load(iv2, Path.BASE_URL+imgs[1]);
        XImage.getInstance().load(iv3, Path.BASE_URL+imgs[2]);
        XImage.getInstance().load(iv4, Path.BASE_URL+imgs[3]);
        XImage.getInstance().load(iv5, Path.BASE_URL+imgs[4]);
    }

    @Override
    public void lazyLoad() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fragment_audit_information_iv1:
                initDialog(Path.BASE_URL+bigImgs[0],1);
                break;
            case R.id.fragment_audit_information_iv2:
                initDialog(Path.BASE_URL+bigImgs[1],1);
                break;
            case R.id.fragment_audit_information_iv3:
                initDialog(Path.BASE_URL+bigImgs[2],1);
                break;
            case R.id.fragment_audit_information_iv4:
                initDialog(Path.BASE_URL+"common/fileRead?isOnLine=online&fname=infoPub.pdf&filePath="+bigImgs[3],2);
                break;
            case R.id.fragment_audit_information_iv5:
                initDialog(Path.BASE_URL+"common/fileRead?isOnLine=online&fname=infoPub.pdf&filePath="+bigImgs[4],2);
                break;

        }
    }

    private CustomDialog dialogPDF;//pdf弹框
    private RemotePDFViewPager remotePDFViewPager;
    private PDFPagerAdapter pdfPagerAdapter;
    private XLoadingView loadingViewDialog;
    private RelativeLayout pdf_root;

    //弹框    1=>图片 2=>pdf
    private void initDialog(String url,int type){
        dialogPDF.show();
        pdf_root =dialogPDF.findViewById(R.id.pdf_root);
        loadingViewDialog =dialogPDF.findViewById(R.id.xloading_view);
        ZoomImageView zoomImageView=dialogPDF.findViewById(R.id.zoom_image_view);

        WindowManager windowManager = getActivity().getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialogPDF.getWindow().getAttributes();
        lp.width =display.getWidth(); //设置宽度
        lp.height = display.getHeight();
        dialogPDF.getWindow().setAttributes(lp);

        if(loadingViewDialog!=null){
            loadingViewDialog.showLoading();
        }

        if(type==1){
            XImage.getInstance().load(zoomImageView,url);
            zoomImageView.setVisibility(View.VISIBLE);

            pdf_root.setVisibility(View.GONE);
            if(loadingViewDialog!=null){
                loadingViewDialog.showContent();
            }
        }else if(type==2){
            remotePDFViewPager = new RemotePDFViewPager(getActivity(), url, new MyFileListener());
            remotePDFViewPager.setId(R.id.pdfViewPager);
            zoomImageView.setVisibility(View.GONE);
            pdf_root.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(pdfPagerAdapter!=null){
            pdfPagerAdapter.close();
        }
    }

    class MyFileListener implements DownloadFile.Listener {

        @Override
        public void onSuccess(String url, String destinationPath) {
            if(remotePDFViewPager!=null){
                pdfPagerAdapter =new PDFPagerAdapter(getActivity(), FileUtil.extractFileNameFromURL(url));
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
}
