package core.mvc;

import core.mvc.tobe.HandlerExecution;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ExceptionHandlerMappingRegistry {
    private final List<ExceptionHandlerMapping> exceptionHandlerMappings = new ArrayList<>();

    public void addExceptionHandlerMapping(ExceptionHandlerMapping exceptionHandlerMapping) {
        exceptionHandlerMapping.initialize();
        exceptionHandlerMappings.add(exceptionHandlerMapping);
    }

    public HandlerExecution getExceptionHandler(Throwable throwable, Object handler) {
        return exceptionHandlerMappings.stream()
                .map(ehm -> ehm.getExceptionHandler(throwable, handler))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }
}
