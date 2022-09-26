package core.mvc.asis;

import core.mvc.DispatcherServlet;
import core.mvc.HandlerMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class ExceptionMapping implements HandlerMapping {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private Map<Throwable, Controller> mappings = new HashMap<>();

    @Override
    public void initialize() {
        logger.info("Initialized ExceptionMapping.");
        mappings.forEach((e, c)-> logger.info("Exception: {}, Controller : {}", e.getClass().getName(), c.getClass()));
    }

    @Override
    public Object getHandler(Throwable e) {
        return mappings.get(e);
    }
}
