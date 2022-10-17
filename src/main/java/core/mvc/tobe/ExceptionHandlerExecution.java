package core.mvc.tobe;

import core.mvc.ModelAndView;
import java.lang.reflect.Method;

public class ExceptionHandlerExecution {

    private Object target;
    private Method method;

    public ExceptionHandlerExecution(Object target, Method method) {
        this.target = target;
        this.method = method;
    }

    public ModelAndView handle(Exception ex) throws Exception {
        return (ModelAndView) method.invoke(target, ex);
    }
}
