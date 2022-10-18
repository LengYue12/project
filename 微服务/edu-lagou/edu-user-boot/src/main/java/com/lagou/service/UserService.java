package com.lagou.service;

/**
 * @BelongsProject edu-lagou
 * @Author lengy
 * @CreateTime 2022/9/14 20:39
 * @Description
 */
public interface UserService {
    /**
     * 修改昵称
     * @param userId    用户编号
     * @param newName 新昵称
     * @param newName 新的头像地址
     */
    void updateUser(Integer userId,String newName,String imgfileId);

    /**
     * 修改密码
     * @param userId    用户编号
     * @param newPwd 新密码
     */
    void updatePassword(Integer userId,String newPwd);
}