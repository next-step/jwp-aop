package core.di.beans.factory.generator;

import core.aop.ProxyBeanDefinition;
import core.di.beans.factory.BeanFactory;
import core.di.beans.factory.FactoryBean;
import core.di.beans.factory.config.BeanDefinition;
import core.di.beans.factory.support.InjectType;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.stream.Collectors;

public class ProxyBeanGenerator extends AbstractBeanGenerator {

    public ProxyBeanGenerator(BeanFactory beanFactory) {
        super(beanFactory);
    }

    @Override
    public boolean support(BeanDefinition beanDefinition) {
        return beanDefinition instanceof ProxyBeanDefinition;
    }

    @Override
    public <T> T generate(Class<T> clazz, BeanDefinition beanDefinition) {
        FactoryBean proxyBean = (FactoryBean) createProxyBean(beanDefinition);
        Object targetBean = proxyBean.getObject();

        initialize(targetBean, clazz);
        beanFactory.putBean(clazz, targetBean);

        return (T) targetBean;
    }

    private Object createProxyBean(BeanDefinition beanDefinition) {
        ProxyBeanDefinition proxyBeanDefinition = (ProxyBeanDefinition) beanDefinition;
        Constructor<?> constructor = proxyBeanDefinition.getInjectConstructor();
        Object[] args = populateArguments(getArgumentTypes(((ProxyBeanDefinition) beanDefinition).getTargetBeanDefinition()));

        try {
            constructor.setAccessible(true);
            return constructor.newInstance(
                    proxyBeanDefinition.getTargetBeanDefinition(),
                    args,
                    proxyBeanDefinition.getAdvice(),
                    proxyBeanDefinition.getPointCut()
            );
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            log.error("Fail to make proxy bean {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private Class<?>[] getArgumentTypes(BeanDefinition beanDefinition) {
        if (beanDefinition.getResolvedInjectMode() == InjectType.INJECT_NO) {
            return new Class<?>[] {};
        } else if (beanDefinition.getResolvedInjectMode() == InjectType.INJECT_FIELD) {
            return beanDefinition.getInjectFields()
                    .stream()
                    .map(Field::getType)
                    .collect(Collectors.toSet()).toArray(new Class<?>[] {});
        } else {
            return beanDefinition.getInjectConstructor()
                    .getParameterTypes();
        }
    }
}
