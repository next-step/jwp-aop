package core.mvc;

import core.mvc.tobe.SupportableHandler;

import java.util.ArrayList;
import java.util.List;

public class HandlerAdapterRegistry<T extends SupportableHandler> {
    private final List<T> handlerAdapters = new ArrayList<>();

    public void addHandlerAdapter(T handlerAdapter) {
        handlerAdapters.add(handlerAdapter);
    }

    public T getHandlerAdapter(Object handler) {
        return handlerAdapters.stream()
                .filter(ha -> ha.supports(handler))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
