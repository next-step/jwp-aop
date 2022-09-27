package core.mvc.tobe;

import core.annotation.web.ControllerAdvice;
import core.di.context.ApplicationContext;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ExceptionHandlerMapping {

    private final ApplicationContext applicationContext;
    private final ExceptionHandlerConverter handlerConverter;

    private final Map<Class<? extends Throwable>, HandlerExecution> handlerExecutions = new HashMap<>();

    public ExceptionHandlerMapping(final ApplicationContext applicationContext, final ExceptionHandlerConverter handlerConverter) {
        this.applicationContext = applicationContext;
        this.handlerConverter = handlerConverter;
    }

    public void initialize() {
        Map<Class<?>, Object> controllerAdvices = getControllerAdvices(applicationContext);
        for (final Object controllerAdvice : controllerAdvices.values()) {
            handlerExecutions.putAll(handlerConverter.convert(controllerAdvice));
        }
    }

    private Map<Class<?>, Object> getControllerAdvices(final ApplicationContext applicationContext) {
        return applicationContext.getBeanClasses().stream()
            .filter(aClass -> aClass.isAnnotationPresent(ControllerAdvice.class))
            .collect(Collectors.toMap(aClass -> aClass, applicationContext::getBean));
    }

    public HandlerExecution getHandler(Class<? extends Throwable> exceptionClazz) {
        return handlerExecutions.get(exceptionClazz);
    }
}
