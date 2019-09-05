package study.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import study.matcher.MethodMatcher;

import java.lang.reflect.Method;

public class ToUpperCaseMethodInterceptor implements MethodInterceptor {

    private MethodMatcher methodMatcher;

    public ToUpperCaseMethodInterceptor(MethodMatcher methodMatcher) {
        this.methodMatcher = methodMatcher;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        Object returnValue = proxy.invokeSuper(obj, args);
        if (methodMatcher.matches(method, method.getClass(), args)) {
            return returnValue.toString().toUpperCase();
        }

        return returnValue;
    }
}
