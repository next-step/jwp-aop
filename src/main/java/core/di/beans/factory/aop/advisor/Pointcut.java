package core.di.beans.factory.aop.advisor;

import java.lang.reflect.Method;

public interface Pointcut extends MethodMatcher {
    default int matches(Method method, Target target) {
        if (matches(method, target.type())) {
            return 1;
        }
        return 0;
    }
}
