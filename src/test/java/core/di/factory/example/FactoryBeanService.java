package core.di.factory.example;

import core.annotation.Component;
import core.di.beans.factory.support.FactoryBean;

@Component
public class FactoryBeanService implements FactoryBean<FactoryTestService> {

    @Override
    public FactoryTestService getObject() {
        return new FactoryTestService();
    }

    @Override
    public Class<?> getType() {
        return FactoryTestService.class;
    }

}
