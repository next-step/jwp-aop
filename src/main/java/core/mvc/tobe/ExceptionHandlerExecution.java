package core.mvc.tobe;

import core.mvc.ModelAndView;
import core.mvc.tobe.support.ArgumentResolver;
import org.springframework.core.ParameterNameDiscoverer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ExceptionHandlerExecution {
    private Object target;
    private Method method;

    public ExceptionHandlerExecution(Object target, Method method) {
        this.target = target;
        this.method = method;
    }

    public ModelAndView handle(Throwable throwable) throws Exception {
        return (ModelAndView) method.invoke(target, throwable);
    }
}
