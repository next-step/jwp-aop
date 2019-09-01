package study.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class UpperInvocationHandler implements InvocationHandler, MethodMatcher {

    private Object target;
    private final Map<String, Method> methods = new HashMap();
    public UpperInvocationHandler(Object target) {
        this.target = target;
        for (Method method : target.getClass().getDeclaredMethods()) {
            this.methods.put(method.getName(), method);
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object returnValue = methods.get(method.getName()).invoke(target, args);

        if(matches(method, target, args)){
            returnValue = upperCase(returnValue);
        }

        return returnValue;
    }

    @Override
    public boolean matches(Method m, Object targetClass, Object[] args) {
        if(m.getName().contains("say")){
            return true;
        }

        return false;
    }

    private Object upperCase(Object str){
        if(!(str instanceof String)){
            return str;
        }

        return ((String) str).toUpperCase();
    }
}
