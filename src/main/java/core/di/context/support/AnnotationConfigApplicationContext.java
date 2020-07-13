package core.di.context.support;

import com.google.common.collect.Lists;
import core.annotation.ComponentScan;
import core.di.beans.factory.DefaultBeanFactory;
import core.di.beans.factory.processor.BeanPostProcessorComposite;
import core.di.beans.factory.processor.TransactionBeanPostProcessor;
import core.di.beans.factory.scanner.ClassBeanScanner;
import core.di.beans.factory.scanner.MethodBeanScanner;
import core.di.context.ApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;

public class AnnotationConfigApplicationContext implements ApplicationContext {
    private static final Logger log = LoggerFactory.getLogger(AnnotationConfigApplicationContext.class);

    private DefaultBeanFactory beanFactory;

    public AnnotationConfigApplicationContext(Class<?>... annotatedClasses) {
        Object[] basePackages = findBasePackages(annotatedClasses);

        beanFactory = new DefaultBeanFactory();

        new ClassBeanScanner(beanFactory).scan(basePackages);
        new MethodBeanScanner(beanFactory).scan(basePackages);

        beanFactory.setBeanPostProcessors(new BeanPostProcessorComposite(
                new TransactionBeanPostProcessor(beanFactory)
        ));
        beanFactory.initialize();
    }

    private Object[] findBasePackages(Class<?>[] annotatedClasses) {
        List<Object> basePackages = Lists.newArrayList();
        for (Class<?> annotatedClass : annotatedClasses) {
            ComponentScan componentScan = annotatedClass.getAnnotation(ComponentScan.class);
            if (componentScan == null) {
                continue;
            }
            for (String basePackage : componentScan.value()) {
                log.info("Component Scan basePackage : {}", basePackage);
            }
            basePackages.addAll(Arrays.asList(componentScan.value()));
        }
        return basePackages.toArray();
    }

    @Nullable
    @Override
    public Object getBean(String name) {
        return beanFactory.getBean(name);
    }

    @Override
    public <T> T getBean(Class<T> clazz) {
        return beanFactory.getBean(clazz);
    }

    @Nullable
    @Override
    public <T> T getBean(String name, Class<T> requireType) {
        return beanFactory.getBean(name, requireType);
    }

    @Nullable
    @Override
    public Object[] getAnnotatedBeans(Class<? extends Annotation> annotation) {
        return beanFactory.getAnnotatedBeans(annotation);
    }

}
