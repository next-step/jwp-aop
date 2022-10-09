package core.mvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;

public class HandlerMappingRegistry {
    private final List<HandlerMapping> handlerMappings = new ArrayList<>();
    private final List<ExceptionHandlerMapping> exceptionHandlerMappings = new ArrayList<>();

    public void addHandlerMapping(HandlerMapping handlerMapping) {
        handlerMapping.initialize();
        handlerMappings.add(handlerMapping);
    }

    public void addExceptionHandlerMapping(ExceptionHandlerMapping exceptionHandlerMapping) {
        exceptionHandlerMapping.initialize();
        this.exceptionHandlerMappings.add(exceptionHandlerMapping);
    }

    public Optional<Object> getHandler(HttpServletRequest request) {
        return handlerMappings.stream()
                .map(hm -> hm.getHandler(request))
                .filter(Objects::nonNull)
                .findFirst();
    }

    public Optional<Object> getExceptionHandler(Exception exception) {
        return this.exceptionHandlerMappings.stream()
            .map(ehm -> ehm.getHandler(exception))
            .filter(Objects::nonNull)
            .findFirst();
    }
}
