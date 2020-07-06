package core.di.beans.factory;

import core.di.beans.factory.aop.Advice;

/**
 * todo: 우선 cglib
 */
public class ProxyFactoryBean implements FactoryBean<Object> {

    private Object target;
    private Advice advice;

    @Override
    public Object getObject() throws Exception {
        return null;
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public void setAdvice(Advice advice) {

    }
}
