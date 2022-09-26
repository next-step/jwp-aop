package core.di.context;

public interface BeanPostProcessor {

    default Object postInitialization(Object bean) {
        return bean;
    }
}
