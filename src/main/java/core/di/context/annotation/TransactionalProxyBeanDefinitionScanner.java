package core.di.context.annotation;

import core.annotation.Transactional;
import core.di.beans.factory.ProxyFactoryBean;
import core.di.beans.factory.support.BeanDefinitionRegistry;
import core.di.beans.factory.support.ProxyBeanDefinition;
import core.di.context.support.TransactionAdvice;
import core.di.context.support.TransactionPointCut;
import org.reflections.Reflections;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Method;
import java.util.Set;

public class TransactionalProxyBeanDefinitionScanner extends AbstractBeanDefinitionScanner {

    public TransactionalProxyBeanDefinitionScanner(BeanDefinitionRegistry beanDefinitionRegistry) {
        super(beanDefinitionRegistry);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void doScan(Object... basePackages) {
        final Reflections reflections = new Reflections(basePackages);

        final Set<Class<?>> beanClasses = getTypesAnnotatedWith(reflections, Transactional.class);
        for (Class<?> beanClass : beanClasses) {
            registerDefinition(beanClass);
        }

        final Set<Method> transactionRequiredMethods = getMethodsAnnotatedWith(reflections, Transactional.class);
        for (Method method : transactionRequiredMethods) {
            final Class<?> clazz = method.getDeclaringClass();
            registerDefinition(clazz);
        }


    }

    private void registerDefinition(Class<?> beanClass) {
        final ProxyFactoryBean proxyFactoryBean = buildProxyFactoryBean(beanClass);
        final ProxyBeanDefinition definition = new ProxyBeanDefinition(proxyFactoryBean);
        beanDefinitionRegistry.registerBeanDefinition(beanClass, definition);
    }


    private ProxyFactoryBean buildProxyFactoryBean(Class<?> clazz) {
        final TransactionAdvice advice = new TransactionAdvice();
        final TransactionPointCut pointCut = new TransactionPointCut();
        final ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setTarget(BeanUtils.instantiateClass(clazz));
        proxyFactoryBean.setAdvice(advice);
        proxyFactoryBean.setPointCut(pointCut);
        return proxyFactoryBean;
    }
}
