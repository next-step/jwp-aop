package core.aop;

/**
 * Created by yusik on 2020/07/03.
 */
@FunctionalInterface
public interface MethodInvocation {
    Object proceed() throws Throwable;
}
