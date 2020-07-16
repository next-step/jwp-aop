package core.mvc.tobe;

import core.annotation.Component;
import core.annotation.ExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

@Slf4j
@Component
public class ExceptionHandlerConverter {
    public Map<Class<? extends Throwable>, ExceptionHandlerExecution> convert(Map<Class<?>, Object> controllerAdvices) {
        Map<Class<? extends Throwable>, ExceptionHandlerExecution> handlers = new HashMap<>();

        Set<Class<?>> controllerAdviceClasses = controllerAdvices.keySet();

        for (Class<?> controllerAdviceClass : controllerAdviceClasses) {
            Object target = controllerAdvices.get(controllerAdviceClass);
            addHandlerExecution(handlers, target, controllerAdviceClass.getMethods());
        }

        return handlers;
    }

    private void addHandlerExecution(
        Map<Class<? extends Throwable>, ExceptionHandlerExecution> handlers,
        final Object target,
        Method[] methods
    ) {
        Arrays.stream(methods)
            .filter(isValidExceptionHandler())
            .forEach(method -> addExceptionHandlerExecution(handlers, target, method));
    }

    private Predicate<Method> isValidExceptionHandler() {
        return method -> method.isAnnotationPresent(ExceptionHandler.class) &&
            !ArrayUtils.isEmpty(method.getAnnotation(ExceptionHandler.class).value());
    }

    private void addExceptionHandlerExecution(
        Map<Class<? extends Throwable>, ExceptionHandlerExecution> handlers,
        Object target,
        Method method
    ) {
        ExceptionHandler exceptionHandler = method.getAnnotation(ExceptionHandler.class);

        for (Class<? extends Throwable> type : exceptionHandler.value()) {
            ExceptionHandlerExecution handlerExecution = new ExceptionHandlerExecution(target, method);
            handlers.put(type, handlerExecution);
            log.info("Add - type: {}, method: {}", type.getSimpleName(), method.getName());
        }
    }
}
