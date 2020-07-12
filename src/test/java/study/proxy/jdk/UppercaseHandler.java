package study.proxy.jdk;

import lombok.RequiredArgsConstructor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

@RequiredArgsConstructor
public class UppercaseHandler implements InvocationHandler {

    private final Hello target;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws ReflectiveOperationException {
        String result = (String) method.invoke(target, args);

        return result.toUpperCase();
    }

}
