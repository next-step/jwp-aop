package core.di.beans.factory.support.proxy.example;

import core.di.beans.factory.proxy.FactoryBean;
import study.proxy.example.Hello;

public class HelloFactoryBean implements FactoryBean<Hello> {
    @Override
    public Hello getObject() throws Exception {
        return null;
    }
}
