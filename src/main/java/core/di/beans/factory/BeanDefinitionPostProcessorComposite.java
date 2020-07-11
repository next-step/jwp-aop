package core.di.beans.factory;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

/**
 * @author KingCjy
 */
public class BeanDefinitionPostProcessorComposite implements BeanDefinitionPostProcessor {

    private Set<BeanDefinitionPostProcessor> postProcessors;

    public BeanDefinitionPostProcessorComposite(BeanDefinitionPostProcessor ...beanDefinitionPostProcessor) {
        postProcessors = new LinkedHashSet<>(Arrays.asList(beanDefinitionPostProcessor));
    }

    @Override
    public boolean support(BeanDefinition beanDefinition) {
        return getPostProcessor(beanDefinition).isPresent();
    }

    @Override
    public BeanDefinition process(BeanDefinition beanDefinition) {
        return getPostProcessor(beanDefinition)
                .map(postProcessor -> postProcessor.process(beanDefinition))
                .orElse(null);
    }

    private Optional<BeanDefinitionPostProcessor> getPostProcessor(BeanDefinition beanDefinition) {
        return postProcessors.stream()
                .filter(postProcessors -> postProcessors.support(beanDefinition))
                .findFirst();
    }
}
