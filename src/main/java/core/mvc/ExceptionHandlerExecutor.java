package core.mvc;


import core.mvc.tobe.HandlerExecution;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExceptionHandlerExecutor {

    public static final String EXCEPTION_ATTRIBUTE_KEY = ExceptionHandlerExecutor.class.getSimpleName()+":exception";

    public ModelAndView handle(HttpServletRequest req, HttpServletResponse res, Throwable e, HandlerExecution handlerExecution)
        throws Exception {
        req.setAttribute(EXCEPTION_ATTRIBUTE_KEY, e);
        return handlerExecution.handle(req, res);
    }
}
