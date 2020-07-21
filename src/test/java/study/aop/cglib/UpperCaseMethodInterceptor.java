package study.aop.cglib;

import java.lang.reflect.Method;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import study.aop.MethodMatcher;

/**
 * Created by iltaek on 2020/07/20 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public class UpperCaseMethodInterceptor implements MethodInterceptor {

    private final MethodMatcher upperMatcher;

    public UpperCaseMethodInterceptor(MethodMatcher upperMatcher) {
        this.upperMatcher = upperMatcher;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        String returnValue = (String) proxy.invokeSuper(obj, args);
        if (upperMatcher.matches(method, obj.getClass(), args)) {
            return returnValue.toUpperCase();
        }
        return returnValue;
    }
}
