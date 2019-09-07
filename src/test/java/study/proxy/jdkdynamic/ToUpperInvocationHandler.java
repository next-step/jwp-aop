package study.proxy.jdkdynamic;

import study.proxy.MethodPredicate;
import study.proxy.MethodResultHelper;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

class ToUpperInvocationHandler implements InvocationHandler {

    private final Object target;

    private MethodPredicate methodPredicate;

    ToUpperInvocationHandler(Object target) {
        this.target = target;
    }

    void setMethodPredicate(MethodPredicate methodPredicate) {
        this.methodPredicate = methodPredicate;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = method.invoke(target, args);
        if (methodPredicate != null && !methodPredicate.matches(method, args)) {
            return result;
        }
        return MethodResultHelper.toUpper(method, result);
    }
}
