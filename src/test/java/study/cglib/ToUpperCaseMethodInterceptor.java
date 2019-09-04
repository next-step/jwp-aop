package study.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class ToUpperCaseMethodInterceptor implements MethodInterceptor {

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        Object returnValue = proxy.invokeSuper(obj, args);
        if (returnValue instanceof String) {
            return ((String) returnValue).toUpperCase();
        }

        return returnValue;
    }
}
