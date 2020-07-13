package core.aop;



import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * @author KingCjy
 */
public class CglibAopProxy {

    public static class DynamicAdvisedInterceptor implements MethodInterceptor {

        private Object target;

        public DynamicAdvisedInterceptor(Object target) {
            this.target = target;
        }

        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            return method.invoke(target, args);
        }
    }
}
