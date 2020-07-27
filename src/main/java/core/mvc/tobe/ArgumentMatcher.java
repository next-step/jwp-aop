package core.mvc.tobe;

import core.mvc.tobe.support.ArgumentResolver;
import core.mvc.tobe.support.HttpRequestArgumentResolver;
import core.mvc.tobe.support.HttpResponseArgumentResolver;
import core.mvc.tobe.support.ModelArgumentResolver;
import core.mvc.tobe.support.PathVariableArgumentResolver;
import core.mvc.tobe.support.RequestParamArgumentResolver;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ArgumentMatcher {

    private static final Map<Method, MethodParameter[]> methodParameterCache = new ConcurrentHashMap<>();
    private static final ParameterNameDiscoverer nameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
    private List<ArgumentResolver> argumentResolvers;

    public ArgumentMatcher() {
        this.argumentResolvers = new ArrayList<>(
                Arrays.asList(
                        new HttpRequestArgumentResolver(),
                        new HttpResponseArgumentResolver(),
                        new RequestParamArgumentResolver(),
                        new PathVariableArgumentResolver(),
                        new ModelArgumentResolver()
                )
        );
    }

    public void addArgumentResolver(ArgumentResolver argumentResolver) {
        this.argumentResolvers.add(argumentResolver);
    }

    public MethodParameter[] getMethodParameters(Method method) {
        MethodParameter[] cachedMethodParameters = methodParameterCache.get(method);
        if (cachedMethodParameters != null) {
            return cachedMethodParameters;
        }

        MethodParameter[] methodParameters = new MethodParameter[method.getParameterCount()];
        Class<?>[] parameterTypes = method.getParameterTypes();
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();

        String[] parameterNames = nameDiscoverer.getParameterNames(method);
        for (int i = 0; i < methodParameters.length; i++) {
            methodParameters[i] = new MethodParameter(method, parameterTypes[i], parameterAnnotations[i], parameterNames[i]);
        }

        methodParameterCache.put(method, methodParameters);
        return methodParameters;
    }

    public Object resolveArgument(MethodParameter methodParameter, HttpServletRequest request, HttpServletResponse response) {
        for (ArgumentResolver resolver : argumentResolvers) {
            if (resolver.supports(methodParameter)) {
                return resolver.resolveArgument(methodParameter, request, response);
            }
        }

        throw new IllegalStateException("No suitable resolver for argument: " + methodParameter.getType());
    }

}
