package com.yushu.service;

import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements BookService{

    @Override
    public void test() {

        System.out.println("人");
    }
}
