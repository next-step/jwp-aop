package core.di.beans.factory.support;

public interface PostBeanProcessor {

    boolean supports(Class<?> clazz, Object bean);

    Object process(Class<?> clazz, Object bean);

}
