package core.aop;

/**
 * Advice가 적용될 위치
 */
public interface Joinpoint {
    Object proceed() throws Throwable;
}
