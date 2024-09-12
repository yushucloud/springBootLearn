package com.example.service;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        // 创建部门列表
        List<Department> departments = Arrays.asList(
            new Department("1", "HR"),
            new Department("2", "Engineering"),
            new Department("3", "Marketing")
        );

        // 创建员工列表
        List<Employee> employees = Arrays.asList(
            new Employee("1", "Alice", "1"),
            new Employee("2", "Bob", "2"),
            new Employee("3", "Charlie", "2"),
            new Employee("4", "David", "3")
        );

        // 合并处理成一个包含 DepartmentEmployee 对象的集合
        List<DepartmentEmployee> result = departments.stream()
            .flatMap(department -> employees.stream()
                .filter(employee -> employee.getDepartmentId().equals(department.getId()))
                .map(employee -> new DepartmentEmployee(department.getName(), employee.getName())))
            .collect(Collectors.toList());

        // 打印结果
        result.forEach(System.out::println);
    }
}
