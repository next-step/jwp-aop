package core.aop;

import core.di.beans.factory.support.FactoryBean;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by yusik on 2020/07/03.
 */
public class ProxyFactoryBean implements FactoryBean<Object> {

    private final List<Advice> advices = new ArrayList<>();

    private Pointcut pointcut = Pointcut.DEFAULT;
    private Object target;

    @Override
    public Object getObject() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(getObjectType());
        enhancer.setCallback(new InvocableMethodChain(pointcut.getMethodMatcher(), advices));
        return getObjectType().cast(enhancer.create());
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

    public void setPointcut(Pointcut pointcut) {
        this.pointcut = pointcut;
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

            System.out.println(advices);
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