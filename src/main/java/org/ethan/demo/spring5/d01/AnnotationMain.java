package org.ethan.demo.spring5.d01;

import org.ethan.demo.spring5.d01.bean.Person;
import org.ethan.demo.spring5.d01.conf.AnnotationConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 使用注解来配置
 */
public class AnnotationMain {

    public static void main(String[] args) {
//        ApplicationContext context1 = new ClassPathXmlApplicationContext("beans.xml");
        // 把类加载到容器中
        ApplicationContext context = new AnnotationConfigApplicationContext(AnnotationConfig.class);

        for (String s : context.getBeanDefinitionNames()) {
            System.out.println(s);
        }

        Object customFacotryBean = context.getBean("customFacotryBean");
        System.out.println(customFacotryBean.getClass());

        // 从容器中获取实例
        Person person = (Person) context.getBean("person");
        System.out.println(person);
    }
}
