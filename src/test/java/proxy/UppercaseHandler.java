package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class UppercaseHandler implements InvocationHandler, HelloMatcher {
    private HelloTarget helloTarget = new HelloTarget();

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (matches(method, helloTarget.getClass(), args)) {
            return ((String) method.invoke(helloTarget, args)).toUpperCase();
        }

        return method.invoke(helloTarget, args);
    }

    @Override
    public boolean matches(Method method, Class targetClass, Object[] args) {
        return method.getName().startsWith("say");
    }
}
