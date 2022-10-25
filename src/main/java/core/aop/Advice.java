package core.aop;

/**
 * 실질적으로 어떤 일을 해야할 지에 대한 것,
 * 실질적인 부가기능을 담은 구현체
 */
public interface Advice {
    Object invoke(Joinpoint joinpoint) throws Throwable;
}
