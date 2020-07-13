package core.di.beans.factory.scanner;

import core.annotation.Bean;
import core.annotation.Configuration;
import core.di.beans.factory.definition.BeanDefinitionRegistry;
import core.di.beans.factory.definition.MethodBeanDefinition;
import core.util.ReflectionUtils;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author KingCjy
 */
public class MethodBeanScanner implements BeanScanner {

    public final BeanDefinitionRegistry beanDefinitionRegistry;

    public MethodBeanScanner(BeanDefinitionRegistry beanDefinitionRegistry) {
        this.beanDefinitionRegistry = beanDefinitionRegistry;
    }

    @Override
    public void scan(Object... basePackages) {
        Reflections reflections = new Reflections(basePackages, new TypeAnnotationsScanner(), new SubTypesScanner(), new MethodAnnotationsScanner());
        Set<Class<?>> configurationClasses = ReflectionUtils.getAnnotatedClasses(reflections, Configuration.class);

        Set<MethodBeanDefinition> beanDefinitions = createMethodBeanDefinitions(configurationClasses);

        beanDefinitions.forEach(beanDefinitionRegistry::registerDefinition);
    }

    private Set<MethodBeanDefinition> createMethodBeanDefinitions(Set<Class<?>> configurationClasses) {
        return configurationClasses.stream()
                .flatMap(targetClass -> Arrays.stream(targetClass.getMethods())
                        .filter(method -> method.isAnnotationPresent(Bean.class))
                        .map(MethodBeanDefinition::new))
                .collect(Collectors.toSet());
    }
}
