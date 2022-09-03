package study.hello;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.lang.reflect.Method;

class StartNameMethodMatcher implements MethodMatcher {

    private final String startName;

    StartNameMethodMatcher(String startName) {
        Assert.hasText(startName, "'startName' must not be blank");
        this.startName = startName;
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass, Object[] args) {
        return StringUtils.startsWithIgnoreCase(method.getName(), startName);
    }
}
