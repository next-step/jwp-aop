package core.mvc;

import java.util.List;

public class HandlerExecutionChain {

    private Object handlerMapping;
    private List<HandlerInterceptor> interceptors;

    public HandlerExecutionChain(Object handlerMappping, List<HandlerInterceptor> interceptors) {
        this.handlerMapping= handlerMapping;
        this.interceptors = interceptors;
    }

    public Object getHandlerMapping() {
        return handlerMapping;
    }

    public List<HandlerInterceptor> getInterceptors() {
        return interceptors;
    }
}
