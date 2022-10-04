package core.aop;

import java.lang.reflect.Method;
import java.util.List;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class Advisors implements MethodInterceptor {

    private final Object originTarget;
    private List<Advisor> advisors;

    public Advisors(Object originTarget, List<Advisor> advisors) {
        this.originTarget = originTarget;
        this.advisors = advisors;
    }

    @Override
    public Object intercept(Object target, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        for (Advisor advisor : advisors) {
            if (advisor.fitPointCut(originTarget, args, method)) {
                return advisor.advice(target, args, methodProxy);
            }
        }
        return methodProxy.invokeSuper(target, args);
    }

}
