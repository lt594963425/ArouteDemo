package com.example.modulebase.data.constant;

/**
 * $activityName
 * 接口常量
 *
 * @author LiuTao
 * @date 2019/2/20/020
 */


public class NetUrls {
    public static String R_BASE_NET = " http://fy.iciba.com/";
    public static  String BASE_IP = "http://192.168.8.122:97/index.php/";
    //开发服务器（及时更新）
//    private static final String BASE_URL = "http://192.168.8.122:97/index.php";
    //演示服务器（只有发版的时候才会更新）
//    private static final String BASE_URL = "http://222.244.147.35:9797/index.php";
    //4.25号临时测试
    public static final String BASE_URL = "http://192.168.8.122:100/index.php";
    /**
     * 个人注册
     */
    public static final String URL_PERSON_REGISTER = BASE_URL + "/base/Personal/personalRegister";
    /**
     * 获取支付宝授权authinfo
     */
    public static final String URL_ALI_LOGIN_AUTHINFO = BASE_URL + "/base/login/getSign";
    /**
     * 支付宝快捷登陆
     */
    public static final String URL_ALI_LOGIN = BASE_URL + "/base/login/aliLogin";
    /**
     * 快捷登录验证手机号码
     */
    public static final String URL_ALI_CHECK_PHONE = BASE_URL + "/base/login/aliCheckPhone";
    /**
     * 添加用户信息
     */
    public static final String URL_ALI_ADD_USER_INFO = BASE_URL + "/base/login/aliAddUserInfo";
    /**
     * 商户注册
     */
    public static final String URL_STORE_REGISTER = BASE_URL + "/base/store/StoreRegister";
    /**
     * 退出登录
     */
    public static final String URL_LOGIN_OUT = BASE_URL + "/base/common/logOut";
    /**
     * 个人和商户注册获取手机验证码使用此接口
     */
    public static final String URL_REGIST_SEDNSMS_CODE = BASE_URL + "/base/Personal/sendSms";
    /**
     * 登录时获取验证码
     */
    public static final String URL_LOGIN_SEDNSMS_CODE = BASE_URL + "/base/Login/sendSms";
    /**
     * 密码登录
     */
    public static final String URL_LOGIN_PWD = BASE_URL + "/base/Login/pwdLogin";
    /**
     * 短信验证码登录
     */
    public static final String URL_LOGIN_SMS = BASE_URL + "/base/login/login.html";
    /**
     * 忘记密码 （没有登录）
     */
    public static final String URL_FORGETPWD_NOLOGIN = BASE_URL + "/base/common/forgetPwdByNoLogin";
    /**
     * 忘记密码（已经登录）
     */
    public static final String URL_FORGETPWD_LOGIN = BASE_URL + "/base/common/forgetPwd";
    /**
     * 设置提现密码
     */
    public static final String URL_SET_PERSON_WITHDRAW_PWD = BASE_URL + "/base/personal/addWithdrawPass";

    public static final String URL_SET_PERSON_UPDATE_WITHDRAW_PWD = BASE_URL + "/base/personal/updateWithdrawPass";
    /**
     * 注销帐号
     */
    public static final String URL_SET_CANCLER_USER_ACCPUNT = BASE_URL + "/base/common/userCancel";
    /**
     * 用户忘记提现密码而修改提现密码 验证身份信息
     */
    public static final String URL_SET_PERSON_WITHDRAW_PWD_1 = BASE_URL + "/base/personal/forgetWithdrawPass1";
    /**
     * 重新设置提现密码
     */
    public static final String URL_SET_PERSON_WITHDRAW_PWD_2 = BASE_URL + "/base/personal/forgetWithdrawPass2";
    /**
     * 个人用户信息获取
     */
    public static final String URL_PERSON_GET_USER_INFO = BASE_URL + "/base/Personal/getUserInfo";
    /**
     * 修改手机号码（可通过密码·短信）
     */
    public static final String URL_PERSON_UPDETE_CELLPHONE = BASE_URL + "/base/common/updateCellphone";
    /**
     * 修改手机号码(通过身份验证)
     */
    public static final String URL_PERSON_UPDETE_CELLPHONE_BY_IDCARD = BASE_URL + "/base/common/updateCellphoneByIdCard";

    /**
     * 编辑个人信息
     */
    public static final String URL_PERSON_EDIT_USER_INFO = BASE_URL + "/base/Personal/editUserInfo";
    /**
     * 商户获取个人信息
     */
    public static final String URL_BUSINESS_GET_USER_INFO = BASE_URL + "/base/store/getStoreInfo";
    /**
     * 获取我的基本信息
     */
    public static final String URL_MINE_GET_BASE_INFO = BASE_URL + "/base/common/getBaseData";
    /**
     * 商户编辑个人信息
     */
    public static final String URL_BUSINESS_EDIT_USER_INFO = BASE_URL + "/base/store/editStoreInfo";
    /**
     * 添加广告（发布广告）
     */
    public static final String URL_BUSINESS_ADD_ADVERT = BASE_URL + "/base/Advertising/add";
    /**
     * 广告管理列表（广告状态）
     */
    public static final String URL_BUSINESS_GET_ADSTATUSLIST = BASE_URL + "/base/Advertising/getStoreList";
    /**
     * 已删除广告列表
     */
    public static final String URL_BUSINESS_AD_DELETE_LIST = BASE_URL + "/base/Advertising/getStoreDeleteList";
    /**
     * 用户反馈接口
     */
    public static final String URL_USER_ADVICE = BASE_URL + "/base/common/advice";
    /**
     * 用户关注店铺
     */
    public static final String URL_USER_ATTENTION = BASE_URL + "/base/common/attention.html";
    /**
     * 用户取消关注店铺
     */
    public static final String URL_USER_ATTENTION_CANCEL = BASE_URL + "/base/common/cancelAttention.html";
    /**
     * 收藏广告
     */
    public static final String URL_USER_COLLECTION = BASE_URL + "/base/common/userAndAdRelation";
    /**
     * 用户取消收藏广告
     */
    public static final String URL_USER_COLLECTION_CANCEL = BASE_URL + "/base/common/cancelUserAndAdRelation";
    /**
     * 观看广告领取奖励
     */
    public static final String URL_USER_GET_REWARD = BASE_URL + "/base/Advertising/reward";
    /**
     * 查看广告
     */
    public static final String URL_USER_SEE_ADVERT = BASE_URL + "/base/Advertising/view";
    /**
     * 广告类型
     */
    public static final String URL_AD_TYPE = BASE_URL + "/base/common/adType";
    /**
     * 广告列表
     */
    public static final String URL_RM_AD_LIST = BASE_URL + "/base/Advertising/index";
    /**
     * 撤回审核
     */
    public static final String URL_AD_MANAGE_REFUSE = BASE_URL + "/base/Advertising/refuse";
    /**
     * 下架
     */
    public static final String URL_AD_TAKEN_DOWN = BASE_URL + "/base/Advertising/takenDown";
    /**
     * 删除
     */
    public static final String URL_AD_DELETE = BASE_URL + "/base/Advertising/del";
    /**
     * 彻底删除
     */
    public static final String URL_AD_SHEFT_DELETE = BASE_URL + "/base/Advertising/trueDelete";
    /**
     * 一键删除
     */
    public static final String URL_AD_COMPLETE_DELETE = BASE_URL + "/base/Advertising/trueDeleteAll";
    /**
     * 关注列表
     */
    public static final String URL_GET_ATTENTION_LIST = BASE_URL + "/base/common/getAttentionList";
    /**
     * 获取关注我的列表
     */
    public static final String URL_GET_BE_ATTENTION_LIST = BASE_URL + "/base/common/getBeAttentionList";
    /**
     * 根据商户ID获取广告列表
     */
    public static final String URL_GET_STOREADLIST = BASE_URL + "/base/Advertising/getStoreAdList";
    /**
     * 获取用户的广告收藏列表
     */
    public static final String URL_GET_USER_COLLECTION_AD_LIST = BASE_URL + "/base/common/getUserAndAdRelationList";
    /**
     * 获取收藏我的广告列表
     */
    public static final String URL_GET_BE_USER_COLLECTION_AD_LIST = BASE_URL + "/base/common/getBeUserAndAdRelationList";
    /**
     * 获取浏览记录
     */
    public static final String URL_GET_HISTORY_AD_LIST = BASE_URL + "/base/common/getHistoryList";
    /**
     * 删除浏览记录接口
     */
    public static final String URL_DELETE_HISTORY_AD = BASE_URL + "/base/common/deleteHistory";
    /**
     * 图片上传
     */
    public static final String URL_TEMPORARY_UPLOAD_IMG = BASE_URL + "/base/temporary/upload";
    /**
     * 视频上传
     */
    public static final String URL_TEMPORARY_UPLOAD_VEDIO = BASE_URL + "/base/temporary/uploadVideo";
    /**
     * 商户金额明细列表
     */
    public static final String URL_STORE_MONEY_DETAIL_LIST = BASE_URL + "/base/store/storeAccountDetailList";
    /**
     * 商户商户金额明细列表详情
     */
    public static final String URL_STORE_MONEY_DETAIL_CONTENT = BASE_URL + "/base/store/appStoreListsDetailed";
    /**
     * 领奖励列表
     */
    public static final String URL_USER_AD_AWARD_LIST = BASE_URL + "/base/common/getAdAwardList";
    /**
     * 提现详情
     */
    public static final String URL_USER_AD_AWARD_LIST_DETAIL = BASE_URL + "/base/Personal/getWithDrawList";

}
