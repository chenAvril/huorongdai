package com.example.administrator.huorongdai.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.administrator.huorongdai.R;

/**
 * Created by Administrator on 2018/5/11 0011.
 */

public class PointDividerView extends View {

    // 创建画笔
    private Paint mPaint; // 画笔
    private float radius; // 圆的半径
    private float dividerWidth; // 圆的间距
    private int mColor ; // 圆点的颜色
    private Context mContext;

    public PointDividerView(Context context) {
        this(context, null);
    }

    public PointDividerView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PointDividerView(Context context,AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext=context;
        TypedArray typedArray=context.obtainStyledAttributes(attrs, R.styleable.PointDividerView,defStyleAttr,0);
        radius = typedArray.getColor(R.styleable.PointDividerView_pointRadius, 4);
        dividerWidth = typedArray.getColor(R.styleable.PointDividerView_dividerWidth, 6);
        mColor = typedArray.getColor(R.styleable.PointDividerView_pointColor, Color.parseColor("#d1d1d1"));
        init(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 限死高度为space_10(实际是10dp)，如果要需要动态设置，可以通过attrs参数获取设置。
        setMeasuredDimension(10, heightMeasureSpec );
    }

    protected void init(Context context, @Nullable AttributeSet attrs) {
        mContext = context;
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setAntiAlias(true);
        mPaint.setColor(mColor);// 设置颜色
        int measuredHeight = getMeasuredHeight() ; // 高度居中
        int measuredWidth = getMeasuredWidth()/ 2;
        // int maxCount = measuredWidth / (dividerWidth + radius * 2);
        for (float i = radius; i < measuredHeight; ) {
            canvas.drawCircle(measuredWidth, i, radius, mPaint);// 小圆
            i += dividerWidth;
        }

    }


    public void setmColor(int mColor) {
        this.mColor = mColor;
    }
}
