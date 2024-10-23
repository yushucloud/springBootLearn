package com.bart.provider.service;

import com.bart.api.common.Result;
import com.bart.api.view.SeckillView;

/**
 * @Author noobBart
 * @Date 2023/7/22/0022 15:20
 */
public interface SeckillService {

    /**
     * 实现秒杀功能
     * @param goodsId
     * @return
     * @throws Exception
     */
    Result<SeckillView> doSeckillGoods(Integer goodsId) throws Exception;
}
