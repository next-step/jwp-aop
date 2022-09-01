package study.hello;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.springframework.util.Assert;

import java.lang.reflect.Method;

class UppercaseHelloMethodInterceptor implements MethodInterceptor {

    private final MethodMatcher methodMatcher;

    UppercaseHelloMethodInterceptor(MethodMatcher methodMatcher) {
        Assert.notNull(methodMatcher, "'methodMatcher' must not be null");
        this.methodMatcher = methodMatcher;
    }

    @Override
    public Object intercept(Object object, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        Object result = proxy.invokeSuper(object, args);
        if (methodMatcher.matches(method, object.getClass(), args)) {
            return result.toString().toUpperCase();
        }
        return result;
    }
}
