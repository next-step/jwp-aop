package core.di.context.support;

import core.annotation.Around;
import core.annotation.Aspect;
import core.di.beans.factory.support.FactoryBean;
import core.aop.ProceedingJoinPoint;
import core.di.beans.factory.config.BeanDefinition;
import core.di.beans.factory.support.BeanFactoryUtils;
import core.di.context.ApplicationContext;
import core.di.context.BeanPostProcessor;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParser;
import org.aspectj.weaver.tools.ShadowMatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AspectBeanPostProcessor implements BeanPostProcessor {

    private static final Logger logger = LoggerFactory.getLogger(AspectBeanPostProcessor.class);
    final private PointcutParser parser =
            PointcutParser.getPointcutParserSupportingAllPrimitivesAndUsingContextClassloaderForResolution();

    private ApplicationContext applicationContext;
    private List<AspectBean> aspectBeans = new ArrayList<>();

    public AspectBeanPostProcessor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        init();
    }

    public void init() {
        List<BeanDefinition> beanDefinitions = applicationContext.getBeanDefinitions();
        for (BeanDefinition beanDefinition : beanDefinitions) {
            Class<?> clazz = beanDefinition.getBeanClass();
            if (clazz.isAnnotationPresent(Aspect.class)) {
                final Object bean = applicationContext.getBean(clazz);
                Set<Method> beanMethods = BeanFactoryUtils.getBeanMethods(clazz, Around.class);
                for (Method method : beanMethods) {
                    final Around around = method.getAnnotation(Around.class);
                    final PointcutExpression pointcutExpression = parser.parsePointcutExpression(around.value());
                    AspectBean aspectBean = new AspectBean(pointcutExpression, bean, method);
                    aspectBeans.add(aspectBean);
                }
            }
        }
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, Class<?> clazz) {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, Class<?> clazz) {
        for (AspectBean aspectBean : aspectBeans) {
            if (aspectBean.matches(clazz)) {
                logger.info("create proxy object: {}", clazz.getName());
                FactoryBean fb = (FactoryBean) aspectBean.createFactoryBean(clazz);
                return fb;
            }
        }
        return bean;
    }

    private static class AspectBean {

        private Object target;
        private PointcutExpression pointcutExpression;
        private Method joinPointMethod;

        public AspectBean(PointcutExpression pointcutExpression,
                          Object target,
                          Method joinPointMethod) {
            this.pointcutExpression = pointcutExpression;
            this.target = target;
            this.joinPointMethod = joinPointMethod;
        }

        public PointcutExpression getPointcutExpression() {
            return pointcutExpression;
        }

        public FactoryBean createFactoryBean(Class<?> clazz) {
            return new FactoryBean() {
                @Override
                public Object getObject() {
                    Enhancer enhancer = new Enhancer();
                    enhancer.setSuperclass(clazz);
                    enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> {
                        final ShadowMatch shadowMatch = pointcutExpression.matchesMethodExecution(method);
                        if (shadowMatch.maybeMatches()) {
                            ProceedingJoinPoint pjp = new ProceedingJoinPoint(obj, method, args, proxy);
                            return joinPointMethod.invoke(target, pjp);
                        }

                        return proxy.invokeSuper(obj, args);
                    });
                    return clazz.cast(enhancer.create());
                }

                @Override
                public Class<?> getType() {
                    return clazz;
                }
            };
        }

        public boolean matches(Class<?> clazz) {
            return pointcutExpression.matchesStaticInitialization(clazz).maybeMatches();
        }
    }

}
