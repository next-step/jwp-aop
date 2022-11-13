package core.mvc;


public interface HandlerMapping<T> {
    void initialize();

    Object getHandler(T target);
}
