package com.yushu.exercise24;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.util.ArrayList;
import java.util.Comparator;

class HeapWithComparator<E> {
    private ArrayList<E> list = new ArrayList();
    private Comparator<? super E> comparator;

    public HeapWithComparator(Comparator<? super E> comparator) {
        this.comparator = comparator;
    }

    public void add(E newObject) {
        this.list.add(newObject);

        int parentIndex;
        for(int currentIndex = this.list.size() - 1; currentIndex > 0; currentIndex = parentIndex) {
            parentIndex = (currentIndex - 1) / 2;
            if (this.comparator.compare(this.list.get(currentIndex), this.list.get(parentIndex)) <= 0) {
                break;
            }

            E temp = this.list.get(currentIndex);
            this.list.set(currentIndex, this.list.get(parentIndex));
            this.list.set(parentIndex, temp);
        }

    }

    public E remove() {
        if (this.list.size() == 0) {
            return null;
        } else {
            E removedObject = this.list.get(0);
            this.list.set(0, this.list.get(this.list.size() - 1));
            this.list.remove(this.list.size() - 1);

            int maxIndex;
            for(int currentIndex = 0; currentIndex < this.list.size(); currentIndex = maxIndex) {
                int leftChildIndex = 2 * currentIndex + 1;
                int rightChildIndex = 2 * currentIndex + 2;
                if (leftChildIndex >= this.list.size()) {
                    break;
                }

                maxIndex = leftChildIndex;
                if (rightChildIndex < this.list.size() && this.comparator.compare(this.list.get(leftChildIndex), this.list.get(rightChildIndex)) < 0) {
                    maxIndex = rightChildIndex;
                }

                if (this.comparator.compare(this.list.get(currentIndex), this.list.get(maxIndex)) >= 0) {
                    break;
                }

                E temp = this.list.get(maxIndex);
                this.list.set(maxIndex, this.list.get(currentIndex));
                this.list.set(currentIndex, temp);
            }

            return removedObject;
        }
    }

    public int getSize() {
        return this.list.size();
    }
}
