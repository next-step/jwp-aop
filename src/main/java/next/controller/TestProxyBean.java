package next.controller;

import core.annotation.Component;
import core.aop.Advice;
import core.aop.PointCut;
import core.aop.ProxyFactoryBean;
import core.di.beans.factory.config.BeanDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

@Component
public class TestProxyBean extends ProxyFactoryBean<Test> {
    private static final Logger logger = LoggerFactory.getLogger(TestProxyBean.class);

    public TestProxyBean(BeanDefinition beanDefinition, Object... arguments) {
        super(beanDefinition, arguments);
    }

    @Override
    protected Advice advice() {
        return (object, method, arguments, proxy) -> {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            Object o = null;
            try {
                o = proxy.invokeSuper(object, arguments);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            stopWatch.stop();
            logger.info("Proxy ApiUserController process time : {}", stopWatch.prettyPrint());
            return o;
        };
    }

    @Override
    protected PointCut pointCut() {
        return (method, targetClass, arguments) -> true;
    }

    @Override
    public Class<?> getClassType() {
        return Test.class;
    }
}
