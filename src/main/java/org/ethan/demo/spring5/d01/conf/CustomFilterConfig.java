package org.ethan.demo.spring5.d01.conf;

import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;

/**
 * 自定义过滤规则
 */
public class CustomFilterConfig implements TypeFilter {

    private static final String FILTER_STR = "order";

    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {

        // 获取当前类注解的信息
        AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
        System.out.println(annotationMetadata.getAnnotationTypes());

        // 获取当前类资源(类路径)
        Resource resource = metadataReader.getResource();
        System.out.println(resource.getFilename());

        // 获取当前扫描的类信息
        ClassMetadata classMetadata = metadataReader.getClassMetadata();
        String className = classMetadata.getClassName();
        if (className.endsWith(FILTER_STR)) {
            return true;
        }
        return false;
    }
}
