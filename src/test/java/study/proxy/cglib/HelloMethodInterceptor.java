package study.proxy.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

public class HelloMethodInterceptor implements MethodInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(HelloMethodInterceptor.class);

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        logger.info("invoke method name: {}, args: {}", method.getName(), args[0]);

        Object result = proxy.invokeSuper(obj, args);

        if (result instanceof String) {
            return String.valueOf(result).toUpperCase();
        }

        return result;
    }
}
