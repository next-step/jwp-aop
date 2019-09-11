package core.di.beans.factory.support;

import core.di.beans.factory.config.BeanDefinition;
import core.di.context.annotation.AnnotatedBeanDefinition;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class BeanCreator {

    private static final Logger log = LoggerFactory.getLogger(BeanCreator.class);

    private DefaultBeanFactory beanFactory;
    private AspectBeanProcessor aspectBeanProcessor;

    public BeanCreator(DefaultBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
        this.aspectBeanProcessor = new AspectBeanProcessor(beanFactory.getAspectBeans());
    }

    public Object create(BeanDefinition beanDefinition) {
        if (beanDefinition.getResolvedInjectMode() == InjectType.INJECT_NO) {
            return BeanUtils.instantiate(beanDefinition.getBeanClass());
        } else if (beanDefinition.getResolvedInjectMode() == InjectType.INJECT_FIELD) {
            return injectFields(beanDefinition);
        } else {
            return createWithConstructor(beanDefinition);
        }
    }

    public Optional<Object> createAnnotatedBean(BeanDefinition beanDefinition, Class<?> clazz) {
        AnnotatedBeanDefinition abd = (AnnotatedBeanDefinition) beanDefinition;
        Method method = abd.getMethod();
        Object[] args = populateArguments(method.getParameterTypes());
        return BeanFactoryUtils.invokeMethod(method,
                beanFactory.getBean(method.getDeclaringClass()), args);
    }

    private Object createWithConstructor(BeanDefinition beanDefinition) {
        Constructor<?> ctor = beanDefinition.getInjectConstructor();
        Object[] args = populateArguments(ctor.getParameterTypes());
        if (!aspectBeanProcessor.isAspect(beanDefinition)) {
            return BeanUtils.instantiateClass(ctor, args);
        }

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(beanDefinition.getBeanClass());
        enhancer.setCallback(new BeanProxyMethodInterceptor(aspectBeanProcessor, beanDefinition));
        return enhancer.create(ctor.getParameterTypes(), args);
    }

    private Object injectFields(BeanDefinition beanDefinition) {
        Object bean = createBean(beanDefinition);
        Set<Field> injectFields = beanDefinition.getInjectFields();
        for (Field field : injectFields) {
            injectField(bean, field);
        }
        return bean;
    }

    private Object createBean(BeanDefinition beanDefinition) {
        if (!aspectBeanProcessor.isAspect(beanDefinition)) {
            return BeanUtils.instantiate(beanDefinition.getBeanClass());
        }

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(beanDefinition.getBeanClass());
        enhancer.setCallback(new BeanProxyMethodInterceptor(aspectBeanProcessor, beanDefinition));
        return enhancer.create();
    }

    private void injectField(Object bean, Field field) {
        log.debug("Inject Bean : {}, Field : {}", bean, field);
        try {
            field.setAccessible(true);
            field.set(bean, beanFactory.getBean(field.getType()));
        } catch (IllegalAccessException | IllegalArgumentException e) {
            log.error(e.getMessage());
        }
    }

    private Object[] populateArguments(Class<?>[] paramTypes) {
        return Arrays.stream(paramTypes)
                .map(this::getBeanInternal)
                .toArray();
    }

    private Object getBeanInternal(Class<?> param) {
        return Objects.requireNonNull(beanFactory.getBean(param), param + "…에 해당하는 Bean이 존재하지 " +
                "않습니다");
    }

    private static class BeanProxyMethodInterceptor implements MethodInterceptor{

        private AspectBeanProcessor aspectBeanProcessor;
        private BeanDefinition beanDefinition;

        public BeanProxyMethodInterceptor(AspectBeanProcessor aspectBeanProcessor, BeanDefinition beanDefinition) {
            this.aspectBeanProcessor = aspectBeanProcessor;
            this.beanDefinition = beanDefinition;
        }

        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            if (!aspectBeanProcessor.isAvailableMethods(beanDefinition, method)) {
                return proxy.invokeSuper(obj, args);
            }

            return aspectBeanProcessor.process(beanDefinition, new ProxyInvocation(obj, method, args,
                    proxy));
        }
    }

}
