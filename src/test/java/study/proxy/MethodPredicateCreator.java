package study.proxy;

public class MethodPredicateCreator {
    public static MethodPredicate startWithName(String name) {
        return (method, args) -> method.getName().startsWith(name);
    }
}
