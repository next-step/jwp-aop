package study.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by youngjae.havi on 2019-09-01
 */
public class UppercaseHandler implements InvocationHandler {
    private MethodMatcher methodMatcher = new SayMethodMatcher();

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = method.invoke(new HelloTarget(), args);
        if (!methodMatcher.matches(method, HelloTarget.class, args)) {
            return result;
        }
        return String.valueOf(result).toUpperCase();
    }
}
