package core.aop;

import core.di.beans.factory.config.BeanDefinition;
import core.di.beans.factory.support.DefaultBeanDefinition;
import core.di.beans.factory.support.InjectType;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Set;

public class ProxyBeanDefinition implements BeanDefinition {
    private final Class<?> proxyClass;
    private final Class<?> targetClass;
    private final BeanDefinition targetBeanDefinition;

    public ProxyBeanDefinition(Class<?> clazz) {
        this.proxyClass = clazz;
        this.targetClass = (Class<?>) ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments()[0];
        this.targetBeanDefinition = new DefaultBeanDefinition(targetClass);
    }

    @Override
    public Constructor<?> getInjectConstructor() {
        Constructor<?>[] declaredConstructors = proxyClass.getDeclaredConstructors();

        return declaredConstructors[0];
    }

    @Override
    public Set<Field> getInjectFields() {
        return targetBeanDefinition.getInjectFields();
    }

    @Override
    public Class<?> getBeanClass() {
        return targetBeanDefinition.getBeanClass();
    }

    @Override
    public InjectType getResolvedInjectMode() {
        return InjectType.INJECT_CONSTRUCTOR;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public Class<?>[] getTargetConstructorParameterTypes() {
        if (targetBeanDefinition.getInjectConstructor() == null) {
            return new Class<?>[0];
        }

        return targetBeanDefinition.getInjectConstructor().getParameterTypes();
    }

    public BeanDefinition getTargetBeanDefinition() {
        return targetBeanDefinition;
    }
}
