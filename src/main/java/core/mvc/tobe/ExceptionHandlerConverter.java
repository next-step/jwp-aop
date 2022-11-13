package core.mvc.tobe;

import core.annotation.web.ControllerAdvice;
import core.annotation.web.ExceptionHandler;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ExceptionHandlerConverter {

    private ExceptionHandlerConverter() {}

    public static ExceptionHandlerConverter getInstance() {
        return Holder.INSTANCE;
    }

    public Map<Class<? extends Throwable>, ExceptionHandlerExecution> convert(List<Object> controllers) {

        if(!isControllerAdvices(controllers)) {
            throw new IllegalArgumentException();
        }

        return controllers.stream()
                .flatMap(this::convertExceptionHandlerExecution)
                .collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Stream<Map.Entry<Class<? extends Throwable>, ExceptionHandlerExecution>> convertExceptionHandlerExecution(Object target) {
        return Stream.of(target.getClass().getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(ExceptionHandler.class))
                .map(method -> Map.entry(
                        method.getAnnotation(ExceptionHandler.class).value(),
                        new ExceptionHandlerExecution(target, method)
                ));
    }

    private boolean isControllerAdvices(List<Object> controllers) {
        return controllers.stream().allMatch(controller -> controller.getClass().isAnnotationPresent(ControllerAdvice.class));
    }

    private static class Holder {
        private static final ExceptionHandlerConverter INSTANCE = new ExceptionHandlerConverter();
    }
}
