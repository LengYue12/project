package com.lagou.controller;

import com.lagou.entity.PromotionAd;
import com.lagou.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @BelongsProject edu-lagou
 * @Author lengy
 * @CreateTime 2022/9/11 15:55
 * @Description
 */
@RestController
@RequestMapping("/ad")
@CrossOrigin    // 解决跨域
public class AdController {

    @Autowired
    private AdService adService;


    // 根据广告位id查询广告信息
    @GetMapping("/getAdsBySpaceId/{spaceId}")
    public List<PromotionAd> getAdsBySpaceId(@PathVariable("spaceId") Integer sid){
        return adService.getAdsBySpaceId(sid);
    }
}
