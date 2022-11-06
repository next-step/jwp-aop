package core.di.beans.factory.proxy;

public interface Advice {

    Object invoke(JoinPoint joinPoint);
}
