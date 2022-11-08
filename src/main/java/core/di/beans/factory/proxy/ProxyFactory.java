package core.di.beans.factory.proxy;

public interface ProxyFactory {

    <T> Object createProxy(Class<T> targetClass, T targetObject, Advisor advisor);
}
