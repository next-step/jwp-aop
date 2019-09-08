package study.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

public class UpperCaseInterceptor implements MethodInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(UpperCaseInterceptor.class);
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {

        Object result = proxy.invokeSuper(obj, args);

        if (result instanceof String) {
            result = ((String) result).toUpperCase();
        }

        return result;
    }
}
