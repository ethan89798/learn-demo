package org.ethan.demo.spring5.d01.conf;

import org.ethan.demo.spring5.d01.bean.Watermelon;
import org.springframework.beans.factory.FactoryBean;

public class CustomFacotryBean implements FactoryBean<Watermelon> {
    @Override
    public Watermelon getObject() throws Exception {
        return new Watermelon();
    }
    @Override
    public Class<?> getObjectType() {
        return Watermelon.class;
    }
}
