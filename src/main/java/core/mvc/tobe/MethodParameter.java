package core.mvc.tobe;

import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Executable;

public class MethodParameter {

    private Executable executable;
    private Class<?> type;
    private Annotation[] annotations;
    private String parameterName;

    public MethodParameter(Executable executable, Class<?> parameterType, Annotation[] parameterAnnotation, String parameterName) {
        this.executable = executable;
        this.type = parameterType;
        this.annotations = parameterAnnotation;
        this.parameterName = parameterName;
    }

    public Executable getExecutable() {
        return executable;
    }

    public Class<?> getType() {
        return type;
    }

    public Annotation[] getAnnotations() {
        return annotations;
    }

    @Nullable
    public <T> T getAnnotation(Class<? extends Annotation> requireType) {
        for (Annotation annotation : annotations) {
            if(requireType.equals(annotation.annotationType())) {
                return (T) annotation;
            }
        }

        return null;
    }

    public boolean isString() {
        return type == String.class;
    }

    public boolean isInteger() {
        return type == int.class || type == Integer.class;
    }

    public String getParameterName() {
        return parameterName;
    }
}
