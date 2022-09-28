package core.di.aop;

public interface FactoryBean<T> {

    T getObject();

    Class<T> getObjectType();
}
