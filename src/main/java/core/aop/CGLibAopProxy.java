package core.aop;

import core.di.beans.factory.BeanFactory;
import core.di.beans.factory.support.BeanFactoryUtils;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CGLibAopProxy implements AopProxy {

    private final Object target;
    private final MethodInterceptor methodInterceptor;
    private final BeanFactory beanFactory;

    public CGLibAopProxy(Object target, AbstractAopAdvisor advisor, BeanFactory beanFactory) {
        this.target = target;
        this.methodInterceptor = methodInterceptor(target, advisor);
        this.beanFactory = beanFactory;
    }

    @Override
    public Object proxy() {
        Class<?> targetClass = target.getClass();
        Constructor<?> constructor = BeanFactoryUtils.getInjectedConstructor(targetClass);

        if (constructor != null) {
            return createTarget(constructor.getParameterTypes(), arguments(constructor.getParameterTypes()));
        }
        return Enhancer.create(targetClass, methodInterceptor);
    }

    private Object createTarget(Class<?>[] parameterTypes, List<Object> arguments) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(methodInterceptor);
        enhancer.setInterfaces(interfaces());
        return enhancer.create(parameterTypes, arguments.toArray());
    }

    private List<Object> arguments(Class<?>[] parameterTypes) {
        List<Object> arguments = new ArrayList<>();
        for (Class<?> parameterType : parameterTypes) {
            Object argument = beanFactory.getBean(parameterType);
            if (argument == null) {
                throw new IllegalArgumentException();
            }
            arguments.add(argument);
        }
        return arguments;
    }

    private MethodInterceptor methodInterceptor(Object target, AbstractAopAdvisor advisor) {
        return (obj, method, args, proxy) -> {
            if (advisor.matches(method, target.getClass())) {
                return advisor.invoke(target, method, args);
            }
            return String.valueOf(proxy.invoke(target, args));
        };
    }

    private Class[] interfaces() {
        Class<?>[] allInterfacesForClass = ClassUtils.getAllInterfacesForClass(target.getClass(), ClassUtils.getDefaultClassLoader());
        return ClassUtils.toClassArray(Arrays.asList(allInterfacesForClass));
    }
}
