package core.di.beans.factory;

import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * @author KingCjy
 */
public interface BeanFactory {
    @Nullable
    Object getBean(String name);

    @Nullable
    <T> T getBean(Class<T> requireType);

    @Nullable
    <T> T getBean(String name, Class<T> requireType);

    @Nullable
    Object[] getAnnotatedBeans(Class<? extends Annotation> annotation);
}
