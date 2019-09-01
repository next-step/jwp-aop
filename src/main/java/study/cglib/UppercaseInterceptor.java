package study.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import study.jdk.HelloTarget;
import study.jdk.MethodMatcher;
import study.jdk.SayMethodMatcher;

import java.lang.reflect.Method;

/**
 * Created by ssosso.follow on 2019-09-01
 */
public class UppercaseInterceptor implements MethodInterceptor {
    private MethodMatcher methodMatcher = new SayMethodMatcher();

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        Object result = proxy.invokeSuper(obj, args);
        if (!methodMatcher.matches(method, HelloTarget.class, args)) {
            return result;
        }
        return String.valueOf(result).toUpperCase();
    }
}
