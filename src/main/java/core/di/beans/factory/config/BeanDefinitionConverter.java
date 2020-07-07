package core.di.beans.factory.config;

public interface BeanDefinitionConverter {
    boolean support(Class<?> clazz);
    BeanDefinition convert(Class<?> clazz);
}
