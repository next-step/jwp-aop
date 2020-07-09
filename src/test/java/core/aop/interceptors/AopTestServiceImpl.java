package core.aop.interceptors;

import org.springframework.util.StringUtils;

public class AopTestServiceImpl {
    public String getUppercasedName(String name) {
        if (StringUtils.isEmpty(name)) {
            return "";
        }

        return name.toUpperCase();
    }

    public String toLowercasedName(String name) {
        if (StringUtils.isEmpty(name)) {
            return "";
        }

        return name.toLowerCase();
    }
}
