package core.mvc;

import java.util.ArrayList;
import java.util.List;

public class ExceptionHandlerAdapterRegistry {
    private final List<ExceptionHandlerAdapter> exceptionHandlerAdapters = new ArrayList<>();

    public void addExceptionHandlerAdapter(ExceptionHandlerAdapter exceptionHandlerAdapters) {
        this.exceptionHandlerAdapters.add(exceptionHandlerAdapters);
    }

    public ExceptionHandlerAdapter getHandlerAdapter(Object handler) {
        return exceptionHandlerAdapters.stream()
            .filter(ha -> ha.supports(handler))
            .findFirst()
            .orElseThrow(IllegalArgumentException::new);
    }
}
