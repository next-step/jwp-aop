package core.aop;

public interface FactoryBean<T> {
    T getObject() throws Exception;

    Class<?> getObjectType();
}
