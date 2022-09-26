package core.aop;

public interface BeanPostProcessor {
    default Object postProcess(Object bean) {
        return bean;
    }
}
