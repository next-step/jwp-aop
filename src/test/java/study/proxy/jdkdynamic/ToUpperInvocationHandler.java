package study.proxy.jdkdynamic;

import com.google.common.collect.Maps;
import org.reflections.ReflectionUtils;
import study.proxy.MethodResultHelper;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

class ToUpperInvocationHandler implements InvocationHandler {

    private final Object target;
    private final Map<String, Method> methodMap = Maps.newHashMap();

    ToUpperInvocationHandler(Object target) {
        this.target = target;
        Set<Method> methodSet = ReflectionUtils.getMethods(target.getClass());
        for (Method method : methodSet) {
            methodMap.put(method.getName(), method);
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = methodMap.get(method.getName()).invoke(target, args);
        return MethodResultHelper.toUpper(method, result);
    }
}
