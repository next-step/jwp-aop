package study.proxy.factorybean;

import core.di.beans.factory.aop.Pointcut;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PrefixPointcut implements Pointcut {
    private final List<String> whiteList = new ArrayList<>();

    public PrefixPointcut(String... prefixes) {
        whiteList.addAll(Arrays.asList(prefixes));
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return whiteList.stream().anyMatch(prefix -> method.getName().startsWith(prefix));
    }
}
