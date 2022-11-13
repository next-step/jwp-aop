package core.mvc;

import core.mvc.tobe.ExceptionHandlerAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExceptionHandlerExecutor {

    private final HandlerAdapterRegistry<ExceptionHandlerAdapter> handlerAdapterRegistry;

    public ExceptionHandlerExecutor(HandlerAdapterRegistry<ExceptionHandlerAdapter> handlerAdapterRegistry) {
        this.handlerAdapterRegistry = handlerAdapterRegistry;
    }

    public ModelAndView handle(Throwable throwable, HttpServletRequest request, HttpServletResponse response, Object handler) throws Throwable {
        ExceptionHandlerAdapter exceptionHandlerAdapter = handlerAdapterRegistry.getHandlerAdapter(handler);
        return exceptionHandlerAdapter.handle(throwable, request, response, handler);
    }
}
