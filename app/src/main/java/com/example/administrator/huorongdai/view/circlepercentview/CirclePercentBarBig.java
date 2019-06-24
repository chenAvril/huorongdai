package com.example.administrator.huorongdai.view.circlepercentview;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.example.administrator.huorongdai.R;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

/**
 * Created by Administrator on 2018/5/11 0011.
 */

public class CirclePercentBarBig extends View {

    private Context mContext;

    private int mArcColor;
    private int mArcWidth;
    private int mCenterTextColor;
    private int mCenterTextSizeBig;
    private int mCenterTextSizeSmall;
    private int mCircleRadius;
    private Paint arcPaint;
    private Paint arcCirclePaint;
    private Paint centerTextPaint;
    private RectF arcRectF;
    private Rect textBoundRect;
    private float mCurData = 0;
    private int arcStartColor;
    private Paint startCirclePaint;
    private String text = "预计年化率";

    private String yearRate="0.0";

    public String getYearRate() {
        return yearRate;
    }

    public void setYearRate(String yearRate) {
        this.yearRate = yearRate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public CirclePercentBarBig(Context context) {
        this(context, null);
    }

    public CirclePercentBarBig(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CirclePercentBarBig(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CirclePercentBarBig, defStyleAttr, 0);
        mArcColor = typedArray.getColor(R.styleable.CirclePercentBarBig_arcColorBig, ContextCompat.getColor(mContext, R.color.light_gray));
        mArcWidth = typedArray.getDimensionPixelSize(R.styleable.CirclePercentBarBig_arcWidthBig, DisplayUtil.dp2px(context, 20));
        mCenterTextSizeBig = typedArray.getDimensionPixelSize(R.styleable.CirclePercentBarBig_centerTextSizeBig, DisplayUtil.dp2px(context, 18));
        mCenterTextSizeSmall = typedArray.getDimensionPixelSize(R.styleable.CirclePercentBarBig_centerTextSizeSmall, DisplayUtil.dp2px(context, 12));
        mCircleRadius = typedArray.getDimensionPixelSize(R.styleable.CirclePercentBarBig_circleRadiusBig, DisplayUtil.dp2px(context, 100));
        mCenterTextColor = typedArray.getColor(R.styleable.CirclePercentBarBig_centerTextColorBig, ContextCompat.getColor(mContext, R.color.blue));
        arcStartColor = typedArray.getColor(R.styleable.CirclePercentBarBig_arcStartColorBig, ContextCompat.getColor(mContext, R.color.blue));
        typedArray.recycle();

        if (!isInEditMode()) {
            mArcWidth = ScreenAdapterTools.getInstance().loadCustomAttrValue(mArcWidth);
            mCenterTextSizeBig = ScreenAdapterTools.getInstance().loadCustomAttrValue(mCenterTextSizeBig);
            mCenterTextSizeSmall = ScreenAdapterTools.getInstance().loadCustomAttrValue(mCenterTextSizeSmall);
            mCircleRadius = ScreenAdapterTools.getInstance().loadCustomAttrValue(mCircleRadius);
        }

        initPaint();
    }

    private void initPaint() {
        startCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        startCirclePaint.setStyle(Paint.Style.STROKE);
        startCirclePaint.setStrokeWidth(mArcWidth);
        startCirclePaint.setColor(arcStartColor);
        startCirclePaint.setStrokeCap(Paint.Cap.ROUND);

        arcCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        arcCirclePaint.setStyle(Paint.Style.STROKE);
        arcCirclePaint.setStrokeWidth(mArcWidth);
        arcCirclePaint.setColor(ContextCompat.getColor(mContext, R.color.light_gray));
        arcCirclePaint.setStrokeCap(Paint.Cap.ROUND);

        arcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        arcPaint.setStyle(Paint.Style.STROKE);
        arcPaint.setStrokeWidth(mArcWidth);
        arcPaint.setColor(mArcColor);
        arcPaint.setStrokeCap(Paint.Cap.ROUND);

        centerTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        centerTextPaint.setStyle(Paint.Style.STROKE);
        centerTextPaint.setColor(mCenterTextColor);
        centerTextPaint.setTextSize(mCenterTextSizeSmall);
        //圓弧的外接矩形
        arcRectF = new RectF();
        //文字的边界矩形
        textBoundRect = new Rect();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureDimension(widthMeasureSpec), measureDimension(heightMeasureSpec));
    }

    private int measureDimension(int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = mCircleRadius * 2;
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.rotate(-90, getWidth() / 2, getHeight() / 2);

        arcRectF.set(getWidth() / 2 - mCircleRadius + mArcWidth / 2, getHeight() / 2 - mCircleRadius + mArcWidth / 2
                , getWidth() / 2 + mCircleRadius - mArcWidth / 2, getHeight() / 2 + mCircleRadius - mArcWidth / 2);

        canvas.drawArc(arcRectF, 180, 360, false, arcCirclePaint);

        canvas.drawArc(arcRectF, 180, 360 * mCurData / 100, false, startCirclePaint);

        canvas.rotate(90, getWidth() / 2, getHeight() / 2);

        //红色大数字
        centerTextPaint.getTextBounds(yearRate, 0, yearRate.length(), textBoundRect);

        centerTextPaint.setTextSize(mCenterTextSizeBig);
        centerTextPaint.setColor(Color.parseColor("#e24d4f"));
        float y=getHeight() / 2 + textBoundRect.height()/3;
        //canvas.drawText(yearRate, getWidth() / 2 - textBoundRect.width()*2 , y, centerTextPaint);
        canvas.drawText(yearRate, getWidth() / 2 - textBoundRect.width()*5/3 , y, centerTextPaint);
        //%
        String data = "%";
        centerTextPaint.getTextBounds(data, 0, data.length(), textBoundRect);
        centerTextPaint.setTextSize(mCenterTextSizeSmall);
        //canvas.drawText(data, getWidth() /2 + textBoundRect.width()*2/3, y, centerTextPaint);
        canvas.drawText(data, getWidth() /2 + textBoundRect.width()*5/4, y, centerTextPaint);
        //预计年化率
        centerTextPaint.getTextBounds(text, 0, text.length(), textBoundRect);
        centerTextPaint.setTextSize(mCenterTextSizeSmall);
        centerTextPaint.setColor(Color.parseColor("#757575"));
        canvas.drawText(text, getWidth() / 2 - textBoundRect.width()/2, getHeight()/2 + textBoundRect.height()*2, centerTextPaint);
    }

    public void setPercentData(float data, TimeInterpolator interpolator) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(mCurData, data);
        valueAnimator.setDuration((long) (Math.abs(mCurData - data) * 10));
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = (float) valueAnimator.getAnimatedValue();
                mCurData = (float) (Math.round(value * 10)) / 10;
                invalidate();
            }
        });
        valueAnimator.setInterpolator(interpolator);
        valueAnimator.start();
    }
}