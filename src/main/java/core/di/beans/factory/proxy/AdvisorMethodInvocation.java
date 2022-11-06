package core.di.beans.factory.proxy;

import java.lang.reflect.Method;

public class AdvisorMethodInvocation implements JoinPoint {

    private final Advisor advisor;
    private final Class<?> targetClass;
    private final Method method;
    private final JoinPoint joinpoint;

    public AdvisorMethodInvocation(Advisor advisor, Class<?> targetClass, Method method, JoinPoint joinpoint) {
        this.advisor = advisor;
        this.targetClass = targetClass;
        this.method = method;
        this.joinpoint = joinpoint;
    }

    @Override
    public Object proceed() {
        if (advisor.matches(targetClass, method)) {
            return advisor.invoke(joinpoint);
        }

        return joinpoint.proceed();
    }
}
