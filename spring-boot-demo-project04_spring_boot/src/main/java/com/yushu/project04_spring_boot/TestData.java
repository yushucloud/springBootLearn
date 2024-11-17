package com.yushu.project04_spring_boot;

import java.util.HashSet;

public class TestData {
    public static void main(String[] args) {
        int l = 100000;
        test1(l);
        test2(l);
    }

    public static void test1(int l) {

        int[] arr = new int[l];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i + 1;
        }
        long start = System.currentTimeMillis();
        for (int j = 1; j <= l; j++) {
            int temp = j;
            for (int i = 0; i < arr.length; i++) {
                if (temp == arr[i]) {
                    break;
                }
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("time： " + (end - start)); //time： 823
    }


    public static void test2(int l) {
        HashSet<Integer> set = new HashSet<>(l);
        for (int i = 0; i < l; i++) {
            set.add(i + 1);
        }
        long start = System.currentTimeMillis();
        for (int j = 1; j <= l; j++) {
            int temp = j;
            boolean contains = set.contains(temp);

        }
        long end = System.currentTimeMillis();
        System.out.println("time： " + (end - start)); //time： 5
    }
}
