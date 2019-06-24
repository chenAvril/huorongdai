package com.example.administrator.huorongdai.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.huorongdai.R;


/**
 * 支持渐变的 actionBar
 * Created by 晖仔(Milo) on 2016/12/28.
 * email:303767416@qq.com
 */

public final class TranslucentActionBar extends LinearLayout {

    private View layRoot;
    private View vStatusBar;
    public TextView tvTitle;

    public TranslucentActionBar(Context context) {
        this(context, null);
    }

    public TranslucentActionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TranslucentActionBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        setOrientation(HORIZONTAL);
        View contentView = inflate(getContext(), R.layout.actionbar_trans, this);
        layRoot = contentView.findViewById(R.id.lay_transroot);
        vStatusBar = contentView.findViewById(R.id.v_statusbar);
        tvTitle = contentView.findViewById(R.id.tv_actionbar_title);
    }

    /**
     * 设置状态栏高度
     *
     * @param statusBarHeight
     */
    public void setStatusBarHeight(int statusBarHeight) {
        ViewGroup.LayoutParams params = vStatusBar.getLayoutParams();
        params.height = statusBarHeight;
        vStatusBar.setLayoutParams(params);
    }

    /**
     * 设置是否需要渐变
     */
    public void setNeedTranslucent() {
        setNeedTranslucent(true, false);
    }

    /**
     * 设置是否需要渐变,并且隐藏标题
     *
     * @param translucent
     */
    public void setNeedTranslucent(boolean translucent, boolean titleInitVisibile) {
        if (translucent) {
            layRoot.setBackgroundDrawable(null);
        }
        if (!titleInitVisibile) {
            tvTitle.setVisibility(View.GONE);
        }
    }

    /**
     * 设置标题
     *
     * @param strTitle
     */
    public void setTitle(String strTitle) {
        if (!TextUtils.isEmpty(strTitle)) {
            tvTitle.setText(strTitle);
        } else {
            tvTitle.setVisibility(View.GONE);
        }
    }

    /**
     * 设置数据
     * @param strTitle
     */
    public void setData(String strTitle) {
        if (!TextUtils.isEmpty(strTitle)) {
            tvTitle.setText(strTitle);
        } else {
            tvTitle.setVisibility(View.GONE);
        }
    }

}
