package core.aop.framework;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import core.aop.Advice;
import core.di.beans.factory.BeanFactory;
import core.di.beans.factory.support.BeanFactoryUtils;

public class CglibAopProxy implements AopProxy {

    private final AdvisedSupport advised;

    public CglibAopProxy(AdvisedSupport config) {
        this.advised = config;
    }

    @Override
    public Object getProxy() {
        Class<?> targetClass = this.advised.getTargetClass();

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(targetClass);
        enhancer.setInterfaces(this.advised.getProxiedInterfaces());
        enhancer.setCallback(new DynamicAdvisedInterceptor(advised));

        Constructor<?> constructor = BeanFactoryUtils.getInjectedConstructor(targetClass);
        if (constructor != null) {
            BeanFactory beanFactory = this.advised.getBeanFactory();
            Class<?>[] parameterTypes = constructor.getParameterTypes();

            List<Object> arguments = new ArrayList<>();
            for (Class<?> parameterType : parameterTypes) {
                Object argument = beanFactory.getBean(parameterType);
                if (argument == null) {
                    throw new IllegalArgumentException();
                }
                arguments.add(argument);
            }

            return enhancer.create(parameterTypes, arguments.toArray());
        }

        return enhancer.create();
    }

    private static class DynamicAdvisedInterceptor implements MethodInterceptor {

        private final AdvisedSupport advised;

        public DynamicAdvisedInterceptor(AdvisedSupport advised) {
            this.advised = advised;
        }

        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            Object target = advised.getTargetSource().getTarget();
            Class<?> targetClass = (target != null) ? target.getClass() : null;
            List<Advice> advices = advised.getAdvices(method, targetClass);
            ReflectiveMethodInvocation invocation = new ReflectiveMethodInvocation(target, method, args, advices);
            return invocation.proceed();
        }
    }
}
