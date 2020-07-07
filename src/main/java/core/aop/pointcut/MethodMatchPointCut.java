package core.aop.pointcut;

import core.aop.PointCut;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class MethodMatchPointCut implements PointCut {
    private final Set<String> methodNames;

    public MethodMatchPointCut(Collection<Method> methods) {
        this.methodNames = methods.stream()
                .map(Method::getName)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass, Object[] arguments) {
        return methodNames.contains(method.getName());
    }
}
