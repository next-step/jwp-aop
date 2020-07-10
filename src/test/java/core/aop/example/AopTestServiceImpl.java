package core.aop.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

@Slf4j
public class AopTestServiceImpl {
    public String getUppercasedName(String name) {
        if (StringUtils.isEmpty(name)) {
            return "";
        }

        return name.toUpperCase();
    }

    public String getLowercasedName(String name) {
        if (StringUtils.isEmpty(name)) {
            return "";
        }

        return name.toLowerCase();
    }

    public String sayHello(String name) {
        log.debug("sayHello - started");
        String result = "Hello " + name;
        log.debug("sayHello - finished");
        return result;
    }

    public String pingpong(String name) {
        return "pong " + name;
    }
}
