package study.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class HelloUppercaseInvocationHandler implements InvocationHandler {
    private Object target; //실행의 타겟

    public HelloUppercaseInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] arguments) throws Throwable {
        Object ret = method.invoke(target, arguments);

        if (!(ret instanceof String)) {
            return ret;
        }

        return ((String) ret).toUpperCase();
    }
}
