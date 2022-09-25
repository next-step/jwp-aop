package next.hello;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Locale;

public class UppercaseMethodInterceptor implements MethodInterceptor {

    private MethodMatcher methodMatcher;

    public UppercaseMethodInterceptor() {
    }

    public UppercaseMethodInterceptor(MethodMatcher methodMatcher) {
        this.methodMatcher = methodMatcher;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        Object returnValue = proxy.invokeSuper(obj, args);
        if (returnValue instanceof String && isMatch(proxy, method, args)) {
            return String.valueOf(returnValue).toUpperCase(Locale.ROOT);
        }
        return returnValue;
    }

    private boolean isMatch(Object proxy, Method method, Object[] args) {
        if (methodMatcher == null) {
            return true;
        }
        return methodMatcher.matches(method, proxy.getClass(), args);
    }
}
