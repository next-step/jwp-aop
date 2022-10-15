package core.di.beans.factory.aop;

public interface Advice {

    Object intercept(Object input) throws Throwable;
}
