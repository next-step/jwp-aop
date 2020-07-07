package core.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DummyExceptionHandlerExecutor implements ExceptionHandlerExecutor {

    @Override
    public ModelAndView handle(Throwable exception, HttpServletRequest request, HttpServletResponse response) throws Throwable {
        throw exception;
    }

}
