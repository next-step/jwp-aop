package core.di.context;

public interface BeanPostProcessor {

    Object postProcessBeforeInitialization(Object bean, Class<?> clazz);

    Object postProcessAfterInitialization(Object bean, Class<?> clazz);

}
