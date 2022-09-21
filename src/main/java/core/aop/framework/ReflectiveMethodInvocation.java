package core.aop.framework;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.springframework.util.ReflectionUtils;

import core.aop.Advice;
import core.aop.intercept.MethodInterceptor;
import core.aop.intercept.MethodInvocation;

public class ReflectiveMethodInvocation implements MethodInvocation {

    private final Object target;
    private final Method method;
    private final Object[] arguments;
    private final List<Advice> advices;

    private int currentIndex = -1;

    public ReflectiveMethodInvocation(Object target, Method method, Object[] arguments, List<Advice> advices) {
        this.target = target;
        this.method = method;
        this.arguments = arguments;
        this.advices = advices;
    }

    @Override
    public Method getMethod() {
        return this.method;
    }

    @Override
    public Object proceed() throws Throwable {
        if (this.currentIndex == this.advices.size() - 1) {
            return invokeJoinpoint();
        }
        Advice advice = advices.get(++this.currentIndex);
        MethodInterceptor interceptor = (MethodInterceptor) advice;
        return interceptor.invoke(this);
    }

    private Object invokeJoinpoint() {
        try {
            ReflectionUtils.makeAccessible(method);
            return method.invoke(target, arguments);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
