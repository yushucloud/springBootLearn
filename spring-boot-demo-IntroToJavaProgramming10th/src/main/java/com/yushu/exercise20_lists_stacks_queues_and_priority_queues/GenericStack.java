package com.yushu.exercise20_lists_stacks_queues_and_priority_queues;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

class GenericStack<E> {
    public static final int INITIAL_SIZE = 16;
    private E[] elements;
    private int size;

    public GenericStack() {
        this(16);
    }

    public GenericStack(int initialCapacity) {
        this.elements = (E[]) new Object[initialCapacity];
    }

    public E push(E value) {
        if (this.size >= this.elements.length) {
            Object[] temp = new Object[this.elements.length * 2];
            System.arraycopy(this.elements, 0, temp, 0, this.elements.length);
            this.elements = (E[]) temp;
        }

        return this.elements[this.size++] = value;
    }

    public E pop() {
        return this.elements[--this.size];
    }

    public E peek() {
        return this.elements[this.size - 1];
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public int getSize() {
        return this.size;
    }
}
