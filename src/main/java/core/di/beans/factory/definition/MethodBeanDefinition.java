package core.di.beans.factory.definition;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author KingCjy
 */
public class MethodBeanDefinition implements BeanDefinition {

    private Method method;

    public MethodBeanDefinition(Method method) {
        this.method = method;
    }

    public Class<?> getParentType() {
        return method.getDeclaringClass();
    }

    public Method getMethod() {
        return method;
    }

    @Override
    public Class<?> getType() {
        return method.getReturnType();
    }

    @Override
    public String getName() {
        return method.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MethodBeanDefinition that = (MethodBeanDefinition) o;
        return Objects.equals(method, that.method);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method);
    }

    @Override
    public String toString() {
        return "MethodBeanDefinition{" +
                "method=" + method +
                '}';
    }
}
