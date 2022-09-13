package study.aop;

import java.lang.reflect.Method;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class HelloTargetCglibProxyTest implements MethodInterceptor {

    private final MethodMatcher methodMatcher;

    public HelloTargetCglibProxyTest(MethodMatcher methodMatcher) {
        this.methodMatcher = methodMatcher;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        String resultString = (String) methodProxy.invokeSuper(obj, args);

        if (methodMatcher.matches(method, obj.getClass(), args)) {
            return resultString.toUpperCase();
        }
        return resultString;
    }
}
