package core.mvc.tobe;

import core.mvc.ModelAndView;

import java.lang.reflect.Method;

public class ExceptionHandlerExecution {
    private final Object target;
    private final Method method;

    public ExceptionHandlerExecution(Object target, Method method) {
        this.target = target;
        this.method = method;
    }

    public ModelAndView handle() throws Exception {
        return (ModelAndView) method.invoke(target);
    }
}
