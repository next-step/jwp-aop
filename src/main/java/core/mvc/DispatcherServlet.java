package core.mvc;

import core.mvc.tobe.AnnotationExceptionHandlerMapping;
import core.mvc.tobe.ExceptionHandlerExecution;
import java.io.IOException;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;

public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private HandlerMappingRegistry handlerMappingRegistry = new HandlerMappingRegistry();

    private HandlerAdapterRegistry handlerAdapterRegistry = new HandlerAdapterRegistry();

    private HandlerExecutor handlerExecutor = new HandlerExecutor(handlerAdapterRegistry);

    public void addHandlerMapping(HandlerMapping handlerMapping) {
        handlerMappingRegistry.addHandlerMapping(handlerMapping);
    }

    public void addExceptionHandlerMapping(AnnotationExceptionHandlerMapping annotationExceptionHandlerMapping) {
        handlerMappingRegistry.addExceptionHandlerMapping(annotationExceptionHandlerMapping);
    }

    public void addHandlerAdapter(HandlerAdapter handlerAdapter) {
        handlerAdapterRegistry.addHandlerAdapter(handlerAdapter);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestUri = req.getRequestURI();
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), requestUri);

        ModelAndView mav = null;
        Exception dispatchException = null;
        Optional<Object> maybeHandler = getHandler(req, resp);
        if (!maybeHandler.isPresent()) {
            return;
        }

        try {
            mav = handlerExecutor.handle(req, resp, maybeHandler.get());
        } catch (Exception e) {
            dispatchException = e;
        }

        processDispatchResult(req, resp, maybeHandler.get(), mav, dispatchException);
    }

    private Optional<Object> getHandler(HttpServletRequest req, HttpServletResponse resp) {
        Optional<Object> maybeHandler;
        maybeHandler = handlerMappingRegistry.getHandler(req);
        if (!maybeHandler.isPresent()) {
            resp.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }
        return maybeHandler;
    }

    private void processDispatchResult(HttpServletRequest request, HttpServletResponse response,
        @Nullable Object handler, @Nullable ModelAndView mv, @Nullable Exception exception) throws ServletException {
        if (exception != null) {
            try {
                mv = processHandlerException(request, response, handler, exception);

                if (mv != null) {
                    render(mv, request, response);
                    return;
                }
            } catch (Exception e) {
                logger.error("Exception : {}", e);
                throw new ServletException(e.getMessage());
            }
        }

        if (logger.isTraceEnabled()) {
            logger.trace("No view rendering, null ModelAndView returned.");
        }
    }

    @Nullable
    protected ModelAndView processHandlerException(HttpServletRequest request, HttpServletResponse response,
        @Nullable Object handler, Exception ex) throws Exception {
        ModelAndView exceptionMav = null;

        Optional<Object> maybeHandler = this.handlerMappingRegistry.getExceptionHandler(ex);
        if (maybeHandler.isPresent()) {
            ExceptionHandlerExecution exceptionHandlerExecution = (ExceptionHandlerExecution) maybeHandler.get();
            exceptionMav = exceptionHandlerExecution.handle(ex);
        }

        if (exceptionMav != null && exceptionMav.hasView()) {
            return exceptionMav;
        }

        return new ModelAndView(new JspView("/"));
    }

    private void render(ModelAndView mav, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        View view = mav.getView();
        view.render(mav.getModel(), req, resp);
    }
}
