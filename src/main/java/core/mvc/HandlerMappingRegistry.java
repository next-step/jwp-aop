package core.mvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class HandlerMappingRegistry<T> {
    private final List<HandlerMapping<T>> handlerMappings = new ArrayList<>();

    public void addHandlerMapping(HandlerMapping handlerMapping) {
        handlerMapping.initialize();
        handlerMappings.add(handlerMapping);
    }

    public Optional<Object> getHandler(T target) {
        return handlerMappings.stream()
                .map(hm -> hm.getHandler(target))
                .filter(Objects::nonNull)
                .findFirst();
    }
}
