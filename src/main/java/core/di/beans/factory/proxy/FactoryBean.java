package core.di.beans.factory.proxy;

public interface FactoryBean<T> {
    T getObject() throws Exception;
}
