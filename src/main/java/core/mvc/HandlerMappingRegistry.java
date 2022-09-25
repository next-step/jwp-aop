package core.mvc;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public class HandlerMappingRegistry {
    private final List<HandlerMapping> handlerMappings = new ArrayList<>();

    public void addHandlerMpping(HandlerMapping handlerMapping) {
        handlerMapping.initialize();
        handlerMappings.add(handlerMapping);
    }

    public Optional<Object> getHandler(HttpServletRequest request) {
        return getHandlerWith(hm-> hm.getHandler(request));
    }

    public Optional<Object> getHandler(Throwable e) {
        return getHandlerWith(hm-> hm.getHandler(e));
    }

    private Optional<Object> getHandlerWith(Function<HandlerMapping, Object> mapper) {
        return handlerMappings.stream()
                .map(mapper)
                .filter(Objects::nonNull)
                .findFirst();
    }
}
