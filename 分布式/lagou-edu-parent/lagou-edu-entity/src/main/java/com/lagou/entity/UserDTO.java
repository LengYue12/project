package com.lagou.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @BelongsProject lagou-edu
 * @Author lengy
 * @CreateTime 2022/8/5 21:23
 * @Description 视图对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO<T> implements Serializable {

    private static final long serialVersionUID = -6198249331723252223L;
    private Boolean success;    // 是否成功
    private int state;  // 操作状态
    private String message; // 状态描述
    private T content;  // 响应内容
}
