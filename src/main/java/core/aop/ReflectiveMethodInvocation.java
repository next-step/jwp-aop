package core.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.Advisor;
import org.springframework.aop.support.AopUtils;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.List;

public class ReflectiveMethodInvocation implements MethodInvocation {
    private final Object proxy;
    private final Class<?> targetClass;
    private final Object target;
    private final Method method;
    private final Object[] arguments;
    private final List<Advisor> advisors;
    private int currentInterceptorIndex = -1;

    public ReflectiveMethodInvocation(
        Object proxy,
        Class<?> targetClass,
        Object target,
        Method method,
        Object[] arguments,
        List<Advisor> advisors
    ) {
        this.proxy = proxy;
        this.targetClass = targetClass;
        this.target = target;
        this.method = method;
        this.arguments = arguments;
        this.advisors = advisors;
    }

    public Object getProxy() {
        return this.proxy;
    }

    @Override
    public Method getMethod() {
        return this.method;
    }

    public Object[] getArguments() {
        return new Object[0];
    }

    @Override
    public Object proceed() throws Throwable {
        if (this.currentInterceptorIndex == this.advisors.size() - 1) {
            return invokeJoinpoint();
        }

        Advisor advisor = this.advisors.get(++this.currentInterceptorIndex);
        return ((MethodInterceptor) advisor.getAdvice()).invoke(this);
    }

    protected Object invokeJoinpoint() throws Throwable {
        return AopUtils.invokeJoinpointUsingReflection(this.target, this.method, this.arguments);
    }

    @Override
    public Object getThis() {
        return this.target;
    }

    @Override
    public AccessibleObject getStaticPart() {
        return this.method;
    }
}
