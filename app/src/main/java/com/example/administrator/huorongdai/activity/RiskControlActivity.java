package com.example.administrator.huorongdai.activity;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.adapter.MyGridLayoutManager;
import com.example.administrator.huorongdai.base.BaseActivity;
import com.example.administrator.huorongdai.xframe.adapter.XRecyclerViewAdapter;
import com.example.administrator.huorongdai.xframe.adapter.XViewHolder;
import com.example.administrator.huorongdai.xframe.utils.XEmptyUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//风险控制
public class RiskControlActivity extends BaseActivity {

    private MyAdapter adapter;
    private RecyclerView recyclerView;
    private List<Map<String ,String>> infoLists;
    private String danbaoInfo;//保障措施
    private String repaymentInfo;//还款来源

    @Override
    protected int getContentView() {
        return R.layout.activity_risk_control;
    }

    @Override
    protected void initView() {
        recyclerView=findViewById(R.id.rl_risk_control);
        setTitle("风险控制");

        Intent intent=getIntent();
        if(intent!=null){
            danbaoInfo=intent.getStringExtra("danbaoInfo");
            repaymentInfo=intent.getStringExtra("repaymentInfo");
        }

        DashlineItemDivider decoration=new DashlineItemDivider(R.color.word_gray,50,50);
        //decoration.setDrawLastItem(false);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setLayoutManager(new MyGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        infoLists=new ArrayList<>();

//        Map<String,String> map1=new HashMap<>();
//        map1.put("title","1.完善的风控体系");
//        map1.put("cont",getResources().getString(R.string.risk1));
//        infoLists.add(map1);
//
//        Map<String,String> map2=new HashMap<>();
//        map2.put("title","2.项目保障");
//        map2.put("cont",getResources().getString(R.string.risk2));
//        infoLists.add(map2);
//
//        Map<String,String> map3=new HashMap<>();
//        map3.put("title","3.技术保障");
//        map3.put("cont",getResources().getString(R.string.risk3));
//        infoLists.add(map3);
        if(!XEmptyUtils.isEmpty(danbaoInfo)){
            Map<String,String> map1=new HashMap<>();
            map1.put("title","1.保障措施");
            map1.put("cont",danbaoInfo);
            infoLists.add(map1);
        }

        if(!XEmptyUtils.isEmpty(repaymentInfo)){
            Map<String,String> map2=new HashMap<>();
            map2.put("title","2.还款来源");
            map2.put("cont",repaymentInfo);
            infoLists.add(map2);
            adapter=new MyAdapter(recyclerView,infoLists);
            recyclerView.setAdapter(adapter);
        }

    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View view) {

    }

    class MyAdapter extends XRecyclerViewAdapter<Map<String ,String>> {

        public MyAdapter(@NonNull RecyclerView mRecyclerView, List<Map<String ,String>> dataLists) {
            super(mRecyclerView, dataLists,R.layout.item_activity_risk_control);
        }

        @Override
        protected void bindData(XViewHolder holder, Map<String ,String> data, int position) {
            TextView tvTitle=holder.getView(R.id.tv_title);
            TextView tvContent=holder.getView(R.id.tv_content);

            String title=data.get("title");
            if(title.contains("<p><br/></p>")){
                title=title.replace("<p><br/></p>","");
            }
            tvTitle.setText(Html.fromHtml(title));

            String cont=data.get("cont");
            if(cont.contains("<p><br/></p>")){
                cont=cont.replace("<p><br/></p>","");
            }
            tvContent.setText(Html.fromHtml(cont));
        }
    }

    class DashlineItemDivider  extends RecyclerView.ItemDecoration{
        private int color;
        private int mPaddingLeft;
        private int mPaddingRight;

        public DashlineItemDivider(){

        }

        public DashlineItemDivider(int color, int paddingLeft, int paddingRight) {
            this.color = color;
            this.mPaddingLeft = paddingLeft;
            this.mPaddingRight = paddingRight;
        }

        public void onDrawOver(Canvas c, RecyclerView parent) {
            final int left = parent.getPaddingLeft()+mPaddingLeft;
            final int right = parent.getWidth() - parent.getPaddingRight()-mPaddingRight;

            final int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                        .getLayoutParams();
                //以下计算主要用来确定绘制的位置
                final int top = child.getBottom() + params.bottomMargin;

                //绘制虚线
                Paint paint = new Paint();
                paint.setStyle(Paint.Style.STROKE);
                paint.setColor(color);
                Path path = new Path();
                path.moveTo(left, top);
                path.lineTo(right,top);
                PathEffect effects = new DashPathEffect(new float[]{15,15,15,15},5);//此处单位是像素不是dp  注意 请自行转化为dp
                paint.setPathEffect(effects);
                c.drawPath(path, paint);


            }
        }

    }
}
