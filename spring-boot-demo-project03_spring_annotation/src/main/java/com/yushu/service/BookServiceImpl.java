package com.yushu.service;

import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    @Override
    public void test() {
        System.out.println("图书");
    }
}
