package study.aop;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class UpperInterceptor implements MethodInterceptor, MethodMatcher {
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        Object returnValue = proxy.invokeSuper(obj, args);

        if(matches(method, obj, args)){
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
