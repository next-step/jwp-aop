package core.mvc.tobe;

import core.annotation.web.ControllerAdvice;
import core.di.context.ApplicationContext;
import core.mvc.ModelAndView;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public final class HandlerExceptionResolver {

    private final Map<Class<? extends Throwable>, ExceptionHandlerExecution> executions;

    private HandlerExceptionResolver(ApplicationContext context) {
        Assert.notNull(context, "'context' must not be null");
        executions = context.getBean(ExceptionHandlerConverter.class)
                .convert(controllerAdvices(context));
    }

    public static HandlerExceptionResolver from(ApplicationContext context) {
        return new HandlerExceptionResolver(context);
    }

    public ModelAndView resolveException(Throwable exception, HttpServletRequest request, HttpServletResponse response) throws Throwable {
        return Optional.of(executions.get(exception.getClass()))
                .orElseThrow(() -> exception)
                .handle(exception, request, response);
    }

    private List<Object> controllerAdvices(ApplicationContext context) {
        return context.getBeanClasses()
                .stream()
                .filter(clazz -> clazz.isAnnotationPresent(ControllerAdvice.class))
                .map(context::getBean)
                .collect(Collectors.toList());
    }
}
