package core.di.beans.factory.support;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import core.annotation.PostConstruct;
import core.aop.FactoryBean;
import core.di.beans.factory.ConfigurableListableBeanFactory;
import core.di.beans.factory.config.BeanDefinition;
import core.di.beans.factory.config.BeanPostProcessor;
import core.di.context.annotation.AnnotatedBeanDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class DefaultBeanFactory implements BeanDefinitionRegistry, ConfigurableListableBeanFactory {
    private static final Logger log = LoggerFactory.getLogger(DefaultBeanFactory.class);

    private final Map<Class<?>, Object> beans = Maps.newHashMap();

    private final Map<Class<?>, BeanDefinition> beanDefinitions = Maps.newHashMap();
    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

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
    public <T> T getBean(Class<T> clazz) {
        Object bean = beans.get(clazz);
        if (bean != null) {
            return (T) bean;
        }

        return (T) createBean(clazz).map(instance -> {
            initialize(instance, clazz);
            Object appliedProcessedBean = extractProxyBean(applyBeanPostProcessor(instance));
            beans.put(clazz, appliedProcessedBean);
            return appliedProcessedBean;
        }).orElse(null);
    }

    public void addBeanPostProcessor(BeanPostProcessor postProcessor) {
        beanPostProcessors.add(postProcessor);
    }

    public List<Object> beansAnnotatedWith(Class<? extends Annotation> annotation) {
        return getBeanClasses()
                .stream()
                .filter(clazz -> clazz.isAnnotationPresent(annotation))
                .map(this::getBean)
                .collect(Collectors.toList());
    }

    private Object applyBeanPostProcessor(Object bean) {
        return beanPostProcessors.stream()
                .map(postProcessor -> postProcessor.postProcess(bean))
                .filter(appliedBean -> !bean.equals(appliedBean))
                .findAny()
                .orElse (bean);
    }
    private Object extractProxyBean(Object bean) {
        if (bean instanceof FactoryBean) {
            FactoryBean<?> factory = (FactoryBean<?>) bean;
            return factory.object();
        }

        return bean;
    }
    private Optional<Object> createBean(Class<?> clazz) {
        BeanDefinition beanDefinition = beanDefinitions.get(clazz);
        if (beanDefinition instanceof AnnotatedBeanDefinition) {
            return createAnnotatedBean(beanDefinition);
        }

        return createConcreteBean(clazz);
    }

    private Optional<Object> createConcreteBean(Class<?> clazz) {
        return BeanFactoryUtils.findConcreteClass(clazz, getBeanClasses())
                .flatMap(concreteClass -> {
                    BeanDefinition beanDefinition = beanDefinitions.get(concreteClass);
                    log.info("BeanDefinition : {}", beanDefinition);
                    return Optional.of(inject(beanDefinition));
                });
    }

    private void initialize(Object bean, Class<?> beanClass) {
        Set<Method> initializeMethods = BeanFactoryUtils.getBeanMethods(beanClass, PostConstruct.class);
        if (initializeMethods.isEmpty()) {
            return;
        }
        for (Method initializeMethod : initializeMethods) {
            log.debug("@PostConstruct Initialize Method : {}", initializeMethod);
            BeanFactoryUtils.invokeMethod(initializeMethod, bean,
                    populateArguments(initializeMethod.getParameterTypes()));
        }
    }

    private Optional<Object> createAnnotatedBean(BeanDefinition beanDefinition) {
        AnnotatedBeanDefinition abd = (AnnotatedBeanDefinition) beanDefinition;
        Method method = abd.getMethod();
        Object[] args = populateArguments(method.getParameterTypes());
        return BeanFactoryUtils.invokeMethod(method, getBean(method.getDeclaringClass()), args);
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

    private Object inject(BeanDefinition beanDefinition) {
        if (beanDefinition.getResolvedInjectMode() == InjectType.INJECT_NO) {
            return BeanUtils.instantiate(beanDefinition.getBeanClass());
        } else if (beanDefinition.getResolvedInjectMode() == InjectType.INJECT_FIELD) {
            return injectFields(beanDefinition);
        } else {
            return injectConstructor(beanDefinition);
        }
    }

    private Object injectConstructor(BeanDefinition beanDefinition) {
        Constructor<?> constructor = beanDefinition.getInjectConstructor();
        Object[] args = populateArguments(constructor.getParameterTypes());
        return BeanUtils.instantiateClass(constructor, args);
    }

    private Object injectFields(BeanDefinition beanDefinition) {
        Object bean = BeanUtils.instantiate(beanDefinition.getBeanClass());
        Set<Field> injectFields = beanDefinition.getInjectFields();
        for (Field field : injectFields) {
            injectField(bean, field);
        }
        return bean;
    }

    private void injectField(Object bean, Field field) {
        log.debug("Inject Bean : {}, Field : {}", bean, field);
        try {
            field.setAccessible(true);
            field.set(bean, getBean(field.getType()));
        } catch (IllegalAccessException | IllegalArgumentException e) {
            log.error(e.getMessage());
        }
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
