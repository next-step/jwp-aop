package core.aop;

import java.lang.reflect.Method;

public class SayPointcut implements Pointcut {
    @Override
    public boolean matches(Method method, Class<?> target) {
        return method.getName().startsWith("say");
    }
}
