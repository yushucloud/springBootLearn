package com.yushu.orm.mybatis.plus.controller;


import com.yushu.orm.mybatis.plus.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 管理员表 前端控制器
 * </p>
 *
 * @author MrWen
 * @since 2021-12-02
 */
@RestController
@RequestMapping("/admin")
//@Api(tags = "管理员表")
public class AdminController {

    @Autowired
    private IAdminService adminService;


}
