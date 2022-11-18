package core.mvc.tobe;

import core.annotation.web.ControllerAdvice;
import core.di.context.ApplicationContext;
import core.mvc.HandlerMapping;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;

public class ExceptionHandlerMapping implements HandlerMapping {

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

    @Override
    public HandlerExecution getHandler(final HttpServletRequest request) {
        final Object exceptionHandler = request.getAttribute("exceptionHandler");
        return handlerExecutions.get(exceptionHandler.getClass());
    }

    private Map<Class<?>, Object> getControllerAdvices(final ApplicationContext applicationContext) {
        return applicationContext.getBeanClasses().stream()
            .filter(aClass -> aClass.isAnnotationPresent(ControllerAdvice.class))
            .collect(Collectors.toMap(aClass -> aClass, applicationContext::getBean));
    }

}
