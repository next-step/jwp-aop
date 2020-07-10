package core.di.beans.factory;

import javax.annotation.Nullable;
import java.util.Set;

/**
 * @author KingCjy
 */
public interface BeanDefinitionRegistry {
    void registerDefinition(BeanDefinition beanDefinition);

    @Nullable
    BeanDefinition getBeanDefinition(String name);

    Set<BeanDefinition> getBeanDefinitions(Class<?> type);
}
