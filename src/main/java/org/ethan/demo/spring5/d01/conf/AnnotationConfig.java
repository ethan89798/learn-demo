package org.ethan.demo.spring5.d01.conf;

import org.ethan.demo.spring5.d01.bean.Apple;
import org.ethan.demo.spring5.d01.bean.Person;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Controller;

/**
 * 相当于配置文件
 *
 * excludeFilters = {
 *         @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class})
 * }, 表示排除@Controller注解的类
 * excludeFilters = {
 *         @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {Person.class})
 * }, 表示排队Person类
 * includeFilters = {
 *         @ComponentScan.Filter(type = FilterType.CUSTOM, classes = {CustomFilterConfig.class})
 * }, 表示使用自定义类来进行过滤.
 * 当然也可以使用定义过滤类,要继承TypeFilter
 *  使用includeFilters时, 一定要将useDefaultFilters=ture
 */
@Configuration
@ComponentScan(value = "org.ethan.demo.spring5.d01", excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class}),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {Person.class})
})
// 进行自定义过滤
@ComponentScan(value = "org.ethan.demo.spring5.d01", useDefaultFilters = false, includeFilters = {
        @ComponentScan.Filter(type = FilterType.CUSTOM, classes = {CustomFilterConfig.class})
})
@Import({Apple.class, CustomImportSelector.class, CustomImportBeanDefinitionRegistrar.class})
public class AnnotationConfig {

    //@Bean注解可以指定名字,也可以指定初始化方法和销毁方法
//    @Conditional(CustomCondtion.class)
    @Lazy
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    @Bean
    public Person person() {
        return new Person("ethan", 29);
    }

    @Bean
    public CustomFacotryBean customFacotryBean() {
        return new CustomFacotryBean();
    }
}
