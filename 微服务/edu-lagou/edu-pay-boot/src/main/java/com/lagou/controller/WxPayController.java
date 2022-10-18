package com.lagou.controller;

import com.github.wxpay.sdk.WXPayUtil;
import com.jfinal.kit.HttpKit;
import com.lagou.commons.PayConfig;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @BelongsProject lagou-edu-web
 * @Author lengy
 * @CreateTime 2022/8/10 20:52
 * @Description 微信支付相关控制
 */
@RestController
@RequestMapping("pay")
@CrossOrigin
public class WxPayController {

    // 创建二维码
    @GetMapping("createCode")
    public Object createCode(String courseId,String courseName,String price) throws Exception {
        courseName = new String(courseName.getBytes("ISO-8859-1"),"UTF-8");
        // 1.编写商户信息写入到map中
        Map<String,String> map = new HashMap<>();
        map.put("appid", PayConfig.appid);  // 公众号id
        map.put("mch_id",PayConfig.partner);    // 商户号
        map.put("nonce_str",WXPayUtil.generateNonceStr());  // 随机字符串
        map.put("body",courseName); // 商品简单描述
        map.put("out_trade_no",WXPayUtil.generateNonceStr()); // 商户订单号
        map.put("total_fee",price); // 订单金额
        map.put("spbill_create_ip","127.0.0.1");    // 终端ip
        map.put("notify_url",PayConfig.notifyurl);   // 通知地址
        map.put("trade_type","NATIVE");              // 支付类型
//        System.out.println("商户信息" + map);

        // 2.生成数字签名，并把商户信息转换成xml格式
        String xml = WXPayUtil.generateSignedXml(map, PayConfig.partnerKey);
//        System.out.println("商户的xml信息：" + xml);

        // 3.将xml数据发送给微信支付平台，从而生成订单
        String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
        // 发送请求，并返回一个xml格式的字符串
        String result = HttpKit.post(url, xml);
//        System.out.println("返回的xml" + result);
        // 4.微信支付平台返回xml格式数据，将其转换成map格式并返回给前端
        Map<String, String> resultMap = WXPayUtil.xmlToMap(result);
//        System.out.println("返回的xml，转换成map后： " + resultMap);
        resultMap.put("orderId",map.get("out_trade_no"));
        return resultMap;
    }


    // 检查订单状态
    @GetMapping("checkOrderStatus")
    public Object checkOrderStatus(String orderId) throws Exception {
        // 1.编写商户信息
        Map<String,String> map = new HashMap<>();
        map.put("appid", PayConfig.appid);  // 公众号id
        map.put("mch_id",PayConfig.partner);    // 商户号
        map.put("out_trade_no",orderId);  // 商品订单号
        map.put("nonce_str",WXPayUtil.generateNonceStr()); // 随机字符串
        // 2.生成数字签名
        // 2.生成数字签名，并把商户信息转换成xml格式
        String xml = WXPayUtil.generateSignedXml(map, PayConfig.partnerKey);
        // 3.发送查询请求给微信支付平台
        String url = "https://api.mch.weixin.qq.com/pay/orderquery";

        // 查询订单状态的开始时间点
        long beginTime = System.currentTimeMillis();
        // 不停地去微信支付平台询问是否支付成功
        while (true) {
             // 4.对微信支付平台返回的查询结果进行处理
            String result = HttpKit.post(url, xml);
            Map<String, String> resultMap = WXPayUtil.xmlToMap(result);

            // 已经支付成功，不再询问
            if (resultMap.get("trade_state").equalsIgnoreCase("SUCCESS")){
                return resultMap;
            }

            // 超过30秒未支付，停止询问
            if (System.currentTimeMillis()-beginTime > 30000) {
                return resultMap;
            }
            Thread.sleep(3000); // 每隔3秒，询问一次微信支付平台
        }
    }


    @RequestMapping("wxCallback")
    public String wxCallBack(HttpServletRequest request, HttpServletResponse response) throws IOException {
        InputStream inStream = null;
        ByteArrayOutputStream outSteam = null;
        String resultxml = null;
        try {
            inStream = request.getInputStream();
            outSteam = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inStream.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
            }
            resultxml = new String(outSteam.toByteArray(), "utf-8");
        } catch (Exception e) {
            System.out.println("回调处理失败");
        }finally {
            if(null != outSteam) {
                outSteam.close();
            }
            if(null != inStream) {
                inStream.close();
            }
        }
        System.out.println("wxCallback - 回调请求参数:"+ resultxml);
        return resultxml;
    }
}
