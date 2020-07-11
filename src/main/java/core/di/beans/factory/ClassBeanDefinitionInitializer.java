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
        Constructor<?> constructor = BeanFactoryUtils.findInjectController(beanDefinition.getType());

        Object[] parameters = BeanFactoryUtils.getParameters(beanFactory, constructor);

        Object instance = BeanUtils.instantiateClass(constructor, parameters);

        Set<Field> injectFields = BeanFactoryUtils.findInjectFields(beanDefinition.getType());
        injectFields.forEach(field -> BeanFactoryUtils.injectField(beanFactory, instance, field));

        Set<Method> postConstructors = BeanFactoryUtils.findPostConstructMethods(beanDefinition.getType());
        postConstructors.forEach(method -> BeanFactoryUtils.invokePostConstructor(beanFactory, instance, method));

        logger.info("bean " + instance.getClass() + " instantiate");

        return instance;
    }
}
