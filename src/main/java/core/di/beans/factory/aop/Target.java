package core.di.beans.factory.aop;

public class Target {

    private final Class<?> clazz;
    private final Object target;

    public Target(Class<?> clazz, Object target) {
        this.clazz = clazz;
        this.target = target;
    }

    public Class<?> getClazz() {
        return clazz;
    }
}