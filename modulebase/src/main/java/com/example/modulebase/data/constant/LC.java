package com.example.modulebase.data.constant;

/**
 * $activityName
 * 常量
 *
 * @author LiuTao
 * @date 2019/2/20/020
 */


public class LC {
    private LC() {
    }

    /**
     * appid 微信分配的公众账号ID
     */
    public static final String WX_APP_ID = "wxa9a1d65364c753aa";
    /**
     * 微信 API密钥，在商户平台设置
     */
    public static final String WX_APP_KEY = "1a037707f12d7e3e126450b85a74de79";

    /**
     * 用于支付宝支付业务的入参 app_id。
     */
    public static final String APPID = "2019041563948015";

    /**
     * 用于支付宝账户登录授权业务的入参 pid。
     */
    public static final String PID = "2088431971946930";

    /**
     * 用于支付宝账户登录授权业务的入参 target_id。
     * 不重复的一个数即可，可用时间戳。
     */
    public static final String TARGET_ID = "";


    /**
     * SP(用户账号为名)
     */
    public static final String SP_DEFAULT_NAME = "redpacket";
    /**
     * 用户ID
     */
    public static final String USER_ACCOUNT_ID = "USER_ACCOUNT_ID";
    /**
     * 用户账号
     */
    public static final String USER_ACCOUNT_TEL = "USER_ACCOUNT_TEL";
    /**
     * 用户密码
     */
    public static final String USER_ACCOUNT_PWD = "USER_ACCOUNT_PWD";

    /**
     * Crash 日志文件路径
     */
    public static final String CRASH_DIR = "Crash";
    /**
     * 用户类型
     */
    public static String USER_TYPE = "USER_TYPE";
    /**
     * 自定义拍照保存路径
     */
    public static String TAKEPHOTO_PATH = "/PwjImages";
    /**
     * 广告状态传递数据KEY
     */
    public static String ADVERT_STATUS_ID = "ADVERT_STATUS_ID";
    /**
     * 商户明细列表传递ID
     */
    public static String BUSSINESS_GOLD_DETAIL_LIST_ID = "BUSSINESS_GOLD_DETAIL_LIST_ID";
    /**
     * 商户退款状态
     */
    public static String BUSSINESS_GOLD_DETAIL_REFUND_STATUS = "BUSSINESS_GOLD_DETAIL_REFUND_STATUS";
    /**
     * 广告状态
     */
    public static String ADVERT_STATUS = "ADVERT_STATUS";
    /**
     * 行业类型选择Ttag
     */
    public static String UPLOAD_CERTIFICATE = "UPLOAD_CERTIFICATE";
    /**
     * 资质证书上传成功
     */
    public static String ADVERT_SELECTED_INDUSTRY_TYPE = "INDUSTRY_TYPE";
    /**
     * 用户修改昵称
     */
    public static String USER_CHANGE_NICK_NAME = "USER_CHANGE_NICK_NAME";
    /**
     * 修改兴趣
     */
    public static String USER_CHANGE_INTEREST = "USER_CHANGE_INTEREST";
    /**
     * 推广范围选择经纬度
     */
    public static String AD_REGION_PROMOTE_LOCATION = "AD_REGION_PROMOTE_LOCATION";
    /**
     * 地址
     */
    public static String AD_REGION_PROMOTE_LOCATION_ADDRESS = "AD_REGION_PROMOTE_LOCATION_ADDRESS";

    /**
     * 推广范围选择公里值
     */
    public static String AD_REGION_PROMOTE_LOCATION_RADIUS = "AD_REGION_PROMOTE_LOCATION_RADIUS";
    /**
     * 广告预览
     */
    public static String AD_CONTEXT_PREVIEW = "AD_CONTEXT_PREVIEW";
    /**
     * 广告修改
     */
    public static String AD_CONTEXT_UPDATE = "AD_CONTEXT_UPDATE";
    /**
     * 广告编辑
     */
    public static String AD_CONTEXT_UPDATE_EDIT = "AD_CONTEXT_UPDATE_EDIT";
    /**
     * 位置选择
     */
    public static String USER_POSITION_HISTORY = "USER_POSITION_HISTORY";
    /**
     * 首页用户位置选择
     */
    public static String USER_POSITION_SELECTED_LOCATION = "USER_POSITION_SELECTED_LOCATION";
    /**
     * 操作成功
     */
    public static String OPERATE_SUCCESS = "OPERATE_SUCCESS";

}
