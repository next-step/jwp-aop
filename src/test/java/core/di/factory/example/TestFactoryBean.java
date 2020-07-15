package core.di.factory.example;

import core.annotation.Component;
import core.di.beans.factory.FactoryBean;

@Component
public class TestFactoryBean implements FactoryBean<String> {

    @Override
    public String getObject() throws Exception {
        return new String("TestFactoryBean");
    }
}
