package core.aop.advice;

import core.aop.Advice;
import net.sf.cglib.proxy.MethodProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

import java.lang.reflect.Method;

public class StopWatchAdvice implements Advice {
    private static final Logger logger = LoggerFactory.getLogger(StopWatchAdvice.class);

    @Override
    public Object doAdvice(Object object, Method method, Object[] arguments, MethodProxy proxy) {
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
    }
}
