package core.di.beans.factory;

import core.aop.Advice;
import core.aop.BeanInterceptor;
import core.aop.Pointcut;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

/**
 * @author KingCjy
 */
public class ProxyFactoryBean<T> implements FactoryBean<T> {

    private Class<?> target;
    private Pointcut pointcut;
    private Advice advice;
    private BeanFactory beanFactory;

    public ProxyFactoryBean(Class<?> target, Pointcut pointcut, Advice advice, BeanFactory beanFactory) {
        this.target = target;
        this.pointcut = pointcut;
        this.advice = advice;
        this.beanFactory = beanFactory;
    }

    @Override
    public T getObject() throws Exception {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target);
        enhancer.setCallback(new BeanInterceptor(pointcut, advice));

        T instance = createInstance(enhancer);
        BeanFactoryUtils.findInjectFields(target).forEach(field -> BeanFactoryUtils.injectField(beanFactory, instance, field));
        BeanFactoryUtils.findPostConstructMethods(target).forEach(method -> BeanFactoryUtils.invokePostConstructor(beanFactory, instance, method));

        return instance;
    }

    private T createInstance(Enhancer enhancer) {
        Constructor<?> constructor = BeanFactoryUtils.findInjectController(target);
        Object[] parameters = BeanFactoryUtils.getParameters(beanFactory, constructor);

        T instance = (T) enhancer.create(constructor.getParameterTypes(), parameters);
        return instance;
    }
}
