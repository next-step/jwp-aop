package core.di.beans.factory.generator;

import core.aop.ProxyBeanDefinition;
import core.di.beans.factory.BeanFactory;
import core.di.beans.factory.FactoryBean;
import core.di.beans.factory.config.BeanDefinition;

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
        Object[] args = null;
        if (proxyBeanDefinition.getAdvice() == null) {
            args = populateArguments(proxyBeanDefinition.getTargetConstructorParameterTypes());
        } else {
            Class<?>[] objects = proxyBeanDefinition.getInjectFields()
                    .stream()
                    .map(Field::getType)
                    .collect(Collectors.toSet()).toArray(new Class<?>[] {});
            args = populateArguments(objects);
        }

        try {
            constructor.setAccessible(true);
            if (proxyBeanDefinition.getAdvice() != null) {
                return constructor.newInstance(
                        proxyBeanDefinition.getTargetBeanDefinition(),
                        args,
                        proxyBeanDefinition.getAdvice(),
                        proxyBeanDefinition.getPointCut()
                );
            } else {
                return constructor.newInstance(proxyBeanDefinition.getTargetBeanDefinition(), args);
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            log.error("Fail to make proxy bean {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
