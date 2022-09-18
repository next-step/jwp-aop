package core.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private final HandlerMappingRegistry<HttpServletRequest> handlerMappingRegistry = new HandlerMappingRegistry<>();
    private final HandlerAdapterRegistry<HandlerAdapter> handlerAdapterRegistry = new HandlerAdapterRegistry<>();
    private final HandlerExecutor handlerExecutor = new HandlerExecutor(handlerAdapterRegistry);

    private final HandlerMappingRegistry<Throwable> exceptionHandlerMappingRegistry = new HandlerMappingRegistry<>();
    private final HandlerAdapterRegistry<ExceptionHandlerAdapter> exceptionHandlerAdapterRegistry = new HandlerAdapterRegistry<>();
    private final ExceptionHandlerExecutor exceptionHandlerExecutor = new ExceptionHandlerExecutor(exceptionHandlerAdapterRegistry);

    public void addHandlerMapping(HandlerMapping<HttpServletRequest> handlerMapping) {
        handlerMappingRegistry.addHandlerMapping(handlerMapping);
    }

    public void addHandlerAdapter(HandlerAdapter handlerAdapter) {
        handlerAdapterRegistry.addHandlerAdapter(handlerAdapter);
    }

    public void addExceptionHandlerMapping(HandlerMapping<Throwable> handlerMapping) {
        exceptionHandlerMappingRegistry.addHandlerMapping(handlerMapping);
    }

    public void addExceptionHandlerAdapter(ExceptionHandlerAdapter exceptionHandlerAdapter) {
        exceptionHandlerAdapterRegistry.addHandlerAdapter(exceptionHandlerAdapter);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
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
            resolveException(e, req, resp);
        }
    }

    private void resolveException(Throwable exception, HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            Object exceptionHandler = exceptionHandlerMappingRegistry.getHandler(exception)
                    .orElseThrow(() -> new ServletException(exception));
            ModelAndView modelAndView = exceptionHandlerExecutor.handle(exception, request, response, exceptionHandler);
            render(modelAndView, request, response);
        } catch (Throwable ex) {
            logger.error("Exception : {}", ex);
            throw new ServletException(ex.getMessage());
        }
    }

    private void render(ModelAndView mav, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        View view = mav.getView();
        view.render(mav.getModel(), req, resp);
    }
}
