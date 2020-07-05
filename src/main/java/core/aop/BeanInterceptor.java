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
    public Object intercept(Object object, Method method, Object[] arguments, MethodProxy proxy) throws Throwable {
        if (!pointCut.matches(method, object.getClass(), arguments)) {
            return proxy.invokeSuper(object, arguments);
        }

        return advice.doAdvice(object, method, arguments, proxy);
    }
}
