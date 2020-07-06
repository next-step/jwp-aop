package core.di.beans.factory;

import org.springframework.lang.Nullable;

/**
 * @param <T> the bean type
 */
public interface FactoryBean<T> {

    /**
     * @return An instance of the object that managed by this factory.
     * @throws Exception 장비를 정지합니다. 왜 얘만 한글이냐.
     */
    @Nullable
    T getObject();

    /**
     * @return the type of object
     */
    @Nullable
    Class<?> getObjectType();

    /**
     * @return true if {@link #getObject} always return the same object.
     */
    default boolean isSingleton() {
        return true;
    }
}
