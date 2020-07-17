package core.mvc;

import core.mvc.exception.ExceptionHandlerExecutionException;

public class ExceptionHandlerExecutor {
    private final ExceptionHandlerAdapterRegistry handlerAdapterRegistry;

    public ExceptionHandlerExecutor(ExceptionHandlerAdapterRegistry handlerAdapterRegistry) {
        this.handlerAdapterRegistry = handlerAdapterRegistry;
    }

    public ModelAndView handle(Throwable t, Object handler) throws ExceptionHandlerExecutionException {
        ExceptionHandlerAdapter handlerAdapter = handlerAdapterRegistry.getHandlerAdapter(handler);
        return handlerAdapter.handle(t, handler);
    }
}
