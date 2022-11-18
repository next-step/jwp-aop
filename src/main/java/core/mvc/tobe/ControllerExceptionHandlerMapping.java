package core.mvc.tobe;

import core.annotation.web.Controller;
import core.di.context.ApplicationContext;
import core.mvc.HandlerMapping;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;

public class ControllerExceptionHandlerMapping implements HandlerMapping {

    private final ApplicationContext applicationContext;
    private final ExceptionHandlerConverter handlerConverter;

    private final Map<Class<? extends Throwable>, HandlerExecution> handlerExecutions = new HashMap<>();

    public ControllerExceptionHandlerMapping(final ApplicationContext applicationContext,
                                             final ExceptionHandlerConverter handlerConverter) {
        this.applicationContext = applicationContext;
        this.handlerConverter = handlerConverter;
    }

    public void initialize() {
        Map<Class<?>, Object> controllerExceptionHandlers = getControllerExceptionHandlers(applicationContext);
        for (final Object controllerExceptionHandler : controllerExceptionHandlers.values()) {
            handlerExecutions.putAll(handlerConverter.convert(controllerExceptionHandler));
        }
    }

    @Override
    public HandlerExecution getHandler(final HttpServletRequest request) {
        final Object exceptionHandler = request.getAttribute("exceptionHandler");

        Class<?> exceptionHandlerClass = exceptionHandler.getClass();
        if (InvocationTargetException.class.isAssignableFrom(exceptionHandlerClass)) {
            return handlerExecutions.get(((InvocationTargetException) exceptionHandler).getTargetException().getClass());
        }

        if (handlerExecutions.containsKey(exceptionHandlerClass)) {
            return handlerExecutions.get(exceptionHandlerClass);
        }

        return null;
    }

    private Map<Class<?>, Object> getControllerExceptionHandlers(final ApplicationContext applicationContext) {
        return applicationContext.getBeanClasses().stream()
                .filter(aClass -> aClass.isAnnotationPresent(Controller.class))
                .collect(Collectors.toMap(aClass -> aClass, applicationContext::getBean));
    }

}
