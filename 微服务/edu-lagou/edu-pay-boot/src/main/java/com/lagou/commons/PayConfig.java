package com.lagou.commons;

/**
 * @BelongsProject lagou-edu-web
 * @Author lengy
 * @CreateTime 2022/8/10 20:54
 * @Description 微信支付商户的配置类
 */
public class PayConfig {
    //企业公众号ID
    public static String appid = "wx8397f8696b538317";
    // 财付通平台的商户帐号
    public static String partner = "1473426802";
    // 财付通平台的商户密钥
    public static String partnerKey = "8A627A4578ACE384017C997F12D68B23";
    // 回调URL
    public static String notifyurl="http://localhost:8006/pay/wxCallback";
}
