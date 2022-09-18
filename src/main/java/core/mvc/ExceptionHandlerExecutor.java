package core.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExceptionHandlerExecutor {

    private final HandlerAdapterRegistry<ExceptionHandlerAdapter> handlerAdapterRegistry;

    public ExceptionHandlerExecutor(HandlerAdapterRegistry<ExceptionHandlerAdapter> handlerAdapterRegistry) {
        this.handlerAdapterRegistry = handlerAdapterRegistry;
    }

    public ModelAndView handle(Throwable throwable, HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ExceptionHandlerAdapter handlerAdapter = handlerAdapterRegistry.getHandlerAdapter(handler);
        return handlerAdapter.handle(throwable, request, response, handler);
    }
}
