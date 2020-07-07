package core.mvc;

import core.annotation.web.ExceptionHandler;
import core.mvc.tobe.HandlerExecution;
import core.mvc.tobe.support.ArgumentResolver;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.*;

public class DefaultExceptionAdaptor implements ExceptionAdaptor {
    private static final ParameterNameDiscoverer nameDiscoverer = new LocalVariableTableParameterNameDiscoverer();

    private final Map<Class<? extends Throwable>, HandlerExecution> exceptionHandlers = new HashMap<>();
    private final List<ArgumentResolver> argumentResolvers;

    public DefaultExceptionAdaptor(List<ArgumentResolver> argumentResolvers, List<Object> controllerAdvices) {
        this.argumentResolvers = argumentResolvers;

        controllerAdvices.forEach(this::initControllerAdvice);
    }

    private void initControllerAdvice(Object controllerAdvice) {
        Class<?> adviceClass = controllerAdvice.getClass();

        Arrays.stream(adviceClass.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(ExceptionHandler.class))
                .forEach(method -> putHandlerExecution(controllerAdvice, method));
    }

    private void putHandlerExecution(Object controllerAdvice, Method method) {
        HandlerExecution handlerExecution =
                new HandlerExecution(nameDiscoverer, argumentResolvers, controllerAdvice, method);
        ExceptionHandler exceptionHandler = method.getAnnotation(ExceptionHandler.class);
        Class<? extends Throwable>[] classes = exceptionHandler.value();

        Arrays.stream(classes)
                .forEach(clazz -> exceptionHandlers.put(clazz, handlerExecution));
    }

    @Override
    public ModelAndView handle(Throwable exception, HttpServletRequest request, HttpServletResponse response) throws Throwable {
        if (!exceptionHandlers.containsKey(exception.getClass())) {
            throw exception;
        }

        HandlerExecution handlerExecution = exceptionHandlers.get(exception.getClass());

        return handlerExecution.handle(request, response);
    }

}
