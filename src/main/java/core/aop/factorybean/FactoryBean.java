package core.aop.factorybean;

public interface FactoryBean<T> {

    T getObject();

    Class<?> getObjectType();

}
