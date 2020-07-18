package core.di.beans.factory;

public interface BeanPostProcessor {
    Object postProcess(Object bean);
}
