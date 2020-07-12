package core.di.beans.factory.initializer;

import core.di.beans.factory.BeanFactory;
import core.di.beans.factory.definition.BeanDefinition;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

/**
 * @author KingCjy
 */
public class BeanInitializerComposite implements BeanInitializer {

    private Set<BeanInitializer> beanInitializers = new LinkedHashSet<>();

    public BeanInitializerComposite(BeanInitializer ...beanInitializers) {
        this.beanInitializers.addAll(Arrays.asList(beanInitializers));
    }

    @Override
    public boolean support(BeanDefinition beanDefinition) {
        return getBeanInitializer(beanDefinition).isPresent();
    }

    @Override
    public Object instantiate(BeanDefinition beanDefinition, BeanFactory beanFactory) {
        return getBeanInitializer(beanDefinition)
                .map(beanInitializer -> beanInitializer.instantiate(beanDefinition, beanFactory))
                .orElse(null);
    }

    private Optional<BeanInitializer> getBeanInitializer(BeanDefinition beanDefinition) {
        return beanInitializers.stream()
                .filter(beanInitializer -> beanInitializer.support(beanDefinition))
                .findFirst();
    }
}
