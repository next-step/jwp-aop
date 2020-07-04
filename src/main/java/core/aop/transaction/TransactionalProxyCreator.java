package core.aop.transaction;

import com.google.common.collect.Lists;
import core.annotation.Transactional;
import core.aop.*;
import core.di.beans.factory.BeanFactory;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collection;

/**
 * Created by yusik on 2020/07/04.
 */
public class TransactionalProxyCreator extends AutoProxyCreator {

    public TransactionalProxyCreator(BeanFactory beanFactory) {
        setBeanFactory(beanFactory);
    }

    @Override
    protected Collection<Advice> getAdvices() {
        return Lists.newArrayList(new TransactionAdvice(beanFactory));
    }

    @Override
    public Pointcut getPointcut() {
        return new TransactionalPointcut(Transactional.class);
    }

    public static class TransactionalPointcut implements Pointcut {

        private final Class<? extends Annotation> targetAnnotation;

        public TransactionalPointcut(Class<? extends Annotation> targetAnnotation) {
            this.targetAnnotation = targetAnnotation;
        }

        @Override
        public ClassFilter getClassFilter() {
            return clazz -> {
                boolean hasMethod = Arrays.stream(clazz.getDeclaredMethods())
                        .anyMatch(method -> method.isAnnotationPresent(targetAnnotation));
                return hasMethod || clazz.isAnnotationPresent(targetAnnotation);
            };
        }

        @Override
        public MethodMatcher getMethodMatcher() {
            return (method, targetClass) -> true;
        }
    }
}
