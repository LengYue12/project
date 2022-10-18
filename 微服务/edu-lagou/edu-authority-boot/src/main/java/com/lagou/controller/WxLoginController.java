package com.lagou.controller;

import com.alibaba.fastjson.JSON;
import com.lagou.entity.EduConstant;
import com.lagou.entity.Token;
import com.lagou.entity.UserDTO;
import com.lagou.entity.WxUser;
import com.lagou.service.UserService;
import com.lagou.tools.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @BelongsProject edu-lagou
 * @Author lengy
 * @CreateTime 2022/9/12 9:48
 * @Description
 */
@RestController
@RequestMapping("user")
@CrossOrigin
public class WxLoginController {

    @Autowired
    private UserService userService;

    // 微信登录
    @GetMapping("wxlogin")
    public UserDTO wxlogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. 微信官方发给我们一个临时凭证
        String code = request.getParameter("code");
        System.out.println("【临时凭证】code = " + code);
        // 2. 通过code，去微信官方申请一个正式的token（令牌）
        String getTokenByCode_url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wxd99431bbff8305a0&secret=60f78681d063590a469f1b297feff3c4&code=" + code + "&grant_type=authorization_code";
        String tokenString = HttpClientUtil.doGet(getTokenByCode_url);
        System.out.println("tokenString = " + tokenString);
        // 将json格式的token字符串转换成实体对象，方便存和取
        Token token = JSON.parseObject(tokenString, Token.class);

        // 3. 通过token，去微信官方获取用户的信息
        String getUserByToken_url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + token.getAccess_token() + "&openid=" + token.getOpenid();
        String userinfoString = HttpClientUtil.doGet(getUserByToken_url);
        System.out.println("userinfoString = " + userinfoString);
        // 将json格式的user字符串转换成实体对象，方便存和取
        WxUser wxUser = JSON.parseObject(userinfoString, WxUser.class);
        System.out.println("微信昵称 = " + wxUser.getNickname());
        System.out.println("微信头像 = " + wxUser.getHeadimgurl());

        // 拉勾的业务流程！ 需要 手机号（wxUser.getUnionid()）和密码（wxUser.getUnionid()）,头像和昵称

        // 检测手机号是否注册
        UserDTO dto = userService.login(wxUser.getUnionid(),wxUser.getUnionid());
        if(dto.getState() == EduConstant.ERROR_NOT_FOUND_PHONE_CODE){
            // 未注册，先注册
            userService.register(wxUser.getUnionid(), wxUser.getUnionid(),wxUser.getNickname(),wxUser.getHeadimgurl());
            // 再登录
            dto = userService.login(wxUser.getUnionid(), wxUser.getUnionid());
        }

        response.sendRedirect("http://localhost:8080/#/?token="+ dto.getToken());

        return dto;
    }
}
