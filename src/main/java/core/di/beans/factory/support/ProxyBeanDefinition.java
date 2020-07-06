package core.di.beans.factory.support;

import core.di.beans.factory.ProxyFactoryBean;
import core.di.beans.factory.config.BeanDefinition;
import core.di.beans.factory.support.InjectType;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Set;

public class ProxyBeanDefinition implements BeanDefinition {

    private final ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();

    public ProxyBeanDefinition(Class<?> target) {
        final Object targetInstance = BeanUtils.instantiateClass(target);
        proxyFactoryBean.setTarget(targetInstance);
    }

    @Override
    public Constructor<?> getInjectConstructor() {
        return null;
    }

    @Override
    public Set<Field> getInjectFields() {
        return null;
    }

    @Override
    public Class<?> getBeanClass() {
        return null;
    }

    @Override
    public InjectType getResolvedInjectMode() {
        return null;
    }
}
