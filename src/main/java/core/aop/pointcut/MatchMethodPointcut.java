package core.aop.pointcut;

import java.lang.reflect.Method;

/**
 * Created by iltaek on 2020/07/20 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public interface MatchMethodPointcut {

    boolean matches(Method m, Class targetClass, Object[] args);
}
