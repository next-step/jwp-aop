package core.mvc.tobe;

import core.mvc.tobe.support.ArgumentResolver;
import org.springframework.core.ParameterNameDiscoverer;

import java.lang.reflect.Method;
import java.util.List;

public class ExceptionHandlerExecution extends HandlerExecution {

    private final Class<? extends Throwable> exceptionClass;

    public ExceptionHandlerExecution(ParameterNameDiscoverer parameterNameDiscoverer, List<ArgumentResolver> argumentResolvers, Object target, Method method, Class<? extends Throwable> exceptionClass) {
        super(parameterNameDiscoverer, argumentResolvers, target, method);
        this.exceptionClass = exceptionClass;
    }

    public boolean supports(Class<? extends Throwable> exceptionClass) {
        return this.exceptionClass == exceptionClass;
    }
}
