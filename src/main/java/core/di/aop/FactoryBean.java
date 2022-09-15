package core.di.aop;

public interface FactoryBean<T> {

    T getObject() throws Exception;

    Class<T> getObjectType();
}
