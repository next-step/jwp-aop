package core.aop;

public interface Advice {

    Object invoke(Joinpoint joinpoint) throws Throwable;
}
