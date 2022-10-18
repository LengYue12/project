package com.lagou.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.lagou.entity.UserDTO;
import com.lagou.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @BelongsProject edu-lagou
 * @Author lengy
 * @CreateTime 2022/9/11 20:12
 * @Description
 */
@RestController
@RequestMapping("/user")
@CrossOrigin
public class AuthorityController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;


    // 登录
    @GetMapping("/login")
    public UserDTO login(String phone, String password) {
        return userService.login(phone, password);

    }


    // 校验token
    @GetMapping("/checkToken")
    public UserDTO checkToken(String token) {

        return userService.checkToken(token);
    }

    // 登出
    @GetMapping("/logout")
    public void logout(String token) {
        // 将redis中的token删除
        redisTemplate.delete(token);
    }


    @Value("${ali.sms.signName}")
    private String signName;
    @Value("${ali.sms.templateCode}")
    private String TemplateCode;
    @Value("${ali.sms.assessKeyId}")
    private String assessKeyId;
    @Value("${ali.sms.assessKeySecret}")
    private String assessKeySecret;


    // 发送手机验证码
    @GetMapping("sendSms")
    public Object sendSms(String phoneNumber) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", assessKeyId, assessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phoneNumber);
        request.putQueryParameter("SignName", signName);
        request.putQueryParameter("TemplateCode", TemplateCode);
        String vcode = "";
        for (int i = 0; i < 6; i++) {
            vcode = vcode + (int) (Math.random() * 9);
        }
        request.putQueryParameter("TemplateParam", "{\"code\":\"" + vcode + "\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            System.out.println(vcode);
            String jsonStr = response.getData();
            JSONObject jsonObject = JSON.parseObject(jsonStr);
            if ("OK".equals(jsonObject.get("Message"))){
                // 返回手机号和验证码的 JSON给前端
                jsonObject.put("phoneNumber",phoneNumber);
                jsonObject.put("smsCode",vcode);
                System.out.println("验证码 = " + jsonObject);
                return jsonObject;
            }
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 手机验证码登录
    @GetMapping("loginPhoneSms")
    public UserDTO loginPhoneSms(String phoneNumber){

        return userService.loginPhoneSms(phoneNumber);
    }
}
