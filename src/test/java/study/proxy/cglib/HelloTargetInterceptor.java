package study.proxy.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import study.proxy.MethodMatcher;

import java.lang.reflect.Method;

public class HelloTargetInterceptor implements MethodInterceptor {

    private final MethodMatcher methodMatcher;

    public HelloTargetInterceptor(MethodMatcher methodMatcher) {
        this.methodMatcher = methodMatcher;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {

        Object result = proxy.invokeSuper(obj, args);
        if (methodMatcher.matches(method, obj.getClass(), args)) {
            return ((String) result).toUpperCase();
        }

        return result;
    }
}
