package com.lagou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lagou.entity.EduConstant;
import com.lagou.entity.User;
import com.lagou.entity.UserDTO;
import com.lagou.mapper.UserMapper;
import com.lagou.service.UserService;
import com.lagou.tools.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @BelongsProject edu-lagou
 * @Author lengy
 * @CreateTime 2022/9/11 20:10
 * @Description
 */
@Service
public class UserServiceImpl implements UserService {

    private UserDTO userDTO;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    @Override
    public UserDTO login(String phone, String password) {

        userDTO = new UserDTO();

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone",phone);
        Integer integer = userMapper.selectCount(queryWrapper); // select count(*) from user where phone = xx
        if (integer == 0) { // 手机号不存在
            userDTO.setState(EduConstant.ERROR_NOT_FOUND_PHONE_CODE);
            userDTO.setMessage(EduConstant.ERROR_NOT_FOUND_PHONE);
        }else {
            queryWrapper.eq("password",password);
            User user = userMapper.selectOne(queryWrapper); // select * from user where phone = x and password = y
            if (user == null) { // 账号密码错误
                userDTO.setState(EduConstant.ERROR_PASSWORD_CODE);
                userDTO.setMessage(EduConstant.ERROR_PASSWORD);
            }else { // 登录成功
                userDTO.setState(EduConstant.LOGIN_SUCCESS_CODE);
                userDTO.setMessage(EduConstant.LOGIN_SUCCESS);
                // 生成token
                String token = JwtUtil.createToken(user);
                // 将token保存到redis中，并设置过期时间
                redisTemplate.opsForValue().set(token,token,600, TimeUnit.SECONDS);
                userDTO.setToken(token);
            }
        }

        return userDTO;
    }

    /**
     * 校验令牌
     * @param token
     * @return
     */
    @Override
    public UserDTO checkToken(String token) {
        userDTO = new UserDTO();

        int verify = JwtUtil.isVerify(token);
        if (verify == 0){   // 令牌有效
            userDTO.setState(EduConstant.TOKEN_SUCCESS_CODE);
            userDTO.setMessage(EduConstant.TOKEN_SUCCESS);
            // token校验通过，重新设置redis的生命周期
            // 将token保存到redis中，并设置过期时间
            redisTemplate.opsForValue().set(token,token,600, TimeUnit.SECONDS);
        }else if (verify == 1){ // 令牌过期
            userDTO.setState(EduConstant.TOKEN_TIMEOUT_CDOE);
            userDTO.setMessage(EduConstant.TOKEN_TIMEOUT);
        } else if (verify == 2) { // 令牌格式错误
            userDTO.setState(EduConstant.TOKEN_NULL_CODE);
            userDTO.setMessage(EduConstant.TOKEN_ERROR1);
        } else {
            userDTO.setState(EduConstant.TOKEN_ERROR_CDOE);
            userDTO.setMessage(EduConstant.TOKEN_ERROR2);
        }

        return userDTO;
    }

    @Override
    public Integer register(String phone, String password, String nickname, String headimg) {
        User user = new User();
        user.setPhone(phone);
        user.setPassword(password);
        user.setName(nickname);
        user.setPortrait(headimg);

        return userMapper.insert(user);
    }

    @Override
    public UserDTO loginPhoneSms(String phoneNumber) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("phone",phoneNumber);
        User user = userMapper.selectOne(wrapper);

        if (user == null){  // 手机号不存在
            // 先注册
            register(phoneNumber,phoneNumber,"手机新用户","xx");
            return loginPhoneSms(phoneNumber);
        }
        // 创建token
        String token = JwtUtil.createToken(user);
        // 封装dto
        UserDTO dto = new UserDTO();
        dto.setState(EduConstant.LOGIN_SUCCESS_CODE);
        dto.setMessage(EduConstant.LOGIN_SUCCESS);
        dto.setToken(token);
        return dto;
    }
}
