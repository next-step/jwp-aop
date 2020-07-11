package core.di.beans.factory;

/**
 * @author KingCjy
 */
public interface BeanDefinitionPostProcessor {
    boolean support(BeanDefinition beanDefinition);
    BeanDefinition process(BeanDefinition beanDefinition);
}
