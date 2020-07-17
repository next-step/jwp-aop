package core.mvc.tobe;

import com.google.common.collect.Maps;
import core.annotation.ControllerAdvice;
import core.di.context.ApplicationContext;
import core.mvc.ExceptionHandlerMapping;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.util.Map;

@Slf4j
public class AnnotationExceptionHandlerMapping implements ExceptionHandlerMapping {
    private ApplicationContext applicationContext;
    private ExceptionHandlerExtractor exceptionHandlerExtractor;

    private Map<Class<? extends Throwable>, ExceptionHandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationExceptionHandlerMapping(ApplicationContext applicationContext, ExceptionHandlerExtractor exceptionHandlerExtractor) {
        this.applicationContext = applicationContext;
        this.exceptionHandlerExtractor = exceptionHandlerExtractor;
    }

    public void initialize() {
        Map<Class<?>, Object> controllerAdvices = getControllerAdvices(applicationContext);
        handlerExecutions.putAll(exceptionHandlerExtractor.extract(controllerAdvices));
        log.info("Initialized AnnotationExceptionHandlerMapping!");
    }

    private Map<Class<?>, Object> getControllerAdvices(ApplicationContext ac) {
        Map<Class<?>, Object> controllerAdvices = Maps.newHashMap();

        for (Class<?> clazz : ac.getBeanClasses()) {
            Annotation annotation = clazz.getAnnotation(ControllerAdvice.class);
            if (annotation != null) {
                controllerAdvices.put(clazz, ac.getBean(clazz));
            }
        }
        return controllerAdvices;
    }

    @Override
    public Object getHandler(Class<? extends Throwable> throwable) {
        return handlerExecutions.get(throwable);
    }
}
