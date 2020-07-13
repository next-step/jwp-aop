package core.aop;

import core.di.beans.factory.BeanFactory;
import core.di.beans.factory.BeanFactoryAware;
import core.di.beans.factory.BeanFactoryUtils;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import org.springframework.objenesis.ObjenesisHelper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * @author KingCjy
 */
public class ProxyFactoryBean<T> implements FactoryBean<T>, BeanFactoryAware {

    private Object target = new Object();
    private Pointcut pointcut = Pointcut.DEFAULT_POINTCUT;
    private Advice advice = Advice.DEFAULT_ADVICE;
    private BeanFactory beanFactory;

    private Class<?>[] callbackTypes;
    private Callback[] callbacks;

    public void setTarget(Object target) {
        this.target = target;
    }

    public void setPointcut(Pointcut pointcut) {
        this.pointcut = pointcut;
    }

    public void setAdvice(Advice advice) {
        this.advice = advice;
    }

    @Override
    public T getObject() throws Exception {
        initCallbacks();

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallbackTypes(callbackTypes);
        enhancer.setCallbackFilter(new ProxyFilter(pointcut));

        Class<?> proxyClass = enhancer.createClass();
        Enhancer.registerCallbacks(proxyClass, callbacks);

        if(beanFactory != null) {
            T instance = createInstanceFromBeanFactory(enhancer);
            BeanFactoryUtils.findInjectFields(target.getClass()).forEach(field -> BeanFactoryUtils.injectField(beanFactory, instance, field));
            BeanFactoryUtils.findPostConstructMethods(target.getClass()).forEach(method -> BeanFactoryUtils.invokePostConstructor(beanFactory, instance, method));
            return instance;
        }

        Object instance = ObjenesisHelper.newInstance(proxyClass);
        injectProxyFields(instance);
        return (T) instance;
    }

    private void injectProxyFields(Object instance) throws IllegalAccessException {
        for (Field field : target.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            field.set(instance, field.get(target));
        }
    }

    private void initCallbacks() {
        this.callbacks = getCallbacks();
        this.callbackTypes = getCallbackTypes();
    }

    private Class<?>[] getCallbackTypes() {
        return Arrays.stream(this.callbacks)
                .map(Callback::getClass)
                .toArray(Class[]::new);
    }

    private MethodInterceptor[] getCallbacks() {
        return new MethodInterceptor[] {
                new BeanInterceptor(pointcut, advice),
                new CglibAopProxy.DynamicAdvisedInterceptor(target) };
    }

    private T createInstanceFromBeanFactory(Enhancer enhancer) {
        Constructor<?> constructor = BeanFactoryUtils.findInjectController(target.getClass());
        Object[] parameters = BeanFactoryUtils.getParameters(beanFactory, constructor);

        T instance = (T) enhancer.create(constructor.getParameterTypes(), parameters);
        return instance;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }
}
