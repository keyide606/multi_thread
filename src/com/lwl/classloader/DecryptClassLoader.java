package com.lwl.classloader;


/**
 * @author liwenlong volatile 只能保证内存可见性和禁止指令重排序,不能保证原子性
 * @date 2021-05-02 15:16
 */
public class DecryptClassLoader extends ClassLoader {


    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        return null;
    }
}
