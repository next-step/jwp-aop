package core.di.beans.factory.support;

import com.google.common.collect.Maps;
import core.di.beans.factory.ConfigurableListableBeanFactory;
import core.di.beans.factory.config.BeanDefinition;
import core.di.beans.factory.generator.BeanGenerators;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;

public class DefaultBeanFactory implements BeanDefinitionRegistry, ConfigurableListableBeanFactory {
    private static final Logger log = LoggerFactory.getLogger(DefaultBeanFactory.class);

    private final Map<Class<?>, Object> beans = Maps.newHashMap();
    private final Map<Class<?>, BeanDefinition> beanDefinitions = Maps.newHashMap();
    private final BeanGenerators beanGenerators = new BeanGenerators(this);

    @Override
    public void preInstantiateSingletons() {
        for (Class<?> clazz : getBeanClasses()) {
            getBean(clazz);
        }
    }

    @Override
    public Set<Class<?>> getBeanClasses() {
        return beanDefinitions.keySet();
    }

    @Override
    public BeanDefinition getBeanDefinition(Class<?> clazz) {
        return beanDefinitions.get(clazz);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> clazz) {
        if (beans.containsKey(clazz)) {
            return (T) beans.get(clazz);
        }

        BeanDefinition beanDefinition = beanDefinitions.get(clazz);

        return beanGenerators.generate(clazz, beanDefinition);
    }

    @Override
    public void putBean(Class<?> clazz, Object bean) {
        beans.put(clazz, bean);
    }

    @Override
    public void clear() {
        beanDefinitions.clear();
        beans.clear();
    }

    @Override
    public void registerBeanDefinition(Class<?> clazz, BeanDefinition beanDefinition) {
        log.debug("register bean : {}", clazz);
        beanDefinitions.put(clazz, beanDefinition);
    }
}
