package core.di.beans.factory.proxy;

@FunctionalInterface
public interface JoinPoint {
    Object proceed();
}
