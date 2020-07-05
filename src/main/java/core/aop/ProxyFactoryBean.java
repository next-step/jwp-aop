package core.aop;

import core.aop.exception.ProxyBeanCreateException;
import core.di.beans.factory.FactoryBean;
import core.di.beans.factory.config.BeanDefinition;
import core.di.beans.factory.support.InjectType;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class ProxyFactoryBean<T> implements FactoryBean<T> {
    protected BeanDefinition beanDefinition;
    protected Class<?> targetClass;
    protected Object[] targetArguments;
    protected Advice advice;
    protected PointCut pointCut;
    protected BeanInterceptor beanInterceptor;

    public ProxyFactoryBean(BeanDefinition beanDefinition,
                            Object[] targetArguments,
                            Advice advice,
                            PointCut pointCut) {
        this.beanDefinition = beanDefinition;
        this.targetClass = beanDefinition.getBeanClass();
        this.targetArguments = targetArguments;
        this.advice = advice;
        this.pointCut = pointCut;
        this.beanInterceptor = new BeanInterceptor(advice, pointCut);
    }

    @Override
    public T getObject() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(targetClass);

        if (beanDefinition.getResolvedInjectMode() == InjectType.INJECT_FIELD) {
            return createWithFieldInjection(enhancer);
        } else if (beanDefinition.getResolvedInjectMode() == InjectType.INJECT_NO) {
            return createWithDefaultConstructor(enhancer);
        } else if (beanDefinition.getResolvedInjectMode() == InjectType.INJECT_CONSTRUCTOR){
            return createWithConstructor(enhancer);
        }

        throw new ProxyBeanCreateException(targetClass);
    }

    private T createWithFieldInjection(Enhancer enhancer) {
        enhancer.setCallbackType(beanInterceptor.getClass());
        Class classForProxy = enhancer.createClass();
        Enhancer.registerCallbacks(classForProxy, new Callback[] {beanInterceptor});
        Object createdProxy = null;
        try {
            createdProxy = classForProxy.getDeclaredConstructor().newInstance();

            int i = 0;
            for (Field realField : beanDefinition.getInjectFields()) {
                realField.setAccessible(true);
                realField.set(createdProxy, targetArguments[i++]);
            }

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new ProxyBeanCreateException(targetClass, e);
        }

        return (T) createdProxy;
    }

    private T createWithDefaultConstructor(Enhancer enhancer) {
        enhancer.setCallback(new BeanInterceptor(advice, pointCut));

        return (T) enhancer.create(); //생성자를 통한 생성
    }

    private T createWithConstructor(Enhancer enhancer) {
        enhancer.setCallback(new BeanInterceptor(advice, pointCut));

        Class<?>[] argumentTypes = beanDefinition.getInjectConstructor().getParameterTypes();
        return (T) enhancer.create(argumentTypes, targetArguments); // argument type에 맞는 생성자를 찾아 생성
    }

    @Override
    public Class<?> getClassType() {
        return targetClass;
    }
}
