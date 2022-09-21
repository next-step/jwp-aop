package core.di.beans.factory.aop.advisor;

import core.di.beans.factory.support.BeanFactoryUtils;

import java.lang.reflect.Constructor;

public class Target {

    private final Object target;
    private final Class<?> type;

    public Target(Object target, Class<?> type) {
        this.target = target;
        this.type = type;
    }

    public Class<?> type() {
        return type;
    }

    public Object target() {
        return target;
    }

    public Class<?>[] parameterTypes() {
        Constructor<?> injectedConstructor = BeanFactoryUtils.getInjectedConstructor(type);

        return injectedConstructor.getParameterTypes();
    }
}
