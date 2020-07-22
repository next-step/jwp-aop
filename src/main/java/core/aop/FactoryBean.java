package core.aop;

/**
 * Created by iltaek on 2020/07/21 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public interface FactoryBean<T> {

    T getObject() throws Exception;

    Class<? extends T> getObjectType();
}
