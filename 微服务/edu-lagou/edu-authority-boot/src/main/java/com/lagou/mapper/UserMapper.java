package com.lagou.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lagou.entity.User;
import org.springframework.stereotype.Repository;

/**
 * @BelongsProject edu-lagou
 * @Author lengy
 * @CreateTime 2022/9/11 20:07
 * @Description
 */
@Repository
public interface UserMapper extends BaseMapper<User> {
}
