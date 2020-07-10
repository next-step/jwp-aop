package core.di.beans.factory;

import core.annotation.Inject;
import core.annotation.PostConstruct;
import core.util.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author KingCjy
 */
public class ClassBeanDefinitionInitializer extends AbstractBeanDefinitionInitializer {

    private static final Logger logger = LoggerFactory.getLogger(ClassBeanDefinitionInitializer.class);

    @Override
    public boolean support(BeanDefinition beanDefinition) {
        return beanDefinition instanceof ClassBeanDefinition;
    }

    @Override
    public Object instantiateBean(BeanDefinition beanDefinition, BeanFactory beanFactory) {
        Constructor<?> constructor = findInjectController(beanDefinition.getType());

        Object[] parameters = BeanFactoryUtils.getParameters(beanFactory, constructor);

        Object instance = BeanUtils.instantiateClass(constructor, parameters);

        Set<Field> injectFields = findInjectFields(beanDefinition.getType());
        injectFields.forEach(field -> injectField(beanFactory, instance, field));

        Set<Method> postConstructors = findPostConstructMethods(beanDefinition.getType());
        postConstructors.forEach(method -> invokePostConstructor(beanFactory, instance, method));

        logger.info("bean " + instance.getClass() + " instantiate");

        return instance;
    }

    private void injectField(BeanFactory beanFactory, Object instance, Field field) {
        try {
            String beanName = ReflectionUtils.getFieldBeanName(field);
            Object value = beanFactory.getBean(beanName, field.getType());
            field.setAccessible(true);
            field.set(instance, value);
        } catch (IllegalAccessException e) {
            throw new BeanInstantiationException(instance.getClass(), "illegal access '" + field.getName() + "'", e);
        }
    }

    private Set<Field> findInjectFields(Class<?> targetClass) {
        return Arrays.stream(targetClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Inject.class))
                .collect(Collectors.toSet());
    }

    private void invokePostConstructor(BeanFactory beanFactory, Object instance, Method method) {
        try {
            Object[] parameters = BeanFactoryUtils.getParameters(beanFactory, method);
            method.invoke(instance, parameters);
        } catch (IllegalAccessException e) {
            throw new BeanInstantiationException(method, "Cannot access method '" + method.getName() + "' is it public?", e);
        } catch (InvocationTargetException e) {
            throw new BeanInstantiationException(method, "Method threw exception", e.getTargetException());
        }
    }

    private Set<Method> findPostConstructMethods(Class<?> targetClass) {
        return Arrays.stream(targetClass.getMethods())
                .filter(method -> method.isAnnotationPresent(PostConstruct.class))
                .collect(Collectors.toSet());
    }

    private Constructor<?> findInjectController(Class<?> targetClass) {
        return Arrays.stream(targetClass.getConstructors())
                .filter(constructor -> constructor.isAnnotationPresent(Inject.class))
                .findAny()
                .orElseGet(() -> findPrimaryConstructor(targetClass));
    }

    private Constructor<?> findPrimaryConstructor(Class<?> targetClass) {
        try {
            return targetClass.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new BeanInstantiationException(targetClass, "Constructor with @Inject not Found");
        }
    }
}
