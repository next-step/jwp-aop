package study.proxy.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import study.proxy.MethodMatcher;

import java.lang.reflect.Method;

/**
 * @author : yusik
 * @date : 01/09/2019
 */
public class SayMethodInterceptor implements MethodInterceptor, MethodMatcher {

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {

        Object returnValue = proxy.invokeSuper(obj, args);
        if (matches(method, obj.getClass(), args)) {
            return ((String) returnValue).toUpperCase();
        }

        return returnValue;
    }

    @Override
    public boolean matches(Method method, Class target, Object[] args) {
        return method.getName().startsWith("say");
    }
}
