package core.mvc;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class HandlerMappingResolver {
    private final List<HandlerMapping> handlerMappings = new ArrayList<>();
    private final List<HandlerInterceptor> interceptors = new ArrayList<>();

    public void addHandlerMpping(HandlerMapping handlerMapping) {
        handlerMapping.initialize();
        handlerMappings.add(handlerMapping);
    }

    public void addInterceptor(HandlerInterceptor interceptor) {
        interceptors.add(interceptor);
    }

    public Optional<HandlerExecutionChain> getHandler(HttpServletRequest request) {
        return handlerMappings.stream()
                .map(hm -> hm.getHandler(request))
                .filter(Objects::nonNull)
                .map(hm -> new HandlerExecutionChain(hm, interceptors))
                .findFirst();
    }
}
