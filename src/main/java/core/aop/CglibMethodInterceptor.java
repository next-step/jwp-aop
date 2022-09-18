package core.aop;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibMethodInterceptor implements MethodInterceptor {

    private Object target;
    private MethodMatcher methodMatcher;

    public CglibMethodInterceptor(Object target, MethodMatcher methodMatcher) {
        this.target = target;
        this.methodMatcher = methodMatcher;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {

        Object result = proxy.invoke(target, args);

        if (methodMatcher.matches(method, proxy.getClass(), args)) {
            return result.toString().toUpperCase();
        }

        return result;
    }
}
