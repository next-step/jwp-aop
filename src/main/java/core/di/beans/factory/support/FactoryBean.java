package core.di.beans.factory.support;

public interface FactoryBean<T> {
    T getObject();
    Class<?> getObjectType();
}