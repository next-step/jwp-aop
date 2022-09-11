package study.aop.cglibproxy;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import study.aop.SayMethodMatcher;

import java.lang.reflect.Method;
import java.util.Arrays;

public class CustomMethodInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.printf("invoke method name: %s, args: %s%n", method.getName(), Arrays.toString(args));
        final Object result = proxy.invokeSuper(obj, args);
        if (new SayMethodMatcher().matches(method, obj.getClass(), args)) {
            return result.toString().toUpperCase();
        }
        return result;
    }
}
