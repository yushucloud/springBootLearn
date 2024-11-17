package com.yushu.exercise20_lists_stacks_queues_and_priority_queues.hsp;

import java.util.HashSet;

@SuppressWarnings({"all"})
public class HashSetDemo {
    public static void main(String[] args) {
        HashSet set = new HashSet();
        set.add( "语言文" );
        set.add( "数学" );
        set.add( "英语" );
        set.add( "历史" );
        /*for (int i = 0; i < 1000000; i++) {
            set.add("数据" + (i + 1));
        }*/
        System.out.println(set);
        /*HashSet<String> set = new HashSet<String>();
        set.add( "语言文" );
        set.add( "数学" );
        set.add( "英语" );
        set.add( "历史" );
        set.add( "政治" );
        set.add( "地理" );
        set.add( "生物" );
        set.add( "化学" );*/
    }
}
