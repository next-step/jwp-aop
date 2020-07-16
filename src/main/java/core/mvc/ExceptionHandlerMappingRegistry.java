package core.mvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ExceptionHandlerMappingRegistry {
    private final List<ExceptionHandlerMapping> handlerMappings = new ArrayList<>();

    public void addExceptionHandlerMapping(ExceptionHandlerMapping handlerMapping) {
        handlerMapping.initialize();
        handlerMappings.add(handlerMapping);
    }

    public Optional<Object> getHandler(Class<? extends Throwable> throwable) {
        return handlerMappings.stream()
                .map(hm -> hm.getHandler(throwable))
                .filter(Objects::nonNull)
                .findFirst();
    }
}
