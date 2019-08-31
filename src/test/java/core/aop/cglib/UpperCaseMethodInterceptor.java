package core.aop.cglib;

import core.aop.MethodMatcher;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Arrays;

public class UpperCaseMethodInterceptor implements MethodInterceptor {

    private static Logger log = LoggerFactory.getLogger(UpperCaseMethodInterceptor.class);

    private final MethodMatcher methodMatcher;

    public UpperCaseMethodInterceptor(MethodMatcher methodMatcher) {
        this.methodMatcher = methodMatcher;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        log.debug("invoke method name : " + method.getName() + ", args : " + Arrays.toString(args));

        final Object object = proxy.invokeSuper(obj, args);

        if (object instanceof String &&
                methodMatcher.matches(method, method.getClass(), args)) {

            return ((String) object).toUpperCase();
        }

        return object;
    }
}
