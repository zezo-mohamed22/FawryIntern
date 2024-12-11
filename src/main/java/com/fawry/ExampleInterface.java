package com.fawry;

@FunctionalInterface
public interface ExampleInterface {
    void exampleMethod(String msg);

    default void defautMethod(String msg){
        System.out.println("msg");
    }

    static void staticMethod(String msg) {
        System.out.println(msg);
    }
}
