package com.yushu.service;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Employee {
    private String id;
    private String name;
    private String departmentId;

    // 构造函数、getter 和 setter
}
