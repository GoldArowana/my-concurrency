package com.king.learn.collection.jdk8.string;

public class AbstractStringBuilderTest {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        sb.append("hello");
        System.out.println(sb.toString()); // hello
        System.out.println("size:" + sb.length()); // 5
        System.out.println("capacity" + sb.capacity()); // 16
        sb.setLength(2);
        System.out.println(sb.toString()); // he
        System.out.println("size:" + sb.length()); // 2
        System.out.println("capacity" + sb.capacity()); // 16
        sb.setLength(20);
        System.out.println(sb.toString()); // he                  \0\0\0\00\0\0\00\0\0\00\0\0\0\0\0
        System.out.println("size:" + sb.length()); // 20
        System.out.println("capacity" + sb.capacity()); // 34
        sb.setLength(77);
        System.out.println("size:" + sb.length()); // 77
        System.out.println("capacity" + sb.capacity()); // 77
    }
}
