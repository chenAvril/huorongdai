package com.example.administrator.huorongdai.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.administrator.huorongdai.R;
import com.example.administrator.huorongdai.base.BaseActivity;

public class InformationDisclosureActivity extends BaseActivity {

    private RelativeLayout rlCompanyProfile;//公司简介
    private RelativeLayout baseInformationRl;//基本信息
    private RelativeLayout rlPersonInfo;//法人信披
    private RelativeLayout fileProgressRl;//法人信披
    private RelativeLayout teamManagerRl;//法人信披
    private RelativeLayout riskEducationRl;//风险教育
    private RelativeLayout knowledgeRl;//网贷知识
    private RelativeLayout partnersRl;//网贷知识
    private RelativeLayout runReportRl;//网贷知识
    private RelativeLayout llLatestNotes;//平台公告

    @Override
    protected int getContentView() {
        return R.layout.activity_information_disclosure;
    }

    @Override
    protected void initView() {

        rlCompanyProfile=findViewById(R.id.rl_company_profile);
        rlPersonInfo=findViewById(R.id.rl_person_info);
        llLatestNotes=findViewById(R.id.ll_latest_notes);
        fileProgressRl=findViewById(R.id.file_progress_rl);
        teamManagerRl=findViewById(R.id.team_manager_rl);
        riskEducationRl=findViewById(R.id.risk_education_rl);
        knowledgeRl=findViewById(R.id.knowledge_rl);
        partnersRl=findViewById(R.id.partners_rl);
        runReportRl=findViewById(R.id.run_report_rl);
        baseInformationRl=findViewById(R.id.base_information_rl);
        baseInformationRl.setOnClickListener(this);
        runReportRl.setOnClickListener(this);
        partnersRl.setOnClickListener(this);
        knowledgeRl.setOnClickListener(this);
        riskEducationRl.setOnClickListener(this);
        teamManagerRl.setOnClickListener(this);
        fileProgressRl.setOnClickListener(this);
        rlCompanyProfile.setOnClickListener(this);
        rlPersonInfo.setOnClickListener(this);
        llLatestNotes.setOnClickListener(this);
        setTitle("信息披露");

    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View view) {
        Intent intent=new Intent();
        switch (view.getId()){
            case R.id.rl_company_profile://公司简介
                intent.setClass(this,CompanyProfileActivity.class);
                break;
            case R.id.base_information_rl://基本信息
                intent.setClass(this,BaseInformationActivity.class);
                break;
            case R.id.rl_person_info://法人信披
                intent.setClass(this, FarenInfoActivity.class);
                break;
            case R.id.file_progress_rl://备案进程
                intent.setClass(this, FileProgressActivity.class);
                break;
            case R.id.team_manager_rl://团队高管
                intent.setClass(this, TeamManagerActivity.class);
                break;
            case R.id.risk_education_rl://风险教育
                intent.setClass(this, NewsListActivity.class);
                intent.putExtra("title","风险教育");
                intent.putExtra("newsType","fengxianjiaoyu");
                break;
            case R.id.knowledge_rl://网贷知识
                intent.setClass(this, NewsListActivity.class);
                intent.putExtra("title","网贷知识");
                intent.putExtra("newsType","wdzhishipuji");
                break;
            case R.id.partners_rl://合作伙伴
                intent.setClass(this, PartnersActivity.class);
                break;
            case R.id.run_report_rl://运行报告
                intent.setClass(this, RunReportActivity.class);
                break;
            case R.id.ll_latest_notes://平台公告
                intent.setClass(this, LatestNoticeActivity.class);
                break;
        }
        startActivity(intent);
    }
}
