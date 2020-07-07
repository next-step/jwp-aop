package core.aop.interceptors;

import core.annotation.Service;
import org.springframework.util.StringUtils;

@Service
public class MyTestServiceImpl implements MyTestService {
    @Override
    public String getUppercasedName(String name) {
        if (StringUtils.isEmpty(name)) {
            return "";
        }

        return name.toUpperCase();
    }
}
