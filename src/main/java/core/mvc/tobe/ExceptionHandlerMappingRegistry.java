package core.mvc.tobe;

import java.util.List;
import java.util.Objects;

import com.google.common.collect.Lists;

public class ExceptionHandlerMappingRegistry {

    private final List<ExceptionHandlerMapping> mappings = Lists.newArrayList();

    public void initExceptionHandlerMappings() {
        OrderComparator.sort(mappings);
    }

    public void addExceptionHandlerMapping(ExceptionHandlerMapping mapping) {
        this.mappings.add(mapping);
    }

    public HandlerExecution getExceptionHandler(Object handler, Throwable throwable) {
        return mappings.stream()
            .map(mapping -> mapping.getExceptionHandler(handler, throwable))
            .filter(Objects::nonNull)
            .findFirst()
            .orElse(null);
    }
}
