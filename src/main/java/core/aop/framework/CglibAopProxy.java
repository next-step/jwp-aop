package core.aop.framework;

import java.lang.reflect.Method;
import java.util.List;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import core.aop.Advice;

public class CglibAopProxy implements AopProxy {

    private final AdvisedSupport advised;

    public CglibAopProxy(AdvisedSupport config) {
        this.advised = config;
    }

    @Override
    public Object getProxy() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(this.advised.getTargetClass());
        enhancer.setInterfaces(this.advised.getProxiedInterfaces());
        enhancer.setCallback(new DynamicAdvisedInterceptor(advised));
        return enhancer.create();
    }

    private static class DynamicAdvisedInterceptor implements MethodInterceptor {

        private final AdvisedSupport advised;

        public DynamicAdvisedInterceptor(AdvisedSupport advised) {
            this.advised = advised;
        }

        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) {
            Object target = advised.getTargetSource().getTarget();
            Class<?> targetClass = (target != null) ? target.getClass() : null;
            List<Advice> advices = advised.getAdvices(method, targetClass);
            ReflectiveMethodInvocation invocation = new ReflectiveMethodInvocation(target, method, args, advices);
            return invocation.proceed();
        }
    }
}
