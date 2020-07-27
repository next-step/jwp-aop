package core.mvc;

import com.google.common.collect.Maps;

import java.util.Map;

public class ControllerAdviceExceptionMapping implements ExceptionMapping {

    private Map<Class<? extends Throwable>, ExceptionHandler> handlers = Maps.newHashMap();

    @Override
    public void initialize() {
        // TODO: 2020/07/26 read ControllerAdvice and register ExceptionHandler
    }

    @Override
    public ExceptionHandler getHandler(Throwable throwable) {
        return null;
    }

}
