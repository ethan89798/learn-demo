package org.ethan.demo.jvm.ssy.d02;


import java.io.*;

public class CustomizeClassLoader extends ClassLoader {

    private String classLoaderName;
    private String path;
    private String fileExtension = ".class";

    public void setPath(String path) {
        this.path = path;
    }

    public CustomizeClassLoader(String classLoaderName) {
        this.classLoaderName = classLoaderName;
    }

    public CustomizeClassLoader(ClassLoader parent, String classLoaderName) {
        super(parent);
        this.classLoaderName = classLoaderName;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        System.out.println("findClass invoked!" + name);
        System.out.println("classLoader " + classLoaderName);
        byte[] data = this.loadClassData(name);
        return defineClass(name, data, 0, data.length);
    }

    private byte[] loadClassData(String name) {
        InputStream is = null;
        byte[] data = null;
        ByteArrayOutputStream baos = null;
        try {
//            classLoaderName = classLoaderName.replaceAll(".", "/");
            name = name.replace(".", File.separator);
            is = new FileInputStream(new File(this.path + name + this.fileExtension));
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
//        ClassLoader classLoader = new CustomizeClassLoader("My first ClassLoader");
        CustomizeClassLoader loader = new CustomizeClassLoader(ClassLoader.getSystemClassLoader(), "My first ClassLoader");
//        loader.testLoader(loader);

        loader.setPath("/home/ethan/Desktop/");
        Class<?> clazz = loader.loadClass("org.ethan.demo.jvm.ssy.d02.MyTest");
        System.out.println(clazz.hashCode());
        Object object = clazz.newInstance();
        System.out.println(object);
        System.out.println(object.getClass().getClassLoader());

        System.out.println("========");


//        CustomizeClassLoader loader2 = new CustomizeClassLoader(ClassLoader.getSystemClassLoader(), "My first ClassLoader");
        CustomizeClassLoader loader2 = new CustomizeClassLoader(loader, "My first ClassLoader");
//        loader.testLoader(loader);

        loader2.setPath("/home/ethan/Desktop/");
        Class<?> clazz2 = loader2.loadClass("org.ethan.demo.jvm.ssy.d02.MyTest");
        System.out.println(clazz2.hashCode());
        Object object2 = clazz2.newInstance();
        System.out.println(object2);
        System.out.println(object2.getClass().getClassLoader());

        System.out.println("=======");
    }

    public void testLoader(ClassLoader classLoader) throws Exception {
        Class<?> clazz = classLoader.loadClass("org.ethan.demo.jvm.ssy.d02.MyTest");
        Object object = clazz.newInstance();
        System.out.println(object);
        System.out.println(object.getClass().getClassLoader());
    }
}
