package core.di.beans.factory.aop.processor;

import core.di.beans.factory.BeanFactory;
import core.di.beans.factory.aop.FactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.BiConsumer;

public class FactoryBeanPostProcessor implements BeanPostProcessor {
    private static final Logger log = LoggerFactory.getLogger(FactoryBeanPostProcessor.class);

    private final int priority;

    public FactoryBeanPostProcessor(int priority) {
        this.priority = priority;
    }

    @Override
    public int priority() {
        return priority;
    }

    @Override
    public boolean supports(Class<?> clazz, Object instance) {
        return instance instanceof FactoryBean;
    }

    @Override
    public void action(Class<?> clazz, Object instance, BiConsumer<Class<?>, Object> consumer, BeanFactory ac) {
        FactoryBean<?> factory = (FactoryBean<?>) instance;

        try {
            instance = factory.getObject();
            consumer.accept(clazz, instance);
        } catch (Exception e) {
            log.error("Failed Register Bean: {}, cause: {}", clazz.getName(), e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
