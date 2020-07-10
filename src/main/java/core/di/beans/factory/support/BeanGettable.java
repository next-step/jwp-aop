package core.di.beans.factory.support;

@FunctionalInterface
public interface BeanGettable {
    <T> T getBean(Class<?> type);
}
