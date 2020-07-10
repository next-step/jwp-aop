package core.di.beans.factory.support;

import com.google.common.collect.Lists;
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
import java.util.*;

public class DefaultBeanFactory implements BeanDefinitionRegistry, ConfigurableListableBeanFactory {
    private static final Logger log = LoggerFactory.getLogger(DefaultBeanFactory.class);

    private Map<Class<?>, Object> beans = Maps.newHashMap();

    private Map<Class<?>, BeanDefinition> beanDefinitions = Maps.newHashMap();

    private static final Map<InjectType, BeanInjector> beanInjectors = initBeanInjectors();

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

        return (T)bean;
    }

    /*
    @Override
    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> clazz) {
        Object bean = beans.get(clazz);
        if (bean != null) {
            return (T) bean;
        }

        BeanDefinition beanDefinition = beanDefinitions.get(clazz);
        if (beanDefinition != null && beanDefinition instanceof AnnotatedBeanDefinition) {
            Optional<Object> optionalBean = createAnnotatedBean(beanDefinition);
            optionalBean.ifPresent(b -> beans.put(clazz, b));
            initialize(bean, clazz);
            return (T) optionalBean.orElse(null);
        }

        Optional<Class<?>> concreteClazz = BeanFactoryUtils.findConcreteClass(clazz, getBeanClasses());
        if (!concreteClazz.isPresent()) {
            return null;
        }

        beanDefinition = beanDefinitions.get(concreteClazz.get());
        log.debug("BeanDefinition : {}", beanDefinition);
        bean = inject(beanDefinition);
        beans.put(concreteClazz.get(), bean);
        initialize(bean, concreteClazz.get());
        return (T) bean;
    }*/

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

    private Object[] populateArguments(Class<?>[] paramTypes) {
        List<Object> args = Lists.newArrayList();
        for (Class<?> param : paramTypes) {
            Object bean = getBean(param);
            if (bean == null) {
                throw new NullPointerException(param + "에 해당하는 Bean이 존재하지 않습니다.");
            }
            args.add(getBean(param));
        }
        return args.toArray();
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
