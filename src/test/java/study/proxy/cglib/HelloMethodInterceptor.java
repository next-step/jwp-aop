package study.proxy.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import study.proxy.MethodMatcher;

import java.lang.reflect.Method;

public class HelloMethodInterceptor implements MethodInterceptor {

    private final Object target;
    private final MethodMatcher methodMatcher;

    public HelloMethodInterceptor(Object target, MethodMatcher methodMatcher) {
        this.target = target;
        this.methodMatcher = methodMatcher;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        if (!methodMatcher.matches(method, target.getClass(), args)) {
            return method.invoke(target, args);
        }
        String result = String.valueOf(method.invoke(target, args));
        return result.toUpperCase();
    }
}
