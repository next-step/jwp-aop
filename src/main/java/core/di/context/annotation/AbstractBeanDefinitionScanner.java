package core.di.context.annotation;

import com.google.common.collect.Sets;
import core.di.beans.factory.support.BeanDefinitionRegistry;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.util.Set;

public abstract class AbstractBeanDefinitionScanner implements BeanDefinitionScanner {

    protected final BeanDefinitionRegistry beanDefinitionRegistry;

    public AbstractBeanDefinitionScanner(BeanDefinitionRegistry beanDefinitionRegistry) {
        this.beanDefinitionRegistry = beanDefinitionRegistry;
    }

    @SuppressWarnings("unchecked")
    protected Set<Class<?>> getTypesAnnotatedWith(Reflections reflections, Class<? extends Annotation>... annotations) {
        final Set<Class<?>> preInstantiatedBeans = Sets.newHashSet();
        for (Class<? extends Annotation> annotation : annotations) {
            preInstantiatedBeans.addAll(reflections.getTypesAnnotatedWith(annotation));
        }
        return preInstantiatedBeans;
    }


}
