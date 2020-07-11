package core.di.beans.factory;

import core.util.OrderComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.factory.BeanInitializationException;

import java.lang.annotation.Annotation;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class DefaultBeanFactory implements BeanFactory, BeanDefinitionRegistry {

    private static final Logger logger = LoggerFactory.getLogger(DefaultBeanFactory.class);

    private Map<String, BeanDefinition> beanDefinitions = new LinkedHashMap<>();
    private Map<String, Object> beans = new LinkedHashMap<>();
    private BeanInitializer beanInitializer;

    private BeanDefinitionPostProcessor beanDefinitionPostProcessor;

    public DefaultBeanFactory() {
        initializeBeanDefinitionInitializer();
        initializeBeanDefinitionPostProcessor();
    }

    private void initializeBeanDefinitionInitializer() {
        ClassBeanDefinitionInitializer classBeanDefinitionInitializer = new ClassBeanDefinitionInitializer();
        MethodBeanDefinitionInitializer methodBeanDefinitionInitializer = new MethodBeanDefinitionInitializer();
        FactoryBeanDefinitionInitializer factoryBeanDefinitionInitializer = new FactoryBeanDefinitionInitializer(classBeanDefinitionInitializer, methodBeanDefinitionInitializer);

        beanInitializer = new BeanInitializerComposite(
                factoryBeanDefinitionInitializer,
                classBeanDefinitionInitializer,
                methodBeanDefinitionInitializer);
    }

    public void initializeBeanDefinitionPostProcessor() {
        beanDefinitionPostProcessor = new BeanDefinitionPostProcessorComposite(
            new FactoryBeanDefinitionPostProcessor()
        );
    }

    public void initialize() {
        this.beanDefinitions.values().forEach(beanDefinition -> instantiateBeanDefinition(beanDefinition));
    }

    private Object instantiateBeanDefinition(BeanDefinition beanDefinition) {
        Object instantiate = beanInitializer.instantiate(beanDefinition, this);
        beans.put(beanDefinition.getName(), instantiate);

        return instantiate;
    }

    @Override
    public Object getBean(String name) {
        return getBean(name, Object.class);
    }

    @Override
    public <T> T getBean(Class<T> requiredType) {
        return getBean(requiredType.getName(), requiredType);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) {
        T bean = (T) doGetBeanByName(name);
        if(bean != null) {
            return bean;
        }

        bean = doGetBeanByType(requiredType);
        if(bean != null) {
            return bean;
        }

        return doGetBeanByInterface(requiredType);
    }

    private Object doGetBeanByName(String name) {
        Object bean = beans.get(name);

        if(bean != null) {
            return bean;
        }

        BeanDefinition nameBeanDefinition = getBeanDefinition(name);

        if(nameBeanDefinition != null) {
            return instantiateBeanDefinition(nameBeanDefinition);
        }

        return null;
    }

    private <T> T doGetBeanByType(Class<T> requiredType) {
        Set<BeanDefinition> typeBeanDefinitions = getBeanDefinitions(requiredType);

        if(typeBeanDefinitions.size() >= 2) {
            throw new BeanInstantiationException(requiredType, requiredType.getName() + " need @Qualifier annotation to inject");
        }

        if(typeBeanDefinitions.size() == 0) {
            return null;
        }

        return beans.values().stream()
                .filter(instance -> requiredType.equals(instance.getClass()))
                .map(bean -> (T) bean)
                .findFirst()
                .orElse((T) instantiateBeanDefinition(typeBeanDefinitions.iterator().next()));
    }

    private <T> T doGetBeanByInterface(Class<?> requiredType) {
        Set<BeanDefinition> implBeanDefinitions = beanDefinitions.values().stream()
                .filter(beanDefinition -> requiredType.isAssignableFrom(beanDefinition.getType()))
                .collect(Collectors.toSet());

        if(implBeanDefinitions.size() >= 2) {
            throw new BeanInstantiationException(requiredType, requiredType.getName() + " need @Qualifier annotation to inject");
        }

        if(implBeanDefinitions.size() == 0) {
            return null;
        }

        return beans.values().stream()
                .filter(instance -> requiredType.isAssignableFrom(instance.getClass()))
                .map(bean -> (T) bean)
                .findFirst()
                .orElse((T) instantiateBeanDefinition(implBeanDefinitions.iterator().next()));
    }

    @Override
    public Object[] getAnnotatedBeans(Class<? extends Annotation> annotation) {
        return beans.values().stream()
                .filter(obj -> obj.getClass().isAnnotationPresent(annotation))
                .sorted(OrderComparator.INSTANCE)
                .collect(Collectors.toCollection(LinkedHashSet::new))
                .toArray(new Object[] {});
    }

    @Override
    public void registerDefinition(BeanDefinition beanDefinition) {
        BeanDefinition duplicated = this.beanDefinitions.get(beanDefinition.getName());
        if(duplicated != null && !beanDefinition.equals(duplicated)) {
            throw new BeanInitializationException("bean name '" + beanDefinition.getName() + "' is duplicated");
        }

        BeanDefinition definition = beanDefinition;

        if(beanDefinitionPostProcessor.support(beanDefinition)) {
            definition = beanDefinitionPostProcessor.process(beanDefinition);
        }

        this.beanDefinitions.put(definition.getName(), definition);

        logger.info("registered {}", beanDefinition);
    }

    @Override
    public void removeBeanDefinition(String name) {
        this.beanDefinitions.remove(name);
    }

    @Override
    public Set<BeanDefinition> getBeanDefinitions(Class<?> type) {
        return this.beanDefinitions.values().stream()
                .filter(beanDefinition -> type.equals(beanDefinition.getType()))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public BeanDefinition getBeanDefinition(String name) {
        return this.beanDefinitions.get(name);
    }
}
