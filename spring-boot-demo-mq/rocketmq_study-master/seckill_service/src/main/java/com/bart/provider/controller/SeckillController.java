package com.bart.provider.controller;

import cn.hutool.core.util.StrUtil;
import com.bart.api.common.Result;
import com.bart.api.view.SeckillView;
import com.bart.provider.service.SeckillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户参与商品秒杀入口
 *
 * @Author noobBart
 * @Date 2023/7/16/0016 12:09
 */
@RestController
@RequestMapping("/seckill")
@Slf4j
public class SeckillController {
    @Autowired
    private SeckillService seckillService;

    @PostMapping("doSeckill")
    public Result<SeckillView> doSeckill(Integer goodsId) {
        try {
            return seckillService.doSeckillGoods(goodsId);
        } catch (Exception e) {
            log.error(StrUtil.format("秒杀失败！错误信息：{}", e.getMessage()));
            return Result.failed(e.getMessage());
        }
    }
}
