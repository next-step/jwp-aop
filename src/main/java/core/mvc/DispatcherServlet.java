package core.mvc;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import core.mvc.tobe.ExceptionHandlerMappings;
import core.mvc.tobe.HandlerExecution;

public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private HandlerMappingRegistry handlerMappingRegistry = new HandlerMappingRegistry();

    private HandlerAdapterRegistry handlerAdapterRegistry = new HandlerAdapterRegistry();

    private HandlerExecutor handlerExecutor = new HandlerExecutor(handlerAdapterRegistry);

    private ExceptionHandlerMappings exceptionHandlerMappings;

    public void addHandlerMapping(HandlerMapping handlerMapping) {
        handlerMappingRegistry.addHandlerMpping(handlerMapping);
    }

    public void addHandlerAdapter(HandlerAdapter handlerAdapter) {
        handlerAdapterRegistry.addHandlerAdapter(handlerAdapter);
    }

    public void setExceptionHandlerMapping(ExceptionHandlerMappings exceptionHandlerMappings) {
        this.exceptionHandlerMappings = exceptionHandlerMappings;
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
        } catch (Throwable e) {
            processHandlerException(req, resp, handler, e);
        }
    }

    private void render(ModelAndView mav, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        View view = mav.getView();
        view.render(mav.getModel(), req, resp);
    }

    private void processHandlerException(HttpServletRequest request, HttpServletResponse response, Object handler, Throwable throwable) throws ServletException {
        try {
            HandlerExecution exceptionHandler = getExceptionHandler(handler, throwable);
            ModelAndView mav = exceptionHandler.handle(request, response);
            render(mav, request, response);
        } catch (Exception e) {
            logger.error("Exception : {}", e.getMessage(), e);
            throw new ServletException(e);
        }
    }

    private HandlerExecution getExceptionHandler(Object handler, Throwable throwable) {
        HandlerExecution exceptionHandler = exceptionHandlerMappings.getControllerExceptionHandler(handler.getClass());
        if (exceptionHandler != null) {
            return exceptionHandler;
        }
        return exceptionHandlerMappings.getControllerAdviceExceptionHandler(throwable.getClass());
    }
}
