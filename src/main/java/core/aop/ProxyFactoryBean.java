package core.aop;

import core.aop.Advice.TransactionalAdvice;
import core.aop.PointCut.TransactionalPointCut;
import core.di.beans.factory.support.BeanFactoryUtils;
import core.di.beans.factory.support.ProxyBeanFactory;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.sql.DataSource;
import net.sf.cglib.proxy.Enhancer;

public class ProxyFactoryBean implements FactoryBean<Object> {

    private final Enhancer enhancer = new Enhancer();
    private final ProxyBeanFactory beanFactory;
    private Object target;
    private List<Advisor> advisors;

    public ProxyFactoryBean(Object target, List<Advisor> advisors, ProxyBeanFactory beanFactory) {
        this.target = target;
        this.advisors = advisors;
        this.beanFactory = beanFactory;
    }

    public static ProxyFactoryBean transactionalBean(Object target, ProxyBeanFactory beanFactory) {
        Advice advice = new TransactionalAdvice(beanFactory.getBean(DataSource.class));
        PointCut pointCut = new TransactionalPointCut();
        Advisor advisor = new Advisor(advice, pointCut);
        return new ProxyFactoryBean(target, Arrays.asList(advisor), beanFactory);
    }

    @Override
    public Object getObject() throws Exception {
        Advisors advisors = new Advisors(this.target, this.advisors);
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(advisors);

        Constructor<?> constructor = BeanFactoryUtils.getInjectedConstructor(target.getClass());
        if (constructor != null) {
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            Object[] arguments = getArguments(parameterTypes);
            return enhancer.create(parameterTypes, arguments);
        }

        return enhancer.create();
    }

    private Object[] getArguments(Class<?>[] parameterTypes) {
        List<Object> arguments = new ArrayList<>();
        for (Class<?> parameterType : parameterTypes) {
            Object argument = this.beanFactory.getBean(parameterType);
            if (argument == null) {
                throw new RuntimeException("Not found bean: " + parameterType);
            }
            arguments.add(argument);
        }

        return arguments.toArray();
    }

    @Override
    public Class<?> getObjectType() {
        return target.getClass();
    }

}
