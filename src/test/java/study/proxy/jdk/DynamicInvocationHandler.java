package study.proxy.jdk;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import core.di.beans.factory.aop.advisor.MethodMatcher;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

public class DynamicInvocationHandler implements InvocationHandler {
    private static final Logger logger = LoggerFactory.getLogger(DynamicInvocationHandler.class);
    private MethodMatcher methodMatcher;

    private Object target;
    private final Map<String, Method> methods = Maps.newHashMap();

    public DynamicInvocationHandler(Object target) {
        this.target = target;
        for (Method method : target.getClass().getDeclaredMethods()) {
            this.methods.put(method.getName(), method);
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        logger.info("invoke method name: {}, args: {}", method.getName(), args[0]);
        Object invokeResult = methods.get(method.getName()).invoke(target, args);

        if (methodMatcher.matches(method, target.getClass()) && invokeResult instanceof String) {
            return StringUtils.upperCase((String) invokeResult);
        }

        return invokeResult;
    }

    public void updateMethodMatcher(MethodMatcher methodMatcher) {
        this.methodMatcher = methodMatcher;
    }
}
