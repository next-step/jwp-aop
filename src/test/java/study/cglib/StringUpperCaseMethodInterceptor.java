package study.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import study.MethodMatcher;

import java.lang.reflect.Method;

/**
 * Created by hspark on 2019-09-04.
 */
public class StringUpperCaseMethodInterceptor implements MethodInterceptor {
    private MethodMatcher methodMatcher;

    public StringUpperCaseMethodInterceptor(MethodMatcher methodMatcher) {
        this.methodMatcher = methodMatcher;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        Object retObject = proxy.invokeSuper(obj, args);
        if (retObject instanceof String &&
                methodMatcher.matches(method, obj.getClass(), args)) {
            return ((String) retObject).toUpperCase();
        }
        return retObject;
    }
}
