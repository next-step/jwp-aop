package core.di.beans.factory.initializer;

import core.di.beans.factory.BeanFactory;
import core.di.beans.factory.BeanFactoryUtils;
import core.di.beans.factory.definition.BeanDefinition;
import core.di.beans.factory.definition.MethodBeanDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanInstantiationException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author KingCjy
 */
public class MethodBeanDefinitionInitializer extends AbstractBeanDefinitionInitializer {

    private static final Logger logger = LoggerFactory.getLogger(MethodBeanDefinitionInitializer.class);

    @Override
    public boolean support(BeanDefinition beanDefinition) {
        return beanDefinition instanceof MethodBeanDefinition;
    }

    @Override
    public Object instantiateBean(BeanDefinition definition, BeanFactory beanFactory) {
        MethodBeanDefinition beanDefinition = (MethodBeanDefinition) definition;

        Object classInstance = beanFactory.getBean(beanDefinition.getParentType());
        Method method = beanDefinition.getMethod();

        Object[] parameters = BeanFactoryUtils.getParameters(beanFactory, method);

        Object instance = invokeMethod(classInstance, method, parameters);
        logger.info("bean class: {} name: {} instantiate", beanDefinition.getType(), beanDefinition.getName());

        return instance;
    }

    private Object invokeMethod(Object instance, Method method, Object[] parameters) {
        try {
            return method.invoke(instance, parameters);
        } catch (IllegalAccessException e) {
            throw new BeanInstantiationException(method, "Cannot access method '" + method.getName() + "' is it public?", e);
        } catch (InvocationTargetException e) {
            throw new BeanInstantiationException(method, "Method threw exception", e.getTargetException());
        }
    }
}