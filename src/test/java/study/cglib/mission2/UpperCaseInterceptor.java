package study.cglib.mission2;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;

public class UpperCaseInterceptor implements MethodInterceptor {

    @Override
    public Object intercept(Object obj, Method method, Object[] args, net.sf.cglib.proxy.MethodProxy proxy) throws
        Throwable {
        var result = proxy.invokeSuper(obj, args);

        return ((String)result).toUpperCase();
    }
}
