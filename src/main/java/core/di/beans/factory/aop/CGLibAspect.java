package core.di.beans.factory.aop;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CGLibAspect extends Aspect implements MethodInterceptor {

    private final Advice advice;

    public CGLibAspect(PointCut pointCut, Advice advice) {
        super(pointCut);
        this.advice = advice;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        Object returnValue = proxy.invokeSuper(obj, args);
        if (matches(method, proxy.getClass(), args)) {
            return advice.intercept(returnValue);
        }
        return returnValue;
    }
}
