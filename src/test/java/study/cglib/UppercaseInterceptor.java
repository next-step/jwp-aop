package study.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import study.methodmatcher.MethodMatcher;

import java.lang.reflect.Method;

public class UppercaseInterceptor implements MethodInterceptor {

    private final Object target;
    private final MethodMatcher methodMatcher;

    public UppercaseInterceptor(Object target, MethodMatcher methodMatcher) {
        this.target = target;
        this.methodMatcher = methodMatcher;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        final String result = (String) proxy.invokeSuper(obj, args);

        if (methodMatcher.matches(method, target.getClass(), args)) {
            return result.toUpperCase();
        }

        return result;
    }
}
