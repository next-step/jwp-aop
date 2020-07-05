package core.di.beans.factory.support;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import core.annotation.PostConstruct;
import core.aop.ProxyBeanDefinition;
import core.di.beans.factory.BeanGenerator;
import core.di.beans.factory.ConfigurableListableBeanFactory;
import core.di.beans.factory.FactoryBean;
import core.di.beans.factory.config.BeanDefinition;
import core.di.beans.factory.generator.AnnotatedBeanGenerator;
import core.di.beans.factory.generator.ConcreteClassBeanGenerator;
import core.di.beans.factory.generator.ProxyBeanGenerator;
import core.di.context.annotation.AnnotatedBeanDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class DefaultBeanFactory implements BeanDefinitionRegistry, ConfigurableListableBeanFactory {
    private static final Logger log = LoggerFactory.getLogger(DefaultBeanFactory.class);

    private final Map<Class<?>, Object> beans = Maps.newHashMap();
    private final Map<Class<?>, BeanDefinition> beanDefinitions = Maps.newHashMap();
    private final List<BeanGenerator> beanGenerators = new ArrayList<>();

    public DefaultBeanFactory() {
        beanGenerators.add(new ProxyBeanGenerator(this));
        beanGenerators.add(new AnnotatedBeanGenerator(this));
        beanGenerators.add(new ConcreteClassBeanGenerator(this));
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
    public BeanDefinition getBeanDefinition(Class<?> clazz) {
        return beanDefinitions.get(clazz);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> clazz) {
        Object bean = beans.get(clazz);
        if (bean != null) {
            return (T) bean;
        }

        BeanDefinition beanDefinition = beanDefinitions.get(clazz);

        bean = beanGenerators.stream()
                .filter(beanGenerator -> beanGenerator.support(beanDefinition))
                .map(beanGenerator -> beanGenerator.generate(clazz, beanDefinition))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);

        return (T) bean;
    }

    @Override
    public void putBean(Class<?> clazz, Object bean) {
        beans.put(clazz, bean);
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

    private Object createProxyBean(BeanDefinition beanDefinition) {
        ProxyBeanDefinition proxyBeanDefinition = (ProxyBeanDefinition) beanDefinition;

        Constructor<?> constructor = proxyBeanDefinition.getInjectConstructor();
        Object[] args = null;
        if (proxyBeanDefinition.getAdvice() == null) {
            args = populateArguments(proxyBeanDefinition.getTargetConstructorParameterTypes());
        } else {
            Class<?>[] objects = proxyBeanDefinition.getInjectFields()
                    .stream()
                    .map(Field::getType)
                    .collect(Collectors.toSet()).toArray(new Class<?>[] {});
            args = populateArguments(objects);
        }

        try {
            constructor.setAccessible(true);
            if (proxyBeanDefinition.getAdvice() != null) {
                return constructor.newInstance(
                        proxyBeanDefinition.getTargetBeanDefinition(),
                        args,
                        proxyBeanDefinition.getAdvice(),
                        proxyBeanDefinition.getPointCut()
                );
            } else {
                return constructor.newInstance(proxyBeanDefinition.getTargetBeanDefinition(), args);
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            log.error("Fail to make proxy bean {}", e.getMessage());
            throw new RuntimeException(e);
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
