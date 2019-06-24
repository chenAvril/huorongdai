package com.example.administrator.huorongdai;

import com.example.administrator.huorongdai.xframe.utils.XEmptyUtils;
import com.example.administrator.huorongdai.xframe.utils.XEncryptUtils;
import com.example.administrator.huorongdai.xframe.utils.XPreferencesUtils;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/12 0012.
 */

public class Path {
    //https://192.168.0.199/svn/app/Android
    //账号：chenlei_ad  密码：chenlei
    /**
     * 测试环境下的账号密码
     * 18375333610
     * 123456
     *
     * 15155118752
     * 123456
     * 投资密码wang123.
     * 提现密码wang123.
     *
     * 18110962900
     * chen123456
     *
     * http://106.14.164.111/hrd_admin/
     * hrd001
     * 123456
     *
     * 打包key
     * 账号:huorongdai
     * 密码:123456
     * 别名:huorongdai
     * 密码:123456
     */

    public static String  BASE_URL = "http://www.huorongdai.com/";//线上
//    public static String  BASE_URL = "http://www.jsxtd.group/";//线上
//    public static String  BASE_URL = "http://106.14.127.18/";//测试环境
//    public static String  BASE_URL = "http://192.168.0.194:8080/hrd_web/";//15155118003  登录密码wang123   投资密码118003   交易密码wang123.
                                                                          //15155118004  登录密码wang123   投资密码118004   交易密码wang123.
    //15155118707  123456   wang123.
    //15155118703  123456   wang123.
    public static String BASE_ImgURL="http://enze.oss-cn-shanghai.aliyuncs.com/";

    public static String check_android_ver=BASE_URL+"app/customer/checkAndroidVer";//安卓版本检查更新接口

    public static String banner_url= BASE_URL+"app/basic/getBannerList";//首页图片banner
    public static String notice_url = BASE_URL + "app/basic/getNoticeList";//首页通知banner
    public static String notice_detail = BASE_URL + "app/basic/getNotice";//首页通知详情
    public static String recommend_loan_url = BASE_URL + "app/customer/getRecommendLoanList";//首页推荐项目
    public static String huodong_url=BASE_URL+"website/actives/list";//首页活动列表

    public static String project_list_url=BASE_URL+"app/customer/loanList";//散标项目查询接口
    public static String loan_view=BASE_URL+"app/customer/loanView";//散标项目详情接口
    public static String get_loanInvest_list=BASE_URL+"app/customer/getLoanInvestList";//项目投资列表查询接口
    public static String invest=BASE_URL+"app/customer/invest";//投资接口

    public static String login_url=BASE_URL+"app/customer/login";//用户登录接口
    public static String get_geal_time_cust=BASE_URL+"app/customer/getRealTimeCust";//实时查询账户信息接口
    public static String open_acc=BASE_URL+"app/customer/openAcct";//开户接口
    public static String bank_name=BASE_URL+"basic/fuyoubank/fuyouBankAjax";//银行名称接口
    public static String find_account_psw=BASE_URL+"app/customer/retrieveCustPwd";//找回登录密码接口
    public static String cust_info=BASE_URL+"app/customer/custInfo";//用户信息查询接口
    public static String cust_icon=BASE_URL+"app/customer/alterCustomerHead";//用户头像修改地址
    public static String bank_card_list=BASE_URL+"app/customer/bankCard";//银行卡查询接口
    public static String account_info=BASE_URL+"app/customer/accountInfo";//获取账户信息接口

    public static String get_sign=BASE_URL+"app/customer/getSign";//查询签到信息接口
    public static String check_signIn=BASE_URL+"app/customer/checkSignIn";//查询是否签到接口
    public static String sign_in=BASE_URL+"app/customer/signIn";//签到接口

    public static String web_service_contract_url=BASE_URL+"app/customer/registProt";//注册协议WebView方式

    public static String register_account=BASE_URL+"app/customer/regist";//注册接口
    public static String get_mobile_code=BASE_URL+"app/customer/getMobileCode";//获取短信验证码接口
    public static String check_mobile_code=BASE_URL+"app/customer/checkMobileCode";//校验短信验证码接口

    public static String recharge=BASE_URL+"app/customer/reCharge";//充值接口
    public static String withdraw=BASE_URL+"app/customer/withDraw";//提现接口

    public static String get_self_invest_list=BASE_URL+"app/customer/getSelfInvestList";//个人投标记录接口
    public static String get_returned_money_list=BASE_URL+"app/customer/getReturnedMoneyList";//回款列表查询接口
    public static String get_returned_money_detail=BASE_URL+"app/customer/getReturnedMoneyDetail";//回款详情查询接口
    public static String repay=BASE_URL+"app/customer/repay";//还款接口
    public static String get_tarde_amt=BASE_URL+"app/customer/getTradeAmt";//客户交易额查询接口

    public static String get_trade_record_list=BASE_URL+"app/customer/getTradeRecordList";//个人交易记录接口

    public static String get_letter_list=BASE_URL+"app/customer/getLetterList";//站内信查询接口
    public static String read_letter=BASE_URL+"app/customer/readLetter";//站内信阅读接口
    public static String delete_letter=BASE_URL+"app/customer/deleteLetter";//站内信删除接口
    public static String get_news_list=BASE_URL+"app/basic/getNewsList";//徽盐动态、媒体报道、徽盐专栏查询接口
    public static String read_news=BASE_URL+"app/basic/readNews";//徽盐动态、媒体报道、徽盐专栏阅读接口

    public static String invite_friend=BASE_URL+"app/invite/inviteFriend";//推荐好友接口
    public static String static_invite_data=BASE_URL+"app/invite/staticInviteData";//统计邀请接口
    public static String invite_friend_list=BASE_URL+"app/invite/getInviteFriendList";//查询邀请好友列表接口

    public static String get_red_packet=BASE_URL+"app/customer/getRedPacket";//红包查询接口
    public static String get_expect_earn=BASE_URL+"app/customer/getExpectEarn";//投资红包接口

    public static String self_loan_list=BASE_URL+"app/customer/selfLoanList";//个人借款列表接口
    public static String rpmt_view=BASE_URL+"app/customer/rpmtView";//个人借款详情接口
    public static String alter_customer_info=BASE_URL+"app/customer/alterCustomerInfo";//个人信息修改接口
    public static String update_trade_pwd=BASE_URL+"app/customer/updateTradePwd";//个人交易密码修改接口
    public static String retrieve_trade_pwd=BASE_URL+"/app/customer/retrieveTradePwd";//个人交易密码找回接口

    public static String send_active_email=BASE_URL+"app/customer/sendActiveEmail";//获取邮箱验证码接口
    public static String email_auth=BASE_URL+"app/customer/emailAuth";//邮箱验证接口

    public static String loan_apply=BASE_URL+"app/customer/loanApply";//融资申请接口
    public static String get_area_list=BASE_URL+"app/customer/getAreaList";//获取行政区域接口
    public static String get_loan_apply_list=BASE_URL+"app/customer/getLoanApplyList";//融资申请列表查询接口
    public static String get_loan_apply=BASE_URL+"app/customer/getLoanApply";//融资申请详情查询接口

    public static String get_points_total=BASE_URL+"app/customer/getPointsTotal";//积分总额查询接口
    public static String get_points_list=BASE_URL+"app/customer/getPointsList";//积分列表查询接口

    public static String help_list=BASE_URL+"app/HelpCenter/helplist";//帮助中心接口
    public static String get_question=BASE_URL+"app/HelpCenter/getQuestion";//问题查询

    public static String goods_img=BASE_URL+"app/Goods/Goodsimg";//积分商城
    public static String goods_list=BASE_URL+"app/Goods/Goodslist";//商品查询列表接口
    public static String point_to_redp=BASE_URL+"app/Goods/pointToRedp";//商品兑换

    public static String cash_discount_list=BASE_URL+"app/activity/getCashDiscountList";//投资奖品列表接口
    public static String update_cash_discount=BASE_URL+"app/activity/UpdatecashDiscount";//投资奖品对换

    public static String regUserByFive=BASE_URL+"app/customer/regUserByFive";//开户

    public static String change_hand_lock=BASE_URL+"app/customer/GesturecipherInster";//手势密码新增/修改
    public static String query_hand_lock=BASE_URL+"app/customer/GesturecipherQuery";//手势密码查询


    public static String progress_list=BASE_URL+"app/CompanyIntro/progresslist";//备案详情
    public static String member_list=BASE_URL+"app/CompanyIntro/memberList";//团队高管
    public static String link_list=BASE_URL+"app/CompanyIntro/linkList";//合作伙伴
    public static String run_report=BASE_URL+"app/CompanyIntro/infopubList";//运营报告

    /**
     * 针对新增接口有sign加密的方法
     *
     * Sign md5 加密算法 用户id 和appkey 加密 然后密文与请求url进行加密
     * 例如  a = MD5（custId + appkey）
     * sing = MD5(请求url 加对应的参数 不包括 sign )
     *
     * */

    private static String appkey="HUORONGDAI@20180604%APP";
    public static String getSign(String url,LinkedHashMap<String,Object > params){
        String sign="";
        String custId=(String) XPreferencesUtils.get("custId","");//用户ID
        if(XEmptyUtils.isEmpty(custId)){
            return null;
        }

        sign= XEncryptUtils.MD5(custId+appkey);

        Iterator iter = params.entrySet().iterator();
        String body="";
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String  key = (String) entry.getKey();
            String  value = (String) entry.getValue();

            body=body+key+"="+value+"&";
        }
        body=body.substring(0,body.length()-1);

        sign=url+"?"+body+sign;
        sign= XEncryptUtils.MD5(sign);
        return sign;
    }

}
