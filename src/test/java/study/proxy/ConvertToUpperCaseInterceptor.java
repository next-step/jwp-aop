package study.proxy;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class ConvertToUpperCaseInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        if (MethodMatchUtil.matches(method, obj.getClass(), args)) {
            return ((String) proxy.invokeSuper(obj, args)).toUpperCase();
        }
        else {
            return proxy.invokeSuper(obj, args);
        }
    }
}
