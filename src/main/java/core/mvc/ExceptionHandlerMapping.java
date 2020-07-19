package core.mvc;

import core.annotation.web.ControllerAdvice;
import core.annotation.web.ExceptionHandler;
import core.di.context.ApplicationContext;
import core.mvc.tobe.ExceptionHandlerConverter;
import core.mvc.tobe.HandlerExecution;
import java.util.LinkedHashMap;
import java.util.Map;

public class ExceptionHandlerMapping {

    private final Map<Class<? extends Throwable>, HandlerExecution> exceptionHandlers = new LinkedHashMap<>();
    private final ApplicationContext ac;
    private final ExceptionHandlerConverter exceptionHandlerConverter;

    public ExceptionHandlerMapping(ApplicationContext ac,
        ExceptionHandlerConverter exceptionHandlerConverter) {
        this.ac = ac;
        this.exceptionHandlerConverter = exceptionHandlerConverter;
    }

    public void initialize() {
        Object exceptionAdviceBean = findExceptionAdviceBean();
        if(exceptionAdviceBean == null){
            return;
        }

        this.exceptionHandlers.putAll(exceptionHandlerConverter.convert(exceptionAdviceBean));
    }


    private Object findExceptionAdviceBean() {
        for (Class<?> clazz : ac.getBeanClasses()) {
            ControllerAdvice annotation = clazz.getAnnotation(ControllerAdvice.class);
            if(annotation != null){
                return ac.getBean(clazz);
            }
        }
        return null;
    }

    public HandlerExecution getHandler(Throwable e) {
        return exceptionHandlers.get(e.getClass());
    }
}
