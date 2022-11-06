package core.aop;

import core.di.beans.factory.FactoryBean;
import study.cglib.mission1.HelloTarget;

public class HelloTargetFactoryBean implements FactoryBean<HelloTarget> {

    @Override
    public HelloTarget getObject() throws Exception {
        return new HelloTarget();
    }

    @Override
    public Class<HelloTarget> getClassType() {
        return HelloTarget.class;
    }
}
