package com.yushu.orm.mybatis.plus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yushu.orm.mybatis.plus.entity.Admin;
import com.yushu.orm.mybatis.plus.mapper.AdminMapper;
import com.yushu.orm.mybatis.plus.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 管理员表 服务实现类
 * </p>
 *
 * @author MrWen
 * @since 2021-12-02
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    @Autowired
    private AdminMapper adminMapper;

}
