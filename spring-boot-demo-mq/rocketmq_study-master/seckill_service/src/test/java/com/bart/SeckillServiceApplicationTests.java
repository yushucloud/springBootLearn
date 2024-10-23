package com.bart;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bart.api.common.Constant;
import com.bart.api.model.Goods;
import com.bart.provider.dao.GoodsMapper;
import com.bart.provider.service.GoodsService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.PartialUpdate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.CollectionUtils;

import java.util.List;

@SpringBootTest
@Slf4j
class SeckillServiceApplicationTests {
	@Autowired
	private StringRedisTemplate redisTemplate;

	@Autowired
	private GoodsService goodsService;

	@Autowired
	private GoodsMapper goodsMapper;

	@Test
	void contextLoads() {
		redisTemplate.opsForValue().set("test", "test");
	}

	@Test
	public void testIService(){
		long count = goodsService.count();
		System.out.println(count);
	}

	@Test
	public void testGetStocks(){
		QueryWrapper<Goods> wrapper = new QueryWrapper<>();
		wrapper.eq(Goods.STATUS, 2).select(Goods.ID, Goods.STOCKS);
		List<Goods> goodsList = goodsService.list(wrapper);
		if (CollectionUtils.isEmpty(goodsList)) {
			return;
		}
		goodsList.forEach(goods -> {
			String s = redisTemplate.opsForValue().get(Constant.GOODS_STOCK + goods.getId());
			System.out.println(s);
		});

	}

}
