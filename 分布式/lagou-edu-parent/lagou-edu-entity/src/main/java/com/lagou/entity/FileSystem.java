package com.lagou.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @BelongsProject lagou-imageServer
 * @Author lengy
 * @CreateTime 2022/7/30 20:22
 * @Description 上传的文件
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FileSystem implements Serializable {

    private String fileId;
    private String filePath;
    private String fileName;

}
