package core.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ExceptionHandler {

    ModelAndView handle(HttpServletRequest request, HttpServletResponse response);

}
