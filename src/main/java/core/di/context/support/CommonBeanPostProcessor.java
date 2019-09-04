package core.di.context.support;

import com.google.common.collect.Lists;
import core.annotation.PostConstruct;
import core.di.beans.factory.support.BeanFactoryUtils;
import core.di.context.ApplicationContext;
import core.di.context.BeanPostProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

public class CommonBeanPostProcessor implements BeanPostProcessor {

    private static final Logger logger = LoggerFactory.getLogger(AspectBeanPostProcessor.class);

    private ApplicationContext applicationContext;

    public CommonBeanPostProcessor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, Class<?> clazz) {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, Class<?> clazz) {
        Set<Method> initializeMethods
                = BeanFactoryUtils.getBeanMethods(clazz, PostConstruct.class);
        if (!initializeMethods.isEmpty()) {
            for (Method initializeMethod : initializeMethods) {
                logger.debug("@PostConstruct Initialize Method : {}", initializeMethod);
                BeanFactoryUtils.invokeMethod(initializeMethod, bean,
                        populateArguments(initializeMethod.getParameterTypes()));
            }
        }
        return bean;
    }

    private Object[] populateArguments(Class<?>[] paramTypes) {
        List<Object> args = Lists.newArrayList();
        for (Class<?> param : paramTypes) {
            Object bean = applicationContext.getBean(param);
            if (bean == null) {
                throw new NullPointerException(param + "에 해당하는 Bean이 존재하지 않습니다.");
            }
            args.add(applicationContext.getBean(param));
        }
        return args.toArray();
    }
}

