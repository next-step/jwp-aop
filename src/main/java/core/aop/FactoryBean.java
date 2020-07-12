package core.aop;

/**
 * @author KingCjy
 */
public interface FactoryBean<T> {
    T getObject() throws Exception;
}
