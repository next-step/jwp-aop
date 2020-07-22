package core.aop.factorybean;

import core.di.beans.factory.support.BeanFactoryUtils;
import core.di.context.ApplicationContext;
import lombok.RequiredArgsConstructor;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

import java.lang.reflect.Constructor;
import java.util.Arrays;

@RequiredArgsConstructor
public abstract class ProxyFactoryBean implements FactoryBean<Object> {

    private final ApplicationContext applicationContext;
    private final Object target;
    private final Class<?> objectType;

    @Override
    public Object getObject() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(getMethodInterceptor());

        Constructor<?> injectedConstructor = BeanFactoryUtils.getInjectedConstructorWithDefault(target.getClass());

        Class<?>[] parameterTypes = injectedConstructor.getParameterTypes();
        Object[] arguments = findConcreteBeanParametersFrom(parameterTypes);

        return enhancer.create(parameterTypes, arguments);
    }

    @Override
    public Class<?> getObjectType() {
        return objectType;
    }

    protected abstract MethodInterceptor getMethodInterceptor();

    private Object[] findConcreteBeanParametersFrom(Class<?>[] parameterTypes) {
        return Arrays.stream(parameterTypes)
                .map(this::findBeanFromContext)
                .toArray();
    }

    private Object findBeanFromContext(Class<?> beanClass) {
        Object bean = applicationContext.getBean(beanClass);
        if (bean == null) {
            throw new IllegalArgumentException("Bean does not exist in context.");
        }

        return bean;
    }

}
