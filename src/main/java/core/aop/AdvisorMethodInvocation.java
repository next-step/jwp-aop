package core.aop;

import java.lang.reflect.Method;

public class AdvisorMethodInvocation implements Joinpoint{

    private final Advisor advisor;
    private final Class<?> targetClass;
    private final Method method;
    private final Joinpoint joinpoint;

    public AdvisorMethodInvocation(Advisor advisor, Class<?> targetClass, Method method, Joinpoint joinpoint) {
        this.advisor = advisor;
        this.targetClass = targetClass;
        this.method = method;
        this.joinpoint = joinpoint;
    }

    @Override
    public Object proceed() throws Throwable {
        if (advisor.matches(targetClass, method)) {
            return advisor.invoke(joinpoint);
        }

        return joinpoint.proceed();
    }
}
