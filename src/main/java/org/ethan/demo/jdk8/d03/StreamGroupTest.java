package org.ethan.demo.jdk8.d03;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StreamGroupTest {
    public static void main(String[] args) {
        Student s1 = new Student("zhangsan", 80, 15);
        Student s2 = new Student("lisi", 90, 13);
        Student s3 = new Student("wangwu", 90, 12);
        Student s4 = new Student("zhangsan", 70, 13);

        List<Student> students = Arrays.asList(s1, s2, s3, s4);

        Map<String, List<Student>> map = students.stream().collect(Collectors.groupingBy(Student::getName));
        System.out.println(map);

        System.out.println("===============");

        Map<Integer, List<Student>> map1 = students.stream().collect(Collectors.groupingBy(Student::getAge));
        System.out.println(map1);

        System.out.println("===============");

        Map<String, Long> map2 = students.stream().collect(Collectors.groupingBy(Student::getName, Collectors.counting()));
        System.out.println(map2);

        System.out.println("=================");

        Map<Integer, Long> map3 = students.stream().collect(Collectors.groupingBy(Student::getAge, Collectors.summingLong(Student::getSorce)));
        System.out.println(map3);


        System.out.println("group by end");


        Map<Boolean, List<Student>> map4 = students.stream().collect(Collectors.partitioningBy(stu -> stu.getSorce() > 80));
        System.out.println(map4);

        System.out.println("================");

        Map<Boolean, Map<Boolean, List<Student>>> map5 = students.stream().collect(Collectors.partitioningBy(stu -> stu.getAge() > 13, Collectors.partitioningBy(stu2 -> stu2.getSorce() > 80)));
        System.out.println(map5);
    }
}

class Student {
    private String name;
    private int sorce;
    private int age;

    public Student(String name, int sorce, int age) {
        this.name = name;
        this.sorce = sorce;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSorce() {
        return sorce;
    }

    public void setSorce(int sorce) {
        this.sorce = sorce;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", sorce=" + sorce +
                ", age=" + age +
                '}';
    }
}