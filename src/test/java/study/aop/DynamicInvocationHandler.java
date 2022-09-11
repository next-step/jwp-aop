package study.aop;

import com.google.common.collect.Maps;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DynamicInvocationHandler implements InvocationHandler {

    private static final Logger logger = LoggerFactory.getLogger(DynamicInvocationHandler.class);

    private final Object target;

    public DynamicInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        logger.info("Method name: " + method.getName() + ", args: " + args.toString());

        String resultString = (String) method.invoke(this.target, args);

        return resultString.toUpperCase();
    }
}
