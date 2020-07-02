package core.aop;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class BeanInterceptor implements MethodInterceptor {

    private final Advice advice;
    private final PointCut pointCut;

    public BeanInterceptor(Advice advice, PointCut pointCut) {
        this.advice = advice;
        this.pointCut = pointCut;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        if (!pointCut.matches(method, obj.getClass(), args)) {
            return proxy.invokeSuper(obj, args);
        }

        return advice.doAdvice(obj, method, args, proxy);
    }
}
