package core.di.beans.factory.support;

public interface PostBeanProcessor {

    Object process(Class<?> clazz, Object bean);

}
