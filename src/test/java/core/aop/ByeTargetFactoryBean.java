package core.aop;

import core.di.beans.factory.FactoryBean;

public class ByeTargetFactoryBean implements FactoryBean<ByeTarget> {

    @Override
    public ByeTarget getObject() throws Exception {
        return new ByeTarget();
    }

    @Override
    public Class<ByeTarget> getClassType() {
        return ByeTarget.class;
    }
}
