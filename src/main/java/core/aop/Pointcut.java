package core.aop;

import java.lang.reflect.Method;

/**
 * @author KingCjy
 */
public interface Pointcut {
    boolean matches(Method method);
}
