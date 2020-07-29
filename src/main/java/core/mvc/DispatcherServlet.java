package core.mvc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Slf4j
public class DispatcherServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private HandlerMappingRegistry handlerMappingRegistry = new HandlerMappingRegistry();
    private HandlerAdapterRegistry handlerAdapterRegistry = new HandlerAdapterRegistry();
    private HandlerExecutor handlerExecutor = new HandlerExecutor(handlerAdapterRegistry);
    private ExceptionMappingRegistry exceptionMappingRegistry = new ExceptionMappingRegistry();

    public void addHandlerMapping(HandlerMapping handlerMapping) {
        handlerMappingRegistry.addHandlerMapping(handlerMapping);
    }

    public void addHandlerAdapter(HandlerAdapter handlerAdapter) {
        handlerAdapterRegistry.addHandlerAdapter(handlerAdapter);
    }

    public void addExceptionMapping(ExceptionMapping exceptionMapping) {
        exceptionMappingRegistry.addExceptionMapping(exceptionMapping);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        String requestUri = req.getRequestURI();
        log.debug("Method : {}, Request URI : {}", req.getMethod(), requestUri);

        try {
            Optional<Object> maybeHandler = handlerMappingRegistry.getHandler(req);
            if (!maybeHandler.isPresent()) {
                resp.setStatus(HttpStatus.NOT_FOUND.value());
                return;
            }

            ModelAndView modelAndView = handlerExecutor.handle(req, resp, maybeHandler.get());
            render(modelAndView, req, resp);
        } catch (Exception e) {
            log.error("Exception : {}", e.getMessage());
            handleException(req, resp, e);
        }
    }

    private void handleException(HttpServletRequest req, HttpServletResponse resp, Exception e) throws ServletException {
        try {
            Optional<ExceptionHandler> maybeExceptionHandler = exceptionMappingRegistry.getHandler(e.getClass());
            if (!maybeExceptionHandler.isPresent()) {
                throw new ServletException(e.getMessage());
            }

            ExceptionHandler exceptionHandler = maybeExceptionHandler.get();
            ModelAndView modelAndView = exceptionHandler.handle(req, resp);
            render(modelAndView, req, resp);
        } catch (Exception ex) {
            throw new ServletException(ex.getMessage());
        }
    }

    private void render(ModelAndView mav, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        View view = mav.getView();
        view.render(mav.getModel(), req, resp);
    }

}
