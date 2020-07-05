package core.di.beans.factory.generator;

import core.di.beans.factory.BeanFactory;
import core.di.beans.factory.config.BeanDefinition;
import core.di.context.annotation.AnnotatedBeanDefinition;

import java.util.Optional;

public class AnnotatedBeanGenerator extends AbstractBeanGenerator {

    public AnnotatedBeanGenerator(BeanFactory beanFactory) {
        super(beanFactory);
    }

    @Override
    public boolean support(BeanDefinition beanDefinition) {
        return beanDefinition instanceof AnnotatedBeanDefinition;
    }

    @Override
    public <T> T generate(Class<T> clazz, BeanDefinition beanDefinition) {
        Optional<Object> optionalBean = createAnnotatedBean(beanDefinition);

        return (T) optionalBean.orElse(null);
    }
}
