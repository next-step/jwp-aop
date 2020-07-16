package core.mvc;

public interface ExceptionHandlerAdapter {
    boolean supports(Object handler);

    ModelAndView handle(Throwable throwable, Object handler) throws Exception;
}
