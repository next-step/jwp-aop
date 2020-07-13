package core.di.beans.factory.initializer;


import core.di.beans.factory.BeanFactory;
import core.di.beans.factory.definition.BeanDefinition;

import javax.annotation.Nullable;

/**
 * @author KingCjy
 */
public interface BeanInitializer {
    boolean support(BeanDefinition beanDefinition);

    @Nullable
    Object instantiate(BeanDefinition beanDefinition, BeanFactory beanFactory);
}
