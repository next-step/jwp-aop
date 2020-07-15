package core.mvc;

import core.mvc.tobe.ExceptionHandlerMapping;
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

    private ExceptionHandlerMapping exceptionHandlerMapping;

    public void addHandlerMapping(HandlerMapping handlerMapping) {
        handlerMappingRegistry.addHandlerMpping(handlerMapping);
    }

    public void addHandlerAdapter(HandlerAdapter handlerAdapter) {
        handlerAdapterRegistry.addHandlerAdapter(handlerAdapter);
    }

    public void addExceptionHandlerMapping(ExceptionHandlerMapping exceptionHandlerMapping) {
        this.exceptionHandlerMapping = exceptionHandlerMapping;
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestUri = req.getRequestURI();
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), requestUri);

        try {
            Optional<Object> maybeHandler = handlerMappingRegistry.getHandler(req);
            if (!maybeHandler.isPresent()) {
                resp.setStatus(HttpStatus.NOT_FOUND.value());
                return;
            }

            ModelAndView mav = handlerExecutor.handle(req, resp, maybeHandler.get());
            render(mav, req, resp);
        } catch (Throwable e) {
            handleException(req, resp, e);
        }
    }

    private void render(ModelAndView mav, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        View view = mav.getView();
        view.render(mav.getModel(), req, resp);
    }

    private void handleException(HttpServletRequest req, HttpServletResponse res, Throwable throwable) throws ServletException {
        if(exceptionHandlerMapping == null) {
            logger.error("Exception : {}", throwable);
            throw new ServletException(throwable.getMessage());
        }

        try {
            HandlerExecution execution = exceptionHandlerMapping.getHandler(throwable);
            if(execution == null) {
                throw throwable;
            }

            ModelAndView modelAndView = execution.handle(req, res, throwable);
            render(modelAndView, req, res);
        } catch (Throwable e) {
            logger.error("Exception : {}", e);
            throw new ServletException(e);
        }
    }
}
