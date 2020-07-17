package core.mvc.tobe;

import core.mvc.ModelAndView;
import core.mvc.exception.ExceptionHandlerExecutionException;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
public class ExceptionHandlerExecution {
    private Object target;
    private Method method;

    public ExceptionHandlerExecution(Object target, Method method) {
        this.target = target;
        this.method = method;
    }

    public ModelAndView handle(Throwable throwable) throws ExceptionHandlerExecutionException {
        try {
            return (ModelAndView) method.invoke(target, throwable);
        }
        catch (IllegalAccessException | InvocationTargetException e) {
            throw new ExceptionHandlerExecutionException(throwable);
        }
    }
}
