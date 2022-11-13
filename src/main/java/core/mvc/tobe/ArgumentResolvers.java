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
import java.util.List;
import java.util.stream.IntStream;

public class ArgumentResolvers {

    private static final ParameterNameDiscoverer NAME_DISCOVERER = new LocalVariableTableParameterNameDiscoverer();
    private static final List<ArgumentResolver> DEFAULT_RESOLVERS = List.of(
            new HttpRequestArgumentResolver(),
            new HttpResponseArgumentResolver(),
            new RequestParamArgumentResolver(),
            new PathVariableArgumentResolver(),
            new ModelArgumentResolver()
    );

    public ArgumentResolvers(List<ArgumentResolver> resolvers) {
        this.resolvers = resolvers;
    }

    private final List<ArgumentResolver> resolvers;

    public static ArgumentResolvers getDefaultResolvers() {
        return Holder.DEFAULT;
    }

    public static ArgumentResolvers withDefault(List<ArgumentResolver>  resolvers) {
        List<ArgumentResolver> defaultResolvers = new ArrayList<>(DEFAULT_RESOLVERS);
        defaultResolvers.addAll(resolvers);
        return new ArgumentResolvers(resolvers);
    }

    public Object[] resolve(Method method, HttpServletRequest request, HttpServletResponse response) {
        return resolve(method, request, response, null);
    }

    public Object[] resolve(Method method, HttpServletRequest request, HttpServletResponse response, Throwable throwable) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        String[] parameterNames = NAME_DISCOVERER.getParameterNames(method);
        return IntStream.range(0, method.getParameterCount())
                .mapToObj(i -> new MethodParameter(method, parameterTypes[i], parameterAnnotations[i], parameterNames[i]))
                .map(methodParameter -> resolveArgument(methodParameter, request, response))
                .toArray();
    }

    private Object resolveArgument(MethodParameter methodParameter, HttpServletRequest request, HttpServletResponse response) {
        return resolvers.stream()
                .filter(resolver -> resolver.supports(methodParameter))
                .map(resolver -> resolver.resolveArgument(methodParameter, request, response))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Not found suitable resolver argument, methodParameter: " + methodParameter));
    }

    private static class Holder {
        private static final ArgumentResolvers DEFAULT = new ArgumentResolvers(DEFAULT_RESOLVERS);
    }
}
