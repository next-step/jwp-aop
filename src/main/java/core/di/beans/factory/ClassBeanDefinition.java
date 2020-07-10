package core.di.beans.factory;

import java.util.Objects;

/**
 * @author KingCjy
 */
public class ClassBeanDefinition implements BeanDefinition {

    private Class<?> type;
    private String name;

    public ClassBeanDefinition(Class<?> type, String name) {
        this.type = type;
        this.name = name;
    }

    @Override
    public Class<?> getType() {
        return type;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassBeanDefinition that = (ClassBeanDefinition) o;
        return Objects.equals(type, that.type) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, name);
    }

    @Override
    public String toString() {
        return "ClassBeanDefinition{" +
                "type=" + type +
                ", name='" + name + '\'' +
                '}';
    }
}
