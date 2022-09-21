package core.aop.framework;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

import org.springframework.util.ClassUtils;

import core.aop.Advice;
import core.aop.TargetSource;
import core.aop.intercept.MethodInvocation;

public class JdkDynamicAopProxy implements AopProxy, InvocationHandler {

    private final AdvisedSupport advised;

    public JdkDynamicAopProxy(AdvisedSupport config) {
        this.advised = config;
    }

    @Override
    public Object getProxy() {
        return Proxy.newProxyInstance(ClassUtils.getDefaultClassLoader(), this.advised.getProxiedInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        TargetSource targetSource = this.advised.getTargetSource();
        Object target = targetSource.getTarget();
        Class<?> targetClass = (target != null) ? target.getClass() : null;
        List<Advice> advices = this.advised.getAdvices(method, targetClass);
        MethodInvocation invocation = new ReflectiveMethodInvocation(target, method, args, advices);
        return invocation.proceed();
    }
}
