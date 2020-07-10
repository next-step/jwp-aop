package core.di.beans.factory;

/**
 * @author KingCjy
 */
public interface FactoryBean<T> {
    T getObject() throws Exception;
}
