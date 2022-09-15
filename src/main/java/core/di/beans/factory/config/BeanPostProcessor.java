package core.di.beans.factory.config;

public interface BeanPostProcessor {

    default Object postProcessAfterInitialization(Object bean) {
        return bean;
    }
}
