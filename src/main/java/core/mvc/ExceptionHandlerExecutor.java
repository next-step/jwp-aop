package core.mvc;


import core.mvc.tobe.HandlerExecution;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExceptionHandlerExecutor {

    public ModelAndView handle(HttpServletRequest req, HttpServletResponse res, Throwable e, HandlerExecution handlerExecution)
        throws Exception {
        req.setAttribute("exception", e);
        return handlerExecution.handle(req, res);
    }
}
