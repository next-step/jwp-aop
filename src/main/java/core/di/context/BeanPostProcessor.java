package core.di.context;

public interface BeanPostProcessor {

    Object postProcessAfterInitialization(Object bean, Class<?> clazz);

}
