package proxy;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class UppercaseInterceptor implements MethodInterceptor, HelloMatcher {

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        if (matches(method, obj.getClass(), args)) {
            return ((String) proxy.invokeSuper(obj, args)).toUpperCase();
        }
        return proxy.invokeSuper(obj, args);
    }

    @Override
    public boolean matches(Method method, Class targetClass, Object[] args) {
        return method.getName().startsWith("say");
    }
}
