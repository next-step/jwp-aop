package study.aop;

import java.lang.reflect.Method;

/**
 * Created by iltaek on 2020/07/20 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public interface MethodMatcher {

    boolean matches(Method m, Class targetClass, Object[] args);
}
