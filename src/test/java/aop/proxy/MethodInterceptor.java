package aop.proxy;

import aop.methodMatcher.MethodMatcher;
import net.sf.cglib.proxy.MethodProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

public class MethodInterceptor implements net.sf.cglib.proxy.MethodInterceptor {

    private static final Logger log = LoggerFactory.getLogger(MethodInterceptor.class);
    private final MethodMatcher methodMatcher;

    public MethodInterceptor(MethodMatcher methodMatcher) {
        this.methodMatcher = methodMatcher;
    }

    @Override
    public Object intercept(Object target, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        log.debug("method name = {}, param = {}", method.getName(), args[0]);
        if (methodMatcher.matches(method, target.getClass(), args)) {
            return proxy.invokeSuper(target, args).toString().toUpperCase();
        }

        return proxy.invokeSuper(target, args);
    }
}
