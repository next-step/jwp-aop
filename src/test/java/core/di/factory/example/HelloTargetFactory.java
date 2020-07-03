package core.di.factory.example;

import core.annotation.Component;
import core.di.beans.factory.support.FactoryBean;
import study.proxy.HelloTarget;

@Component
public class HelloTargetFactory implements FactoryBean<HelloTarget> {

    @Override
    public HelloTarget getObject() {
        return new HelloTarget();
    }

    @Override
    public Class<?> getObjectType() {
        return HelloTarget.class;
    }

}
