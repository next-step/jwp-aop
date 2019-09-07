package study.proxy.jdkdynamic;

import study.proxy.MethodResultHelper;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

class ToUpperInvocationHandler implements InvocationHandler {

    private final Object target;

    ToUpperInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = method.invoke(target, args);
        return MethodResultHelper.toUpper(method, result);
    }
}
