package core.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private HandlerMappingResolver handlerMappingResolver = new HandlerMappingResolver();

    private HandlerAdapterRegistry handlerAdapterRegistry = new HandlerAdapterRegistry();

    private HandlerExecutor handlerExecutor = new HandlerExecutor(handlerAdapterRegistry);

    public void addHandlerMapping(HandlerMapping handlerMapping) {
        handlerMappingResolver.addHandlerMpping(handlerMapping);
    }

    public void addHandlerAdapter(HandlerAdapter handlerAdapter) {
        handlerAdapterRegistry.addHandlerAdapter(handlerAdapter);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestUri = req.getRequestURI();
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), requestUri);

        try {
            Optional<HandlerExecutionChain> maybeHandlerExecution =
                    handlerMappingResolver.getHandler(req);
            if (!maybeHandlerExecution.isPresent()) {
                resp.setStatus(HttpStatus.NOT_FOUND.value());
                return;
            }

            if (!preHandle(req, resp, maybeHandlerExecution.get())) {
                return;
            }

            ModelAndView mav = handlerExecutor.handle(req, resp, maybeHandlerExecution.get());

            if (postHandle(req, resp, maybeHandlerExecution.get(), mav)) {

            }

            render(mav, req, resp);
        } catch (Throwable e) {
            logger.error("Exception : {}", e);
            throw new ServletException(e.getMessage());
        }
    }

    private boolean postHandle(HttpServletRequest req, HttpServletResponse resp,
                               HandlerExecutionChain hec, ModelAndView mav) {
        final List<HandlerInterceptor> interceptors = hec.getInterceptors();
        for (int i = interceptors.size() - 1; i >= 0; i--) {
            final HandlerInterceptor interceptor = interceptors.get(i);
            interceptor.postHandle(req, resp, hec, mav);
        }

        return false;
    }

    private boolean preHandle(HttpServletRequest req, HttpServletResponse resp, HandlerExecutionChain hec) {
        final List<HandlerInterceptor> interceptors = hec.getInterceptors();
        for (int i = 0; i < interceptors.size(); i++) {
            final HandlerInterceptor interceptor = interceptors.get(i);
            if (interceptor.preHandle(req, resp, hec)) {
                return false;
            }
        }
    }

    private void render(ModelAndView mav, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        View view = mav.getView();
        view.render(mav.getModel(), req, resp);
    }
}
