package study.dynamic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class DynamicInvocationHandler implements InvocationHandler {

    private static final Logger log = LoggerFactory.getLogger(DynamicInvocationHandler.class);

    private Object target;
    private final Map<String, Method> methods = new HashMap<>();

    DynamicInvocationHandler(Object target) {
        this.target = target;
        for (Method method : target.getClass().getDeclaredMethods()) {
            methods.put(method.getName(), method);
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.debug("Invoke method name : {}, args : {}", method.getName(), args[0]);
        Object invoke = methods.get(method.getName()).invoke(target, args);
        if(method.getName().startsWith("say")){
            return invoke.toString().toUpperCase();
        }
        return invoke;
    }
}
