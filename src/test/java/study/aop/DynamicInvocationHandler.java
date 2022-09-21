package study.aop;

import core.aop.MethodMatcher;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DynamicInvocationHandler implements InvocationHandler {

    private static final Logger logger = LoggerFactory.getLogger(DynamicInvocationHandler.class);

    private final Object target;
    private final MethodMatcher methodMatcher;

    public DynamicInvocationHandler(Object target, MethodMatcher methodMatcher) {
        this.target = target;
        this.methodMatcher = methodMatcher;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        logger.info("Method name: " + method.getName() + ", args: " + args.toString());

        String resultString = (String) method.invoke(this.target, args);

        if (methodMatcher.matches(method, proxy.getClass(), args)) {
            return resultString.toUpperCase();
        }
        return resultString;
    }
}
