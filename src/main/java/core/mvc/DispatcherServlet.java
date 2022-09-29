package core.mvc;

import core.mvc.tobe.ExceptionHandlerMapping;
import core.mvc.tobe.ExceptionHandlerMappingRegistry;
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

    private HandlerAdapterRegistry handlerAdapterRegistry = new HandlerAdapterRegistry();

    private HandlerExecutor handlerExecutor = new HandlerExecutor(handlerAdapterRegistry);

    private final ExceptionHandlerMappingRegistry exceptionHandlerMappingRegistry = new ExceptionHandlerMappingRegistry();

    public void addHandlerMapping(HandlerMapping handlerMapping) {
        handlerMappingRegistry.addHandlerMpping(handlerMapping);
    }

    public void addHandlerAdapter(HandlerAdapter handlerAdapter) {
        handlerAdapterRegistry.addHandlerAdapter(handlerAdapter);
    }

    public void addExceptionHandlerMapping(ExceptionHandlerMapping exceptionHandlerMapping) {
        exceptionHandlerMappingRegistry.addExceptionHandlerMapping(exceptionHandlerMapping);
    }

    public void initStrategiesByExceptionHandlers() {
        exceptionHandlerMappingRegistry.initExceptionHandlerMappings();
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

    private HandlerExecution getExceptionHandler(Object handler, Throwable throwable) throws ServletException {
        HandlerExecution exceptionHandler = exceptionHandlerMappingRegistry.getExceptionHandler(handler, throwable);
        if (exceptionHandler == null) {
            throw new ServletException("No exception handler is found");
        }
        return exceptionHandler;
    }
}
