package com.example.administrator.huorongdai.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.administrator.huorongdai.R;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

/**
 * 自定义组件：购买数量，带减少增加按钮
 * Created by hiwhitley on 2016/7/4.
 */
public class AmountView extends LinearLayout implements View.OnClickListener, TextWatcher {

    private static final String TAG = "AmountView";
    private int amount = 0; //购买数量
    private int goods_storage = 0; //商品库存

    private OnAmountChangeListener mListener;

    public EditText etAmount;
    private LinearLayout btnDecrease;
    private LinearLayout btnIncrease;

    public AmountView(Context context) {
        this(context, null);
    }

    public AmountView(Context context, AttributeSet attrs) {
        super(context, attrs);

        View view=LayoutInflater.from(context).inflate(R.layout.view_amount, this);
        if (!isInEditMode()) {
            ScreenAdapterTools.getInstance().loadView(view);
        }
        etAmount = findViewById(R.id.etAmount);
        btnDecrease = findViewById(R.id.btnDecrease);
        btnIncrease = findViewById(R.id.btnIncrease);
        btnDecrease.setOnClickListener(this);
        btnIncrease.setOnClickListener(this);
        etAmount.addTextChangedListener(this);

        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attrs, R.styleable.AmountView);
        int btnWidth = obtainStyledAttributes.getDimensionPixelSize(R.styleable.AmountView_btnWidth, LayoutParams.WRAP_CONTENT);
        int tvWidth = obtainStyledAttributes.getDimensionPixelSize(R.styleable.AmountView_tvWidth, 80);
        int tvTextSize = obtainStyledAttributes.getDimensionPixelSize(R.styleable.AmountView_tvTextSize, 0);
        obtainStyledAttributes.recycle();

        LayoutParams btnParams = new LayoutParams(btnWidth, LayoutParams.MATCH_PARENT);
        btnDecrease.setLayoutParams(btnParams);
        btnIncrease.setLayoutParams(btnParams);
//        if (btnTextSize != 0) {
//            btnDecrease.setTextSize(TypedValue.COMPLEX_UNIT_PX, btnTextSize);
//            btnIncrease.setTextSize(TypedValue.COMPLEX_UNIT_PX, btnTextSize);
//        }

        LayoutParams textParams = new LayoutParams(tvWidth, LayoutParams.MATCH_PARENT);
        etAmount.setLayoutParams(textParams);
        if (tvTextSize != 0) {
            etAmount.setTextSize(tvTextSize);
        }
    }

    public void setOnAmountChangeListener(OnAmountChangeListener onAmountChangeListener) {
        this.mListener = onAmountChangeListener;
    }

    public void setGoods_storage(int goods_storage) {
        this.goods_storage = goods_storage;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btnDecrease) {
            if (amount > 0) {
                amount--;
                etAmount.setText(amount*100 + "");
            }
        } else if (i == R.id.btnIncrease) {
            if (amount < goods_storage) {
                amount++;
                etAmount.setText(amount*100 + "");
            }
        }

        etAmount.clearFocus();

        if (mListener != null) {
            //mListener.onAmountChange(this, amount);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String text = s.toString();
        if (text.isEmpty())
            return;

        int len = text.length();
        if (len > 1 && text.startsWith("0")) {
            s.replace(0,1,"");
        }

//        int num=Integer.valueOf(text);
//        if(num%100!=0){
//            return;
//        }

        amount = Integer.valueOf(text)/100;
        if (amount > goods_storage) {
            etAmount.setText(goods_storage*100 + "");
            return;
        }

        if (mListener != null) {
            mListener.onAmountChange(this, Integer.valueOf(text));
        }
    }

    public interface OnAmountChangeListener {
        void onAmountChange(View view, int tMoney);
    }

}
