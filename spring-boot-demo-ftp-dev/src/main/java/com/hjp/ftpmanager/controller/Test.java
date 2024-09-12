package com.hjp.ftpmanager.controller;

import com.hjp.ftpmanager.domain.bean.ResultBean;
import com.hjp.ftpmanager.domain.bo.FileUploadBO;
import org.springframework.web.bind.annotation.*;

/**
 * @program: hjpPersonalTest
 * @description:
 * @author: jumper Hou
 * @create: 2022/9/30 14:02
 **/
@RestController
@RequestMapping("/hjp")
public class Test {

    @PostMapping("/consumer")
    public ResultBean<String> picAndExcelUpload(@RequestBody FileUploadBO fileUploadBo) {
        return ResultBean.success(fileUploadBo.getFileName());
    }
}
