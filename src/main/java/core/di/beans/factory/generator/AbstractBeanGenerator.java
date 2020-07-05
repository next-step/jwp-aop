package core.di.beans.factory.generator;

import com.google.common.collect.Lists;
import core.di.beans.factory.BeanFactory;
import core.di.beans.factory.BeanGenerator;
import core.di.beans.factory.config.BeanDefinition;
import core.di.beans.factory.support.BeanFactoryUtils;
import core.di.beans.factory.support.InjectType;
import core.di.context.annotation.AnnotatedBeanDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public abstract class AbstractBeanGenerator implements BeanGenerator {
    protected static final Logger log = LoggerFactory.getLogger(AbstractBeanGenerator.class);
    protected BeanFactory beanFactory;

    public AbstractBeanGenerator(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    protected Optional<Object> createAnnotatedBean(BeanDefinition beanDefinition) {
        AnnotatedBeanDefinition abd = (AnnotatedBeanDefinition) beanDefinition;
        Method method = abd.getMethod();
        Object[] args = populateArguments(method.getParameterTypes());

        return BeanFactoryUtils.invokeMethod(method, beanFactory.getBean(method.getDeclaringClass()), args);
    }


    protected Object[] populateArguments(Class<?>[] paramTypes) {
        List<Object> args = Lists.newArrayList();
        for (Class<?> param : paramTypes) {
            Object bean = beanFactory.getBean(param);
            if (bean == null) {
                throw new NullPointerException(param + "에 해당하는 Bean이 존재하지 않습니다.");
            }
            args.add(beanFactory.getBean(param));
        }
        return args.toArray();
    }

    protected Object inject(BeanDefinition beanDefinition) {
        if (beanDefinition.getResolvedInjectMode() == InjectType.INJECT_NO) {
            return BeanUtils.instantiate(beanDefinition.getBeanClass());
        } else if (beanDefinition.getResolvedInjectMode() == InjectType.INJECT_FIELD) {
            return injectFields(beanDefinition);
        } else {
            return injectConstructor(beanDefinition);
        }
    }

    protected Object injectConstructor(BeanDefinition beanDefinition) {
        Constructor<?> constructor = beanDefinition.getInjectConstructor();
        Object[] args = populateArguments(constructor.getParameterTypes());
        return BeanUtils.instantiateClass(constructor, args);
    }

    protected Object injectFields(BeanDefinition beanDefinition) {
        Object bean = BeanUtils.instantiate(beanDefinition.getBeanClass());
        Set<Field> injectFields = beanDefinition.getInjectFields();
        for (Field field : injectFields) {
            injectField(bean, field);
        }
        return bean;
    }

    protected void injectField(Object bean, Field field) {
        log.debug("Inject Bean : {}, Field : {}", bean, field);
        try {
            field.setAccessible(true);
            field.set(bean, beanFactory.getBean(field.getType()));
        } catch (IllegalAccessException | IllegalArgumentException e) {
            log.error(e.getMessage());
        }
    }
}
