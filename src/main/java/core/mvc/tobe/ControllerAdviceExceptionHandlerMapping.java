package core.mvc.tobe;

import com.google.common.collect.Maps;
import core.annotation.ControllerAdvice;
import core.di.context.ApplicationContext;
import core.mvc.ExceptionHandlerMapping;

import java.lang.annotation.Annotation;
import java.util.Map;

public class ControllerAdviceExceptionHandlerMapping implements ExceptionHandlerMapping {
    private ApplicationContext applicationContext;
    private ExceptionHandlerConverter exceptionHandlerConverter;
    private Map<Class<?>, HandlerExecution> exceptionHandlerExecutions = Maps.newHashMap();

    public ControllerAdviceExceptionHandlerMapping(ApplicationContext applicationContext, ExceptionHandlerConverter exceptionHandlerConverter) {
        this.applicationContext = applicationContext;
        this.exceptionHandlerConverter = exceptionHandlerConverter;
    }

    @Override
    public void initialize() {
        Map<Class<?>, Object> adviceHandlers = getControllerAdvices(applicationContext);
        exceptionHandlerExecutions.putAll(exceptionHandlerConverter.convert(adviceHandlers));
    }

    private Map<Class<?>, Object> getControllerAdvices(ApplicationContext applicationContext) {
        Map<Class<?>, Object> controllerAdvices = Maps.newHashMap();
        for (Class<?> clazz : applicationContext.getBeanClasses()) {
            Annotation annotation = clazz.getAnnotation(ControllerAdvice.class);
            if (annotation != null) {
                controllerAdvices.put(clazz, applicationContext.getBean(clazz));
            }
        }
        return controllerAdvices;
    }

    @Override
    public HandlerExecution getExceptionHandler(Throwable throwable, Object handler) {
        return exceptionHandlerExecutions.get(throwable.getClass());
    }
}
