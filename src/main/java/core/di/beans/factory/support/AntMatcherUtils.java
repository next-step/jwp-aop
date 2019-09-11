package core.di.beans.factory.support;

import org.springframework.util.AntPathMatcher;

import java.lang.reflect.Method;

public class AntMatcherUtils {

    public static String fullMethodName(Class<?> clazz, Method method) {
        return clazz.getName() + "." + method.getName();
    }

    public static boolean matches(String pattern, String inputStr) {
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        return antPathMatcher.match(pattern, inputStr);
    }

}
