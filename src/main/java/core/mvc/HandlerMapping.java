package core.mvc;

import javax.servlet.http.HttpServletRequest;

public interface HandlerMapping {
    void initialize();

    default Object getHandler(HttpServletRequest request) {
        return null;
    }

    default Object getHandler(Throwable e) {
        return null;
    }
}
