package core.di.beans.factory.aop;

import core.di.beans.factory.config.BeanDefinition;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

public class ProxyBeanFactories {
    private final Set<ProxyBeanFactory<?>> proxyBeanFactories;

    public ProxyBeanFactories(Collection<ProxyBeanFactory<?>> proxyBeanFactories) {
        this.proxyBeanFactories = new LinkedHashSet<>(proxyBeanFactories);
    }

    public boolean support(BeanDefinition beanDefinition) {
        return proxyBeanFactories.stream()
                .anyMatch(proxyBeanFactory -> proxyBeanFactory.support(beanDefinition));
    }

    public Object getBean(BeanDefinition beanDefinition) {
        return proxyBeanFactories.stream()
                .filter(proxyBeanFactory -> proxyBeanFactory.support(beanDefinition))
                .findFirst()
                .map(proxyBeanFactory -> proxyBeanFactory.getBean(beanDefinition))
                .orElse(null);
    }
}
