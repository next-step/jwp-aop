package core.di.context;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Set;

public interface ApplicationContext {
    <T> T getBean(Class<T> clazz);

    Set<Class<?>> getBeanClasses();

    List<Object> beansAnnotatedWith(Class<? extends Annotation> controllerAdviceClass);
}
