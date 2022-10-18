package com.lagou.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @BelongsProject edu-lagou
 * @Author lengy
 * @CreateTime 2022/9/11 15:55
 * @Description 广告表
 */
@Data
@TableName("promotion_ad")
public class PromotionAd implements Serializable {

    private static final long serialVersionUID = 5863582843330615833L;

    @TableId
    private Integer  id;
    /**
     * 广告名
     */
    private String name;
    private Integer spaceId;
    private String keyword;
    private String htmlContent;
    private String text;
    private String link;
    private Date startTime;
    private Date endTime;
    private Date createTime;
    private Date updateTime;
    private Integer status ;
    private Integer priority;
    private String img;
}
