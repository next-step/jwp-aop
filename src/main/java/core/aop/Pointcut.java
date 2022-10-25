package core.aop;

import java.lang.reflect.Method;

/**
 * 조인 포인트 중에서 advice가 적용될 위치를 선별하는 기능
 */
public interface Pointcut {
    boolean matches(Class<?> targetClass, Method method);
}
