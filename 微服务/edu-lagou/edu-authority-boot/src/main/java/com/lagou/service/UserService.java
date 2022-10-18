package com.lagou.service;

import com.lagou.entity.UserDTO;

/**
 * @BelongsProject edu-lagou
 * @Author lengy
 * @CreateTime 2022/9/11 20:08
 * @Description
 */
public interface UserService {

    /**
     * 登录
     * @return
     */
    UserDTO login(String phone,String password);


    /**
     * 检验token
     */
    UserDTO checkToken(String token);


    /**
     * 用户注册
     *
     * @param phone    手机号
     * @param password 密码
     * @param nickname 昵称
     * @param headimg 头像
     * @return 受影响的行数
     */
    Integer register( String phone, String password,String nickname,String headimg);


    UserDTO loginPhoneSms(String phoneNumber);
}
