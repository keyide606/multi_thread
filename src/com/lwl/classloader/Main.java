package com.lwl.classloader;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author liwenlong
 * @date 2021-05-02 13:49
 */
public class Main {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        testMyClassLoader();
    }

    static void testMyClassLoader() throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        MyClassLoader classLoader = new MyClassLoader("myClassLoader");

        Class<?> simpleObject = classLoader.loadClass("com.lwl.classloader.SimpleObject");
        System.out.println(simpleObject);
        System.out.println(simpleObject.getClassLoader());
        Object o = simpleObject.newInstance();
        Method hello = simpleObject.getDeclaredMethod("hello");
        hello.setAccessible(true);
        hello.invoke(o);

    }

    static void testClassLoader() {
        // 根类加载器bootstrap加载那些类
        System.out.println("根类加载器加载内容");
        Arrays.asList(System.getProperty("sun.boot.class.path").split(";"))
                .stream()
                .forEach(System.out::println);
        System.out.println();
        // 扩展类加载器加载的内容
        System.out.println("扩展类加载器加载内容");
        Arrays.asList(System.getProperty("java.ext.dirs").split(";"))
                .stream()
                .forEach(System.out::println);

        User user = new User();

        System.out.println(user.getClass().getClassLoader());
        System.out.println(user.getClass().getClassLoader().getParent());
        System.out.println(user.getClass().getClassLoader().getParent().getParent());
    }
}
