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

    private final HandlerMappingRegistry handlerMappingRegistry = new HandlerMappingRegistry();

    private final HandlerAdapterRegistry handlerAdapterRegistry = new HandlerAdapterRegistry();

    private final HandlerExecutor handlerExecutor = new HandlerExecutor(handlerAdapterRegistry);

    public void addHandlerMapping(HandlerMapping handlerMapping) {
        handlerMappingRegistry.addHandlerMpping(handlerMapping);
    }

    public void addHandlerAdapter(HandlerAdapter handlerAdapter) {
        handlerAdapterRegistry.addHandlerAdapter(handlerAdapter);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestUri = req.getRequestURI();
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), requestUri);

        try {
            Optional<Object> maybeHandler = handlerMappingRegistry.getHandler(req);
            if (maybeHandler.isEmpty()) {
                resp.setStatus(HttpStatus.NOT_FOUND.value());
                return;
            }


            ModelAndView mav = handlerExecutor.handle(req, resp, maybeHandler.get());
            render(mav, req, resp);
        } catch (Throwable e) {
            logger.error("Exception : {}", e.getMessage());
            proceedExceptionHandle(req, resp, e);
        }
    }

    private void proceedExceptionHandle(HttpServletRequest req, HttpServletResponse resp, Throwable e) throws ServletException {
        Optional<Object> maybeHandler = handlerMappingRegistry.getHandler(e);

        if (maybeHandler.isEmpty()) {
            throw new ServletException(e.getMessage());
        }

        try {
            ModelAndView mav = handlerExecutor.handle(req, resp, maybeHandler.get());
            render(mav, req, resp);
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

    private void render(ModelAndView mav, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        View view = mav.getView();
        view.render(mav.getModel(), req, resp);
    }
}
