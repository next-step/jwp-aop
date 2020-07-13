package core.di.beans.factory.processor;

import core.di.beans.factory.definition.BeanDefinition;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author KingCjy
 */
public class BeanPostProcessorComposite implements BeanPostProcessor {

    private Set<BeanPostProcessor> beanPostProcessors;

    public BeanPostProcessorComposite(BeanPostProcessor ...beanPostProcessors) {
        this.beanPostProcessors = new LinkedHashSet<>(Arrays.asList(beanPostProcessors));
    }

    @Override
    public Object postProcess(BeanDefinition beanDefinition, Object bean) {
        Object result = bean;

        for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
            Object current = beanPostProcessor.postProcess(beanDefinition, result);
            if(current == null) {
                return result;
            }
            result = current;
        }

        return result;
    }
}
