package core.aop;

public interface Joinpoint {

    Object proceed() throws Throwable;
}
