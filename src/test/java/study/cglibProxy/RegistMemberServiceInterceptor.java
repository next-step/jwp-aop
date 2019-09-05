package study.cglibProxy;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.lang.reflect.Method;

public class RegistMemberServiceInterceptor implements MethodInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(RegistMemberServiceInterceptor.class);

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        logger.debug("RegistMemberServiceInterceptor intercept start");
        Object returnValue = proxy.invokeSuper(obj, args);
        logger.debug("RegistMemberServiceInterceptor intercept end");
        if(returnValue == null) {
            return "Null change Name";
        }
        return returnValue;
    }
}
