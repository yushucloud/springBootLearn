package com.yushu.cache.springboot01cache.service;


import com.yushu.cache.springboot01cache.bean.Employee;
import com.yushu.cache.springboot01cache.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

@CacheConfig(cacheNames = "emp"/*,cacheManager = "employeeCacheManager"*/) //抽取缓存的公共配置
@Service
public class EmployeeService {

    @Autowired
    EmployeeMapper employeeMapper;

    @Cacheable(cacheNames = "emp",key = "#root.args[0]",keyGenerator = "myKeyGenerator")
    public Employee getEmp(Integer id) {
        System.out.println("查询" + id + "号员工");
        Employee emp = employeeMapper.getEmpById(id);
        return emp;
    }

    @CachePut(/*value = "emp",*/key = "#result.id")
    public Employee updateEmp(Employee employee) {
        System.out.println("updateEmp:" + employee);
        employeeMapper.updateEmp(employee);
        return employee;
    }

    @CacheEvict(value = "emp", beforeInvocation = true/*key = "#id",*/)
    public void deleteEmp(Integer id) {
        System.out.println("deleteEmp:" + id);
        //employeeMapper.deleteEmpById(id);
        int i = 10 / 0;
    }

    public Employee getEmpByLastName(String lastName) {
        return employeeMapper.getEmpByLastName(lastName);
    }


}
