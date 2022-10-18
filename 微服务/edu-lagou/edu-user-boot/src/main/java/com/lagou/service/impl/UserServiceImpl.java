package com.lagou.service.impl;

import com.lagou.entity.User;
import com.lagou.mapper.UserMapper;
import com.lagou.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @BelongsProject edu-lagou
 * @Author lengy
 * @CreateTime 2022/9/14 20:46
 * @Description
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    // 根据id修改昵称和头像
    @Override
    public void updateUser(Integer userId, String newName, String imgfileId) {
        User user = new User();
        user.setId(userId);
        user.setName(newName);
        user.setPortrait(imgfileId);

        userMapper.updateById(user);
    }

    // 根据id修改密码
    @Override
    public void updatePassword(Integer userId, String newPwd) {
        User user = new User();
        user.setId(userId);
        user.setPassword(newPwd);

        userMapper.updateById(user);

    }
}
