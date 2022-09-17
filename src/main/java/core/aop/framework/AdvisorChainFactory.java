package core.aop.framework;

import java.lang.reflect.Method;
import java.util.List;

import core.aop.Advice;

public interface AdvisorChainFactory {

    List<Advice> getAdvices(Advised config, Method method, Class<?> targetClass);
}
