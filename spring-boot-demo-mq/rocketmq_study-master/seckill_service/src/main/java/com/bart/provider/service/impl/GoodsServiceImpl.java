package com.bart.provider.service.impl;

import com.bart.api.model.Goods;
import com.bart.provider.dao.GoodsMapper;
import com.bart.provider.service.GoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author bart
 * @since 2023年07月16日
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {

}
