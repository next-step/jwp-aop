package core.aop;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

class ProxyMethodInterceptor implements MethodInterceptor {

    private final Object target;
    private final List<Advisor> advisors;

    ProxyMethodInterceptor(Object target, List<Advisor> advisors) {
        this.target = target;
        this.advisors = advisors;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy)
        throws Throwable {

        List<Advice> advices = this.advisors.stream()
            .filter(advisor -> advisor.getPointcut().match(method, obj.getClass(), args))
            .map(Advisor::getAdvice)
            .collect(Collectors.toList());

        return new ProxyMethodInvocation(this.target, method, args, advices, 0).proceed();
    }
}
