package core.mvc.tobe;

import core.annotation.web.ExceptionHandler;
import core.mvc.tobe.support.ArgumentResolver;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

public class ExceptionHandlerConverter {

    private static final ParameterNameDiscoverer NAME_DISCOVERER = new LocalVariableTableParameterNameDiscoverer();

    private final ArgumentResolver argumentResolver;

    public ExceptionHandlerConverter(final ArgumentResolver argumentResolver) {
        this.argumentResolver = argumentResolver;
    }

    public Map<Class<? extends Throwable>, HandlerExecution> convert(Object controllerAdvice) {
        Map<Class<? extends Throwable>, HandlerExecution> handlers = new HashMap<>();

        Method[] methods = controllerAdvice.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (!method.isAnnotationPresent(ExceptionHandler.class)) {
                continue;
            }

            handlers.putAll(addExceptionHandlers(controllerAdvice, method));
        }

        return handlers;
    }

    private Map<Class<? extends Throwable>, HandlerExecution> addExceptionHandlers(final Object target, final Method method) {
        final Class<? extends Throwable> exception = method.getDeclaredAnnotation(ExceptionHandler.class).value();
        final HandlerExecution handlerExecution = new HandlerExecution(NAME_DISCOVERER, List.of(argumentResolver), target, method);

        return Map.of(exception, handlerExecution);
    }
}
