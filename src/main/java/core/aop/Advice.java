package core.aop;

/**
 * Created by yusik on 2020/07/03.
 */
@FunctionalInterface
public interface Advice {

    default int getOrder() {
        return Integer.MAX_VALUE;
    }

    Object invoke(MethodInvocation invocation) throws Throwable;
}
