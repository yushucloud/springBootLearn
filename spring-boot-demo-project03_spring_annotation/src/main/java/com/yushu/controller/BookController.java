package com.yushu.controller;

import com.yushu.service.BookService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {
    @Autowired
    private BookService personServiceImpl;

    public String test() {
        personServiceImpl.test();
        return "";
    }
}
