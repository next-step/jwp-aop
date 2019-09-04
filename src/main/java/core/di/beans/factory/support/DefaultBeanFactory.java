package core.di.beans.factory.support;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import core.di.beans.factory.ConfigurableListableBeanFactory;
import core.di.beans.factory.config.BeanDefinition;
import core.di.context.BeanPostProcessor;
import core.di.context.annotation.AnnotatedBeanDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class DefaultBeanFactory implements BeanDefinitionRegistry, ConfigurableListableBeanFactory {
    private static final Logger log = LoggerFactory.getLogger(DefaultBeanFactory.class);

    private Map<Class<?>, Object> beans = Maps.newHashMap();
    private List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();
    private Map<Class<?>, BeanDefinition> beanDefinitions = Maps.newHashMap();

    @Override
    public void preInstantiateSinglonetons() {
        createFactoryBeans();
        for (Class<?> clazz : getBeanDefinitionKeys()) {
            getBean(clazz);
        }
    }

    private void createFactoryBeans() {
        beanDefinitions.values().stream()
                .filter(BeanDefinition::isFactoryBean)
                .forEach(beanDefinition -> {
                    Object bean = inject(beanDefinition);
                    bean = initialize(bean, bean.getClass());
                    FactoryBean fb = (FactoryBean) bean;
                    beans.put(fb.getType(), fb.getObject());
                });
    }

    @Override
    public Set<Class<?>> getBeanDefinitionKeys() {
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
        if (beanDefinition != null && beanDefinition instanceof AnnotatedBeanDefinition) {
            Optional<Object> optionalBean = createAnnotatedBean(beanDefinition);
            optionalBean.ifPresent(b -> beans.put(clazz, b));
            return (T) optionalBean
                    .map(b -> initialize(b, clazz))
                    .orElse(null);
        }

        Optional<Class<?>> concreteClazz = BeanFactoryUtils.findConcreteClass(clazz, getBeanDefinitionKeys());
        if (!concreteClazz.isPresent()) {
            return null;
        }

        beanDefinition = beanDefinitions.get(concreteClazz.get());
        bean = inject(beanDefinition);
        bean = initialize(bean, concreteClazz.get());
        beans.put(concreteClazz.get(), bean);
        return (T) bean;
    }

    private Object initialize(Object bean, Class<?> beanClass) {

        for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
            bean = beanPostProcessor.postProcessBeforeInitialization(bean, beanClass);
        }

        for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
            bean = beanPostProcessor.postProcessAfterInitialization(bean, beanClass);
        }

        return bean;
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
}
