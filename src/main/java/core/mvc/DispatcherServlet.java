package core.mvc;

import core.mvc.tobe.HandlerExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private HandlerMappingRegistry handlerMappingRegistry = new HandlerMappingRegistry();

    private ExceptionHandlerMappingRegistry exceptionHandlerMappingRegistry = new ExceptionHandlerMappingRegistry();

    private HandlerAdapterRegistry handlerAdapterRegistry = new HandlerAdapterRegistry();

    private HandlerExecutor handlerExecutor = new HandlerExecutor(handlerAdapterRegistry);

    public void addHandlerMapping(HandlerMapping handlerMapping) {
        handlerMappingRegistry.addHandlerMapping(handlerMapping);
    }

    public void addExceptionHandlerMapping(ExceptionHandlerMapping exceptionHandlerMapping) {
        exceptionHandlerMappingRegistry.addExceptionHandlerMapping(exceptionHandlerMapping);
    }

    public void addHandlerAdapter(HandlerAdapter handlerAdapter) {
        handlerAdapterRegistry.addHandlerAdapter(handlerAdapter);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestUri = req.getRequestURI();
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), requestUri);

        Object handler = null;
        try {
            Optional<Object> maybeHandler = handlerMappingRegistry.getHandler(req);
            if (maybeHandler.isEmpty()) {
                resp.setStatus(HttpStatus.NOT_FOUND.value());
                return;
            }

            handler = maybeHandler.get();
            ModelAndView mav = handlerExecutor.handle(req, resp, handler);
            render(mav, req, resp);
        } catch (Throwable throwable) {
            exceptionHandle(throwable, handler, req, resp);
        }
    }

    private void exceptionHandle(Throwable throwable, Object handler, HttpServletRequest req, HttpServletResponse resp) {
        try {
            HandlerExecution exceptionHandler = getExceptionHandler(throwable, handler);
            ModelAndView mav = exceptionHandler.handle(req, resp);
            render(mav, req, resp);
        } catch (Exception e) {
            logger.error("ExceptionHandler Exception : {}", e);
            throw new RuntimeException(e);
        }
    }

    private HandlerExecution getExceptionHandler(Throwable e, Object handler) throws ServletException {
        HandlerExecution exceptionHandler = exceptionHandlerMappingRegistry.getExceptionHandler(e, handler);
        if (exceptionHandler == null) {
            logger.error("Servlet Exception : {}", e);
            throw new ServletException(e.getMessage());
        }
        return exceptionHandler;
    }

    private void render(ModelAndView mav, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        View view = mav.getView();
        view.render(mav.getModel(), req, resp);
    }
}
