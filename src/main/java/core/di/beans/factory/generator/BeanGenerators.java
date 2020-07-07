package core.di.beans.factory.generator;

import core.di.beans.factory.BeanFactory;
import core.di.beans.factory.BeanGenerator;
import core.di.beans.factory.config.BeanDefinition;

import java.util.*;

public class BeanGenerators {
    private final List<BeanGenerator> beanGenerators;

    public BeanGenerators(BeanFactory beanFactory) {
        this(Arrays.asList(
                new ProxyBeanGenerator(beanFactory),
                new ConcreteProxyBeanGenerator(beanFactory),
                new AnnotatedBeanGenerator(beanFactory),
                new ConcreteClassBeanGenerator(beanFactory)
        ));
    }

    public BeanGenerators(Collection<BeanGenerator> beanGenerators) {
        this.beanGenerators = new ArrayList<>(beanGenerators);
    }

    public <T> T generate(Class<T> clazz, BeanDefinition beanDefinition) {
        return beanGenerators.stream()
                .filter(beanGenerator -> beanGenerator.support(beanDefinition))
                .map(beanGenerator -> beanGenerator.generate(clazz, beanDefinition))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }
}
