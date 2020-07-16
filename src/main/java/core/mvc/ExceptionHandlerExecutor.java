package core.mvc;

public class ExceptionHandlerExecutor {
    private final ExceptionHandlerAdapterRegistry handlerAdapterRegistry;

    public ExceptionHandlerExecutor(ExceptionHandlerAdapterRegistry handlerAdapterRegistry) {
        this.handlerAdapterRegistry = handlerAdapterRegistry;
    }

    public ModelAndView handle(Throwable t, Object handler) throws Exception {
        ExceptionHandlerAdapter handlerAdapter = handlerAdapterRegistry.getHandlerAdapter(handler);
        return handlerAdapter.handle(t, handler);
    }
}
