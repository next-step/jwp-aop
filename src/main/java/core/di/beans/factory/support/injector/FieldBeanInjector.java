package core.di.beans.factory.support.injector;

import core.di.beans.factory.config.BeanDefinition;
import core.di.beans.factory.support.BeanGettable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.util.Set;

@Slf4j
public class FieldBeanInjector implements BeanInjector {
    @Override
    public <T> T inject(BeanGettable beanGettable, BeanDefinition beanDefinition) {
        T bean = (T) BeanUtils.instantiateClass(beanDefinition.getBeanClass());

        Set<Field> injectFields = beanDefinition.getInjectFields();
        for (Field field : injectFields) {
            injectField(beanGettable, bean, field);
        }
        return bean;
    }

    private void injectField(BeanGettable beanGettable, Object bean, Field field) {
        log.debug("Inject Bean : {}, Field : {}", bean, field);

        try {
            field.setAccessible(true);
            field.set(bean, beanGettable.getBean(field.getType()));
        }
        catch (IllegalAccessException | IllegalArgumentException e) {
            log.error(e.getMessage());
        }
    }

}
