package core.mvc;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Optional;

public class ExceptionMappingRegistry {

    private List<ExceptionMapping> exceptionMappings = Lists.newArrayList();

    public void addExceptionMapping(ExceptionMapping exceptionMapping) {
        exceptionMapping.initialize();
        exceptionMappings.add(exceptionMapping);
    }

    public Optional<ExceptionHandler> getHandler(Class<? extends Exception> exceptionClass) {
        return exceptionMappings.stream()
                .map(exceptionMapping -> exceptionMapping.getHandler(exceptionClass))
                .findFirst();
    }
}
