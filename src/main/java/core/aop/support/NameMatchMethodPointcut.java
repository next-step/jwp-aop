package core.aop.support;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.util.PatternMatchUtils;

public class NameMatchMethodPointcut extends StaticMethodMatcherPointcut {

    private List<String> mappedNames = new ArrayList<>();

    public void setMappedName(String mappedName) {
        setMappedNames(mappedName);
    }

    public void setMappedNames(String... mappedNames) {
        this.mappedNames = new ArrayList<>(Arrays.asList(mappedNames));
    }

    public NameMatchMethodPointcut addMethodName(String name) {
        this.mappedNames.add(name);
        return this;
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return mappedNames.stream()
            .anyMatch(mappedName -> mappedName.equals(method.getName()) || isMatch(method.getName(), mappedName));
    }

    private boolean isMatch(String methodName, String mappedName) {
        return PatternMatchUtils.simpleMatch(mappedName, methodName);
    }
}
