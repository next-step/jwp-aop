package study.cglibProxy.hello;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import study.dynamicProxy.MethodMatcher;

import java.lang.reflect.Method;

public class HelloInterceptor implements MethodInterceptor {

    private MethodMatcher methodMatcher;

    public HelloInterceptor() {
    }

    public HelloInterceptor(MethodMatcher methodMatcher) {
        this.methodMatcher = methodMatcher;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        Object returnValue = proxy.invokeSuper(obj, args);
        if (methodMatcher != null && methodMatcher.matches(method, proxy.getClass(), args)) {
            return returnValue.toString().toUpperCase();
        }
        return returnValue;
    }

}
