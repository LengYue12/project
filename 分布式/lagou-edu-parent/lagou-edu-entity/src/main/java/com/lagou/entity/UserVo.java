package com.lagou.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @BelongsProject lagou-edu
 * @Author lengy
 * @CreateTime 2022/8/11 10:47
 * @Description user视图对象
 */
@Data // get和set都全部生成了
@AllArgsConstructor // 生成全参数的构造方法
@NoArgsConstructor  // 生成空构造方法
public class UserVo {

    private Integer userId;
    private String password;
    private String portrait;
    private String name;
}
