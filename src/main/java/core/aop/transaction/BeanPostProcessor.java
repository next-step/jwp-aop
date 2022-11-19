package core.aop.transaction;

public interface BeanPostProcessor {

    default Object process(Object object) {
        return object;
    }
}
