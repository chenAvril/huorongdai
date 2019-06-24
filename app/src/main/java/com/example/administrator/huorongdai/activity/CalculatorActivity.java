package com.example.administrator.huorongdai.activity;

import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.base.BaseActivity;
import com.example.administrator.huorongdai.view.XCDropDownListView;
import com.example.administrator.huorongdai.xframe.utils.XEmptyUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CalculatorActivity extends BaseActivity {

    private LinearLayout llInput;
    private TextView tvJisuan;//计算收益

    private LinearLayout llResult;
    private ImageView ivClose;//关闭
    private TextView tvReCalculator;//重新计算
    private TextView tvSet;//重置

    private EditText etMoney;//投资金额
    private EditText etDate;//投资期限
    private EditText etRate;//投资利率

    private TextView tvAllMoney;//本息合计
    private TextView tvIncome;//利息收入
    private TextView tvEach;//每期回款
    private TextView tvLastEach;//最后一期

    private XCDropDownListView spinner;
    private ArrayList<String> dataList;
    private int style=0;//计算方式：0=》每期还息,到期还本   1=》等额本息 2=》一次性还本付息

    @Override
    protected int getContentView() {
        return R.layout.activity_calculator;
    }

    @Override
    protected void initView() {
        setTitle("利益计算器");
        llInput=findViewById(R.id.ll_input);
        llResult=findViewById(R.id.ll_result);
        ivClose=findViewById(R.id.iv_close);
        tvJisuan=findViewById(R.id.tv_jisuan);
        tvReCalculator=findViewById(R.id.tv_re_calculator);
        tvReCalculator.setOnClickListener(this);
        tvJisuan.setOnClickListener(this);
        ivClose.setOnClickListener(this);
        spinner=findViewById(R.id.drop_down_list_view);
        etMoney=findViewById(R.id.et_touzi_money);
        etDate=findViewById(R.id.et_touzi_date);
        etRate=findViewById(R.id.et_touzi_rate);
        tvAllMoney=findViewById(R.id.tv_all_money);
        tvIncome=findViewById(R.id.tv_income);
        tvEach=findViewById(R.id.tv_each);
        tvLastEach=findViewById(R.id.tv_last_each);
        tvSet=findViewById(R.id.tv_re_set);
        tvSet.setOnClickListener(this);
        etRate.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        //为dataList赋值，将下面这些数据添加到数据源中
        dataList = new ArrayList<>();
        dataList.add("每期还息,到期还本");
        //dataList.add("等额本息");
        dataList.add("一次性还本付息");

        spinner.setItemsData(dataList);
        spinner.setSetPosition(new XCDropDownListView.SetPosition() {
            @Override
            public void position(int pos) {
                style=pos;
            }
        });

        decimal=new DecimalFormat("0.00");
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_re_set://重置
                etMoney.setText("");
                etDate.setText("");
                etRate.setText("");
                break;
            case R.id.tv_jisuan://计算收益
                String money=etMoney.getText().toString().trim();
                String date=etDate.getText().toString().trim();
                String rate=etRate.getText().toString().trim();
                if(XEmptyUtils.isEmpty(money)){
                    return;
                }
                if(XEmptyUtils.isEmpty(date)){
                    return;
                }
                if(XEmptyUtils.isEmpty(rate)){
                    return;
                }
                calculator(money,date,rate,style);
                llInput.setVisibility(View.GONE);
                llResult.setVisibility(View.VISIBLE);

                break;
            case R.id.iv_close:
            case R.id.tv_re_calculator://重新计算
                llInput.setVisibility(View.VISIBLE);
                llResult.setVisibility(View.GONE);
                break;
        }
    }

    //计算
    DecimalFormat decimal;
    private void calculator(String money, String date, String rate, int style) {
        double m=Double.parseDouble(money);
        double d=Double.parseDouble(date);
        double r=Double.parseDouble(rate);

        switch (style){
            case 0://每期还息,到期还本
                double lixi0;
                double eachM;
                //int dInt=Integer.parseInt(date);
                lixi0=m*d*r/360/100;
                eachM=lixi0/d;

                tvAllMoney.setText(decimal.format(lixi0+m));
                tvLastEach.setText(decimal.format(eachM+m));

                tvIncome.setText(decimal.format(lixi0));
                tvEach.setText(decimal.format(eachM));

                break;
            case 1://一次性还本付息
                double lixi2=m*d*r/360/100;
                double allM2=m+lixi2;
                tvAllMoney.setText(decimal.format(allM2));
                tvIncome.setText(decimal.format(lixi2));
                tvEach.setText("0");
                tvLastEach.setText(decimal.format(allM2));
                break;
        }
    }


}
