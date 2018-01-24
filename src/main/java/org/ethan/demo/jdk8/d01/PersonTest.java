package org.ethan.demo.jdk8.d01;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * @author Ethan Huang
 * @since 2018-01-21 10:24
 */
public class PersonTest {

    public static void main(String[] args) {
        Person p1 = new Person("zhangsan", 20);
        Person p2 = new Person("lisi", 30);
        Person p3 = new Person("wangwu", 40);

        List<Person> list = Arrays.asList(p1, p2, p3);

        PersonTest test = new PersonTest();

        test.getPersonByName("zhangsan", list).forEach(System.out::println);

        System.out.println("============");

        test.getPersonByAge(25, list).forEach(System.out::println);

        System.out.println("============");

        test.getPersonByAge2(25, list, (age, persons) -> persons.stream().filter(p -> p.getAge() > age).collect(Collectors.toList())).forEach(System.out::println);

        System.out.println("============");

        test.getPersonByAge2(25, list, (age, persons) -> persons.stream().filter(p -> p.getAge() <= age).collect(Collectors.toList())).forEach(System.out::println);

    }

    public List<Person> getPersonByName(String username, List<Person> persons) {
        return persons.stream().filter(p -> p.getUsername().equals(username)).collect(Collectors.toList());
    }

    public List<Person> getPersonByAge(int age, List<Person> persons) {
        BiFunction<Integer, List<Person>, List<Person>> biFunction = (ageOfPerson, personList) ->
                personList.stream().filter(p -> p.getAge() > ageOfPerson).collect(Collectors.toList());
        return biFunction.apply(age, persons);
    }

    public List<Person> getPersonByAge2(int age, List<Person> persons, BiFunction<Integer, List<Person>, List<Person>> biFunction) {
        return biFunction.apply(age, persons);
    }
}

class Person {
    private String username;
    private int age;

    public Person(String username, int age) {
        this.username = username;
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "username='" + username + '\'' +
                ", age=" + age +
                '}';
    }
}