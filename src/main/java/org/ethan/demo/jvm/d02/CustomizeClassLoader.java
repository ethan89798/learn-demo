package org.ethan.demo.jvm.d02;


import java.io.*;

public class CustomizeClassLoader extends ClassLoader {

    private String classLoaderName;
    private String fileExtension = ".class";
    public CustomizeClassLoader(String classLoaderName) {
        this.classLoaderName = classLoaderName;
    }

    public CustomizeClassLoader(ClassLoader parent, String classLoaderName) {
        super(parent);
        this.classLoaderName = classLoaderName;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] data = this.loadClassData(name);
        return defineClass(name, data, 0, data.length);
    }

    private byte[] loadClassData(String name) {
        InputStream is = null;
        byte[] data = null;
        ByteArrayOutputStream baos = null;
        try {
//            classLoaderName = classLoaderName.replaceAll(".", "/");
            is = new FileInputStream(new File(name + this.fileExtension));
            baos = new ByteArrayOutputStream();
            int length = 0;
            while (-1 != (length = is.read())) {
                baos.write(length);
            }
            data = baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return data;
    }

    @Override
    public String toString() {
        return "CustomizeClassLoader{" +
                "classLoaderName='" + classLoaderName + '\'' +
                ", fileExtension='" + fileExtension + '\'' +
                '}';
    }

    public static void main(String[] args) throws Exception {
        ClassLoader classLoader = new CustomizeClassLoader("My first ClassLoader");
        System.out.println(classLoader);
        Class<?> clazz = classLoader.loadClass("org.ethan.demo.jvm.d02.MyTest");
        System.out.println("==========");
        Object object = clazz.newInstance();
        System.out.println(object);
    }
}
