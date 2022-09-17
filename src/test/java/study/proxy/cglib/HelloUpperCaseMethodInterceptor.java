package study.proxy.cglib;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class HelloUpperCaseMethodInterceptor implements MethodInterceptor {
    private final Object target;

    public HelloUpperCaseMethodInterceptor(Object target) {
        this.target = target;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        Object invokeResult = methodProxy.invoke(target, objects);

        if (invokeResult instanceof String) {
            return ((String) invokeResult).toUpperCase();
        }

        return invokeResult;
    }
}
