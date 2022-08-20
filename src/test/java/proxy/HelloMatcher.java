package proxy;

import java.lang.reflect.Method;

public interface HelloMatcher {
    boolean matches(Method method, Class targetClass, Object[] args);
}
