package study.proxy.cglib;

import lombok.RequiredArgsConstructor;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import core.aop.MethodMatcher;

import java.lang.reflect.Method;

@RequiredArgsConstructor
public class UppercaseInterceptor implements MethodInterceptor {

    private final MethodMatcher methodMatcher;

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        String result = (String) proxy.invokeSuper(obj, args);
        if (methodMatcher.matches(method, obj.getClass(), args)) {
            return result.toUpperCase();
        }

        return result;
    }

}
