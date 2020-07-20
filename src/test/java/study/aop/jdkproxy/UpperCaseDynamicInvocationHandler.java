package study.aop.jdkproxy;

import com.google.common.collect.Maps;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import study.aop.MethodMatcher;

/**
 * Created by iltaek on 2020/07/20 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public class UpperCaseDynamicInvocationHandler implements InvocationHandler {

    private final Logger logger = LoggerFactory.getLogger(UpperCaseDynamicInvocationHandler.class);

    private final Object target;
    private final Map<String, Method> methods = Maps.newHashMap();
    private final MethodMatcher upperMethodMatcher;

    public UpperCaseDynamicInvocationHandler(Object target, MethodMatcher upperMethodMatcher) {
        this.target = target;
        this.upperMethodMatcher = upperMethodMatcher;
        for (Method method : target.getClass().getDeclaredMethods()) {
            this.methods.put(method.getName(), method);
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        logger.debug("invoke method name: {}, args: {}", method.getName(), args[0]);

        String result = (String) methods.get(method.getName()).invoke(target, args);

        if (upperMethodMatcher.matches(method, proxy.getClass(), args)) {
            return result.toUpperCase();
        }
        return result;
    }
}
