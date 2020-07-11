package core.di.beans.factory;

import core.aop.Advice;
import core.aop.BeanInterceptor;
import core.aop.Pointcut;
import net.sf.cglib.proxy.Enhancer;

import java.lang.reflect.Constructor;

/**
 * @author KingCjy
 */
public class ProxyFactoryBean<T> implements FactoryBean<T>, BeanFactoryAware {

    private Class<?> target;
    private Pointcut pointcut;
    private Advice advice;
    private BeanFactory beanFactory;

    public ProxyFactoryBean(Class<?> target, Pointcut pointcut, Advice advice) {
        this.target = target;
        this.pointcut = pointcut;
        this.advice = advice;
    }

    @Override
    public T getObject() throws Exception {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target);
        enhancer.setCallback(new BeanInterceptor(pointcut, advice));

        if(beanFactory != null) {
            T instance = createInstanceFromBeanFactory(enhancer);
            BeanFactoryUtils.findInjectFields(target).forEach(field -> BeanFactoryUtils.injectField(beanFactory, instance, field));
            BeanFactoryUtils.findPostConstructMethods(target).forEach(method -> BeanFactoryUtils.invokePostConstructor(beanFactory, instance, method));
            return instance;
        }

        return (T) enhancer.create();
    }

    private T createInstanceFromBeanFactory(Enhancer enhancer) {
        Constructor<?> constructor = BeanFactoryUtils.findInjectController(target);
        Object[] parameters = BeanFactoryUtils.getParameters(beanFactory, constructor);

        T instance = (T) enhancer.create(constructor.getParameterTypes(), parameters);
        return instance;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }
}
