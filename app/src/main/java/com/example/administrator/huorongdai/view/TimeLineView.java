package com.example.administrator.huorongdai.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.administrator.huorongdai.R;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

public class TimeLineView extends View {

    private int width;//宽
    private int high;//高

    private int radius;//圆的半径
    private int lineWidth;//线的宽度
    private  Paint paint;

    public TimeLineView(Context context) {
        this(context,null);
    }

    public TimeLineView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TimeLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array= context.obtainStyledAttributes(attrs, R.styleable.TimeLineView,defStyleAttr,0);
        radius=array.getDimensionPixelSize(R.styleable.TimeLineView_time_line_radius,20);
        lineWidth=array.getDimensionPixelSize(R.styleable.TimeLineView_time_line_width,2);
        array.recycle();

        if (!isInEditMode()) {
            radius = ScreenAdapterTools.getInstance().loadCustomAttrValue(radius);
            lineWidth = ScreenAdapterTools.getInstance().loadCustomAttrValue(lineWidth);
        }

        initPaint();
    }

    private void initPaint() {
        paint=new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(lineWidth);
        paint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        width=getMeasuredWidth()/2;
        high=getMeasuredHeight()/2;
        canvas.drawCircle(width,high,radius,paint);
        canvas.drawLine(width,0,width,high-radius,paint);
        canvas.drawLine(width,high+radius,width,high*2,paint);
    }
}
