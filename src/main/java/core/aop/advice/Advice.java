package core.aop.advice;

import core.aop.pointcut.MatchMethodPointcut;
import net.sf.cglib.proxy.MethodInterceptor;

/**
 * Created by iltaek on 2020/07/21 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public abstract class Advice implements MethodInterceptor {

    protected final MatchMethodPointcut pointcut;

    public Advice(MatchMethodPointcut pointcut) {
        this.pointcut = pointcut;
    }
}
