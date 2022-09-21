package study.proxy.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import core.di.beans.factory.aop.advisor.MethodMatcher;

import java.lang.reflect.Method;

public class HelloMethodInterceptor implements MethodInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(HelloMethodInterceptor.class);
    private MethodMatcher methodMatcher;

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        logger.info("invoke method name: {}, args: {}", method.getName(), args[0]);

        Object result = proxy.invokeSuper(obj, args);
        if (methodMatcher.matches(method, obj.getClass()) && result instanceof String) {
            return StringUtils.upperCase((String) result);
        }

        return result;
    }

    public void updateMethodMatcher(MethodMatcher methodMatcher) {
        this.methodMatcher = methodMatcher;
    }
}
