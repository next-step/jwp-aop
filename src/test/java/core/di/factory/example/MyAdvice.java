package core.di.factory.example;

import core.di.beans.factory.support.MethodMatcher;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class MyAdvice implements MethodInterceptor {
    private MethodMatcher matcher;

    public MyAdvice(MethodMatcher matcher) {
        this.matcher = matcher;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        String result = (String) proxy.invokeSuper(obj, args);

        if (this.matcher.matches(method, obj.getClass(), args)) {
            return result.toUpperCase();
        }
        return result;
    }
}
