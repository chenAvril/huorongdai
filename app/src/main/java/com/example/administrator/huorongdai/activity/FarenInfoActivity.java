package com.example.administrator.huorongdai.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.base.BaseActivity;
import com.example.administrator.huorongdai.util.GImageLoader;
import com.example.administrator.huorongdai.view.ZoomImageView;
import com.example.administrator.huorongdai.xframe.utils.imageload.XImage;

public class FarenInfoActivity extends BaseActivity {

    private ZoomImageView iv;

    @Override
    protected int getContentView() {
        return R.layout.activity_faren_info;
    }

    @Override
    protected void initView() {
        setTitle("法人信披");
        iv=findViewById(R.id.iv_faren_info);
        XImage.getInstance().load(iv,"http://www.huorongdai.com/images/gywm/images/frxp.jpg");
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View view) {

    }
}
