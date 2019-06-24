package com.example.administrator.huorongdai.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.administrator.huorongdai.view.banner.loader.ImageLoader;

/**
 * Created by Administrator on 2018/3/15 0015.
 */

public class GImageLoader  extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        //Glide 加载图片简单用法
        Glide.with(context)
                .load(path)
                .skipMemoryCache(true)//跳过内存缓存。
                .diskCacheStrategy(DiskCacheStrategy.RESULT)//不要在disk硬盘中缓存
                .crossFade()
                .into(imageView);
    }
}
