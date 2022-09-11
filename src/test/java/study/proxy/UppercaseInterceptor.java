package study.proxy;

import java.lang.reflect.Method;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class UppercaseInterceptor implements MethodInterceptor {

    private final Object target;
    private final MethodMatcher methodMatcher;

    public UppercaseInterceptor(final Object target) {
        this.target = target;
        this.methodMatcher = new SayMethodMatcher();
    }

    public UppercaseInterceptor(final Object target, final MethodMatcher methodMatcher) {
        this.target = target;
        this.methodMatcher = methodMatcher;
    }

    @Override
    public Object intercept(final Object obj, final Method method, final Object[] args, final MethodProxy proxy) throws Throwable {
        final Object result = proxy.invoke(target, args);

        if (methodMatcher.matches(method, target.getClass(), args)) {
            return ((String) result).toUpperCase();
        }

        return result;
    }
}
