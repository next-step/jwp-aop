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

public class DispatcherServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private HandlerMappingRegistry handlerMappingRegistry = new HandlerMappingRegistry();
    private HandlerMappingRegistry exceptionHandlerMappingRegistry = new HandlerMappingRegistry();

    private HandlerAdapterRegistry handlerAdapterRegistry = new HandlerAdapterRegistry();

    private HandlerExecutor handlerExecutor = new HandlerExecutor(handlerAdapterRegistry);

    public void addHandlerMapping(HandlerMapping handlerMapping) {
        handlerMappingRegistry.addHandlerMapping(handlerMapping);
    }

    public void addHandlerAdapter(HandlerAdapter handlerAdapter) {
        handlerAdapterRegistry.addHandlerAdapter(handlerAdapter);
    }

    public void addExceptionHandlerMapping(HandlerMapping handlerMapping) {
        exceptionHandlerMappingRegistry.addHandlerMapping(handlerMapping);
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
            logger.error("Exception : {}", e);
            req.setAttribute("exceptionHandle", e);
            exceptionHandle(req, resp, e);
        }
    }

    private void exceptionHandle(HttpServletRequest req, HttpServletResponse resp, Throwable e) throws
        ServletException {
        var handler = exceptionHandlerMappingRegistry.getHandler(req)
            .orElseThrow(() -> new ServletException(e.getMessage(), e));

        try {
            var modelAndView = handlerExecutor.handle(req, resp, handler);
            render(modelAndView, req, resp);
        } catch (Exception ex) {
            throw new ServletException(e.getMessage(), ex);
        }
    }

    private void render(ModelAndView mav, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        View view = mav.getView();
        view.render(mav.getModel(), req, resp);
    }
}
