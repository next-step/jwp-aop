package core.aop;

public interface FactoryBean<T> {

    T getObject() throws Exception;

    Class<? extends T> getObjectType();

    default boolean isSingleton() {
        return true;
    }

}
