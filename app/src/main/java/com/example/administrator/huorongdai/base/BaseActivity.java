package com.example.administrator.huorongdai.base;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.util.DensityUtils;
import com.example.administrator.huorongdai.xframe.utils.statusbar.XStatusBar;
import com.yatoooon.screenadaptation.ScreenAdapterTools;


public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener{
    protected ViewGroup contentView;
    protected TextView rightBtn;
    protected View leftBtn;
    protected TextView titltTv;
    protected View titlebar;
    protected int titlebarColor= Color.parseColor("#e24d4f");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        int titlebarResId = getTitlebarResId();
        if (titlebarResId!=0) {
            LinearLayout view=findViewById(R.id.base_view);
            view.removeViewAt(0);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DensityUtils.dp2px(this, 50));
            ViewGroup titleView=(ViewGroup) View.inflate(this, titlebarResId, null);
            view.addView(titleView, 0,lp);
            view.setBackgroundDrawable(titleView.getBackground());
            titlebar=titleView;
        }else {
            titlebar=findViewById(R.id.base_titlebar);
            leftBtn = findViewById(R.id.base_back_btn);
            leftBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickLeft();
                }
            });
            rightBtn = findViewById(R.id.base_menu_btn);
            rightBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickRight();
                }
            });
            titltTv=findViewById(R.id.base_title_tv);

        }
        contentView=findViewById(R.id.base_contentview);
        contentView.addView(View.inflate(this, getContentView(), null));
        setRightBtnVisible(false);
        //在setContentView();后面加上适配语句
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());

        initView();
        setStatusBar();
        loadData();
    }

    protected void setStatusBar() {
        XStatusBar.setColor(this,titlebarColor,0);
    }

    /**
     * 加载布局文件
     * @return
     */
    protected abstract int getContentView();

    /**
     * 初始化界面
     **/
    protected  abstract    void   initView();

    /**
     * 加载数据
     **/
    protected  abstract void   loadData();

    /**
     * 点击左侧按钮
     * 默认是退出
     */
    protected void onClickLeft() {
        finish();
    }
    /**
     * 点击左侧按钮
     * 默认什么都不做并且隐藏
     */
    protected void onClickLeftTv(){

    }

    /**
     * 点击右侧按钮
     * 默认什么都不做
     */
    protected void onClickRight() {

    }

    /**
     * 设置左侧按钮显示与隐藏
     * @param visible
     */
    public void setLeftBtnVisible(Boolean visible) {
        if (leftBtn!=null) {
            if (visible) {
                leftBtn.setVisibility(View.VISIBLE);
            }else{
                leftBtn.setVisibility(View.GONE);
            }
        }

    }

    /**
     * 设置右侧按钮显示与隐藏
     * @param visible
     */
    public void setRightBtnVisible(Boolean visible) {
        if (rightBtn!=null) {
            if (visible) {
                rightBtn.setVisibility(View.VISIBLE);
            }else{
                rightBtn.setVisibility(View.GONE);
            }
        }

    }

    /**
     * 获取自定义标题栏
     * 如果子类复写并返回不等于0的布局文件，将会覆盖默认标题
     * 返回0 将会采用默认标题
     * @return
     */
    protected int getTitlebarResId() {
        return 0;
    }

    /**
     * 设置中间标题
     * @param title
     */
    public void setTitle(String title){
        if (titltTv!=null) {
            titltTv.setText(title);
        }
    }

    /**
     * 设置右边你按钮文字属性
     * @param title
     */
    public void setRtTitle(String title){
        if (rightBtn!=null) {
            rightBtn.setText(title);
        }
    }

    public View getTitleBar(){

        return titlebar;
    }

    @Override
    public void onBackPressed() {
        finish();
        //super.onBackPressed();
    }
}
