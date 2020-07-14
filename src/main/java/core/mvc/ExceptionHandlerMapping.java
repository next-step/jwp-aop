package core.mvc;

import com.google.common.collect.Maps;
import core.annotation.web.ControllerAdvice;
import core.annotation.web.ExceptionHandler;
import core.di.context.ApplicationContext;
import core.mvc.tobe.ExceptionHandlerExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Map;

public class ExceptionHandlerMapping implements HandlerMapping<Throwable> {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerMapping.class);

    private final ApplicationContext applicationContext;
    private final Map<Class<? extends Throwable>, ExceptionHandlerExecution> handlerExecutions = Maps.newHashMap();

    public ExceptionHandlerMapping(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void initialize() {
        final Map<Class<?>, Object> controllerAdvice = getControllerAdvice(applicationContext);
        for (Map.Entry<Class<?>, Object> entry : controllerAdvice.entrySet()) {
            registerExceptionHandlerExecutions(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public ExceptionHandlerExecution getHandler(Throwable type) {
        return handlerExecutions.get(type.getClass());
    }

    private Map<Class<?>, Object> getControllerAdvice(ApplicationContext ac) {
        final Map<Class<?>, Object> advice = Maps.newHashMap();
        ac.getBeanClasses().stream()
                .filter(clazz -> clazz.isAnnotationPresent(ControllerAdvice.class))
                .forEach(adviceClass -> advice.put(adviceClass, ac.getBean(adviceClass)));
        return advice;
    }

    private void registerExceptionHandlerExecutions(Class<?> clazz, Object bean) {
        for (Method method : clazz.getMethods()) {
            final ExceptionHandler handler = method.getAnnotation(ExceptionHandler.class);
            if (handler != null) {
                register(bean, method, handler);
            }
        }
    }

    private void register(Object bean, Method method, ExceptionHandler handler) {
        final Class<? extends Throwable>[] types = handler.value();
        final ExceptionHandlerExecution exceptionHandler = new ExceptionHandlerExecution(bean, method);
        for (Class<? extends Throwable> type : types) {
            handlerExecutions.put(type, exceptionHandler);
        }
    }
}
