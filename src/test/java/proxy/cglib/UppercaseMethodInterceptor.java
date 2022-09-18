package proxy.cglib;

import java.lang.reflect.Method;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class UppercaseMethodInterceptor implements MethodInterceptor {

    private MethodMatcher matcher;
    public UppercaseMethodInterceptor(MethodMatcher matcher) {
        this.matcher = matcher;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        if (matcher.matches(method, obj.getClass(), args)) {
            return proxy.invokeSuper(obj, args).toString().toUpperCase();
        }
        return proxy.invokeSuper(obj, args);
    }
}
