package core.di.beans.factory.proxy;

@FunctionalInterface
public interface Advice {

    Object invoke(JoinPoint joinPoint);
}
