package core.mvc.exception;

public class ViewRenderException extends RuntimeException {
    private final String viewName;

    public ViewRenderException(Exception exception, String viewName) {
        super(exception.getMessage(), exception.getCause());
        this.viewName = viewName;
    }
}