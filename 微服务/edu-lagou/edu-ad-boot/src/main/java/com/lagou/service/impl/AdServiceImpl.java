package com.lagou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lagou.entity.PromotionAd;
import com.lagou.mapper.AdDao;
import com.lagou.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @BelongsProject edu-lagou
 * @Author lengy
 * @CreateTime 2022/9/11 15:57
 * @Description
 */
@Service
public class AdServiceImpl implements AdService {

    @Autowired
    private AdDao adDao;

    @Override
    public List<PromotionAd> getAdsBySpaceId(Integer sid) {

        QueryWrapper<PromotionAd> queryWrapper = new QueryWrapper<>();  // select * from table
        queryWrapper.eq("space_id",sid); // where space_id = sid

        return adDao.selectList(queryWrapper);
    }
}
