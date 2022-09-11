package study.proxy;

import java.lang.reflect.Method;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class UppercaseInterceptor implements MethodInterceptor {

    private final Object target;
    private final SayMethodMatcher sayMethodMatcher;

    public UppercaseInterceptor(final Object target) {
        this.target = target;
        this.sayMethodMatcher = new DefaultSayMethodMatcher();
    }

    public UppercaseInterceptor(final Object target, final SayMethodMatcher sayMethodMatcher) {
        this.target = target;
        this.sayMethodMatcher = sayMethodMatcher;
    }

    @Override
    public Object intercept(final Object obj, final Method method, final Object[] args, final MethodProxy proxy) throws Throwable {
        final Object result = proxy.invoke(target, args);

        if (sayMethodMatcher.matches(method, target.getClass(), args)) {
            return ((String) result).toUpperCase();
        }

        return result;
    }
}
