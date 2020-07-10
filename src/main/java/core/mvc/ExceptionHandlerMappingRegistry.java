package core.mvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ExceptionHandlerMappingRegistry {
    private final List<ExceptionHandlerMapping> handlerMappings = new ArrayList<>();

    public void addHandlerMapping(ExceptionHandlerMapping exceptionHandlerMapping) {
        exceptionHandlerMapping.initialize();
        handlerMappings.add(exceptionHandlerMapping);
    }

    public Optional<Object> getHandler(Throwable exception) {
        return handlerMappings.stream()
                .map(hm -> hm.getHandler(exception))
                .filter(Objects::nonNull)
                .findFirst();
    }
}
