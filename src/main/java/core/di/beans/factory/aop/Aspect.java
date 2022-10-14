package core.di.beans.factory.aop;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class Aspect implements MethodInterceptor {

    private final Advice advice;
    private final PointCut pointCut;

    public Aspect(Advice advice, PointCut pointCut) {
        this.advice = advice;
        this.pointCut = pointCut;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        if (matches(method, proxy.getClass(), args)) {
            return advice.intercept(obj, method, args, proxy);
        }
        return proxy.invokeSuper(obj, args);
    }

    private boolean matches(Method method, Class<?> targetClass, Object[] args) {
        return pointCut.matches(method, targetClass, args);
    }
}