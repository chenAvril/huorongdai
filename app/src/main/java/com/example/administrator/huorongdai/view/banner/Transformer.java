package com.example.administrator.huorongdai.view.banner;

import android.support.v4.view.ViewPager.PageTransformer;

import com.example.administrator.huorongdai.view.banner.transformer.AccordionTransformer;
import com.example.administrator.huorongdai.view.banner.transformer.BackgroundToForegroundTransformer;
import com.example.administrator.huorongdai.view.banner.transformer.CubeInTransformer;
import com.example.administrator.huorongdai.view.banner.transformer.CubeOutTransformer;
import com.example.administrator.huorongdai.view.banner.transformer.DefaultTransformer;
import com.example.administrator.huorongdai.view.banner.transformer.DepthPageTransformer;
import com.example.administrator.huorongdai.view.banner.transformer.FlipHorizontalTransformer;
import com.example.administrator.huorongdai.view.banner.transformer.FlipVerticalTransformer;
import com.example.administrator.huorongdai.view.banner.transformer.ForegroundToBackgroundTransformer;
import com.example.administrator.huorongdai.view.banner.transformer.RotateDownTransformer;
import com.example.administrator.huorongdai.view.banner.transformer.RotateUpTransformer;
import com.example.administrator.huorongdai.view.banner.transformer.ScaleInOutTransformer;
import com.example.administrator.huorongdai.view.banner.transformer.StackTransformer;
import com.example.administrator.huorongdai.view.banner.transformer.TabletTransformer;
import com.example.administrator.huorongdai.view.banner.transformer.ZoomInTransformer;
import com.example.administrator.huorongdai.view.banner.transformer.ZoomOutSlideTransformer;
import com.example.administrator.huorongdai.view.banner.transformer.ZoomOutTranformer;


public class Transformer {
    public static Class<? extends PageTransformer> Default = DefaultTransformer.class;
    public static Class<? extends PageTransformer> Accordion = AccordionTransformer.class;
    public static Class<? extends PageTransformer> BackgroundToForeground = BackgroundToForegroundTransformer.class;
    public static Class<? extends PageTransformer> ForegroundToBackground = ForegroundToBackgroundTransformer.class;
    public static Class<? extends PageTransformer> CubeIn = CubeInTransformer.class;
    public static Class<? extends PageTransformer> CubeOut = CubeOutTransformer.class;
    public static Class<? extends PageTransformer> DepthPage = DepthPageTransformer.class;
    public static Class<? extends PageTransformer> FlipHorizontal = FlipHorizontalTransformer.class;
    public static Class<? extends PageTransformer> FlipVertical = FlipVerticalTransformer.class;
    public static Class<? extends PageTransformer> RotateDown = RotateDownTransformer.class;
    public static Class<? extends PageTransformer> RotateUp = RotateUpTransformer.class;
    public static Class<? extends PageTransformer> ScaleInOut = ScaleInOutTransformer.class;
    public static Class<? extends PageTransformer> Stack = StackTransformer.class;
    public static Class<? extends PageTransformer> Tablet = TabletTransformer.class;
    public static Class<? extends PageTransformer> ZoomIn = ZoomInTransformer.class;
    public static Class<? extends PageTransformer> ZoomOut = ZoomOutTranformer.class;
    public static Class<? extends PageTransformer> ZoomOutSlide = ZoomOutSlideTransformer.class;
}
