package core.di.beans.factory.config;

import core.di.beans.factory.config.converter.ConcreteProxyBeanDefinitionConverter;
import core.di.beans.factory.config.converter.DefaultBeanDefinitionConverter;
import core.di.beans.factory.config.converter.TransactionBeanDefinitionConverter;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

public class BeanDefinitionConverters {
    private final Set<BeanDefinitionConverter> converters = new LinkedHashSet<>();

    public BeanDefinitionConverters(Collection<BeanDefinitionConverter> converters) {
        this.converters.addAll(converters);
    }

    public BeanDefinitionConverters() {
        this.converters.add(new TransactionBeanDefinitionConverter());
        this.converters.add(new ConcreteProxyBeanDefinitionConverter());
        this.converters.add(new DefaultBeanDefinitionConverter());
    }

    public BeanDefinition convert(Class<?> clazz) {
        return converters.stream()
                .filter(converter -> converter.support(clazz))
                .map(converter -> converter.convert(clazz))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No matching bean definition converter for " + clazz));
    }
}
