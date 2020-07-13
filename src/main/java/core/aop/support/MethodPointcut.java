package core.aop.support;

import core.aop.Pointcut;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * @author KingCjy
 */
public class MethodPointcut implements Pointcut {

    private Set<Method> methods;

    public MethodPointcut(Set<Method> methods) {
        this.methods = methods;
    }

    @Override
    public boolean matches(Method targetMethod) {
        return this.methods.stream()
                .anyMatch(targetMethod::equals);
    }
}
