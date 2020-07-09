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

public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private ExceptionHandlerMappingRegistry exceptionHandlerMappingRegistry = new ExceptionHandlerMappingRegistry();
    private HandlerMappingRegistry handlerMappingRegistry = new HandlerMappingRegistry();
    private HandlerAdapterRegistry handlerAdapterRegistry = new HandlerAdapterRegistry();
    private HandlerExecutor handlerExecutor = new HandlerExecutor(handlerAdapterRegistry);

    public void addHandlerMapping(HandlerMapping handlerMapping) {
        handlerMappingRegistry.addHandlerMpping(handlerMapping);
    }

    public void addHandlerAdapter(HandlerAdapter handlerAdapter) {
        handlerAdapterRegistry.addHandlerAdapter(handlerAdapter);
    }

    public void addExceptionHandlerMapping(ExceptionHandlerMapping exceptionHandlerMapping) {
        exceptionHandlerMappingRegistry.addHandlerMapping(exceptionHandlerMapping);
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
            handleException(e, req, resp);
        }
    }

    private void handleException(Throwable exception, HttpServletRequest request, HttpServletResponse response) throws ServletException {
        Optional<Object> handler = exceptionHandlerMappingRegistry.getHandler(exception);

        try {
            if (!handler.isPresent()) {
                throw exception;
            }

            ModelAndView mav = handlerExecutor.handle(request, response, handler.get());

            if (mav != null) {
                render(mav, request, response);
            }
        } catch (Throwable e) {
            logger.error("Exception : {}", e);
            throw new ServletException(exception.getMessage());
        }
    }

    private void render(ModelAndView mav, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        View view = mav.getView();
        view.render(mav.getModel(), req, resp);
    }
}
