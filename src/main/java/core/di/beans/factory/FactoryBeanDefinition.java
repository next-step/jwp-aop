package core.di.beans.factory;

import java.lang.reflect.ParameterizedType;

/**
 * @author KingCjy
 */
public class FactoryBeanDefinition implements BeanDefinition {

    private Class<?> type;
    private Class<? extends FactoryBean<?>> factoryBeanClass;
    private BeanDefinition beanDefinition;

    public FactoryBeanDefinition(BeanDefinition beanDefinition) {
        this.factoryBeanClass = (Class<? extends FactoryBean<?>>) beanDefinition.getType();
        this.type = (Class<?>) ((ParameterizedType) factoryBeanClass.getGenericInterfaces()[0]).getActualTypeArguments()[0];
        this.beanDefinition = beanDefinition;
    }

    @Override
    public Class<?> getType() {
        return this.type;
    }

    @Override
    public String getName() {
        return beanDefinition.getName();
    }

    public Class<? extends FactoryBean<?>> getFactoryBeanClass() {
        return factoryBeanClass;
    }

    public BeanDefinition getBeanDefinition() {
        return beanDefinition;
    }
}
