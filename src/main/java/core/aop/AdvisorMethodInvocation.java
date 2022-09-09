package core.aop;

import org.springframework.util.Assert;

import java.lang.reflect.Method;

final class AdvisorMethodInvocation implements Joinpoint {

    private final Advisor advisor;
    private final Class<?> targetClass;
    private final Method method;
    private final Joinpoint joinpoint;

    private AdvisorMethodInvocation(Advisor advisor, Class<?> targetClass, Method method, Joinpoint joinpoint) {
        Assert.notNull(advisor, "'advisor' must not be null");
        Assert.notNull(targetClass, "'targetClass' must not be null");
        Assert.notNull(method, "'method' must not be null");
        Assert.notNull(joinpoint, "'joinpoint' must not be null");
        this.advisor = advisor;
        this.targetClass = targetClass;
        this.method = method;
        this.joinpoint = joinpoint;
    }

    static AdvisorMethodInvocation of(Advisor advisor, Class<?> targetClass, Method method, Joinpoint joinpoint) {
        return new AdvisorMethodInvocation(advisor, targetClass, method, joinpoint);
    }

    @Override
    public Object proceed() throws Throwable {
        if (advisor.matches(targetClass) && advisor.matches(method)) {
            return advisor.invoke(joinpoint);
        }
        return joinpoint.proceed();
    }
}
