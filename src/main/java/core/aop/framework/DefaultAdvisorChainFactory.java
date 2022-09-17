package core.aop.framework;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import core.aop.Advice;
import core.aop.Advisor;
import core.aop.PointcutAdvisor;

public class DefaultAdvisorChainFactory implements AdvisorChainFactory {

    @Override
    public List<Advice> getAdvices(Advised config, Method method, Class<?> targetClass) {
        Class<?> actualClass = (targetClass != null) ? targetClass : method.getDeclaringClass();
        return Arrays.stream(config.getAdvisors())
            .filter(PointcutAdvisor.class::isInstance)
            .map(PointcutAdvisor.class::cast)
            .filter(advisor -> advisor.getPointcut().getClassFilter().matches(actualClass))
            .filter(advisor -> advisor.getPointcut().getMethodMatcher().matches(method, actualClass))
            .map(Advisor::getAdvice)
            .collect(Collectors.toList());
    }
}
