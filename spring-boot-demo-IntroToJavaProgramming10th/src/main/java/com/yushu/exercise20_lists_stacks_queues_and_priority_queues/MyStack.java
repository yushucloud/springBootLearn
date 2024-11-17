package com.yushu.exercise20_lists_stacks_queues_and_priority_queues;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.util.ArrayList;

class MyStack extends ArrayList<Object> {
    MyStack() {
    }

    public boolean isEmpty() {
        return super.isEmpty();
    }

    public int getSize() {
        return this.size();
    }

    public Object peek() {
        return this.get(this.getSize() - 1);
    }

    public Object pop() {
        return this.remove(this.getSize() - 1);
    }

    public Object push(Object o) {
        this.add(o);
        return o;
    }

    public int search(Object o) {
        return this.indexOf(o);
    }

    public String toString() {
        return "stack: " + this.toString();
    }
}
