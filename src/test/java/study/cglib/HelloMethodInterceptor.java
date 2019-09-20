package study.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import study.MethodMatcher;

import java.lang.reflect.Method;

public class HelloMethodInterceptor implements MethodInterceptor {

    private MethodMatcher methodMatcher;

    HelloMethodInterceptor(MethodMatcher methodMatcher) {
        this.methodMatcher = methodMatcher;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        Object target = proxy.invokeSuper(obj, args);

        if(methodMatcher.matches(method, proxy.getClass(), args)){
            return target.toString().toUpperCase();
        }
        return target;
    }
}
