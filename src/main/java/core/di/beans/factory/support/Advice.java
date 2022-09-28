package core.di.beans.factory.support;

import net.sf.cglib.proxy.MethodInterceptor;

import java.lang.reflect.Method;

public interface Advice {
    default MethodInterceptor getMethodInterceptor(PointCut pointCut) {
        return (obj, method, args, proxy) -> {
            if (pointCut.matches(method, obj.getClass(), args)) {
                before(method, obj.getClass(), args);
            }

            final Object result = proxy.invokeSuper(obj, args);

            if (pointCut.matches(method, obj.getClass(), args)) {
                return after(method, obj.getClass(), args, result);
            }

            return result;
        };
    }

    void before(Method method, Class<?> clazz, Object[] args);

    Object after(Method method, Class<?> clazz, Object[] args, Object result);
}
