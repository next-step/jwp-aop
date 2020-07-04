package core.aop;

import core.di.beans.factory.BeanFactory;
import core.di.beans.factory.support.BeanFactoryAware;
import core.di.beans.factory.support.BeanFactoryUtils;
import core.di.beans.factory.support.FactoryBean;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by yusik on 2020/07/03.
 */
public class ProxyFactoryBean implements FactoryBean<Object> {

    private final List<Advice> advices = new ArrayList<>();

    private Pointcut pointcut = Pointcut.DEFAULT;
    private Object target;
    private BeanFactory beanFactory;

    @Override
    public Object getObject() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(getObjectType());
        enhancer.setCallback(new InvocableMethodChain(pointcut.getMethodMatcher(), advices));

        Constructor<?> injectedConstructor = BeanFactoryUtils.getInjectedConstructor(getObjectType());

        if (injectedConstructor != null) {
            Object[] args = Arrays.stream(injectedConstructor.getParameterTypes())
                    .map(dependency -> beanFactory.getBean(dependency))
                    .toArray();
            return enhancer.create(injectedConstructor.getParameterTypes(), args);

        }
        return enhancer.create();
    }

    @Override
    public Class<?> getObjectType() {
        return target.getClass();
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public void addAdvice(Advice advice) {
        this.advices.add(advice);
    }

    public void addAdvices(Collection<Advice> advices) {
        this.advices.addAll(advices);
    }

    public void setPointcut(Pointcut pointcut) {
        this.pointcut = pointcut;
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    private static class InvocableMethodChain implements MethodInterceptor {

        private final MethodMatcher methodMatcher;
        private final List<Advice> advices;


        public InvocableMethodChain(MethodMatcher methodMatcher, List<Advice> advices) {
            this.methodMatcher = methodMatcher;
            this.advices = advices.stream()
                    .sorted(Comparator.comparingInt(Advice::getOrder))
                    .collect(Collectors.toList());
        }

        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {

            // target invocation
            MethodInvocation invocation = () -> proxy.invokeSuper(obj, args);
            if (methodMatcher.matches(method, obj.getClass())) {
                for (int i = advices.size() - 1; i >= 0; i--) {
                    int index = i;
                    MethodInvocation preInvocation = invocation;
                    invocation = () -> advices.get(index).invoke(preInvocation);
                }
            }
            return invocation.proceed();
        }
    }
}