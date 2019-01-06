package org.ethan.demo.spring5.d01.conf;

import org.ethan.demo.spring5.d01.bean.Orange;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class CustomImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        boolean b1 = registry.containsBeanDefinition("org.ethan.demo.spring5.d01.bean.Banana");
        boolean b2 = registry.containsBeanDefinition("org.ethan.demo.spring5.d01.bean.Apple");
        if (b1 && b2) {
            RootBeanDefinition beanDefinition = new RootBeanDefinition(Orange.class);
            registry.registerBeanDefinition("orange", beanDefinition);
        }
    }
}
