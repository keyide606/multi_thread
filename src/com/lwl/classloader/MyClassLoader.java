package com.lwl.classloader;


import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

import java.io.*;

/**
 * @author liwenlong
 * @date 2021-05-02 14:11
 */
public class MyClassLoader extends ClassLoader {

    private final static String DEFAULT_DIR = "D:/MyFile/classloader";

    private String dir = DEFAULT_DIR;

    private String classLoaderName;

    public MyClassLoader(String classLoaderName) {

        this.classLoaderName = classLoaderName;
    }

    public MyClassLoader(ClassLoader parent, String classLoaderName) {
        super(parent);
        this.classLoaderName = classLoaderName;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getClassLoaderName() {
        return classLoaderName;
    }

    public void setClassLoaderName(String classLoaderName) {
        this.classLoaderName = classLoaderName;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String classPath = name.replace('.', '/');
        File file = new File(dir, classPath + ".class");
        if (!file.exists()) {
            throw new ClassNotFoundException("文件没找到");
        }
        byte[] classBytes = loadClassBytes(file);
        if (classBytes == null || classBytes.length == 0) {
            throw new ClassNotFoundException("文件没找到");
        }

        return defineClass(name, classBytes, 0, classBytes.length);
    }

    private byte[] loadClassBytes(File file) {

        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             FileInputStream fileInputStream = new FileInputStream(file)) {
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = fileInputStream.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
