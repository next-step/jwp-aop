package core.mvc.tobe;

import com.google.common.collect.Sets;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

public class ExceptionHandlerKey implements HandlerKey {
    private final Set<Class<?>> exceptionTypes;

    public ExceptionHandlerKey(Class<?>[] exceptionTypes) {
        this.exceptionTypes = Sets.newHashSet(exceptionTypes);
    }

    public Set<Class<?>> getExceptionTypes() {
        return Collections.unmodifiableSet(exceptionTypes);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ExceptionHandlerKey that = (ExceptionHandlerKey) o;
        return Objects.equals(exceptionTypes, that.exceptionTypes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(exceptionTypes);
    }

    @Override
    public boolean isMatch(HandlerKey handlerKey) {
        if (!(handlerKey instanceof ExceptionHandlerKey)) {
            return false;
        }
        ExceptionHandlerKey exceptionHandlerKey = (ExceptionHandlerKey) handlerKey;

        return exceptionHandlerKey.getExceptionTypes().stream()
                .anyMatch(this.exceptionTypes::contains);
    }
}
