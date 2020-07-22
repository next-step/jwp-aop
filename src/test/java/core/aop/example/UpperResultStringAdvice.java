package core.aop.example;

import core.aop.advice.Advice;
import core.aop.pointcut.MatchMethodPointcut;
import java.lang.reflect.Method;
import net.sf.cglib.proxy.MethodProxy;

/**
 * Created by iltaek on 2020/07/21 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public class UpperResultStringAdvice extends Advice {

    public UpperResultStringAdvice(MatchMethodPointcut pointcut) {
        super(pointcut);
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        String returnValue = (String) proxy.invokeSuper(obj, args);
        if (pointcut.matches(method, obj.getClass(), args)) {
            return returnValue.toUpperCase();
        }
        return returnValue;
    }
}
