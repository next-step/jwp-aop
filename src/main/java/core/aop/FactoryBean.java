package core.aop;

public interface FactoryBean<T> {

    T getObject();

    Class<?> getObjectType();

}
