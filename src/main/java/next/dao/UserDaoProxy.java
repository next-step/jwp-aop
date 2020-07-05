package next.dao;

import core.annotation.Component;
import core.aop.Advice;
import core.aop.PointCut;
import core.aop.TempProxyFactoryBean;
import core.di.beans.factory.config.BeanDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

@Component
public class UserDaoProxy extends TempProxyFactoryBean<UserDao> {
    private final Logger logger = LoggerFactory.getLogger(UserDaoProxy.class);

    public UserDaoProxy(BeanDefinition beanDefinition, Object... arguments) {
        super(beanDefinition, arguments);
    }

    @Override
    protected Advice advice() {
        return (object, method, arguments, proxy) -> {
            StopWatch stopWatch = new StopWatch(method.getName());
            stopWatch.start();

            Object ret = null;
            try {
                ret = proxy.invokeSuper(object, arguments);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            stopWatch.stop();

            logger.info("Proxy [{}]\n{}", method.getName(), stopWatch.prettyPrint());
            return ret;
        };
    }

    @Override
    protected PointCut pointCut() {
        return (method, targetClass, arguments) -> {
            boolean match = method.getName()
                    .startsWith("find");

            logger.info("PointCut : [{}] / match : [{}]", method.getName(), match);
            return match;
        };
    }

    @Override
    public Class<?> getClassType() {
        return UserDao.class;
    }
}
