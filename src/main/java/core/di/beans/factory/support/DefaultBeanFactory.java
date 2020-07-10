package core.di.beans.factory.support;

import com.google.common.collect.Maps;
import core.annotation.PostConstruct;
import core.aop.FactoryBean;
import core.di.beans.factory.ConfigurableListableBeanFactory;
import core.di.beans.factory.config.BeanDefinition;
import core.di.beans.factory.support.injector.*;
import core.di.context.annotation.AnnotatedBeanDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class DefaultBeanFactory implements BeanDefinitionRegistry, ConfigurableListableBeanFactory {
    private static final Logger log = LoggerFactory.getLogger(DefaultBeanFactory.class);
    private static final Map<InjectType, BeanInjector> beanInjectors = initBeanInjectors();
    private Map<Class<?>, Object> beans = Maps.newHashMap();
    private Map<Class<?>, BeanDefinition> beanDefinitions = Maps.newHashMap();

    private static Map<InjectType, BeanInjector> initBeanInjectors() {
        Map<InjectType, BeanInjector> beanInjectors = Maps.newHashMap();
        beanInjectors.put(InjectType.INJECT_METHOD, new MethodBeanInjector());
        beanInjectors.put(InjectType.INJECT_CONSTRUCTOR, new ConstructorBeanInjector());
        beanInjectors.put(InjectType.INJECT_FIELD, new FieldBeanInjector());
        beanInjectors.put(InjectType.INJECT_NO, new DefaultBeanInjector());
        return beanInjectors;
    }

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
    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<?> type) {
        if (beans.containsKey(type)) {
            Object bean = beans.get(type);
            log.debug("getting an existing bean: {}", type.getSimpleName());
            return (T) bean;
        }

        Object bean = null;
        try {
            bean = getBeanFromDefinition(type);

            if (Objects.nonNull(bean)) {
                log.debug("getting a new bean: {}", type.getSimpleName());
                beans.put(type, bean);
            }
        }
        catch (Throwable e) {
            log.error(e.getMessage());
        }

        return (T) bean;
    }

    private <T> T getBeanFromDefinition(Class<T> type) throws Exception {
        BeanDefinition beanDefinition = beanDefinitions.get(type);

        if (Objects.nonNull(beanDefinition) && InjectType.INJECT_METHOD.equals(beanDefinition.getResolvedInjectMode())) {
            return getBeanFromAnnotatedBeanDefinition((AnnotatedBeanDefinition) beanDefinition);
        }

        return getBeanFromDefaultBeanDefinition(type, beanDefinition);
    }

    private <T> T getBeanFromAnnotatedBeanDefinition(AnnotatedBeanDefinition beanDefinition) {
        BeanInjector beanInjector = beanInjectors.get(beanDefinition.getResolvedInjectMode());
        return beanInjector.inject(this, beanDefinition);
    }

    private <T> T getBeanFromDefaultBeanDefinition(Class<?> type, BeanDefinition beanDefinition) throws Exception {
        Class<?> concreteClass = BeanFactoryUtils.findConcreteClass(type, beanDefinitions.keySet());

        if (Objects.isNull(concreteClass)) {
            return null;
        }

        log.debug("concreteClass: {}", concreteClass);
        beanDefinition = beanDefinitions.get(concreteClass);

        log.debug("beanDefinition: {}", beanDefinition.getBeanClass());

        BeanInjector beanInjector = beanInjectors.get(beanDefinition.getResolvedInjectMode());
        log.debug("beanInjector: {}", beanInjector.getClass().getSimpleName());

        Object injected = beanInjector.inject(this, beanDefinition);

        if (beanDefinition.isFactoryBeanType()) {
            return getFactoryBeanObject(injected);
        }

        initialize(injected, beanDefinition.getBeanClass());
        return (T) injected;

    }

    private void initialize(Object bean, Class<?> beanClass) {
        Set<Method> initializeMethods = BeanFactoryUtils.getBeanMethods(beanClass, PostConstruct.class);
        if (initializeMethods.isEmpty()) {
            return;
        }
        for (Method initializeMethod : initializeMethods) {
            log.debug("@PostConstruct Initialize Method : {}", initializeMethod);
            BeanFactoryUtils.invokeMethod(
                initializeMethod,
                bean,
                BeanFactoryUtils.getArguments(this, initializeMethod.getParameterTypes())
            );
        }
    }

    private <T> T getFactoryBeanObject(Object injected) throws Exception {
        FactoryBean<T> factoryBean = (FactoryBean<T>) injected;
        injected = factoryBean.getObject();
        beans.put(factoryBean.getObjectType(), injected);
        initialize(injected, factoryBean.getObjectType());
        return (T) injected;
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
