package com.bart.api.common;

import java.io.Serializable;

/**
 * @Author noobBart
 * @Date 2023/7/16/0016 12:31
 */
public interface BaseEntity extends Serializable {
    Long getId();

    BaseEntity setId(Long var1);

//    default void randomId() {
//        this.setId(RandomIDSequence.Instance().nextId());
//    }

//    default void randomId() {
//        this.setId(RandomIDSequence.Instance().nextId());
//    }
}
