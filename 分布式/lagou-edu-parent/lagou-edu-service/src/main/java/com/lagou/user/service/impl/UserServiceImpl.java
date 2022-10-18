package com.lagou.user.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.lagou.entity.User;
import com.lagou.user.service.UserService;
import mapper.UserDao;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2022-08-05 18:25:07
 */
@Service    // 暴露服务：让消费者能够找到我
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User login(String phone, String password) {
        return userDao.login(phone,password);
    }

    @Override
    public Integer checkPhone(String phone) {
        return userDao.checkPhone(phone);
    }

    @Override
    public Integer register(String phone, String password,String nickName,String headImg) {
        return userDao.register(phone,password,nickName,headImg);
    }

    @Override
    public Integer updateUserInfo(String portrait, String name, Integer userId) {
        return userDao.updateUserInfo(portrait,name,userId);
    }

    @Override
    public Integer updatePassword(Integer userId, String password) {
        return userDao.updatePassword(userId,password);
    }
}
