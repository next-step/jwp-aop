package core.di.beans.factory.aop;

public class Target {

    private final Object target;
    private final Class<?> clazz;

    public Target(Object target, Class<?> clazz) {
        this.target = target;
        this.clazz = clazz;
    }

    public Class<?> getClazz() {
        return clazz;
    }
}