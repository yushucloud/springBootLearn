package com.yushu.cache.springboot01cache.bean;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee implements Serializable {
    private Integer id;
    private String lastName;
    private String email;
    private Integer gender; //性别 1男  0女
    private Integer dId;


}
