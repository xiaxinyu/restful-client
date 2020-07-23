package com.xiaxinyu.restful.client;

import java.lang.reflect.Field;

public class Test {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        String t = String.valueOf("summer") + String.valueOf("winner");
        System.out.println(t);


        char value[] = new char[6];
        value[0] = 's';
        value[1] = 's';
        value[2] = 's';
        value[3] = 's';
        value[4] = 's';
        value[5] = 's';

        String cat = new String("cat");
        System.out.println(cat.toCharArray());

        Field field = cat.getClass().getDeclaredField("value");
        field.setAccessible(true);
        field.set(cat, value);
        System.out.println(cat.toCharArray());


    }
}
