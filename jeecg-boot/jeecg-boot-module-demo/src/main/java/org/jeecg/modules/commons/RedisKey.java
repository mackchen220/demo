package org.jeecg.modules.commons;

public class RedisKey {

    /**
     * redis分布式锁
     */
    public final static String LOCK_KEY = "str_lock";

    //分割
    public final static String KEY_SPLIT=":";

    public final static String WEIXIN_USER_INFO="w_user_info:";

    public final static String SMS_CONFIG="sms_config";

    public final static String SMS_CODE="sms_code:";

    //同一手机号短信发送次数缓存
    public final static String SMS_SEND_TIMES="sms_send_times:";

    //同一ip短信发送次数缓存
    public final static String SMS_SEND_ip_TIMES="send_ip_times:";

    public final static String PARTY_NUM="party_num:";

    public final static String USER_LOGIN_CODE_KEY = "login_code";


    public final static String USER_LOGIN_TOKEN = "login_token";

    public final static String WATCH_NUM="watch_num:";

    public final static String VIP_NUM = "vip_num";

}
