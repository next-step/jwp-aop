package core.di.beans.factory.aop.processor;

import core.di.beans.factory.BeanFactory;

import java.util.function.BiConsumer;

public class NoOpBeanPostProcessor implements BeanPostProcessor {
    @Override
    public int priority() {
        return 9;
    }

    @Override
    public boolean supports(Class<?> clazz, Object instance) {
        return true;
    }

    @Override
    public void action(Class<?> clazz, Object instance, BiConsumer<Class<?>, Object> consumer, BeanFactory ac) {
        consumer.accept(clazz, instance);
    }
}
