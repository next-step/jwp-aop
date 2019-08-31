package core.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

public class DynamicInvocationHandler implements InvocationHandler {

    private static Logger log = LoggerFactory.getLogger(DynamicInvocationHandler.class);

    private final Object target;
    private final Map<String, Method> methods;

    public DynamicInvocationHandler(Object target) {
        this.target = target;
        this.methods = Arrays.stream(target.getClass().getDeclaredMethods())
                .collect(toMap(Method::getName, method -> method));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.debug("invoke method name : " + method.getName() + ", args : " + Arrays.toString(args));

        final Method found = methods.get(method.getName());
        final Object object = found.invoke(target, args);

        if (object instanceof String) {
            return ((String) object).toUpperCase();
        }

        return object;
    }
}
