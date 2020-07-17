package core.mvc;

import core.mvc.exception.ExceptionHandlerExecutionException;
import core.mvc.exception.TargetThrowableGettable;
import core.mvc.exception.ViewRenderException;
import core.mvc.tobe.AnnotationExceptionHandlerMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);

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
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        String requestUri = req.getRequestURI();
        log.debug("Method : {}, Request URI : {}", req.getMethod(), requestUri);

        try {
            handleRequest(req, resp);
        }
        catch (Throwable throwable) {
            log.error("", throwable);
            handleThrowable(req, resp, throwable);
        }
    }

    private boolean handleRequest(HttpServletRequest req, HttpServletResponse resp) {
        return handlerMappingRegistry.getHandler(req)
            .map(handler -> {
                ModelAndView mav = handlerExecutor.handle(req, resp, handler);
                render(mav, req, resp);
                return true;
            })
            .orElseGet(() -> {
                resp.setStatus(HttpStatus.NOT_FOUND.value());
                return false;
            });
    }

    private void render(ModelAndView mav, HttpServletRequest req, HttpServletResponse resp) throws ViewRenderException {
        View view = mav.getView();
        view.render(mav.getModel(), req, resp);
    }

    private void handleThrowable(HttpServletRequest req, HttpServletResponse resp, Throwable throwable) {
        if (throwable instanceof TargetThrowableGettable) {
            throwable = ((TargetThrowableGettable)throwable).getTargetThrowable();
        }

        Throwable finalThrowable = throwable;

        exceptionHandlerMappingRegistry.getHandler(throwable.getClass())
            .map(handler -> {
                ModelAndView mav = exceptionHandlerExecutor.handle(finalThrowable, handler);
                render(mav, req, resp);
                return true;
            })
            .orElseGet(() -> {
                throw new ExceptionHandlerExecutionException(finalThrowable);
            });
    }
}
