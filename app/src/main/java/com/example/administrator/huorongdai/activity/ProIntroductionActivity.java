package com.example.administrator.huorongdai.activity;

import android.content.Intent;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.huorongdai.Path;
import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.base.BaseActivity;
import com.example.administrator.huorongdai.util.GImageLoader;
import com.example.administrator.huorongdai.view.banner.Banner;
import com.example.administrator.huorongdai.view.banner.listener.OnBannerListener;
import com.example.administrator.huorongdai.xframe.utils.XEmptyUtils;


import java.util.ArrayList;

public class ProIntroductionActivity extends BaseActivity {
    private LinearLayout llDataType;//证件类型
    private LinearLayout llProInfo;//证件类型
    private ArrayList<String> bannerImages=new ArrayList<>();//证件类型的图片地址
    private Banner dataTypeBanner;//图片banner
    private TextView tvLoanDesc;//项目描述
    private String loanDesc;//项目描述
    private String bannerPath;//证件类型

    @Override
    protected int getContentView() {
        return R.layout.activity_pro_introduction;
    }

    @Override
    protected void initView() {
        llDataType=findViewById(R.id.ll_data_type);
        llProInfo=findViewById(R.id.ll_pro_info);
        dataTypeBanner=findViewById(R.id.data_type_banner);
        tvLoanDesc=findViewById(R.id.tv_loan_desc);

        setTitle("项目简介");

        Intent intent=getIntent();
        if(intent!=null){
            loanDesc=intent.getStringExtra("loanDesc");
            bannerPath=intent.getStringExtra("bannerPath");
        }
    }

    @Override
    protected void loadData() {

        if(XEmptyUtils.isEmpty(loanDesc)){
            llProInfo.setVisibility(View.GONE);
        }else {
            tvLoanDesc.setText(Html.fromHtml(loanDesc));
        }

        if(XEmptyUtils.isEmpty(bannerPath)){
            llDataType.setVisibility(View.GONE);
        }else {
            String[] paths=bannerPath.split(",");
            if(paths.length>0){
                llDataType.setVisibility(View.VISIBLE);
                for (String path : paths) {
                    if(path.contains(".")){
                        bannerImages.add(Path.BASE_ImgURL+path);
                    }
                }

                //设置图片加载器
                dataTypeBanner.setImageLoader(new GImageLoader());
                //设置图片集合
                dataTypeBanner.setImages(bannerImages);
                //设置轮播时间
                dataTypeBanner.setDelayTime(3000);
                dataTypeBanner.start();
                dataTypeBanner.startAutoPlay();
                dataTypeBanner.setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        Intent intent = new Intent(ProIntroductionActivity.this, ImagePagerActivity.class);
                        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, bannerImages);
                        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
                        startActivity(intent);
                    }
                });
            }else {
                llDataType.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(dataTypeBanner!=null){
            dataTypeBanner.stopAutoPlay();
        }
    }
}
