package core.aop;

import java.lang.reflect.Method;

public interface Advice {
    Object invoke(Object object, Method method, Object... objects);
}
