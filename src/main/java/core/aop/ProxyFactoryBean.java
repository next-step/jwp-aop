package core.aop;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import org.springframework.objenesis.ObjenesisHelper;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * @author KingCjy
 */
public class ProxyFactoryBean<T> implements FactoryBean<T> {

    private Object target;
    private Pointcut pointcut;
    private Advice advice;

    private Class<?>[] callbackTypes;
    private Callback[] callbacks;

    public ProxyFactoryBean(Object target, Pointcut pointcut, Advice advice) {
        this.target = target;
        this.pointcut = pointcut;
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
}
