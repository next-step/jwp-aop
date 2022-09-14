package core.di.beans.factory.aop;

public class Target {

    private final Object target;
    private final Class<?> type;

    public Target(Object target, Class<?> type) {
        this.target = target;
        this.type = type;
    }

    public Class<?> type() {
        return type;
    }

    public Object target() {
        return target;
    }
}
