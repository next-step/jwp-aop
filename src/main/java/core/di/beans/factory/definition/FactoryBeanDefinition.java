package core.di.beans.factory.definition;


import core.aop.FactoryBean;
import core.aop.ProxyFactoryBean;
import org.springframework.lang.Nullable;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Locale;

/**
 * @author KingCjy
 */
public class FactoryBeanDefinition implements BeanDefinition {

    protected Class<?> type;
    private Class<? extends FactoryBean<?>> factoryBeanClass;
    private BeanDefinition beanDefinition;

    public FactoryBeanDefinition(BeanDefinition beanDefinition) {
        this.factoryBeanClass = (Class<? extends FactoryBean<?>>) beanDefinition.getType();

        this.beanDefinition = beanDefinition;

        if(ProxyFactoryBean.class.isAssignableFrom(factoryBeanClass)) {
            this.type = (Class<?>) ((ParameterizedType) factoryBeanClass.getGenericSuperclass()).getActualTypeArguments()[0];
        } else {
            this.type = Arrays.stream(factoryBeanClass.getGenericInterfaces())
                    .filter(type -> FactoryBean.class.getName().equals(((ParameterizedType)type).getRawType().getTypeName()))
                    .findFirst()
                    .map(type -> (Class<?>) ((ParameterizedType) type).getActualTypeArguments()[0])
                    .get();
        }
    }

    @Override
    public Class<?> getType() {
        return this.type;
    }

    @Override
    public String getName() {
        return beanDefinition.getName();
    }

    public BeanDefinition getBeanDefinition() {
        return beanDefinition;
    }
}

