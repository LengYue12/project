package com.lagou.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @BelongsProject edu-lagou
 * @Author lengy
 * @CreateTime 2022/9/14 20:41
 * @Description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FileSystem {
    private String fileId;
    private String filePath;
    private String fileName;
}