package core.di.beans.factory;

/**
 * Created by yusik on 2020/07/04.
 */
public interface BeanPostProcessor {
    default Object postProcess(Object bean) {
        return bean;
    }
}
