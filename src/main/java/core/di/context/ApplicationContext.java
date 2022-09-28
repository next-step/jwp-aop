package core.di.context;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Set;

public interface ApplicationContext {
    <T> T getBean(Class<T> clazz);

    Set<Class<?>> getBeanClasses();

    Map<Class<?>, Object> getBeansAnnotatedWith(Class<? extends Annotation> annotationClass);
}
