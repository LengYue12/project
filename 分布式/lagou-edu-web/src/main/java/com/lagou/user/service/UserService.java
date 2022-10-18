package com.lagou.user.service;

import com.lagou.entity.User;

/**
 * @BelongsProject lagou-edu-web
 * @Author lengy
 * @CreateTime 2022/8/5 21:03
 * @Description 服务提供方的接口模板
 */
public interface UserService {

    User login(String phone, String password);

    /**
     *  检查手机号是否注册过
     * @param phone 手机号
     * @return 0：未注册，1：已注册
     */
    Integer checkPhone(String phone);

    /**
     * 用户注册
     * @param phone 手机号
     * @param password  密码
     * @param nickName 昵称
     * @param headImg   头像
     * @return 受影响的行数
     */
    Integer register(String phone,String password,String nickName,String headImg);


    /**
     * 更新用户头像与昵称
     * @param portrait  头像新地址
     * @param name      新昵称
     * @param userId    用户id
     * @return          受影响的行数
     */
    Integer updateUserInfo(String portrait,String name,Integer userId);


    /**
     * 修改密码
     * @param userId    用户Id
     * @param password  验证通过的新密码
     * @return
     */
    Integer updatePassword(Integer userId,String password);
}
