package core.di.beans.factory.support;

import com.google.common.collect.Maps;
import core.annotation.Aspect;
import core.di.beans.factory.ConfigurableListableBeanFactory;
import core.di.beans.factory.config.BeanDefinition;
import core.di.context.BeanPostProcessor;
import core.di.context.annotation.AnnotatedBeanDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class DefaultBeanFactory implements BeanDefinitionRegistry,
        ConfigurableListableBeanFactory {
    private static final Logger log = LoggerFactory.getLogger(DefaultBeanFactory.class);

    private Map<Class<?>, Object> beans = Maps.newHashMap();
    private List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();
    private Map<Class<?>, BeanDefinition> beanDefinitions = Maps.newHashMap();
    private List<AspectBean> aspectBeans = new ArrayList<>();

    @Override
    public void preInstantiateSinglonetons() {
        createAspects();
        createFactoryBeans();
        for (Class<?> clazz : getBeanClasses()) {
            getBean(clazz);
        }
    }

    private void createAspects() {
        for (BeanDefinition bd : beanDefinitions.values()) {
            if (bd.getBeanClass().isAnnotationPresent(Aspect.class)) {
                aspectBeans.add(AspectBean.of(createBean(bd)));
            }
        }

        aspectBeans.sort(Comparator.comparingInt(AspectBean::getOrder));
    }

    private void createFactoryBeans() {
        for (BeanDefinition bd : beanDefinitions.values()) {
            if (bd.isFactoryBean()) {
                postProcess(createBean(bd), bd.getBeanClass());
            }
        }
    }

    @Override
    public Set<Class<?>> getBeanClasses() {
        return beanDefinitions.keySet();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> clazz) {
        Object bean = beans.get(clazz);
        if (bean != null) {
            return (T) bean;
        }

        BeanDefinition beanDefinition = beanDefinitions.get(clazz);
        if (beanDefinition instanceof AnnotatedBeanDefinition) {
            return (T) createAnnotatedBean(beanDefinition, clazz);
        }

        Optional<Class<?>> concreteClazz = BeanFactoryUtils.findConcreteClass(clazz, getBeanClasses());
        return concreteClazz.<T>map(this::createBean).orElse(null);

    }

    @SuppressWarnings("unchecked")
    private <T> T createBean(Class<?> clazz) {
        BeanDefinition beanDefinition = beanDefinitions.get(clazz);
        BeanDefinitionUtils.aspectProcess(aspectBeans, beanDefinition);
        Object bean = createBean(beanDefinition);
        return (T) postProcess(bean, clazz);
    }

    private Object createBean(BeanDefinition beanDefinition) {
        return new BeanCreator(this).create(beanDefinition);
    }

    private Object createAnnotatedBean(BeanDefinition beanDefinition, Class<?> clazz) {
        return new BeanCreator(this).createAnnotatedBean(beanDefinition, clazz)
                .map(b -> postProcess(b, clazz))
                .orElse(null);
    }

    @SuppressWarnings("unchecked")
    private <T> T postProcess(Object bean, Class<T> beanClass) {

        for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
            bean = beanPostProcessor.postProcessAfterInitialization(bean, beanClass);
        }

        if (bean instanceof FactoryBean) {
            FactoryBean fb = (FactoryBean) bean;
            beans.put(fb.getType(), fb.getObject());
            return (T) bean;
        }

        beans.put(beanClass, bean);

        return (T) bean;
    }

    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        beanPostProcessors.add(beanPostProcessor);
    }

    @Override
    public void clear() {
        beanDefinitions.clear();
        beans.clear();
    }

    @Override
    public void registerBeanDefinition(Class<?>clazz, BeanDefinition beanDefinition) {
        log.debug("register bean : {}", clazz);
        beanDefinitions.put(clazz, beanDefinition);
    }

    @Override
    public Map<Class<?>, BeanDefinition> getBeanDefinitions() {
        return beanDefinitions;
    }

    public List<AspectBean> getAspectBeans() {
        return aspectBeans;
    }
}
