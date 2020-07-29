package study.proxy.example.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import study.proxy.example.TextUpperCaseMethodMatcher;

import java.lang.reflect.Method;

public class MethodCallLogInterceptor implements MethodInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(MethodCallLogInterceptor.class);

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        Object returnValue = proxy.invokeSuper(obj, args);
        logger.debug("{}, {}", obj, args);
        TextUpperCaseMethodMatcher matcher = new TextUpperCaseMethodMatcher("say", "talk");
        if (matcher.matches(method, method.getDeclaringClass(), args)) {
            returnValue = ((String) returnValue).toUpperCase();
        }

        return returnValue;
    }
}
