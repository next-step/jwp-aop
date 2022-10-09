package core.mvc.tobe;

import com.google.common.collect.Maps;
import core.di.context.ApplicationContext;
import core.mvc.ExceptionHandlerMapping;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class AnnotationExceptionHandlerMapping implements ExceptionHandlerMapping {

    private Logger logger = LoggerFactory.getLogger(AnnotationExceptionHandlerMapping.class);

    private final ApplicationContext applicationContext;
    private Map<Class<?>, ExceptionHandlerExecution> exceptionHandlerExecutions = Maps.newHashMap();

    public AnnotationExceptionHandlerMapping(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void initialize() {
        Map<Class<?>, Object> controllers = this.getControllers(this.applicationContext);
        this.exceptionHandlerExecutions.putAll(getExceptionHandlerExecutions(controllers));
        logger.info("Initialized AnnotationExceptionHandlerMapping!");
    }

    private Map<Class<?>, ExceptionHandlerExecution> getExceptionHandlerExecutions(Map<Class<?>, Object> controllers) {
        Map<Class<?>, ExceptionHandlerExecution> handlers = Maps.newHashMap();
        for (Entry<Class<?>, Object> controllerEntry : controllers.entrySet()) {
            Class clazz = controllerEntry.getKey();
            Object value = controllerEntry.getValue();

            Arrays.stream(clazz.getMethods())
                .filter(method -> method.isAnnotationPresent(ExceptionHandler.class))
                .forEach(method -> {
                    ExceptionHandler exceptionHandler = method.getAnnotation(ExceptionHandler.class);
                    Arrays.stream(exceptionHandler.value())
                        .forEach(targetException -> {
                            ExceptionHandlerExecution handlerExecution = new ExceptionHandlerExecution(value, method);
                            this.exceptionHandlerExecutions.put(targetException, handlerExecution);
                        });
                });
        }
        return handlers;
    }

    private Map<Class<?>, Object> getControllers(ApplicationContext ac) {
        Map<Class<?>, Object> controllers = Maps.newHashMap();
        for (Class<?> clazz : ac.getBeanClasses()) {
            Annotation annotation = clazz.getAnnotation(ControllerAdvice.class);
            if (annotation != null) {
                controllers.put(clazz, ac.getBean(clazz));
            }
        }
        return controllers;
    }

    @Override
    public ExceptionHandlerExecution getHandler(Exception exception) {
        logger.debug("exception : {}", exception);
        return this.exceptionHandlerExecutions.getOrDefault(exception.getClass(), null);
    }

}
