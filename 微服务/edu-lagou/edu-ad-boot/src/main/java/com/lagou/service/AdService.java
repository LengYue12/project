package com.lagou.service;

import com.lagou.entity.PromotionAd;

import java.util.List;

/**
 * @BelongsProject edu-lagou
 * @Author lengy
 * @CreateTime 2022/9/11 15:57
 * @Description
 */
public interface AdService {

    List<PromotionAd> getAdsBySpaceId(Integer sid);
}
