package core.di.context.support;

import core.di.beans.factory.support.BeanFactoryUtils;
import core.di.context.ApplicationContext;
import core.di.context.BeanPostProcessor;
import net.sf.cglib.proxy.Enhancer;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Objects;

import static core.di.context.support.TransactionUtils.getTransactionMetadata;

public class TransactionBeanPostProcessor implements BeanPostProcessor {

    private ApplicationContext applicationContext;

    public TransactionBeanPostProcessor(AnnotationConfigApplicationContext annotationConfigApplicationContext) {
        this.applicationContext = annotationConfigApplicationContext;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, Class<?> clazz) {
        TransactionMetadata metadata = getTransactionMetadata(clazz);

        if (!metadata.hasTransaction()) {
            return bean;
        }

        return createTransactionBean(metadata);
    }

    private Object createTransactionBean(TransactionMetadata metadata) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(metadata.getBeanClass());
        enhancer.setCallback(new TransactionMethodInterceptor(applicationContext, metadata));

        final Constructor<?> injectedConstructor =
                BeanFactoryUtils.getInjectedConstructorWithDefault(metadata.getBeanClass());
        final Object[] args = Arrays.stream(injectedConstructor.getParameterTypes())
                .map(this::getBeanInternal)
                .toArray();

        return enhancer.create(injectedConstructor.getParameterTypes(), args);
    }

    private Object getBeanInternal(Class<?> param) {
        return Objects.requireNonNull(applicationContext.getBean(param),
                param + "…에 해당하는 Bean이 존재하지 않습니다");
    }

}
