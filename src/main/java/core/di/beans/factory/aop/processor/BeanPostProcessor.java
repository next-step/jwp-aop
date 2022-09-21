package core.di.beans.factory.aop.processor;

import core.di.beans.factory.BeanFactory;

import java.util.function.BiConsumer;

public interface BeanPostProcessor {

    int priority();

    boolean supports(Class<?> clazz, Object instance);

    void action(Class<?> clazz, Object instance, BiConsumer<Class<?>, Object> consumer, BeanFactory ac);
}
