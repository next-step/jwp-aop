package core.mvc;

import core.mvc.tobe.AnnotationExceptionHandlerMapping;
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

    private ExceptionHandlerMappingRegistry exceptionHandlerMappingRegistry = new ExceptionHandlerMappingRegistry();
    private ExceptionHandlerAdapterRegistry exceptionHandlerAdapterRegistry = new ExceptionHandlerAdapterRegistry();
    private ExceptionHandlerExecutor exceptionHandlerExecutor = new ExceptionHandlerExecutor(exceptionHandlerAdapterRegistry);

    public void addHandlerMapping(HandlerMapping handlerMapping) {
        handlerMappingRegistry.addHandlerMpping(handlerMapping);
    }

    public void addHandlerAdapter(HandlerAdapter handlerAdapter) {
        handlerAdapterRegistry.addHandlerAdapter(handlerAdapter);
    }

    public void addExceptionHandlerMapping(AnnotationExceptionHandlerMapping exceptionHandlerMapping) {
        exceptionHandlerMappingRegistry.addExceptionHandlerMapping(exceptionHandlerMapping);
    }

    public void addExceptionHandlerAdapter(ExceptionHandlerAdapter exceptionHandlerAdapter) {
        exceptionHandlerAdapterRegistry.addExceptionHandlerAdapter(exceptionHandlerAdapter);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestUri = req.getRequestURI();
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), requestUri);

        try {
            if (!handleRequest(req, resp)) {
                return;
            }
        }
        catch (Throwable e) {
            handleThrowable(e);
        }
    }

    private boolean handleRequest(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Optional<Object> maybeHandler = handlerMappingRegistry.getHandler(req);
        if (!maybeHandler.isPresent()) {
            resp.setStatus(HttpStatus.NOT_FOUND.value());
            return false;
        }

        ModelAndView mav = handlerExecutor.handle(req, resp, maybeHandler.get());
        render(mav, req, resp);
        return true;
    }

    private void render(ModelAndView mav, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        View view = mav.getView();
        view.render(mav.getModel(), req, resp);
    }

    private void handleThrowable(Throwable e) throws ServletException {
        logger.error("Throwable : {}", e);

        Optional<Object> maybeHandler = exceptionHandlerMappingRegistry.getHandler(e.getClass());

        if (!maybeHandler.isPresent()) {
            throw new ServletException(e.getMessage());
        }

        try {
            exceptionHandlerExecutor.handle(e, maybeHandler.get());
        }
        catch (Exception ex) {
            logger.error("Throwable : {}", ex);
            throw new ServletException(ex.getMessage());
        }
    }
}
