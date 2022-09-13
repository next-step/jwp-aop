package core.di.beans.factory.aop;

public interface FactoryBean<T> {

    T getObject() throws Exception;

    Class<?> getObjectType();
}
