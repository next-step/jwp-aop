package core.aop.test;

import core.aop.MethodMatcher;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class UpperMethodInterceptor implements MethodInterceptor {

    private final MethodMatcher methodMatcher;

    public UpperMethodInterceptor(MethodMatcher methodMatcher) {
        this.methodMatcher = methodMatcher;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        Object o = proxy.invokeSuper(obj, args);
        if (methodMatcher.matches(obj, method, args)) {
            return o.toString().toUpperCase();
        }
        return o.toString();
    }
}
