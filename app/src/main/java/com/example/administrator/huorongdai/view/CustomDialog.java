package com.example.administrator.huorongdai.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.yatoooon.screenadaptation.ScreenAdapterTools;

/**
 * Created by Windows User on 2017/12/26.
 */

public class CustomDialog extends Dialog {
    private Context context;
    private int resId;
    public CustomDialog(Context context, int resLayout) {
        this(context,0,0);
    }
    public CustomDialog(Context context, int themeResId, int resLayout) {
        super(context, themeResId);
        this.context = context;
        this.resId = resLayout;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(resId);
        //在setContentView();后面加上适配语句
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
    }
}
