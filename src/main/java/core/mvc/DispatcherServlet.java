package core.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import core.mvc.tobe.ExceptionHandlerMapping;
import core.mvc.tobe.HandlerExecution;

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

    public void setExceptionHandlerMapping(ExceptionHandlerMapping exceptionHandlerMapping) {
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
            processHandlerException(req, resp, e);
        }
    }

    private void render(ModelAndView mav, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        View view = mav.getView();
        view.render(mav.getModel(), req, resp);
    }

    private void processHandlerException(HttpServletRequest request, HttpServletResponse response, Throwable throwable) throws ServletException {
        try {
            HandlerExecution exceptionHandler = exceptionHandlerMapping.getHandler(throwable);
            ModelAndView mav = exceptionHandler.handle(request, response);
            render(mav, request, response);
        } catch (Exception e) {
            logger.error("Exception : {}", e.getMessage(), e);
            throw new ServletException(e);
        }
    }
}
