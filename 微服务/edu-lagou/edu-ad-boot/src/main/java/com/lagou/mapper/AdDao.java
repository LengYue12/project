package com.lagou.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lagou.entity.PromotionAd;
import org.springframework.stereotype.Repository;

/**
 * @BelongsProject edu-lagou
 * @Author lengy
 * @CreateTime 2022/9/11 15:57
 * @Description
 */
@Repository
public interface AdDao extends BaseMapper<PromotionAd> {

//    List<PromotionAd> getAdsBySpaceId(@Param("space_id") Integer sid);
}
